package org.example.gerenciadorcontratos;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static org.example.gerenciadorcontratos.UtilitiesLibrary.capitalizeWords;

public class SettingsScreenController implements Initializable {
    private Application app;
    private User user;
    private Settings settings;

    public SettingsScreenController() {
        this.app = new Application();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @FXML
    private HBox btnClosePushMsgSettingsWindow;

    @FXML
    private HBox btnHelpDetailsSettingsWindow;

    @FXML
    private Button btnNavBarCallaboratorsWindow;

    @FXML
    private Button btnNavBarCloudWindow;

    @FXML
    private Button btnNavBarContractsWindow;

    @FXML
    private Button btnNavBarFinanceWindow;

    @FXML
    private Button btnNavBarSettingsWindow;

    @FXML
    private Button btnNavBarSignOutWindow;

    @FXML
    private Button btnSaveSettingsWindow;

    @FXML
    private HBox hbPushMsgSettingsWindow;

    @FXML
    private ImageView imgDataBaseConnectionSettingsWindow;

    @FXML
    private Label lbPushMsgSettingsWindow;

    @FXML
    private Label lbUserNameWindow;

    @FXML
    private PasswordField pfAccountPasswordSettingsWindow;

    @FXML
    private PasswordField pfDataBasePasswordSettingsWindow;

    @FXML
    private PasswordField pfRecoveryPasswordSettingsWindow;

    @FXML
    private TextField tfAccountEmailSettingsWindow;

    @FXML
    private TextField tfAccountNameSettingsWindow;

    @FXML
    private TextField tfDataBaseUrlSettingsWindow;

    @FXML
    private TextField tfDataBaseUserSettingsWindow;

    @FXML
    private TextField tfRecoveryEmailSettingsWindow;

    @FXML
    private TextField tfRecoveryHostSettingsWindow;

    @FXML
    private TextField tfRecoveryPortSettingsWindow;

    @FXML
    void closePushMsg(MouseEvent event) {
        hbPushMsgSettingsWindow.setVisible(false);
    }

    @FXML
    public void goToContractsScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getContractsScreenController().setUser(user);
        sm.getContractsScreenController().setDataScreen(capitalizeWords(user.getName()));
        sm.changeScreen("contract-menu-screen.fxml", "Gerenciador de Contratos - Contratos");
    }

    @FXML
    public void goToFinanceScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getFinancesScreenController().setUser(user);
        sm.changeScreen("finance-menu-screen.fxml", "Gerenciador de Contratos - Finanças");
        sm.getFinancesScreenController().setDataWindow();
    }

    @FXML
    public void goToCollaboratorsScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getCollaboratorsScreenController().setUser(user);
        sm.getCollaboratorsScreenController().setDataScreen(capitalizeWords(user.getName()));
        sm.changeScreen("collaborator-menu-screen.fxml", "Gerenciador de Contratos - Colaboradores");
        this.delayHidePushMsg();
    }

    @FXML
    public void goToCloudScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getCloudScreenController().setUser(user);
        sm.getCloudScreenController().initializeWindow();
        sm.changeScreen("cloud-menu-screen.fxml", "Gerenciador de Contratos - Nuvem");
        this.delayHidePushMsg();
    }

    @FXML
    public void goToLoginScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.changeScreen("login-screen.fxml", "Gerenciador de Contratos - Login");
    }

    @FXML
    public void editSettings(){
        String accountName = tfAccountNameSettingsWindow.getText();
        String accountEmail = tfAccountEmailSettingsWindow.getText();
        String accountPassword = pfAccountPasswordSettingsWindow.getText();
        String recoveryEmail = tfRecoveryEmailSettingsWindow.getText();
        String recoveryPassword = pfRecoveryPasswordSettingsWindow.getText();
        String recoveryHost = tfRecoveryHostSettingsWindow.getText();
        String recoveryPort = tfRecoveryPortSettingsWindow.getText();
        String dataBaseURL = tfDataBaseUrlSettingsWindow.getText();
        String dataBaseUser = tfDataBaseUserSettingsWindow.getText();
        String dataBasePassword = pfDataBasePasswordSettingsWindow.getText();

        if((accountName.isEmpty() || accountName.equals(user.getName())) && (accountEmail.isEmpty() || accountEmail.equals(user.getEmail())) && (accountPassword.isEmpty() || accountPassword.equals(user.getPassword())) &&
           (recoveryEmail.isEmpty() || recoveryEmail.equals(settings.getAddressEmail())) && (recoveryPassword.isEmpty() || recoveryPassword.equals(settings.getPasswordEmail())) && (recoveryHost.isEmpty() || recoveryHost.equals(settings.getHostEmail())) &&
           (recoveryPort.isEmpty() || recoveryPort.equals(settings.getPortEmail())) && (dataBaseURL.isEmpty() || dataBaseURL.equals(settings.getUrlDB())) && (dataBaseUser.isEmpty() || dataBaseUser.equals(settings.getUserDB())) && (dataBasePassword.isEmpty() || dataBasePassword.equals(settings.getPasswordDB()))){
            lbPushMsgSettingsWindow.setText("Não houve alterações!");
            hbPushMsgSettingsWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgSettingsWindow.setVisible(true);
            this.delayHidePushMsg();
        }else{
            if(accountName.isEmpty() || accountName.equals(user.getName())){
                accountName = user.getName();
            }
            if(accountEmail.isEmpty() || accountEmail.equals(user.getEmail())){
                accountEmail = user.getEmail();
            }
            if(accountPassword.isEmpty() || accountPassword.equals(user.getPassword())){
                accountPassword = user.getPassword();
            }
            if(recoveryEmail.isEmpty() || recoveryEmail.equals(settings.getAddressEmail())){
                recoveryEmail = settings.getAddressEmail();
            }
            if(recoveryPassword.isEmpty() || recoveryPassword.equals(settings.getPasswordEmail())){
                recoveryPassword = settings.getPasswordEmail();
            }
            if(recoveryHost.isEmpty() || recoveryHost.equals(settings.getHostEmail())){
                recoveryHost = settings.getHostEmail();
            }
            if(recoveryPort.isEmpty() || recoveryPort.equals(settings.getPortEmail())){
                recoveryPort = settings.getPortEmail();
            }
            if(dataBaseURL.isEmpty() || dataBaseURL.equals(settings.getUrlDB())){
                dataBaseURL = settings.getUrlDB();
            }
            if(dataBaseUser.isEmpty() || dataBaseUser.equals(settings.getUserDB())){
                dataBaseUser = settings.getUserDB();
            }
            if(dataBasePassword.isEmpty() || dataBasePassword.equals(settings.getPasswordDB())){
                dataBasePassword = settings.getPasswordDB();
            }
            try {
                app.getServer().updateUser(user, accountEmail, accountName, accountPassword, "", LocalDateTime.now());
            } catch (ConnectionFailureDbException | UserNullException | UserDoesNotExistException | UserAlreadyExistsEception e) {
                lbPushMsgSettingsWindow.setText(e.getMessage());
                hbPushMsgSettingsWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgSettingsWindow.setVisible(true);
                this.delayHidePushMsg();
            } catch (UserUpdatedSuccessfullyException e) {
                try {
                    app.getServer().saveSettings(new Settings(dataBaseURL, dataBaseUser, dataBasePassword, recoveryHost, recoveryPort, recoveryEmail, recoveryPassword));
                }catch (SettingsSavingFailedException ex) {
                    lbPushMsgSettingsWindow.setText(ex.getMessage());
                    hbPushMsgSettingsWindow.getStyleClass().setAll("push-msg-error");
                    hbPushMsgSettingsWindow.setVisible(true);
                    this.delayHidePushMsg();
                } catch (SettingsSavedSuccessfullyException ex) {
                    app.getServer().setSettings(settings);
                    try {
                        this.setUser(app.getServer().getUserByEmail(accountEmail));
                    } catch (ConnectionFailureDbException | UserDoesNotExistException exc) {
                        lbPushMsgSettingsWindow.setText(exc.getMessage());
                        hbPushMsgSettingsWindow.getStyleClass().setAll("push-msg-error");
                        hbPushMsgSettingsWindow.setVisible(true);
                        this.delayHidePushMsg();
                    }
                    this.resetWindow();
                    this.initializeWindow();
                    lbPushMsgSettingsWindow.setText("Alterações feitas com sucesso!");
                    hbPushMsgSettingsWindow.getStyleClass().setAll("push-msg-success");
                    hbPushMsgSettingsWindow.setVisible(true);
                    this.delayHidePushMsg();
                }
            }

        }

    }

    @FXML
    public void goToHelpScreen(){
        lbPushMsgSettingsWindow.setText("Em Desenvolvimento!");
        hbPushMsgSettingsWindow.getStyleClass().setAll("push-msg-info");
        hbPushMsgSettingsWindow.setVisible(true);
        this.delayHidePushMsg();
    }

    public void resetWindow(){
        hbPushMsgSettingsWindow.setVisible(false);
        tfAccountNameSettingsWindow.setText("");
        tfAccountEmailSettingsWindow.setText("");
        pfAccountPasswordSettingsWindow.setText("");
        tfRecoveryEmailSettingsWindow.setText("");
        pfRecoveryPasswordSettingsWindow.setText("");
        tfRecoveryHostSettingsWindow.setText("");
        tfRecoveryPortSettingsWindow.setText("");
        tfDataBaseUrlSettingsWindow.setText("");
        tfDataBaseUserSettingsWindow.setText("");
        pfDataBasePasswordSettingsWindow.setText("");
    }

    public void initializeWindow(){
        lbUserNameWindow.setText(String.format("%s!", capitalizeWords(user.getName())));
        tfAccountNameSettingsWindow.setPromptText(user.getName());
        tfAccountEmailSettingsWindow.setPromptText(user.getEmail());
        pfAccountPasswordSettingsWindow.setPromptText("**********");
        tfRecoveryEmailSettingsWindow.setPromptText(settings.getAddressEmail());
        pfRecoveryPasswordSettingsWindow.setPromptText("**********");
        tfRecoveryHostSettingsWindow.setPromptText(settings.getHostEmail());
        tfRecoveryPortSettingsWindow.setPromptText(settings.getPortEmail());
        tfDataBaseUrlSettingsWindow.setPromptText(settings.getUrlDB());
        tfDataBaseUserSettingsWindow.setPromptText(settings.getUserDB());
        pfDataBasePasswordSettingsWindow.setPromptText("**********");
    }

    void delayHidePushMsg(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ev) {
                ev.printStackTrace();
            }
            Platform.runLater(() -> {
                hbPushMsgSettingsWindow.setVisible(false);
            });
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgSettingsWindow.setVisible(false);
    }

}
