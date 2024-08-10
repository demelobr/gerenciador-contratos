package org.example.gerenciadorcontratos;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {
    private Application app;

    public LoginScreenController(){
        this.app = new Application();
    }

    @FXML
    private Button btnEnterLoginWindow;

    @FXML
    private HBox btnFecharPushMsgLoginWindow;

    @FXML
    private Button btnForgotPasswordLoginWindow;

    @FXML
    private Button btnJoinUsLoginWindow;

    @FXML
    private HBox hbPushMsgLoginWindow;

    @FXML
    private Label lbPushMsgLoginWindow;

    @FXML
    private TextField tfEmailLoginWindow;

    @FXML
    private PasswordField tfPasswordLoginWindow;

    @FXML
    void closePushMsg(MouseEvent event) {
        hbPushMsgLoginWindow.setVisible(false);
    }

    @FXML
    void recoverPassword(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.changeScreen("recover-account-email-screen.fxml", "Recuperar Conta - Enviar Código");
    }

    @FXML
    void registerAccount(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.changeScreen("registration-screen.fxml", "Gerenciador de Contratos - Criar Conta");
    }

    @FXML
    void makeLogin(){
        String email = tfEmailLoginWindow.getText();
        String password = tfPasswordLoginWindow.getText();

        if(email.isEmpty() || password.isEmpty()){
            lbPushMsgLoginWindow.setText("Preencha as credenciais!");
            hbPushMsgLoginWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgLoginWindow.setVisible(true);
            this.delayHidePushMsg();
        }else{
            try {
                if(app.getServer().checkUserCredentials(email, password)){
                    lbPushMsgLoginWindow.setText("Login feito com sucesso!");
                    hbPushMsgLoginWindow.getStyleClass().setAll("push-msg-success");
                    hbPushMsgLoginWindow.setVisible(true);

                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ev) {
                            ev.printStackTrace();
                        }
                        Platform.runLater(() -> {
                            hbPushMsgLoginWindow.setVisible(false);
                            this.resetWindow();
                            ScreenManager sm = ScreenManager.getInstance();
                            try {
                                sm.getCollaboratorsScreenController().setUser(app.getServer().getUserByEmail(email));
                                sm.getCollaboratorsScreenController().setDataScreen(app.getServer().getUserByEmail(email).getName());
                            } catch (ConnectionFailureDbException | UserDoesNotExistException e) {
                                throw new RuntimeException(e);
                            }
                            sm.getCollaboratorsScreenController().initializeTable();
                            sm.changeScreen("collaborator-menu-screen.fxml", "Gerenciador de Contratos - Colaboradores");
                        });
                    }).start();
                }else{
                    lbPushMsgLoginWindow.setText("Erro ao realizar login");
                    hbPushMsgLoginWindow.getStyleClass().setAll("push-msg-error");
                    hbPushMsgLoginWindow.setVisible(true);
                    this.delayHidePushMsg();
                }
            } catch (ConnectionFailureDbException | UserDoesNotExistException e) {
                lbPushMsgLoginWindow.setText(e.getMessage());
                hbPushMsgLoginWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgLoginWindow.setVisible(true);
                this.delayHidePushMsg();
            }
        }

    }

    void delayHidePushMsg(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ev) {
                ev.printStackTrace();
            }
            Platform.runLater(() -> {
                hbPushMsgLoginWindow.setVisible(false);
            });
        }).start();
    }

    void resetWindow(){
        hbPushMsgLoginWindow.setVisible(false);
        tfEmailLoginWindow.setText("");
        tfPasswordLoginWindow.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgLoginWindow.setVisible(false);
        tfEmailLoginWindow.setText("roosevelt@gmail.com");
        tfPasswordLoginWindow.setText("12345678");
    }
}
