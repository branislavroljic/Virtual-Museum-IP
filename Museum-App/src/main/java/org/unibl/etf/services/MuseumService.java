package org.unibl.etf.services;

import com.itextpdf.text.DocumentException;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.models.dto.Artifact;
import org.unibl.etf.models.entities.MuseumEntity;
import org.unibl.etf.models.entities.VirtualVisitEntity;
import org.unibl.etf.models.requests.PurchaseRequest;
import org.unibl.etf.models.responses.VirtualVisitResponse;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface MuseumService {

    MuseumEntity addMuseum(MuseumEntity museum);

    void editMuseum(MuseumEntity museum);

    void deleteMuseum(Integer id);

    List<MuseumEntity> getAll();

    MuseumEntity getMuseumById(Integer id);

    List<VirtualVisitEntity> getActiveVirtualVisitsInMuseum(Integer museumId);

    List<VirtualVisitResponse> getFutureAndActiveVirtualVisitsInMuseum(Integer museumId);

    void processPurchase(Integer museumId, Integer visitId, Authentication principal, PurchaseRequest purchaseRequest) throws DocumentException, FileNotFoundException, MessagingException;

    boolean ticketExists(Integer visitId, Authentication authentication, String ticketNumber);


}
