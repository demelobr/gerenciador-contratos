package org.example.gerenciadorcontratos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Presence {
    private String cpfCollaborator;
    private String nameContract;
    private String record;
    private String status;
    private LocalDateTime presenceDateTime;
    private LocalDateTime recordDateTime;

    public Presence(String cpfCollaborator, String nameContract, String record, String status, LocalDate presenceDate, int presenceHour, int presenceMinute){
        this.cpfCollaborator = cpfCollaborator;
        this.nameContract = nameContract;
        this.record = record;
        this.status = status;
        this.presenceDateTime = presenceDate.atTime(LocalTime.of(presenceHour, presenceMinute));
        this.recordDateTime = LocalDateTime.now();
    }

    public Presence(String cpfCollaborator, String nameContract, String record, String status, LocalDateTime presenceDateTime, LocalDateTime recordDateTime) {
        this.cpfCollaborator = cpfCollaborator;
        this.nameContract = nameContract;
        this.record = record;
        this.status = status;
        this.presenceDateTime = presenceDateTime;
        this.recordDateTime = recordDateTime;
    }

    public String getCpfCollaborator() {
        return cpfCollaborator;
    }

    public void setCpfCollaborator(String cpfCollaborator) {
        this.cpfCollaborator = cpfCollaborator;
    }

    public String getNameContract() {
        return nameContract;
    }

    public void setNameContract(String nameContract) {
        this.nameContract = nameContract;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPresenceDateTime() {
        return presenceDateTime;
    }

    public void setPresenceDateTime(LocalDateTime presenceDateTime) {
        this.presenceDateTime = presenceDateTime;
    }

    public LocalDateTime getRecordDateTime() {
        return recordDateTime;
    }

    public void setRecordDateTime(LocalDateTime recordDateTime) {
        this.recordDateTime = recordDateTime;
    }

    @Override
    public String toString(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        return String.format("<Presence>\nCpf Collaborator: %s\nName Contract: %s\nRecord: %s\nStatus: %s\nDateTime of Presence: %s\nDateTime of Record: %s\n==============================",
                             cpfCollaborator, nameContract, record, status, presenceDateTime.format(dateTimeFormatter), recordDateTime.format(dateTimeFormatter));
    }

}
