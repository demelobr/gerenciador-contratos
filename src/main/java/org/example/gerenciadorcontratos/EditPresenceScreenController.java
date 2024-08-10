package org.example.gerenciadorcontratos;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditPresenceScreenController implements Initializable {
    private Application app;
    private Presence presence;
    private List<String> hours;
    private List<String> minutes;
    private List<String> contractsNames;

    public EditPresenceScreenController(){
        this.app = new Application();
        this.hours = new ArrayList<>();
        this.minutes = new ArrayList<>();
        this.contractsNames = new ArrayList<>();
    }

    public Presence getPresence() {
        return presence;
    }

    public void setPresence(Presence presence) {
        this.presence = presence;
    }

    @FXML
    private HBox btnClosePushMsgEditPresenceWindow;

    @FXML
    private Button btnRegisterEditPresenceWindow;

    @FXML
    private ChoiceBox<String> cbContractEditPresenceWindow;

    @FXML
    private ChoiceBox<String> cbPresenceHourEditPresenceWindow;

    @FXML
    private ChoiceBox<String> cbPresenceMinuteEditPresenceWindow;

    @FXML
    private DatePicker dpPresenceDateEditPresenceWindow;

    @FXML
    private HBox hbPushMsgEditPresenceWindow;

    @FXML
    private Label lbPushMsgEditPresenceWindow;

    @FXML
    private RadioButton rbArrivalEditPresenceWindow;

    @FXML
    private RadioButton rbExitEditPresenceWindow;

    @FXML
    private RadioButton rbNotPresentEditPresenceWindow;

    @FXML
    private RadioButton rbPresentEditPresenceWindow;

    @FXML
    void closePushMsg(MouseEvent event) {
        hbPushMsgEditPresenceWindow.setVisible(false);
    }


    @FXML
    public void selectArrival(){
        rbArrivalEditPresenceWindow.setSelected(true);
        rbExitEditPresenceWindow.setSelected(false);
    }

    @FXML
    public void selectExit(){
        rbArrivalEditPresenceWindow.setSelected(false);
        rbExitEditPresenceWindow.setSelected(true);
    }

    @FXML
    public void selectPresent(){
        rbPresentEditPresenceWindow.setSelected(true);
        rbNotPresentEditPresenceWindow.setSelected(false);
    }

    @FXML
    public void selectNotPresent(){
        rbPresentEditPresenceWindow.setSelected(false);
        rbNotPresentEditPresenceWindow.setSelected(true);
    }

    @FXML
    public void editPresence(){
        String record = rbArrivalEditPresenceWindow.isSelected() ? "CHEGADA" : "SAÍDA";
        String status = rbPresentEditPresenceWindow.isSelected() ? "PRESENTE" : "NÃO PRESENTE";
        LocalDate presenceDate = dpPresenceDateEditPresenceWindow.getValue();
        String presenceHour = cbPresenceHourEditPresenceWindow.getValue();
        String presenceMinute = cbPresenceMinuteEditPresenceWindow.getValue();
        String nameContract = cbContractEditPresenceWindow.getValue();

        if(record.equals(presence.getRecord()) && status.equals(presence.getStatus()) && presenceDate.equals(presence.getPresenceDateTime().toLocalDate()) &&
           presence.getPresenceDateTime().getHour() == Integer.parseInt(presenceHour) && presence.getPresenceDateTime().getMinute() == Integer.parseInt(presenceMinute) &&
           (nameContract.equals(presence.getNameContract()) || nameContract.equals(contractsNames.getFirst()))){
            lbPushMsgEditPresenceWindow.setText("Não houve alterações!");
            hbPushMsgEditPresenceWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgEditPresenceWindow.setVisible(true);
            this.delayHidePushMsg();
        }else{
            if(record.equals(presence.getRecord())){
                record = presence.getRecord();
            }
            if(status.equals(presence.getStatus())){
                status = presence.getStatus();
            }
            if(presenceDate.equals(presence.getPresenceDateTime().toLocalDate())){
                presenceDate = presence.getPresenceDateTime().toLocalDate();
            }
            if(presence.getPresenceDateTime().getHour() == Integer.parseInt(presenceHour)){
                presenceHour = String.valueOf(presence.getPresenceDateTime().getHour());
            }
            if(presence.getPresenceDateTime().getMinute() == Integer.parseInt(presenceMinute)){
                presenceMinute = String.valueOf(presence.getPresenceDateTime().getMinute());
            }
            if(nameContract.equals(presence.getNameContract())){
                nameContract = presence.getNameContract();
            }

            try {
                if(!app.getServer().checkIfThereIsAnExistingPresenceWithTheRecord(presence.getCpfCollaborator(), presenceDate.atTime(LocalTime.of(Integer.parseInt(presenceHour), Integer.parseInt(presenceMinute))), presence.getRecord(), record)){
                    try {
                        app.getServer().updatePresence(presence, presence.getCpfCollaborator(), nameContract, record, status, presenceDate, Integer.parseInt(presenceHour), Integer.parseInt(presenceMinute));
                    } catch (ConnectionFailureDbException | PresenceDoesNotExistException | PresenceNullException e) {
                        lbPushMsgEditPresenceWindow.setText(e.getMessage());
                        hbPushMsgEditPresenceWindow.getStyleClass().setAll("push-msg-error");
                        hbPushMsgEditPresenceWindow.setVisible(true);
                        this.delayHidePushMsg();
                    } catch (PresenceUpdatedSuccessfullyException e) {
                        lbPushMsgEditPresenceWindow.setText(e.getMessage());
                        hbPushMsgEditPresenceWindow.getStyleClass().setAll("push-msg-success");
                        hbPushMsgEditPresenceWindow.setVisible(true);

                        new Thread(() -> {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ev) {
                                ev.printStackTrace();
                            }
                            Platform.runLater(() -> {
                                hbPushMsgEditPresenceWindow.setVisible(false);
                                ScreenManager sm = ScreenManager.getInstance();
                                sm.getCollaboratorDetailsScreenController().initializePresenceTable();
                                try {
                                    this.presence = app.getServer().getPresenceByCpfAndDateTime(presence.getCpfCollaborator(), presence.getRecordDateTime());
                                } catch (ConnectionFailureDbException | PresenceDoesNotExistException ignored) {}
                                this.clearFiels();
                                Stage stage = (Stage) sm.getEditPresenceScreenScene().getWindow();
                                stage.close();
                            });
                        }).start();
                    }
                }else{
                    lbPushMsgEditPresenceWindow.setText("Presença já existente!");
                    hbPushMsgEditPresenceWindow.getStyleClass().setAll("push-msg-error");
                    hbPushMsgEditPresenceWindow.setVisible(true);
                    this.delayHidePushMsg();
                }
            } catch (ConnectionFailureDbException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @FXML
    public void clearFiels(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dpPresenceDateEditPresenceWindow.setPromptText(LocalDate.now().format(dateTimeFormatter));
        cbPresenceHourEditPresenceWindow.getItems().clear();
        cbPresenceMinuteEditPresenceWindow.getItems().clear();
        cbContractEditPresenceWindow.getItems().clear();
    }

    @FXML
    public void initializeWindow(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        rbArrivalEditPresenceWindow.setSelected(presence.getRecord().equals("CHEGADA"));
        rbExitEditPresenceWindow.setSelected(!presence.getRecord().equals("CHEGADA"));
        rbPresentEditPresenceWindow.setSelected(presence.getStatus().equals("PRESENTE"));
        rbNotPresentEditPresenceWindow.setSelected(!presence.getStatus().equals("PRESENTE"));
        dpPresenceDateEditPresenceWindow.setValue(presence.getPresenceDateTime().toLocalDate());
        this.setCombosBoxOptions();
        for(int i = 1; i < hours.size(); i++){
            if(presence.getPresenceDateTime().getHour() == Integer.parseInt(hours.get(i))) cbPresenceHourEditPresenceWindow.setValue(hours.get(i));
        }
        for(int i = 1; i < minutes.size(); i++){
            if(presence.getPresenceDateTime().getMinute() == Integer.parseInt(minutes.get(i))) cbPresenceMinuteEditPresenceWindow.setValue(minutes.get(i));
        }
        for(String item : contractsNames){
            if(presence.getNameContract().equals(item)) cbContractEditPresenceWindow.setValue(item);
        }
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
        } catch (ConnectionFailureDbException ignored) {}
        cbPresenceHourEditPresenceWindow.getItems().addAll(hours);
        cbPresenceMinuteEditPresenceWindow.getItems().addAll(minutes);
        cbContractEditPresenceWindow.getItems().addAll(contractsNames);
    }

    void delayHidePushMsg(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ev) {
                ev.printStackTrace();
            }
            Platform.runLater(() -> {
                hbPushMsgEditPresenceWindow.setVisible(false);
            });
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgEditPresenceWindow.setVisible(false);
    }

}
