package org.unibl.etf.services.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.jni.Local;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.exceptions.HttpException;
import org.unibl.etf.exceptions.NotFoundException;
import org.unibl.etf.models.EmailTask;
import org.unibl.etf.models.dto.Artifact;
import org.unibl.etf.models.entities.MuseumEntity;
import org.unibl.etf.models.entities.TicketEntity;
import org.unibl.etf.models.entities.UserEntity;
import org.unibl.etf.models.entities.VirtualVisitEntity;
import org.unibl.etf.models.requests.PurchaseRequest;
import org.unibl.etf.models.responses.VirtualVisitResponse;
import org.unibl.etf.repositories.*;
import org.unibl.etf.services.MailService;
import org.unibl.etf.services.MuseumService;

import javax.mail.MessagingException;
import java.io.*;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MuseumServiceImpl implements MuseumService {


    private final ModelMapper modelMapper;
    private final TicketRepository ticketRepository;
    private final MuseumEntityRepository museumEntityRepository;
    private final VirtualVisitEntityRepository virtualVisitEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final MailService mailService;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private static final String TICKET_FOLDER = "tickets";


    public MuseumServiceImpl(ModelMapper modelMapper, TicketRepository ticketRepository,
                             MuseumEntityRepository museumEntityRepository,
                             VirtualVisitEntityRepository virtualVisitEntityRepository,
                             UserEntityRepository userEntityRepository, MailService mailService,
                             ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.modelMapper = modelMapper;
        this.ticketRepository = ticketRepository;
        this.museumEntityRepository = museumEntityRepository;
        this.virtualVisitEntityRepository = virtualVisitEntityRepository;
        this.userEntityRepository = userEntityRepository;
        this.mailService = mailService;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    @Override
    public MuseumEntity addMuseum(MuseumEntity museum) {
        return museumEntityRepository.save(museum);
    }

    @Override
    public void editMuseum(MuseumEntity museum) {
        if (!museumEntityRepository.existsById(museum.getId()))
            throw new NotFoundException();
        museumEntityRepository.save(museum);
    }

    @Override
    public void deleteMuseum(Integer id) {
        if (!museumEntityRepository.existsById(id))
            throw new NotFoundException();
        museumEntityRepository.deleteById(id);
    }

    @Override
    public List<MuseumEntity> getAll() {
        return museumEntityRepository.findAll();
    }

    @Override
    public MuseumEntity getMuseumById(Integer id) {
        return museumEntityRepository.findById(id).orElseThrow(() -> new NotFoundException("Museum not found!"));
    }


    @Override
    public List<VirtualVisitEntity> getActiveVirtualVisitsInMuseum(Integer museumId) {
        Date currentDate = new Date(new java.util.Date().getTime());
        List<VirtualVisitEntity> visits =
                virtualVisitEntityRepository.findAllByMuseum_IdAndDateIsGreaterThanEqual(museumId, currentDate);
        Time currentTimePlusOneHour = Time.valueOf(LocalTime.now().plusHours(1));
        return visits.stream().filter(v -> v.getDate().after(currentDate) || v.getDate().equals(currentDate) && v.getStartTime().after(currentTimePlusOneHour)).collect(Collectors.toList());
    }

    @Override
    public List<VirtualVisitResponse> getFutureAndActiveVirtualVisitsInMuseum(Integer museumId) {


        LocalDateTime now = LocalDateTime.now();
        Date yesterdayDate =
                new Date(java.util.Date.from(now.minusDays(1).atZone(ZoneId.systemDefault()).toInstant()).getTime());
        List<VirtualVisitResponse> retVisits;
        List<VirtualVisitResponse> finishedVisits = new ArrayList<>();
        LocalTime currentTime = LocalTime.now();
        retVisits =
                virtualVisitEntityRepository.findAllByMuseum_IdAndDateIsGreaterThanEqual(museumId, yesterdayDate).stream().map(visit -> modelMapper.map(visit, VirtualVisitResponse.class)).peek(v -> {
                    LocalDateTime startDateTime = LocalDateTime.of(v.getDate().toLocalDate(),
                            v.getStartTime().toLocalTime());
                    LocalDateTime endDateTime =
                            LocalDateTime.of(v.getDate().toLocalDate(), v.getStartTime().toLocalTime()).plusMinutes(v.getDuration());

                    if (startDateTime.isBefore(now) && endDateTime.isAfter(now))
                        v.setActive(true);
                    else if (startDateTime.isBefore(now))
                        finishedVisits.add(v);
                    v.setMuseumId(museumId);
                }).collect(Collectors.toList());
        retVisits.removeAll(finishedVisits);
        return retVisits;
    }

    @Override
    public void processPurchase(Integer museumId, Integer visitId, Authentication principal,
                                PurchaseRequest purchaseRequest) throws DocumentException, FileNotFoundException,
            MessagingException {
        UserEntity user = userEntityRepository.findUserEntityByUsername(principal.getName()).get();
        MuseumEntity museum = museumEntityRepository.getById(museumId);
        VirtualVisitEntity virtualVisit = virtualVisitEntityRepository.getById(visitId);

        String ticketNumber = "" + new java.util.Date().getTime();
        String pdfContent = getPDFContent(user, museum, virtualVisit, ticketNumber, purchaseRequest);

        createPDF(pdfContent, ticketNumber);

        mailService.sendTicket(user.getEmail(), TICKET_FOLDER + File.separator + ticketNumber + ".pdf");

        ticketRepository.save(new TicketEntity(ticketNumber, virtualVisit, user));

        LocalDateTime anHourBeforeVisitStart = LocalDateTime.of(virtualVisit.getDate().toLocalDate(),
                virtualVisit.getStartTime().toLocalTime().minusHours(1));

        LocalDateTime fiveMinutesBeforeVisitEnd = LocalDateTime.of(virtualVisit.getDate().toLocalDate(),
                virtualVisit.getStartTime().toLocalTime()).plusMinutes(virtualVisit.getDuration() - 5);

        scheduleMailTask(user.getEmail(), pdfContent, "Virtual visit starts in 1 hour!", anHourBeforeVisitStart);
        scheduleMailTask(user.getEmail(), pdfContent, "Virtual visit ends in 5 minutes!", fiveMinutesBeforeVisitEnd);
    }

    @Override
    public boolean ticketExists(Integer visitId, Authentication principal, String ticketNumber) {
        return ticketRepository.existsTicketEntityByVirtualVisitIdAndUserIdAndNumber(visitId,
                userEntityRepository.findUserEntityByUsername(principal.getName()).get().getId(), ticketNumber);
    }


    private void createPDF(String pdfContent, String ticketNumber) throws FileNotFoundException, DocumentException {
        if (!new File(TICKET_FOLDER).exists()) {
            new File(TICKET_FOLDER).mkdir();
        }

        System.out.println(new File(TICKET_FOLDER).getAbsolutePath());
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(TICKET_FOLDER + File.separator + ticketNumber + ".pdf"));

        document.open();
        document.add(new Paragraph(pdfContent));
        document.close();
    }

    private String getPDFContent(UserEntity user, MuseumEntity museum, VirtualVisitEntity virtualVisit,
                                 String ticketNumber, PurchaseRequest purchaseRequest) {
        StringBuilder sb = new StringBuilder();

        sb.append("Ticket number: " + ticketNumber).append("\r\n");
        ;
        sb.append("Client: " + user.getName() + " " + user.getSurname()).append("\r\n");
        ;
        sb.append("Museum: " + museum.getName()).append("\r\n");
        ;
        sb.append("Address: " + museum.getAddress()).append("\r\n");
        ;

        SimpleDateFormat formatter = new SimpleDateFormat(" 'Date:' dd-MM-yyyy 'Time:' HH:mm ");
        sb.append("Time of purchase: " + formatter.format(new java.util.Date())).append("\r\n");
        ;
        sb.append("Price: " + purchaseRequest.getRequestedAmount()).append("\r\n");
        ;

        java.util.Date date = new java.util.Date(virtualVisit.getDate().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'Date:' dd-MM-yyyy");
        sb.append("Virtual visit starts at: " + dateFormat.format(date) + "  Time: " + virtualVisit.getStartTime()).append("\r\n");
        ;

        LocalTime localTime = virtualVisit.getStartTime().toLocalTime();
        localTime = localTime.plusMinutes(virtualVisit.getDuration());
        sb.append("Virtual visit ends at: " + localTime.toString());

        return sb.toString();
    }

    private void scheduleMailTask(String to, String ticket, String notification, LocalDateTime time) {
        threadPoolTaskScheduler.schedule(new EmailTask(to, ticket, notification, mailService),
                time.atZone(ZoneId.systemDefault()).toInstant());
    }
}
