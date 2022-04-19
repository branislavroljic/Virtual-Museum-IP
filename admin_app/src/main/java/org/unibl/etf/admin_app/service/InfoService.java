package org.unibl.etf.admin_app.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.unibl.etf.admin_app.beans.LogBean;
import org.unibl.etf.admin_app.beans.ReportBean;
import org.unibl.etf.admin_app.dao.LogDAO;
import org.unibl.etf.admin_app.dao.UserDAO;

import java.io.*;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class InfoService implements Serializable {

   private static final Font trBold15 = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, new BaseColor(0, 0, 0));
   private static final Font tr12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);

    public List<LogBean> getLogs() {
        try {
            return LogDAO.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<ReportBean> getLoginsByHourFromLast24Hour() {
        try {
            return LogDAO.selectLoginsByHourFromLast24Hour();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void saveLogsToPDF(File pdfFile) throws IOException, DocumentException {
        if(pdfFile.exists())
            pdfFile.delete();

        Document document = new Document();

        OutputStream os = new FileOutputStream(pdfFile);
        PdfWriter.getInstance(document, os);
        document.open();

        float [] pointColumnWidths = {150F, 150F, 150F};
        PdfPTable table = new PdfPTable(pointColumnWidths);
        table.setWidthPercentage(50f);

        insertCell(table, "Type", trBold15);
        insertCell(table, "Message", trBold15);
        insertCell(table, "Date and time", trBold15);
        //repeat header in next page
        table.setHeaderRows(1);

        for(LogBean logBean : getLogs()){
            insertCell(table, logBean.getType(), tr12);
            insertCell(table, logBean.getMessage(), tr12);
            insertCell(table, logBean.getDateTime(), tr12);
        }

        document.add(table);

        document.close();
        os.close();

        System.out.println("Created PDF: " + pdfFile.getAbsolutePath());
    }

    public Integer countLoggedInUsers(){
        try {
            return UserDAO.countLoggedInUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Integer countRegisteredUsers(){
        try {
            return UserDAO.countRegisteredUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void insertCell(PdfPTable table, String text, Font font){
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        table.addCell(cell);
    }
}
