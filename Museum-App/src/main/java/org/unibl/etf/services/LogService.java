package org.unibl.etf.services;

import com.itextpdf.text.DocumentException;
import org.springframework.core.io.Resource;
import org.unibl.etf.models.dto.Report;
import org.unibl.etf.models.entities.LogEntity;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public interface LogService {

    List<Report> selectLoginsByHourFromLast24Hour();

    List<LogEntity> getAll();

    Resource downloadLogsAsPDF() throws DocumentException, IOException;

    void log(String username, String type, String message, Timestamp dateTime);
}
