package org.example.gerenciadorcontratos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private String email;
    private String name;
    private String password;
    private String verificationCode;
    private LocalDateTime codeDateTime;

    public User(String email, String name, String password){
        this.email = email;
        this.name = name;
        this.password = password;
        this.verificationCode = "";
        this.codeDateTime = LocalDateTime.now();
    }

    public User(String email, String name, String password, String verificationCode, LocalDateTime codeDateTime) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.verificationCode = verificationCode;
        this.codeDateTime = codeDateTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public LocalDateTime getCodeDateTime() {
        return codeDateTime;
    }

    public void setCodeDateTime(LocalDateTime codeDateTime) {
        this.codeDateTime = codeDateTime;
    }

    @Override
    public String toString(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        return String.format("<User>\nName: %s\nE-mail: %s\nPassword: %s\nVerification Code: %s\nIs valid code? %s\n==============================",
                            name, email, password, verificationCode, codeDateTime.format(dateTimeFormatter));
    }

}
