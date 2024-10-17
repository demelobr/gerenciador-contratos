package org.example.gerenciadorcontratos;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class RecoverAccountNewPasswordScreenController implements Initializable {
    private Application app;
    private String email;

    public RecoverAccountNewPasswordScreenController() {
        this.app = new Application();
    }

    @FXML
    private HBox btnBackRecoverAccountNewPasswordWindow;

    @FXML
    private HBox btnClosePushMsgRecoverAccountNewPasswordWindow;

    @FXML
    private Button btnRecoverAccountNewPasswordWindow;

    @FXML
    private HBox hbPushMsgRecoverAccountNewPasswordWindow;

    @FXML
    private Label lbPushMsgRecoverAccountNewPasswordWindow;

    @FXML
    private PasswordField tfRecoverAccountNewPasswordWindow;

    @FXML
    private PasswordField tfRecoverAccountRepeatNewPasswordWindow;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @FXML
    void closePushMsg(MouseEvent event) {
        hbPushMsgRecoverAccountNewPasswordWindow.setVisible(false);
    }

    @FXML
    public void backToEmailSendScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.changeScreen("recover-account-email-screen.fxml", "Gerenciador de Contratos - Recuperar Conta");
    }

    @FXML
    public void updateUserPassword(){
        String password = tfRecoverAccountNewPasswordWindow.getText();
        String repeatedPassword = tfRecoverAccountRepeatNewPasswordWindow.getText();
        try {
            User user = app.getServer().getUserByEmail(this.email);
            if(!password.isEmpty() || !repeatedPassword.isEmpty()){
                if(password.equals(repeatedPassword)){
                    if(password.length() >= 8){
                        app.getServer().updateUser(user, user.getEmail(), user.getName(), password, "", LocalDateTime.now());
                    }else{
                        lbPushMsgRecoverAccountNewPasswordWindow.setText("A senha precisa de ao menos 8 dítigos!");
                        hbPushMsgRecoverAccountNewPasswordWindow.getStyleClass().setAll("push-msg-error");
                        hbPushMsgRecoverAccountNewPasswordWindow.setVisible(true);
                        this.delayHidePushMsg();
                    }
                }else{
                    lbPushMsgRecoverAccountNewPasswordWindow.setText("As senhas precisam ser iguais!");
                    hbPushMsgRecoverAccountNewPasswordWindow.getStyleClass().setAll("push-msg-error");
                    hbPushMsgRecoverAccountNewPasswordWindow.setVisible(true);
                    this.delayHidePushMsg();
                }
            }else{
                lbPushMsgRecoverAccountNewPasswordWindow.setText("Preencha todos os campos!");
                hbPushMsgRecoverAccountNewPasswordWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgRecoverAccountNewPasswordWindow.setVisible(true);
                this.delayHidePushMsg();
            }
        } catch (ConnectionFailureDbException | UserAlreadyExistsEception | UserNullException |
                 UserDoesNotExistException e) {
            lbPushMsgRecoverAccountNewPasswordWindow.setText(e.getMessage());
            hbPushMsgRecoverAccountNewPasswordWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgRecoverAccountNewPasswordWindow.setVisible(true);
            this.delayHidePushMsg();
        } catch (UserUpdatedSuccessfullyException e) {
            lbPushMsgRecoverAccountNewPasswordWindow.setText(e.getMessage());
            hbPushMsgRecoverAccountNewPasswordWindow.getStyleClass().setAll("push-msg-success");
            hbPushMsgRecoverAccountNewPasswordWindow.setVisible(true);
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ev) {
                    ev.printStackTrace();
                }
                Platform.runLater(() -> {
                    hbPushMsgRecoverAccountNewPasswordWindow.setVisible(false);
                    this.resetWindow();
                    ScreenManager sm = ScreenManager.getInstance();
                    sm.changeScreen("login-screen.fxml", "Gerenciador de Contratos - Login");
                });
            }).start();
        }
    }

    private void delayHidePushMsg(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ev) {
                ev.printStackTrace();
            }
            Platform.runLater(() -> {
                hbPushMsgRecoverAccountNewPasswordWindow.setVisible(false);
            });
        }).start();
    }

    public void setDataWindow(String email){
        this.email = email;
    }

    private void resetWindow(){
        hbPushMsgRecoverAccountNewPasswordWindow.setVisible(false);
        tfRecoverAccountNewPasswordWindow.setText("");
        tfRecoverAccountRepeatNewPasswordWindow.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgRecoverAccountNewPasswordWindow.setVisible(false);
    }
}
