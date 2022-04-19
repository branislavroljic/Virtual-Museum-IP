package org.unibl.etf.services.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.unibl.etf.exceptions.HttpException;
import org.unibl.etf.models.dto.Report;
import org.unibl.etf.models.entities.LogEntity;
import org.unibl.etf.repositories.LogEntityRepository;
import org.unibl.etf.services.LogService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    private static final Font trBold15 = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, new BaseColor(0, 0, 0));
    private static final Font tr12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
    private static final Path logsPDFPath = Paths.get("logs.pdf");

    private final  LogEntityRepository logEntityRepository;

    public LogServiceImpl(LogEntityRepository logEntityRepository) {
        this.logEntityRepository = logEntityRepository;
    }

    @Override
    public List<Report> selectLoginsByHourFromLast24Hour() {
       return logEntityRepository.selectLoginsByHourFromLast24Hour();
    }

    @Override
    public List<LogEntity> getAll() {
        return logEntityRepository.findByOrderByDateTimeDesc();
    }

    @Override
    public Resource downloadLogsAsPDF() throws DocumentException, IOException {
        saveLogsToPDF(logsPDFPath.toFile());
        Resource resource = new UrlResource(logsPDFPath.toUri());
        if(resource.exists() || resource.isReadable())
            return resource;
        else throw new HttpException("Download failed!");
    }

    @Override
    public void log(String username, String type, String message, Timestamp dateTime) {
        logEntityRepository.save(new LogEntity(username, type, message, dateTime));
    }

    private void saveLogsToPDF(File pdfFile) throws IOException, DocumentException {
        if(pdfFile.exists())
            FileUtils.delete(pdfFile);

        Document document = new Document();

        OutputStream os = new FileOutputStream(pdfFile);
        PdfWriter.getInstance(document, os);
        document.open();

        float [] pointColumnWidths = {150F, 150F, 250F, 150F};
        PdfPTable table = new PdfPTable(pointColumnWidths);
        table.setWidthPercentage(80f);

        insertCell(table, "Type", trBold15);
        insertCell(table, "User", trBold15);
        insertCell(table, "Message", trBold15);
        insertCell(table, "Date and time", trBold15);
        //repeat header in next page
        table.setHeaderRows(1);

        for(LogEntity log : logEntityRepository.findAll()){
            insertCell(table, log.getType(), tr12);
            insertCell(table, log.getUsername(), tr12);
            insertCell(table, log.getMessage(), tr12);
            insertCell(table, log.getDateTime().toString(), tr12);
        }

        document.add(table);

        document.close();
        os.close();

        System.out.println("Created PDF: " + pdfFile.getAbsolutePath());
    }

    private void insertCell(PdfPTable table, String text, Font font){
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        table.addCell(cell);
    }

}
