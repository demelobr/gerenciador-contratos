package org.example.gerenciadorcontratos;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.example.gerenciadorcontratos.UtilitiesLibrary.capitalizeWords;

public class EditContractScreenController implements Initializable {
    private Application app;
    private Contract contract;
    private User user;
    private List<String> collaboratorsNames;

    public EditContractScreenController() {
        this.app = new Application();
        this.collaboratorsNames = new ArrayList<>();
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private Button btnBackEditContractWindow;

    @FXML
    private Button btnChooseContractFileEditContractWindow;

    @FXML
    private HBox btnClosePushMsgEditContractWindow;

    @FXML
    private HBox btnHelpEditContractWindow;

    @FXML
    private Button btnRegisterEditContractWindow;

    @FXML
    private ChoiceBox<String> cbEngineerEditContractWindow;

    @FXML
    private DatePicker dpEndDateEditContractWindow;

    @FXML
    private DatePicker dpExpectedEndDateEditContractWindow;

    @FXML
    private DatePicker dpExpectedStartDateEditContractWindow;

    @FXML
    private DatePicker dpStartDateEditContractWindow;

    @FXML
    private HBox hbPushMsgAddEditContractWindow;

    @FXML
    private ImageView imgDataBaseConnectionWindow;

    @FXML
    private Label lbContractFileUrlEditContractWindow;

    @FXML
    private Label lbPushMsgEditContractWindow;

    @FXML
    private TextArea taDescriptonEditContractWindow;

    @FXML
    private TextField tfAddressEditContractWindow;

    @FXML
    private TextField tfNameEditContractWindow;

    @FXML
    public void closePushMsg(MouseEvent event) {
        hbPushMsgAddEditContractWindow.setVisible(false);
    }

    @FXML
    public void goBackContractDetailsScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getContractDetailsScreenController().setContract(contract);
        sm.getContractDetailsScreenController().setUser(user);
        try {
            sm.getContractDetailsScreenController().setDataWindow();
        } catch (ConnectionFailureDbException e) {
            lbPushMsgEditContractWindow.setText(e.getMessage());
            hbPushMsgAddEditContractWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddEditContractWindow.setVisible(true);
            this.delayHidePushMsg();
        }
        sm.changeScreen("contract-details-screen.fxml", "Gerenciador de Contratos - Contratos");
    }

    @FXML
    public void editContract() {
        String name = tfNameEditContractWindow.getText();
        String address = tfAddressEditContractWindow.getText();
        String engineer = cbEngineerEditContractWindow.getValue();
        String contractFileUrl = lbContractFileUrlEditContractWindow.getText();
        String description = taDescriptonEditContractWindow.getText();
        LocalDate expectedStartDate = dpExpectedStartDateEditContractWindow.getValue();
        LocalDate expectedEndDate = dpExpectedEndDateEditContractWindow.getValue();
        LocalDate startDate = dpStartDateEditContractWindow.getValue();
        LocalDate endDate = dpEndDateEditContractWindow.getValue();

        if(name.isEmpty() && address.isEmpty() && engineer.isEmpty() && description.isEmpty() &&
        (contractFileUrl.isEmpty() || contractFileUrl.equals(contract.getContractFile())) &&
        (expectedStartDate == null || expectedStartDate == contract.getExpectedStartDate()) &&
        (expectedEndDate == null || expectedEndDate == contract.getExpectedEndDate()) &&
        (startDate == null || startDate == contract.getExpectedStartDate()) &&
        (endDate == null || endDate == contract.getExpectedEndDate())){
            lbPushMsgEditContractWindow.setText("Não houve alterações!");
            hbPushMsgAddEditContractWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddEditContractWindow.setVisible(true);
            this.delayHidePushMsg();
        }else{
            if(name.isEmpty() || name.equals(contract.getName())){
                name = contract.getName();
            }
            if(address.isEmpty() || address.equals(contract.getAddress())){
                address = contract.getAddress();
            }
            if(engineer.isEmpty() || engineer.equals(contract.getEngineer())){
                engineer = contract.getEngineer();
            }
            if(engineer.equals("----------")) engineer = "NÃO INFORMADO";
            if(contractFileUrl.isEmpty() || contractFileUrl.equals(contract.getContractFile())){
                contractFileUrl = contract.getContractFile();
            }
            if(description.isEmpty() || description.equals(contract.getDescription())){
                description = contract.getDescription();
            }
            if(expectedStartDate == null || expectedStartDate == contract.getExpectedStartDate()){
                expectedStartDate = contract.getExpectedStartDate();
            }
            if(expectedEndDate == null || expectedEndDate == contract.getExpectedEndDate()){
                expectedEndDate = contract.getExpectedEndDate();
            }
            if(startDate == null || startDate == contract.getExpectedStartDate()){
                startDate = contract.getExpectedStartDate();
            }
            if(endDate == null || endDate == contract.getExpectedEndDate()){
                endDate = contract.getExpectedEndDate();
            }

            try {
                app.getServer().updateContract(contract, name, description, address, engineer, contractFileUrl, expectedStartDate, expectedEndDate, startDate, endDate);
            } catch (ConnectionFailureDbException | ContractDoesNotExistException | ContractNullException e) {
                System.out.println(e.getMessage());
                lbPushMsgEditContractWindow.setText(e.getMessage());
                hbPushMsgAddEditContractWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgAddEditContractWindow.setVisible(true);
                this.delayHidePushMsg();
            } catch (ContractUpdatedSuccessfullyException e) {
                lbPushMsgEditContractWindow.setText(e.getMessage());
                hbPushMsgAddEditContractWindow.getStyleClass().setAll("push-msg-success");
                hbPushMsgAddEditContractWindow.setVisible(true);
                this.delayHidePushMsg();

                String finalName = name;
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ev) {
                        ev.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        this.resetWindow();
                        ScreenManager sm = ScreenManager.getInstance();
                        try {
                            sm.getContractDetailsScreenController().setContract(app.getServer().getContractByName(finalName));
                        } catch (ConnectionFailureDbException | ContractDoesNotExistException ex) {
                            lbPushMsgEditContractWindow.setText(ex.getMessage());
                            hbPushMsgAddEditContractWindow.getStyleClass().setAll("push-msg-error");
                            hbPushMsgAddEditContractWindow.setVisible(true);
                            this.delayHidePushMsg();
                        }
                        sm.getContractDetailsScreenController().setUser(user);
                        try {
                            sm.getContractDetailsScreenController().setDataWindow();
                        } catch (ConnectionFailureDbException ev) {
                            lbPushMsgEditContractWindow.setText(ev.getMessage());
                            hbPushMsgAddEditContractWindow.getStyleClass().setAll("push-msg-error");
                            hbPushMsgAddEditContractWindow.setVisible(true);
                            this.delayHidePushMsg();
                        }
                        sm.changeScreen("contract-details-screen.fxml", "Gerenciador de Contratos - Contratos");
                    });
                }).start();
            }

        }

    }

    @FXML
    public void chooseContractFile(ActionEvent event){
        Stage stg = ScreenManager.getStg();
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Documentos", "*.docx", "*.pdf"));
        List<File> documento = fc.showOpenMultipleDialog(stg);
        if(documento != null && documento.size() == 1){
            lbContractFileUrlEditContractWindow.setText(documento.get(0).getAbsolutePath());
        }else{
            lbPushMsgEditContractWindow.setText("Necessário escolher 1 documento!");
            hbPushMsgAddEditContractWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddEditContractWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    @FXML
    public void goToHelpScreen(){
        lbPushMsgEditContractWindow.setText("Em Desenvolvimento!");
        hbPushMsgAddEditContractWindow.getStyleClass().setAll("push-msg-info");
        hbPushMsgAddEditContractWindow.setVisible(true);
        this.delayHidePushMsg();
    }

    public void resetWindow(){
        hbPushMsgAddEditContractWindow.setVisible(false);
        tfNameEditContractWindow.setText("");
        tfAddressEditContractWindow.setText("");
        cbEngineerEditContractWindow.getItems().clear();
        lbContractFileUrlEditContractWindow.setText("");
        taDescriptonEditContractWindow.setText("");
        dpExpectedStartDateEditContractWindow.setValue(null);
        dpExpectedEndDateEditContractWindow.setValue(null);
        dpStartDateEditContractWindow.setValue(null);
        dpEndDateEditContractWindow.setValue(null);
    }

    public void initializeWindow(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        tfNameEditContractWindow.setPromptText(capitalizeWords(contract.getName()));
        tfAddressEditContractWindow.setPromptText(capitalizeWords(contract.getAddress()));
        taDescriptonEditContractWindow.setPromptText(capitalizeWords(contract.getDescription()));
        lbContractFileUrlEditContractWindow.setText(contract.getContractFile());
        dpExpectedStartDateEditContractWindow.setPromptText(contract.getExpectedStartDate().format(dateTimeFormatter));
        dpExpectedEndDateEditContractWindow.setPromptText(contract.getExpectedEndDate().format(dateTimeFormatter));
        dpStartDateEditContractWindow.setPromptText(contract.getStartDate().format(dateTimeFormatter));
        dpEndDateEditContractWindow.setPromptText(contract.getEndDate().format(dateTimeFormatter));
        this.setCombosBoxOptions();
        if(contract.getEngineer().equals("NÃO INFORMADO")) cbEngineerEditContractWindow.setValue(collaboratorsNames.getFirst());
        else cbEngineerEditContractWindow.setValue(contract.getEngineer());
    }

    private void setCombosBoxOptions(){
        try {
            List<Collaborator> listOfAllCollaborators = app.getServer().listAllCollaborators();
            collaboratorsNames.clear();
            collaboratorsNames.add("----------");
            for(Collaborator collaborator : listOfAllCollaborators){
                if(collaborator.getOffice().equalsIgnoreCase("ENGENHEIRO") || collaborator.getOffice().equalsIgnoreCase("ENGENHEIRA")) collaboratorsNames.add(collaborator.getName());
            }
        } catch (ConnectionFailureDbException ignored) {}
        cbEngineerEditContractWindow.getItems().addAll(collaboratorsNames);
    }

    void delayHidePushMsg(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ev) {
                ev.printStackTrace();
            }
            Platform.runLater(() -> {
                hbPushMsgAddEditContractWindow.setVisible(false);
            });
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgAddEditContractWindow.setVisible(false);
    }

}
