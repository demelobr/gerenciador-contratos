package org.example.gerenciadorcontratos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Finance {
    private String title;
    private String notes;
    private String contractName;
    private String type;
    private String financeClass;
    private String paymentMethod;
    private LocalDate date;
    private LocalDateTime recordDateTime;
    private double value;
    private String collaboratorCpf;

    public Finance(String title, String notes, String contractName, String type, String financeClass, String paymentMethod, LocalDate date, LocalDateTime recordDateTime, double value, String collaboratorCpf){
        this.title = title;
        this.notes = notes;
        this.contractName = contractName;
        this.type = type;
        this.financeClass = financeClass;
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.recordDateTime = recordDateTime;
        this.value = value;
        this.collaboratorCpf = collaboratorCpf;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFinanceClass() {
        return financeClass;
    }

    public void setFinanceClass(String financeClass) {
        this.financeClass = financeClass;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getRecordDateTime() {
        return recordDateTime;
    }

    public void setRecordDateTime(LocalDateTime recordDateTime) {
        this.recordDateTime = recordDateTime;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCollaboratorCpf() {
        return collaboratorCpf;
    }

    public void setCollaboratorCpf(String collaboratorCpf) {
        this.collaboratorCpf = collaboratorCpf;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        return String.format("<Finance>\nTítulo: %s\nNotas: %s\nNome do Contrato: %s\nTipo: %s\nClasse: %s\nData da Finança: %s\nData/Hora do registro: %s\nValor(R$): %s\nCpf do colaborador: %s",
                             title, notes, contractName, type, financeClass, paymentMethod, date.format(dateTimeFormatter), recordDateTime.format(dateTimeFormatterWithSeconds), value, collaboratorCpf);
    }

}
