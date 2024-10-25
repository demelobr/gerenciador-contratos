package org.example.gerenciadorcontratos;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.example.gerenciadorcontratos.UtilitiesLibrary.capitalizeWords;

public class CloudScreenController implements Initializable {
    private Application app;
    private User user;
    private List<String> listOfPeriodicity;

    public CloudScreenController() {
        this.app = new Application();
        this.listOfPeriodicity = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private Button btnChooseFolderCloudWindow;

    @FXML
    private HBox btnClosePushMsgCloudWindow;

    @FXML
    private HBox btnHelpDetailsCloudWindow;

    @FXML
    private Button btnManualUpdateCloudWindow;

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
    private Button btnSaveSettingsCloudWindow;

    @FXML
    private ChoiceBox<String> cbPeriodicityCloudWindow;

    @FXML
    private HBox hbPushMsgCloudWindow;

    @FXML
    private ImageView imgDataBaseConnectionCloudWindow;

    @FXML
    private Label lbFolderUrlCloudWindow;

    @FXML
    private Label lbLastAutomaticUpdateCloudWindow;

    @FXML
    private Label lbLastManualBackupCloudWindow;

    @FXML
    private Label lbPushMsgCloudWindow;

    @FXML
    private Label lbUserNameWindow;

    @FXML
    void closePushMsg(MouseEvent event) {
        hbPushMsgCloudWindow.setVisible(false);
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
    public void goToSettingsScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getSettingsScreenController().setUser(user);
        sm.getSettingsScreenController().setSettings(app.getServer().getSettings());
        sm.getSettingsScreenController().initializeWindow();
        sm.changeScreen("settings-menu-screen.fxml", "Gerenciador de Contratos - Configurações");
        this.delayHidePushMsg();
    }

    @FXML
    public void goToLoginScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.changeScreen("login-screen.fxml", "Gerenciador de Contratos - Login");
    }

    @FXML
    public void selectFolderPath(){
        Stage stg = (Stage) btnChooseFolderCloudWindow.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedFolder = directoryChooser.showDialog(stg);
        if(selectedFolder != null){
            lbFolderUrlCloudWindow.setText(selectedFolder.getAbsolutePath());
        }else{
            lbPushMsgCloudWindow.setText("Necessário selecionar uma pasta para backup!");
            hbPushMsgCloudWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgCloudWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    @FXML
    public void saveCloudSettings(){
        String folderPath = lbFolderUrlCloudWindow.getText();
        String periodicity = cbPeriodicityCloudWindow.getValue();
        Cloud cloud = app.getServer().getCloud();
        Cloud newCloud = null;

        if((folderPath.isEmpty() || folderPath.equals(cloud.getFolderPath()) && (periodicity.isEmpty() || periodicity.equals(cloud.getPeriodicity())))){
            lbPushMsgCloudWindow.setText("Nenhuma mudança foi feita!");
            hbPushMsgCloudWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgCloudWindow.setVisible(true);
            this.delayHidePushMsg();
        }else{
            if(folderPath.isEmpty() || folderPath.equals(cloud.getFolderPath())){
                folderPath = cloud.getFolderPath();
            }
            if(periodicity.isEmpty() || periodicity.equals(cloud.getPeriodicity())){
                periodicity = cloud.getPeriodicity();
            }
            try {
                newCloud = new Cloud(folderPath, periodicity);
                CloudController.saveCloud(newCloud);
            } catch (CloudSavingFailedException e) {
                lbPushMsgCloudWindow.setText(e.getMessage());
                hbPushMsgCloudWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgCloudWindow.setVisible(true);
                this.delayHidePushMsg();
            } catch (CloudSavedSuccessfullyException e) {
                app.getServer().setCloud(newCloud);
                lbPushMsgCloudWindow.setText(e.getMessage());
                hbPushMsgCloudWindow.getStyleClass().setAll("push-msg-success");
                hbPushMsgCloudWindow.setVisible(true);
                this.delayHidePushMsg();
            }
        }

    }

    @FXML
    public void manualUploadData(){
        if(DataBaseBackup.CreateDataBaseBackup() == 0){
            lbPushMsgCloudWindow.setText("Salvamento manual feito com sucesso!");
            hbPushMsgCloudWindow.getStyleClass().setAll("push-msg-success");
            hbPushMsgCloudWindow.setVisible(true);
            this.delayHidePushMsg();
            LocalDateTime dateTime = LocalDateTime.now();
            app.getServer().getCloud().setLastManualUpdate(dateTime);
            try {
                app.getServer().saveCloud(app.getServer().getCloud());
            } catch (CloudSavedSuccessfullyException ignored) {}
              catch (CloudSavingFailedException e) {
                  lbPushMsgCloudWindow.setText(e.getMessage());
                  hbPushMsgCloudWindow.getStyleClass().setAll("push-msg-error");
                  hbPushMsgCloudWindow.setVisible(true);
                  this.delayHidePushMsg();
            }
            Locale locale = new Locale("pt", "BR");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy H:m", locale);
            lbLastManualBackupCloudWindow.setText("Último backup manual feito em " + dateTime.format(formatter));
        }else{
            lbPushMsgCloudWindow.setText("Erro ao salvar manualmente!");
            hbPushMsgCloudWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgCloudWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    @FXML
    public void goToHelpScreen(){
        lbPushMsgCloudWindow.setText("Em Desenvolvimento!");
        hbPushMsgCloudWindow.getStyleClass().setAll("push-msg-info");
        hbPushMsgCloudWindow.setVisible(true);
        this.delayHidePushMsg();
    }

    private void setCombosBoxOptions(){
        listOfPeriodicity.clear();
        listOfPeriodicity.add("Diário");
        listOfPeriodicity.add("Semanal");
        listOfPeriodicity.add("Mensal");
        cbPeriodicityCloudWindow.getItems().setAll(listOfPeriodicity);
        cbPeriodicityCloudWindow.getSelectionModel().selectFirst();
    }

    public void resetWindow(){
        hbPushMsgCloudWindow.setVisible(false);
        lbLastAutomaticUpdateCloudWindow.setText("");
        lbFolderUrlCloudWindow.setText("");
        lbLastManualBackupCloudWindow.setText("");
        cbPeriodicityCloudWindow.getItems().clear();
    }

    public void initializeWindow(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        lbUserNameWindow.setText(String.format("%s!", capitalizeWords(user.getName())));
        if(app.getServer().getCloud().getLastAutomaticUpdate() != null) lbLastAutomaticUpdateCloudWindow.setText(String.format("Último backup automático às %s", app.getServer().getCloud().getLastAutomaticUpdate().format(dateTimeFormatter)));
        else lbLastAutomaticUpdateCloudWindow.setText("");
        lbFolderUrlCloudWindow.setText(app.getServer().getCloud().getFolderPath());
        if(app.getServer().getCloud().getLastManualUpdate() != null) lbLastManualBackupCloudWindow.setText(String.format("Último backup manual às %s", app.getServer().getCloud().getLastManualUpdate().format(dateTimeFormatter)));
        else lbLastManualBackupCloudWindow.setText("");
        this.setCombosBoxOptions();
    }

    void delayHidePushMsg(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ev) {
                ev.printStackTrace();
            }
            Platform.runLater(() -> {
                hbPushMsgCloudWindow.setVisible(false);
            });
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgCloudWindow.setVisible(false);
    }

}
