package org.example.gerenciadorcontratos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Contract {
    private String name;
    private String description;
    private String address;
    private float budget;
    private LocalDate startDate;
    private LocalDate endDate;

    public Contract(String name, String description, String address, float budget, LocalDate startDate, LocalDate endDate){
        this.name = name;
        this.description = description;
        this.address = address;
        this.budget = budget;
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

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
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
        return String.format("<Contract>\nName: %s\nDescription: %s\nAddress: %s\nBudget: %.2f\nStart Date: %s\nEnd Date: %s\n==============================",
                             name, description, address, budget, startDate.format(dateTimeFormatter), endDate.format(dateTimeFormatter));
    }

}
