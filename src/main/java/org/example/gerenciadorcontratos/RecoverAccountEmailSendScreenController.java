package org.example.gerenciadorcontratos;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RecoverAccountEmailSendScreenController implements Initializable {
    private Application app;

    public RecoverAccountEmailSendScreenController(){
        this.app = new Application();
    }

    @FXML
    private HBox btnBackRecoverAccountEmaiWindow;

    @FXML
    private HBox btnClosePushMsgRecoverAccountEmaiWindow;

    @FXML
    private Button btnRecoverAccountEmaiWindow;

    @FXML
    private HBox hbPushMsgRecoverAccountEmailWindow;

    @FXML
    private Label lbPushMsgRecoverAccountEmaiWindow;

    @FXML
    private TextField tfEmailRecoverAccountEmaiWindow;

    @FXML
    void closePushMsg(MouseEvent event) {
        hbPushMsgRecoverAccountEmailWindow.setVisible(false);
    }

    @FXML
    public void backToLoginScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.changeScreen("login-screen.fxml", "Gerenciador de Contratos - Login");
    }

    @FXML
    public void sendEmailWithCodeVerification(){
        String email = tfEmailRecoverAccountEmaiWindow.getText();

        if(!email.isEmpty()){
            try {
                if(app.getServer().userExists(email)){
                    String verificationCode = app.getServer().generateVerificationCode();
//                    EmailMessenger.sendEmail(app.getServer().getSettings().getHostEmail(),
//                            app.getServer().getSettings().getPortEmail(),
//                            app.getServer().getSettings().getAddressEmail(),
//                            app.getServer().getSettings().getPasswordEmail(),
//                            email, "Código de Verificação",
//                            String.format("Seu Código de Verificação é: %s", verificationCode));

                    try {
                        app.getServer().setVerificationCode(app.getServer().getUserByEmail(email), verificationCode);
                    } catch (ConnectionFailureDbException | UserDoesNotExistException e) {
                        lbPushMsgRecoverAccountEmaiWindow.setText(e.getMessage());
                        hbPushMsgRecoverAccountEmailWindow.getStyleClass().setAll("push-msg-error");
                        hbPushMsgRecoverAccountEmailWindow.setVisible(true);
                        this.delayHidePushMsg();
                    }
                }else{
                    lbPushMsgRecoverAccountEmaiWindow.setText("Conta com este e-mail não existe!");
                    hbPushMsgRecoverAccountEmailWindow.getStyleClass().setAll("push-msg-error");
                    hbPushMsgRecoverAccountEmailWindow.setVisible(true);
                    this.delayHidePushMsg();
                }
//            } catch (ConnectionFailureDbException | MessagingException e) {
            } catch (ConnectionFailureDbException e) {
//                System.out.println(e.getMessage());
                lbPushMsgRecoverAccountEmaiWindow.setText(e.getMessage());
                hbPushMsgRecoverAccountEmailWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgRecoverAccountEmailWindow.setVisible(true);
                this.delayHidePushMsg();
            } catch (UserUpdatedSuccessfullyException e) {
                lbPushMsgRecoverAccountEmaiWindow.setText(String.format("Código enviado para o e-mail %s", email));
                hbPushMsgRecoverAccountEmailWindow.getStyleClass().setAll("push-msg-success");
                hbPushMsgRecoverAccountEmailWindow.setVisible(true);

                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ev) {
                        ev.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        hbPushMsgRecoverAccountEmailWindow.setVisible(false);
                        ScreenManager sm = ScreenManager.getInstance();
                        sm.getRecoverAccountCodeSendController().setDataWindow(email);
                        sm.changeScreen("recover-account-code.fxml", "Recuperar Conta - Verificação de Código");
                    });
                }).start();

            } catch (UserNullException e) {
                throw new RuntimeException(e);
            }

        }else{
            lbPushMsgRecoverAccountEmaiWindow.setText("Preencha o campo abaixo!");
            hbPushMsgRecoverAccountEmailWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgRecoverAccountEmailWindow.setVisible(true);
            this.delayHidePushMsg();
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
                hbPushMsgRecoverAccountEmailWindow.setVisible(false);
            });
        }).start();
    }

    private void resetWindow(){
        hbPushMsgRecoverAccountEmailWindow.setVisible(false);
        tfEmailRecoverAccountEmaiWindow.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgRecoverAccountEmailWindow.setVisible(false);
    }
}
