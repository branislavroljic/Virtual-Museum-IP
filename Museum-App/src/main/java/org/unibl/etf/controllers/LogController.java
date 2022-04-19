package org.unibl.etf.controllers;

import com.itextpdf.text.DocumentException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.models.entities.LogEntity;
import org.unibl.etf.services.LogService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping
    public ResponseEntity<List<LogEntity>> getAllLogs(Authentication principal) {
        logService.log(principal.getName(), "LOGS", "User requested logs!", new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(logService.getAll());
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadLogsAsPDF(Authentication principal) throws DocumentException, IOException {
        Resource logsPDF = logService.downloadLogsAsPDF();
        Path logsPDFPath = logsPDF.getFile().toPath();
        logService.log(principal.getName(), "LOGS_DOWNLOAD", "User requested logs download!",
                new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(logsPDFPath))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + logsPDF.getFilename() + "\"")
                .body(logsPDF);
    }
}
