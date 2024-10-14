package org.example.gerenciadorcontratos;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class AddNewPresenceScreenController implements Initializable {
    private Application app;
    private Collaborator collaborator;
    private List<String> hours;
    private List<String> minutes;
    private List<String> contractsNames;

    public AddNewPresenceScreenController(){
        this.app = new Application();
        this.hours = new ArrayList<>();
        this.minutes = new ArrayList<>();
        this.contractsNames = new ArrayList<>();
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    @FXML
    private HBox btnClosePushMsgAddNewPresenceWindow;

    @FXML
    private Button btnRegisterAddNewPresenceWindow;

    @FXML
    private ChoiceBox<String> cbContractAddNewPresenceWindow;

    @FXML
    private ChoiceBox<String> cbPresenceHourAddNewPresenceWindow;

    @FXML
    private ChoiceBox<String> cbPresenceMinuteAddNewPresenceWindow;

    @FXML
    private DatePicker dpPresenceDateAddNewPresenceWindow;

    @FXML
    private HBox hbPushMsgAddNewPresenceWindow;

    @FXML
    private Label lbPushMsgAddNewPresenceWindow;

    @FXML
    private RadioButton rbArrivalAddNewPresenceWindow;

    @FXML
    private RadioButton rbExitAddNewPresenceWindow;

    @FXML
    private RadioButton rbNotPresentAddNewPresenceWindow;

    @FXML
    private RadioButton rbPresentAddNewPresenceWindow;

    @FXML
    private TextArea taJustificationAddNewPresenceWindow;

    @FXML
    private TextArea taObservationAddNewPresenceWindow;

    @FXML
    private VBox vbJustificationAddNewPresenceWindow;

    @FXML
    void closePushMsg(MouseEvent event) {
        hbPushMsgAddNewPresenceWindow.setVisible(false);
    }

    @FXML
    public void selectArrival(){
        rbArrivalAddNewPresenceWindow.setSelected(true);
        rbExitAddNewPresenceWindow.setSelected(false);
    }

    @FXML
    public void selectExit(){
        rbArrivalAddNewPresenceWindow.setSelected(false);
        rbExitAddNewPresenceWindow.setSelected(true);
    }

    @FXML
    public void selectPresent(){
        rbPresentAddNewPresenceWindow.setSelected(true);
        rbNotPresentAddNewPresenceWindow.setSelected(false);
        vbJustificationAddNewPresenceWindow.setVisible(false);
    }

    @FXML
    public void selectNotPresent(){
        rbPresentAddNewPresenceWindow.setSelected(false);
        rbNotPresentAddNewPresenceWindow.setSelected(true);
        vbJustificationAddNewPresenceWindow.setVisible(true);
    }

    @FXML
    public void registerPresence(){
        String record = rbArrivalAddNewPresenceWindow.isSelected() ? "CHEGADA" : "SAÍDA";
        String status = rbPresentAddNewPresenceWindow.isSelected() ? "PRESENTE" : "NÃO PRESENTE";
        String justification = taJustificationAddNewPresenceWindow.getText();
        String observation = taObservationAddNewPresenceWindow.getText();
        LocalDate presenceDate = dpPresenceDateAddNewPresenceWindow.getValue();
        String presenceHour = cbPresenceHourAddNewPresenceWindow.getValue();
        String presenceMinute = cbPresenceMinuteAddNewPresenceWindow.getValue();
        String nameContract = cbContractAddNewPresenceWindow.getValue();

        if(presenceDate != null && !presenceHour.isEmpty() && !presenceMinute.isEmpty() &&
          !presenceHour.equals(hours.getFirst()) && !presenceMinute.equals(minutes.getFirst()) && !nameContract.equals(contractsNames.getFirst())){
            Presence presence = new Presence(collaborator.getCpf(), nameContract, record, status, justification, observation, presenceDate, Integer.parseInt(presenceHour), Integer.parseInt(presenceMinute));
            try {
                if(app.getServer().checkPresenceData(presence)){
                    app.getServer().createPresence(presence);
                }
            } catch (InvalidPresenseOrRecordDateTimeException | EmptyfieldsException | PresenceNullException |
                     ConnectionFailureDbException | InvalidPresenceException |
                     ThereIsAlreadyARegisteredPresenceException | JustificationRequiredException e) {
                lbPushMsgAddNewPresenceWindow.setText(e.getMessage());
                hbPushMsgAddNewPresenceWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgAddNewPresenceWindow.setVisible(true);
                this.delayHidePushMsg();
            } catch (PresenceCreatedSuccessfullyException e) {
                lbPushMsgAddNewPresenceWindow.setText(e.getMessage());
                hbPushMsgAddNewPresenceWindow.getStyleClass().setAll("push-msg-success");
                hbPushMsgAddNewPresenceWindow.setVisible(true);

                try {
                    app.getServer().updateCollaborator(collaborator,
                            collaborator.getName(), collaborator.getCpf(),
                            collaborator.getRg(), collaborator.getAddress(),
                            collaborator.getTelephone(), collaborator.getEmail(),
                            collaborator.getOffice(), collaborator.isStatus(),
                            presence.getPresenceDateTime(), collaborator.getAdmissionDate(),
                            collaborator.getTerminationDate(), collaborator.getPhotoUrl());
                } catch (ConnectionFailureDbException | CollaboratorDoesNotExistException | CollaboratorNullException ex) {
                    lbPushMsgAddNewPresenceWindow.setText(e.getMessage());
                    hbPushMsgAddNewPresenceWindow.getStyleClass().setAll("push-msg-error");
                    hbPushMsgAddNewPresenceWindow.setVisible(true);
                    this.delayHidePushMsg();
                } catch (CollaboratorUpdatedSuccessfullyException ex) {
                    ScreenManager sm = ScreenManager.getInstance();
                    sm.getCollaboratorsScreenController().initializeTable();
                    try {
                        this.collaborator = app.getServer().getCollaboratorByCpf(collaborator.getCpf());
                        sm.getCollaboratorsScreenController().setFielsOfCard(app.getServer().getCollaboratorByCpf(collaborator.getCpf()));
                    } catch (ConnectionFailureDbException | CollaboratorDoesNotExistException ignored) {}
                }

                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ev) {
                        ev.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        hbPushMsgAddNewPresenceWindow.setVisible(false);
                        this.clearFiels();
                        ScreenManager sm = ScreenManager.getInstance();
                        Stage stage = (Stage) sm.getAddNewPresenceScreenScene().getWindow();
                        stage.close();
                    });
                }).start();
            }
        }else{
            lbPushMsgAddNewPresenceWindow.setText("Preencha todos os campos!");
            hbPushMsgAddNewPresenceWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddNewPresenceWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    public void clearFiels(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dpPresenceDateAddNewPresenceWindow.setPromptText(LocalDate.now().format(dateTimeFormatter));
        taJustificationAddNewPresenceWindow.setText("");
        taObservationAddNewPresenceWindow.setText("");
        cbPresenceHourAddNewPresenceWindow.getItems().clear();
        cbPresenceMinuteAddNewPresenceWindow.getItems().clear();
        cbContractAddNewPresenceWindow.getItems().clear();
    }

    public void initializeWindow(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        rbArrivalAddNewPresenceWindow.setSelected(true);
        rbExitAddNewPresenceWindow.setSelected(false);
        rbPresentAddNewPresenceWindow.setSelected(true);
        rbNotPresentAddNewPresenceWindow.setSelected(false);
        vbJustificationAddNewPresenceWindow.setVisible(false);
        dpPresenceDateAddNewPresenceWindow.setPromptText(LocalDate.now().format(dateTimeFormatter));
        this.setCombosBoxOptions();
        cbPresenceHourAddNewPresenceWindow.setValue(hours.getFirst());
        cbPresenceMinuteAddNewPresenceWindow.setValue(minutes.getFirst());
        cbContractAddNewPresenceWindow.setValue(contractsNames.getFirst());
    }

    private void setCombosBoxOptions(){
        hours.clear();
        hours.add("--");
        for (int i = 1; i < 25; i++) {
            hours.add(String.valueOf(i-1));
        }
        minutes.clear();
        minutes.add("--");
        for (int i = 1; i < 61; i++) {
            minutes.add(String.valueOf(i-1));
        }
        try {
            List<Contract> listOfAllContracts = app.getServer().listAllContracts();
            contractsNames.clear();
            contractsNames.add("----------");
            for(Contract contract : listOfAllContracts){
                contractsNames.add(contract.getName());
            }
            Collections.sort(contractsNames);
        } catch (ConnectionFailureDbException ignored) {}
        cbPresenceHourAddNewPresenceWindow.getItems().addAll(hours);
        cbPresenceMinuteAddNewPresenceWindow.getItems().addAll(minutes);
        cbContractAddNewPresenceWindow.getItems().addAll(contractsNames);
    }

    void delayHidePushMsg(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ev) {
                ev.printStackTrace();
            }
            Platform.runLater(() -> {
                hbPushMsgAddNewPresenceWindow.setVisible(false);
            });
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgAddNewPresenceWindow.setVisible(false);
    }

}
