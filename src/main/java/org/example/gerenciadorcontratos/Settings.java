package org.example.gerenciadorcontratos;

import java.io.Serializable;

public class Settings implements Serializable {
    private static final long serialVersionID = 1L;
    private String urlDB;
    private String userDB;
    private String passwordDB;
    private String hostEmail;
    private String portEmail;
    private String addressEmail;
    private String passwordEmail;

    public Settings(String urlDB, String userDB, String passwordDB, String hostEmail, String portEmail, String addressEmail, String passwordEmail){
        this.urlDB = urlDB;
        this.userDB = userDB;
        this.passwordDB = passwordDB;
        this.hostEmail = hostEmail;
        this.portEmail = portEmail;
        this.addressEmail = addressEmail;
        this.passwordEmail = passwordEmail;
    }

    public String getUrlDB() {
        return urlDB;
    }

    public void setUrlDB(String urlDB) {
        this.urlDB = urlDB;
    }

    public String getUserDB() {
        return userDB;
    }

    public void setUserDB(String userDB) {
        this.userDB = userDB;
    }

    public String getPasswordDB() {
        return passwordDB;
    }

    public void setPasswordDB(String passwordDB) {
        this.passwordDB = passwordDB;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public String getPortEmail() {
        return portEmail;
    }

    public void setPortEmail(String portEmail) {
        this.portEmail = portEmail;
    }

    public String getAddressEmail() {
        return addressEmail;
    }

    public void setAddressEmail(String addressEmail) {
        this.addressEmail = addressEmail;
    }

    public String getPasswordEmail() {
        return passwordEmail;
    }

    public void setPasswordEmail(String passwordEmail) {
        this.passwordEmail = passwordEmail;
    }

    public String toString(){
        return String.format("<Settings>\nUrl DB: %s\nUser DB: %s\nPassword: %s\n" +
                "Host email: %s\nPort email: %s\nAddress email: %s\nPassword email: %s\n" +
                "==============================", urlDB, userDB, passwordDB, hostEmail, portEmail, addressEmail, passwordEmail);
    }
}
