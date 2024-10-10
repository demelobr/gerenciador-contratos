package org.example.gerenciadorcontratos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Contract {
    private String name;
    private String description;
    private String address;
    private String engineer;
    private String contractFile;
    private LocalDate expectedStartDate;
    private LocalDate expectedEndDate;
    private LocalDate startDate;
    private LocalDate endDate;

    public Contract(String name, String description, String address, String engineer, String contractFile, LocalDate expectedStartDate, LocalDate expectedEndDate, LocalDate startDate, LocalDate endDate){
        this.name = name;
        this.description = description;
        this.address = address;
        this.engineer = engineer;
        this.contractFile = contractFile;
        this.expectedStartDate = expectedStartDate;
        this.expectedEndDate = expectedEndDate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public String getContractFile() {
        return contractFile;
    }

    public void setContractFile(String contractFile) {
        this.contractFile = contractFile;
    }

    public LocalDate getExpectedStartDate() {
        return expectedStartDate;
    }

    public void setExpectedStartDate(LocalDate expectedStartDate) {
        this.expectedStartDate = expectedStartDate;
    }

    public LocalDate getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(LocalDate expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("<Contract>\nName: %s\nDescription: %s\nAddress: %s\nEngineer: %s\nContract File: %s\nExpected Start Date: %s\nExpected End Date: %s\nStart Date: %s\nEnd Date: %s\n==============================",
                             name, description, address, engineer, contractFile, expectedStartDate.format(dateTimeFormatter), expectedEndDate.format(dateTimeFormatter), startDate.format(dateTimeFormatter), endDate.format(dateTimeFormatter));
    }
}
