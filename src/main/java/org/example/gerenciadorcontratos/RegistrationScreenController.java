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

public class RegistrationScreenController implements Initializable {
    private Application app;

    @FXML
    private HBox btnBackCreateAccountWindow;

    @FXML
    private HBox btnClosePushMsgCreateAccountWindow;

    @FXML
    private Button btnRegisterCreateAccountWindow;

    @FXML
    private HBox hbPushMsgCreateAccountWindow;

    @FXML
    private Label lbPushMsgCreateAccountWindow;

    @FXML
    private TextField tfEmailCreateAccountWindow;

    @FXML
    private TextField tfNameCreateAccountWindow;

    @FXML
    private PasswordField tfPasswordCreateAccountWindow;

    @FXML
    private PasswordField tfRepeatPasswordCreateAccountWindow;

    public RegistrationScreenController(){
        this.app = new Application();
    }

    @FXML
    public void closePushMsg(MouseEvent event){
        hbPushMsgCreateAccountWindow.setVisible(false);
    }

    @FXML
    public void backToLoginScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.changeScreen("login-screen.fxml", "Gerenciador de Contratos - Login");
    }

    @FXML
    public void createAccount(){
        String email = tfEmailCreateAccountWindow.getText();
        String name = tfNameCreateAccountWindow.getText();
        String password = tfPasswordCreateAccountWindow.getText();
        String passwordRepeated = tfRepeatPasswordCreateAccountWindow.getText();

        if(password.equals(passwordRepeated)){
            User user = new User(email, name, password);
            try {
                if(app.getServer().checkUserData(user)){
                    app.getServer().createUser(user);
                }
            } catch (ConnectionFailureDbException | WeakPasswordException | EmptyfieldsException |
                     InvalidEmailException | AccountWithThisEmailAlreadyExistsException | UserNullException |
                     InvalidUserException e) {
                lbPushMsgCreateAccountWindow.setText(e.getMessage());
                hbPushMsgCreateAccountWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgCreateAccountWindow.setVisible(true);
                this.delayHidePushMsg();

            } catch (UserCreatedSuccessfullyException e) {
                lbPushMsgCreateAccountWindow.setText(e.getMessage());
                hbPushMsgCreateAccountWindow.getStyleClass().setAll("push-msg-success");
                hbPushMsgCreateAccountWindow.setVisible(true);

                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ev) {
                        ev.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        hbPushMsgCreateAccountWindow.setVisible(false);
                        this.resetWindow();
                        ScreenManager sm = ScreenManager.getInstance();
                        sm.changeScreen("login-screen.fxml", "Gerenciador de Contratos - Login");
                    });
                }).start();
            }
        }else{
            lbPushMsgCreateAccountWindow.setText("Repita sua senha corretamente!");
            hbPushMsgCreateAccountWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgCreateAccountWindow.setVisible(true);
            this.delayHidePushMsg();
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
                hbPushMsgCreateAccountWindow.setVisible(false);
            });
        }).start();
    }

    void resetWindow(){
        hbPushMsgCreateAccountWindow.setVisible(false);
        tfNameCreateAccountWindow.setText("");
        tfEmailCreateAccountWindow.setText("");
        tfPasswordCreateAccountWindow.setText("");
        tfRepeatPasswordCreateAccountWindow.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgCreateAccountWindow.setVisible(false);
    }
}
