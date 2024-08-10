package org.example.gerenciadorcontratos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Collaborator {
    private String name;
    private String cpf;
    private String rg;
    private String address;
    private String telephone;
    private String email;
    private String office;
    private boolean status;
    private LocalDateTime lastPoint;
    private LocalDate admissionDate;
    private LocalDate terminationDate;
    private String photoUrl;

    public Collaborator(String name, String cpf, String rg, String address, String telephone, String email, String office, boolean status, LocalDateTime lastPoint, LocalDate admissionDate, LocalDate terminationDate, String photoUrl){
        this.name = name;
        this.cpf = cpf;
        this.rg = rg;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.office = office;
        this.status = status;
        this.lastPoint = lastPoint;
        this.admissionDate = admissionDate;
        this.terminationDate = terminationDate;
        this.photoUrl = photoUrl;
    }

    public Collaborator(String name, String cpf, String rg, String address, String telephone, String email, String office, LocalDate admissionDate, String photoUrl){
        this.name = name;
        this.cpf = cpf;
        this.rg = rg;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.office = office;
        this.status = true;
        this.lastPoint = null;
        this.admissionDate = admissionDate;
        this.terminationDate = null;
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public LocalDateTime getLastPoint() {
        return lastPoint;
    }

    public void setLastPoint(LocalDateTime lastPoint) {
        this.lastPoint = lastPoint;
    }

    @Override
    public String toString(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormatterWithTime = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        if(terminationDate != null && lastPoint != null) return String.format("<Collaborator>\nName: %s\nCpf: %s\nRg: %s\nAddress: %s\nTelephone: %s\nE-mail: %s\nOffice: %s\nStatus: %s\nLast Point: %s\nAdmission Date: %s\nTermination Date: %s\nPhoto Url: %s\n==============================",
                name, cpf, rg, address, telephone, email, office, status, lastPoint.format(dateTimeFormatterWithTime), admissionDate.format(dateTimeFormatter), terminationDate.format(dateTimeFormatter), photoUrl);
        else if(terminationDate == null && lastPoint != null) return String.format("<Collaborator>\nName: %s\nCpf: %s\nRg: %s\nAddress: %s\nTelephone: %s\nE-mail: %s\nOffice: %s\nStatus: %s\nAdmission Date: %s\nTermination Date: %s\nPhoto Url: %s\n==============================",
                name, cpf, rg, address, telephone, email, office, status, admissionDate.format(dateTimeFormatter), "NÃO INFORMADO", photoUrl);
        else if(terminationDate != null && lastPoint == null) return String.format("<Collaborator>\nName: %s\nCpf: %s\nRg: %s\nAddress: %s\nTelephone: %s\nE-mail: %s\nOffice: %s\nStatus: %s\nAdmission Date: %s\nTermination Date: %s\nPhoto Url: %s\n==============================",
                name, cpf, rg, address, telephone, email, office, status, "NÃO INFORMADO", admissionDate.format(dateTimeFormatter), terminationDate.format(dateTimeFormatter), photoUrl);
        else return String.format("<Collaborator>\nName: %s\nCpf: %s\nRg: %s\nAddress: %s\nTelephone: %s\nE-mail: %s\nOffice: %s\nStatus: %s\nAdmission Date: %s\nTermination Date: %s\nPhoto Url: %s\n==============================",
                name, cpf, rg, address, telephone, email, office, status, "NÃO INFORMADO", admissionDate.format(dateTimeFormatter), "NÃO INFORMADO", photoUrl);

    }

}
