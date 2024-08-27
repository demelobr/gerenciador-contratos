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

import static org.example.gerenciadorcontratos.UtilitiesLibrary.getQueryOfSearch;

public class FinanceReportGenerator {
    public Application app;

    public FinanceReportGenerator() {
        this.app = new Application();
    }

    public void reportGenarator(String type, double entries, double expenses, double balance, LocalDate startDateTime, LocalDate endDateTime, String nameContract, String minValue, String maxValue){
        Connection conn = null;
        String logo = "src/main/resources/org/example/gerenciadorcontratos/logo.png";
        String reportPath = "";
        String sql = "";

        JasperDesign jasperDesign = null;
        JasperReport jasperReport = null;
        JasperPrint jasperPrint = null;
        InputStream fileIn = null;

        Map<String, Object> parameters = new HashMap<>();

        DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if(type.equals("complete")){
            reportPath = "src/main/resources/org/example/gerenciadorcontratos/balance-finances-complete.jrxml";
            sql = getQueryOfSearch(false, true, "", "", nameContract, startDateTime, endDateTime,minValue, maxValue, "finances");
            sql = sql + " ORDER BY STR_TO_DATE(finances.`date`, '%d/%m/%Y');";
            parameters.put("balance", balance);
            parameters.put("entries", entries);
            parameters.put("expenses", expenses);
        }else if(type.equals("entries")){
            reportPath = "src/main/resources/org/example/gerenciadorcontratos/balance-finances-entries.jrxml";
            sql = getQueryOfSearch(false, false, "", "", nameContract, startDateTime, endDateTime,minValue, maxValue, "finances");
            sql = sql + " ORDER BY STR_TO_DATE(finances.`date`, '%d/%m/%Y');";
            parameters.put("entries", entries);
            parameters.put("queryFinanceType", "RECEITA");
        }else if(type.equals("expenses")){
            reportPath = "src/main/resources/org/example/gerenciadorcontratos/balance-finances-expenses.jrxml";
            sql = getQueryOfSearch(false, false, "", "", nameContract, startDateTime, endDateTime,minValue, maxValue, "finances");
            sql = sql + " ORDER BY STR_TO_DATE(finances.`date`, '%d/%m/%Y');";
            parameters.put("expenses", expenses);
            parameters.put("queryFinanceType", "DESPESA");
            System.out.println(sql);
        }

        try {
            conn = DriverManager.getConnection(app.getServer().getSettings().getUrlDB(), app.getServer().getSettings().getUserDB(), app.getServer().getSettings().getPasswordDB());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        parameters.put("logo", logo);
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
    }

}
