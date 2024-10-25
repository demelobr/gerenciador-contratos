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

public class AddNewContractScreenController implements Initializable {
    Application app;
    private List<String> collaboratorsNames;

    public AddNewContractScreenController() {
        this.app = new Application();
        this.collaboratorsNames = new ArrayList<>();
    }

    @FXML
    private Button btnBackAddNewContractWindow;

    @FXML
    private Button btnChooseContractFileAddNewContractWindow;

    @FXML
    private HBox btnClosePushMsgAddNewContractWindow;

    @FXML
    private HBox btnHelpAddNewCollaboratorWindow;

    @FXML
    private Button btnRegisterAddNewContractWindow;

    @FXML
    private DatePicker dpEndDateAddNewContractWindow;

    @FXML
    private DatePicker dpExpectedEndDateAddNewContractWindow;

    @FXML
    private DatePicker dpExpectedStartDateAddNewContractWindow;

    @FXML
    private DatePicker dpStartDateAddNewContractWindow;

    @FXML
    private HBox hbPushMsgAddNewContractWindow;

    @FXML
    private ImageView imgDataBaseConnectionWindow;

    @FXML
    private ChoiceBox<String> cbEngineerAddNewContractWindow;

    @FXML
    private Label lbContractFileUrlAddNewContractWindow;

    @FXML
    private Label lbPushMsgAddNewContractWindow;

    @FXML
    private TextArea taDescriptonAddNewContractWindow;

    @FXML
    private TextField tfAddressAddNewContractWindow;

    @FXML
    private TextField tfNameAddNewContractWindow;

    @FXML
    public void closePushMsg(MouseEvent event) {
        hbPushMsgAddNewContractWindow.setVisible(false);
    }

    @FXML
    public void goToBackContractScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getContractsScreenController().setDataScreen(capitalizeWords(sm.getContractsScreenController().getUser().getName()));
        sm.changeScreen("contract-menu-screen.fxml", "Gerenciador de Contratos - Contratos");
    }

    @FXML
    public void createContract(){
        String name = tfNameAddNewContractWindow.getText();
        String address = tfAddressAddNewContractWindow.getText();
        String description = taDescriptonAddNewContractWindow.getText();
        String engineer = cbEngineerAddNewContractWindow.getValue();
        String contractFileUrl = lbContractFileUrlAddNewContractWindow.getText();
        LocalDate expectedStartDate = dpExpectedStartDateAddNewContractWindow.getValue();
        LocalDate expectedEndDate = dpExpectedEndDateAddNewContractWindow.getValue();
        LocalDate startDate = dpStartDateAddNewContractWindow.getValue();
        LocalDate endDate = dpEndDateAddNewContractWindow.getValue();

        Contract contract = new Contract(name, description, address, engineer, contractFileUrl, expectedStartDate, expectedEndDate, startDate, endDate);
        try {
            if(app.getServer().checkContractData(contract)){
                app.getServer().createContract(contract);
            }
        } catch (InvalidBudgetException | ContractNullException | InvalidContractException |
                 ContractWithThisNameAlreadyExistsException | ConnectionFailureDbException |
                 StartDateAfterEndDateException | EmptyfieldsException | CopyFileFailedException e) {
            lbPushMsgAddNewContractWindow.setText(e.getMessage());
            hbPushMsgAddNewContractWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddNewContractWindow.setVisible(true);
            this.delayHidePushMsg();
        } catch (ContractCreatedSuccessfullyException e) {
            lbPushMsgAddNewContractWindow.setText(e.getMessage());
            hbPushMsgAddNewContractWindow.getStyleClass().setAll("push-msg-success");
            hbPushMsgAddNewContractWindow.setVisible(true);
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ev) {
                    ev.printStackTrace();
                }
                Platform.runLater(() -> {
                    hbPushMsgAddNewContractWindow.setVisible(false);
                    this.resetWindow();
                    ScreenManager sm = ScreenManager.getInstance();
                    sm.getContractsScreenController().setDataScreen(capitalizeWords(sm.getContractsScreenController().getUser().getName()));
                    sm.getContractsScreenController().initializeTable();
                    sm.changeScreen("contract-menu-screen.fxml", "Gerenciador de Contratos - Contratos");
                });
            }).start();
        }
    }

    @FXML
    public void chooseContractFile(ActionEvent event){
        Stage stg = ScreenManager.getStg();
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Documentos", "*.docx", "*.pdf"));
        List<File> documento = fc.showOpenMultipleDialog(stg);
        if(documento != null && documento.size() == 1){
            lbContractFileUrlAddNewContractWindow.setText(documento.get(0).getAbsolutePath());
        }else{
            lbPushMsgAddNewContractWindow.setText("Necessário escolher 1 documento!");
            hbPushMsgAddNewContractWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddNewContractWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    @FXML
    public void goToHelpScreen(){
        lbPushMsgAddNewContractWindow.setText("Em Desenvolvimento!");
        hbPushMsgAddNewContractWindow.getStyleClass().setAll("push-msg-info");
        hbPushMsgAddNewContractWindow.setVisible(true);
        this.delayHidePushMsg();
    }

    public void resetWindow(){
        hbPushMsgAddNewContractWindow.setVisible(false);
        tfNameAddNewContractWindow.setText("");
        tfAddressAddNewContractWindow.setText("");
        cbEngineerAddNewContractWindow.getItems().clear();
        lbContractFileUrlAddNewContractWindow.setText("");
        taDescriptonAddNewContractWindow.setText("");
        dpExpectedStartDateAddNewContractWindow.setValue(null);
        dpExpectedEndDateAddNewContractWindow.setValue(null);
        dpStartDateAddNewContractWindow.setValue(null);
        dpEndDateAddNewContractWindow.setValue(null);
    }

    public void initializeWindow(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lbContractFileUrlAddNewContractWindow.setText("");
        dpExpectedStartDateAddNewContractWindow.setPromptText(LocalDate.now().format(dateTimeFormatter));
        dpExpectedEndDateAddNewContractWindow.setPromptText(LocalDate.now().format(dateTimeFormatter));
        dpStartDateAddNewContractWindow.setPromptText(LocalDate.now().format(dateTimeFormatter));
        dpEndDateAddNewContractWindow.setPromptText(LocalDate.now().format(dateTimeFormatter));
        this.setCombosBoxOptions();
        cbEngineerAddNewContractWindow.setValue(collaboratorsNames.getFirst());
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
        cbEngineerAddNewContractWindow.getItems().addAll(collaboratorsNames);
    }

    void delayHidePushMsg(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ev) {
                ev.printStackTrace();
            }
            Platform.runLater(() -> {
                hbPushMsgAddNewContractWindow.setVisible(false);
            });
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgAddNewContractWindow.setVisible(false);
    }
}
