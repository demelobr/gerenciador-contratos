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
    private String justification;
    private String observation;
    private LocalDateTime presenceDateTime;
    private LocalDateTime recordDateTime;

    public Presence(String cpfCollaborator, String nameContract, String record, String status, String justification, String observation, LocalDate presenceDate, int presenceHour, int presenceMinute){
        this.cpfCollaborator = cpfCollaborator;
        this.nameContract = nameContract;
        this.record = record;
        this.status = status;
        this.justification = justification;
        this.observation = observation;
        this.presenceDateTime = presenceDate.atTime(LocalTime.of(presenceHour, presenceMinute));
        this.recordDateTime = LocalDateTime.now();
    }

    public Presence(String cpfCollaborator, String nameContract, String record, String status, String justification, String observation, LocalDateTime presenceDateTime, LocalDateTime recordDateTime) {
        this.cpfCollaborator = cpfCollaborator;
        this.nameContract = nameContract;
        this.record = record;
        this.status = status;
        this.justification = justification;
        this.observation = observation;
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

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
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
        return String.format("<Presence>\nCpf Collaborator: %s\nName Contract: %s\nRecord: %s\nStatus: %s\nJutificativa: %s\nObservações: %s\nDateTime of Presence: %s\nDateTime of Record: %s\n==============================",
                             cpfCollaborator, nameContract, record, status, justification, observation, presenceDateTime.format(dateTimeFormatter), recordDateTime.format(dateTimeFormatter));
    }

}
