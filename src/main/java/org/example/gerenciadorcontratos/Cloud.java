package org.example.gerenciadorcontratos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cloud implements Serializable {
    private static final long serialVersionID = 2L;
    private String folderPath;
    private String periodicity;
    private LocalDateTime lastManualUpdate;
    private LocalDateTime lastAutomaticUpdate;

    public Cloud(String folderPath, String periodicity){
        this.folderPath = folderPath;
        this.periodicity = periodicity;
        this.lastManualUpdate = null;
        this.lastAutomaticUpdate = null;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public LocalDateTime getLastManualUpdate() {
        return lastManualUpdate;
    }

    public void setLastManualUpdate(LocalDateTime lastManualUpdate) {
        this.lastManualUpdate = lastManualUpdate;
    }

    public LocalDateTime getLastAutomaticUpdate() {
        return lastAutomaticUpdate;
    }

    public void setLastAutomaticUpdate(LocalDateTime lastAutomaticUpdate) {
        this.lastAutomaticUpdate = lastAutomaticUpdate;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        if(lastManualUpdate != null && lastAutomaticUpdate != null) return String.format("<Cloud>\nPasta de Upload: %s\nPeriodicidade: %s\nÚltimo Upload Automático: %s\nÚltimo Upload Manual: %s", folderPath, periodicity, lastAutomaticUpdate.format(dateTimeFormatter), lastManualUpdate.format(dateTimeFormatter));
        else{
            if(lastManualUpdate != null) return String.format("<Cloud>\nPasta de Upload: %s\nPeriodicidade: %s\nÚltimo Upload Automático: %s\nÚltimo Upload Manual: %s", folderPath, periodicity, "", lastManualUpdate.format(dateTimeFormatter));
            if(lastAutomaticUpdate != null) return String.format("<Cloud>\nPasta de Upload: %s\nPeriodicidade: %s\nÚltimo Upload Automático: %s\nÚltimo Upload Manual: %s", folderPath, periodicity, lastAutomaticUpdate.format(dateTimeFormatter), "");
        }
        return "";
    }

}
