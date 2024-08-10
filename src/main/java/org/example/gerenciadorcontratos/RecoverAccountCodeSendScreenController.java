package org.example.gerenciadorcontratos;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class RecoverAccountCodeSendScreenController implements Initializable {
    private Application app;
    private String email;
    private int time = 90;


    public RecoverAccountCodeSendScreenController(){
        this.app = new Application();
    }

    @FXML
    private HBox btnBackRecoverAccountCodeWindow;

    @FXML
    private HBox btnClosePushMsgRecoverAccountCodeWindow;

    @FXML
    private Button btnRecoverAccountCodeWindow;

    @FXML
    private HBox hbPushMsgRecoverAccountCodeWindow;

    @FXML
    private Label lbEmailRecoverAccountCodeWindow;

    @FXML
    private Label lbPushMsgRecoverAccountCodeWindow;

    @FXML
    private Label lbTimeRecoverAccountCodeWindow;

    @FXML
    private TextField tfCodeRecoverAccountCodeWindow;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @FXML
    void closePushMsg(MouseEvent event) {
        hbPushMsgRecoverAccountCodeWindow.setVisible(false);
    }

    @FXML
    public void backToEmailSendScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.changeScreen("recover-account-email-screen.fxml", "Gerenciador de Contratos - Recuperar Conta");
    }

    @FXML
    public void checkVerificationCode(){
        String verificationCode = tfCodeRecoverAccountCodeWindow.getText();
        try {
            User user  = app.getServer().getUserByEmail(this.email);
            if(app.getServer().isValidVerificationCode(user.getCodeDateTime())){
                if(user.getVerificationCode().equals(verificationCode)){
                    lbPushMsgRecoverAccountCodeWindow.setText("Código verificado com sucesso!");
                    hbPushMsgRecoverAccountCodeWindow.getStyleClass().setAll("push-msg-success");
                    hbPushMsgRecoverAccountCodeWindow.setVisible(true);
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ev) {
                            ev.printStackTrace();
                        }
                        Platform.runLater(() -> {
                            hbPushMsgRecoverAccountCodeWindow.setVisible(false);
                            this.resetWindow();
                            ScreenManager sm = ScreenManager.getInstance();
                            sm.getRecoverAccountNewPasswordController().setDataWindow(email);
                            sm.changeScreen("recover-account-new-password.fxml", "Gerenciador de Contratos - Recuperar Conta");
                        });
                    }).start();
                }else{
                    lbPushMsgRecoverAccountCodeWindow.setText("Código inválido!");
                    hbPushMsgRecoverAccountCodeWindow.getStyleClass().setAll("push-msg-error");
                    hbPushMsgRecoverAccountCodeWindow.setVisible(true);
                    this.delayHidePushMsg();
                }
            }else{
                lbPushMsgRecoverAccountCodeWindow.setText("Este código está fora da validade!");
                hbPushMsgRecoverAccountCodeWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgRecoverAccountCodeWindow.setVisible(true);
                this.delayHidePushMsg();
            }
        } catch (ConnectionFailureDbException | UserDoesNotExistException e) {
            throw new RuntimeException(e);
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
                hbPushMsgRecoverAccountCodeWindow.setVisible(false);
            });
        }).start();
    }

    public void setDataWindow(String email){
        this.email = email;
        lbEmailRecoverAccountCodeWindow.setText(String.format("\"%s\"", email));
    }

    private void resetWindow(){
        hbPushMsgRecoverAccountCodeWindow.setVisible(false);
        tfCodeRecoverAccountCodeWindow.setText("");
    }

    private void updateCountdown(){
        if(this.time >= 0) lbTimeRecoverAccountCodeWindow.setText(String.valueOf(this.time)+"s");
        this.time--;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgRecoverAccountCodeWindow.setVisible(false);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateCountdown()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

}
