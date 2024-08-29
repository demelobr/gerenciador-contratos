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

    public void reportGenerator(Collaborator collaborator, String record, String status, String nameContract, LocalDate startDateTime, LocalDate endDateTime, String minValue, String maxValue, String typeOfReport){
        Connection conn = null;
        String logo = "src/main/resources/org/example/gerenciadorcontratos/logo.png";
        String reportPath;
        String name;
        String sql;

        JasperDesign jasperDesign = null;
        JasperReport jasperReport = null;
        JasperPrint jasperPrint = null;
        InputStream fileIn = null;

        Map<String, Object> parameters = new HashMap<>();

        DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if(typeOfReport.equals("data")){
            System.out.println("Data Report generate");
            try {
                conn = DriverManager.getConnection(app.getServer().getSettings().getUrlDB(), app.getServer().getSettings().getUserDB(), app.getServer().getSettings().getPasswordDB());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            reportPath = "src/main/resources/org/example/gerenciadorcontratos/collaborator-data.jrxml";

            parameters.put("logo", logo);
            parameters.put("photo", collaborator.getPhotoUrl());
            parameters.put("name", capitalizeWords(collaborator.getName()));
            parameters.put("office", capitalizeWords(collaborator.getOffice()));
            parameters.put("cpf", collaborator.getCpf());
            parameters.put("rg", collaborator.getRg());
            parameters.put("address", capitalizeWords(collaborator.getAddress()));
            parameters.put("telephone", collaborator.getTelephone());
            parameters.put("email", capitalizeWords(collaborator.getEmail()));
            parameters.put("admissionDate", collaborator.getAdmissionDate().format(dateFormatter));
            if(collaborator.getTerminationDate() == null) parameters.put("terminationDate", "Não Informado");
            else parameters.put("terminationDate", collaborator.getTerminationDate().format(dateFormatter));
            parameters.put("lastPoint", collaborator.getLastPoint().format(dateTimeFormatterWithSeconds));
            String statusOfCollaborator = collaborator.isStatus() ? "Ativo" : "Inativo";
            parameters.put("status", statusOfCollaborator);

            try{
                fileIn = new FileInputStream(reportPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try{
                jasperDesign = JRXmlLoader.load(fileIn);
            } catch (JRException e) {
                e.printStackTrace();
            }

            try{
                jasperReport = JasperCompileManager.compileReport(jasperDesign);
            } catch (JRException e) {
                e.printStackTrace();
            }

            try{
                jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            } catch (JRException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            JasperViewer.viewReport(jasperPrint, false);

        }else if(typeOfReport.equals("finances")){
            try {
                conn = DriverManager.getConnection(app.getServer().getSettings().getUrlDB(), app.getServer().getSettings().getUserDB(), app.getServer().getSettings().getPasswordDB());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
//            reportPath = "src/main/resources/org/example/gerenciadorcontratos/collaborator-finances.jrxml";
            reportPath = "src/main/resources/org/example/gerenciadorcontratos/collaborator-finances-teste.jrxml";
            name = collaborator.getName();
            sql = getQueryOfSearch(true, true, "", "", nameContract, startDateTime, endDateTime,minValue, maxValue, "finances");
            sql = sql + " ORDER BY STR_TO_DATE(finances.`date`, '%d/%m/%Y');";

            parameters.put("logo", logo);
            parameters.put("name", capitalizeWords(name));
            parameters.put("queryCpfCollaborator", collaborator.getCpf());
            if(!nameContract.equals("----------")) parameters.put("queryNameContract", nameContract);
            if(startDateTime != null) parameters.put("queryStartDateTime", startDateTime.format(dateFormatter));
            if(endDateTime != null) parameters.put("queryEndDateTime", endDateTime.format(dateFormatter));
            if(!minValue.equals("")) parameters.put("queryMinValue", minValue);
            if(!maxValue.equals("")) parameters.put("queryMaxValue", maxValue);

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
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            JasperViewer.viewReport(jasperPrint, false);

        }else if(typeOfReport.equals("presences")){
            try {
                conn = DriverManager.getConnection(app.getServer().getSettings().getUrlDB(), app.getServer().getSettings().getUserDB(), app.getServer().getSettings().getPasswordDB());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
//            reportPath = "src/main/resources/org/example/gerenciadorcontratos/collaborator-presences.jrxml";
            reportPath = "src/main/resources/org/example/gerenciadorcontratos/collaborator-presences-teste.jrxml";
            name = collaborator.getName();
            sql = getQueryOfSearch(true, true, record, status, nameContract, startDateTime, endDateTime, "", "", "presences");
            sql = sql + " ORDER BY STR_TO_DATE(presences.`presenceDateTime`, '%d/%m/%Y - %H:%i');";

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
