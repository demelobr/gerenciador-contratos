package org.example.gerenciadorcontratos;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.example.gerenciadorcontratos.UtilitiesLibrary.capitalizeWords;
import static org.example.gerenciadorcontratos.UtilitiesLibrary.getQueryOfSearch;

public class CollaboratorReportGenerator {
    public Application app;

    public CollaboratorReportGenerator() {
        this.app = new Application();
    }

    public void reportGenerator(Collaborator collaborator, String record, String status, String nameContract, LocalDate startDateTime, LocalDate endDateTime, String typeOfReport){
        Connection conn = null;
        String logo = "src/main/resources/org/example/gerenciadorcontratos/logo.png";
        DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

        switch (typeOfReport){
            case "complete":
            case "finances":
            case "presences":
                try {
                    conn = DriverManager.getConnection(app.getServer().getSettings().getUrlDB(), app.getServer().getSettings().getUserDB(), app.getServer().getSettings().getPasswordDB());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                String reportPath = "src/main/resources/org/example/gerenciadorcontratos/collaborator-presences.jrxml";
                String name = collaborator.getName();
                String sql = getQueryOfSearch(record, status, nameContract, startDateTime, endDateTime, "", "", "presences");
                sql = sql + " ORDER BY STR_TO_DATE(presences.`presenceDateTime`, '%d/%m/%Y - %H:%i')";

                JasperDesign jasperDesign = null;
                JasperReport jasperReport = null;
                JasperPrint jasperPrint = null;
                InputStream fileIn = null;

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("logo", logo);
                parameters.put("name", capitalizeWords(name));
                parameters.put("queryCpfCollaborator", collaborator.getCpf());
                if(!record.equals("----------")) parameters.put("queryRecord", record);
                if(!status.equals("----------")) parameters.put("queryStatus", status);
                if(!nameContract.equals("----------")) parameters.put("queryNameContract", nameContract);
                if(startDateTime != null) parameters.put("queryStartDateTime", startDateTime.atTime(0,0,0).format(dateTimeFormatterWithSeconds));
                if(endDateTime != null) parameters.put("queryEndDateTime", endDateTime.atTime(23,59,59).format(dateTimeFormatterWithSeconds));

                try{
                    fileIn = new FileInputStream(reportPath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                try{
                    jasperDesign = JRXmlLoader.load(fileIn);
                    JRDesignQuery query = new JRDesignQuery();
                    query.setText(sql);
                    jasperDesign.setQuery(query);
                } catch (JRException e) {
                    e.printStackTrace();
                }

                try{
                    jasperReport = JasperCompileManager.compileReport(jasperDesign);
                } catch (JRException e) {
                    System.out.println("jasperReport" + e.getMessage());
                    e.printStackTrace();
                }

                try{
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
                } catch (JRException e) {
                    e.printStackTrace();
                }

                JasperViewer.viewReport(jasperPrint, false);
        }
    }

}
