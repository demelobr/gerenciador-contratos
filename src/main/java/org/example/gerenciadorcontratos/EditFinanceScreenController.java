package org.example.gerenciadorcontratos;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.mail.internet.ParseException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.example.gerenciadorcontratos.UtilitiesLibrary.capitalizeWords;
import static org.example.gerenciadorcontratos.UtilitiesLibrary.formatValue;

public class EditFinanceScreenController implements Initializable {
    private Application app;
    private Finance finance;
    private List<String> types;
    private List<String> paymentMethods;
    private List<String> collaboratorsName;
    private List<String> collaboratorsCpf;
    private List<String> contractsName;

    public EditFinanceScreenController() {
        this.app = new Application();
        this.types = new ArrayList<>();
        this.paymentMethods = new ArrayList<>();
        this.collaboratorsName = new ArrayList<>();
        this.collaboratorsCpf = new ArrayList<>();
        this.contractsName = new ArrayList<>();
    }

    public Finance getFinance() {
        return finance;
    }

    public void setFinance(Finance finance) {
        this.finance = finance;
    }

    @FXML
    private Button btnBackEditFinanceWindow;

    @FXML
    private HBox btnClosePushMsgEditFinanceWindow;

    @FXML
    private HBox btnHelpEditFinanceWindow;

    @FXML
    private Button btnRegisterEditFinanceWindow;

    @FXML
    private ChoiceBox<String> cbCollaboratorEditFinanceWindow;

    @FXML
    private ChoiceBox<String> cbContractEditFinanceWindow;

    @FXML
    private ChoiceBox<String> cbPaymentMethodEditFinanceWindow;

    @FXML
    private ChoiceBox<String> cbTypeEditFinanceWindow;

    @FXML
    private DatePicker dpFinanceDateEditFinanceWindow;

    @FXML
    private HBox hbPushMsgEditFinanceWindow;

    @FXML
    private ImageView imgDataBaseConnectionWindow;

    @FXML
    private Label lbPushMsgEditFinanceWindow;

    @FXML
    private RadioButton rbNoEditFinanceWindow;

    @FXML
    private RadioButton rbYesEditFinanceWindow;

    @FXML
    private TextField tfTitileEditFinanceWindow;

    @FXML
    private TextField tfValueEditFinanceWindow;

    @FXML
    private TextArea taNotesEditFinanceWindow;

    @FXML
    private VBox vbCollaboratorEditFinanceWindow;

    @FXML
    public void closePushMsg(MouseEvent event) {
        hbPushMsgEditFinanceWindow.setVisible(false);
    }

    @FXML
    public void goBackCollaboratorDetailsScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getCollaboratorDetailsScreenController().setDataScreen();
        sm.getCollaboratorDetailsScreenController().initializeFinanceTable();
        sm.getCollaboratorDetailsScreenController().initializePresenceTable();
        sm.getCollaboratorDetailsScreenController().initializeComboBoxsWindow();
        sm.getCollaboratorDetailsScreenController().addOrRemoveListeners(true);
        sm.changeScreen("collaborator-details-screen.fxml", "Gerenciador de Contratos - Informações do Colaborador");
    }

    @FXML
    public void selectYesRb(){
        rbYesEditFinanceWindow.setSelected(true);
        rbNoEditFinanceWindow.setSelected(false);
        vbCollaboratorEditFinanceWindow.setVisible(true);
    }

    @FXML
    public void selectNoRb(){
        rbYesEditFinanceWindow.setSelected(false);
        rbNoEditFinanceWindow.setSelected(true);
        vbCollaboratorEditFinanceWindow.setVisible(false);
    }

    @FXML
    public void editFinance(){
        String title = tfTitileEditFinanceWindow.getText();
        String notes = taNotesEditFinanceWindow.getText();
        String type = cbTypeEditFinanceWindow.getValue();
        String paymentMethod = cbPaymentMethodEditFinanceWindow.getValue();
        String contractName = cbContractEditFinanceWindow.getValue();
        String value = tfValueEditFinanceWindow.getText();
        LocalDate date = dpFinanceDateEditFinanceWindow.getValue();
        String collaboratorName;
        if(rbYesEditFinanceWindow.isSelected()){
            collaboratorName = cbCollaboratorEditFinanceWindow.getValue();
        }else{
            cbCollaboratorEditFinanceWindow.setValue(collaboratorsName.get(0));
        }

        try {
            if(title.isEmpty() && type.equals(finance.getType()) && contractName == null && value.isEmpty() && date == null && rbYesEditFinanceWindow.isSelected() && app.getServer().getCollaboratorByCpf(finance.getCollaboratorCpf()).getName().equals(cbCollaboratorEditFinanceWindow.getValue())){
                lbPushMsgEditFinanceWindow.setText("Não houve alterações!");
                hbPushMsgEditFinanceWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgEditFinanceWindow.setVisible(true);
                this.delayHidePushMsg();
            }else{
                if(title.isEmpty() || title.equals(finance.getTitle())){
                    title = finance.getTitle();
                }
                if(notes.equals(finance.getNotes())){
                    notes = finance.getNotes();
                }
                if(type.isEmpty() || type.equals(finance.getType())){
                    type = finance.getType();
                }
                if(paymentMethod.isEmpty() || paymentMethod.equals(finance.getPaymentMethod())){
                    paymentMethod = finance.getPaymentMethod();
                }
                if(contractName == null || contractName.equals(finance.getContractName())){
                    contractName = finance.getContractName();
                }
                if(value.isEmpty() || value.equals(finance.getValue())){
                    value = String.valueOf(finance.getValue());
                }
                if(date == null || date.equals(finance.getDate())){
                    date = finance.getDate();
                }

                try {
                    int index = cbCollaboratorEditFinanceWindow.getSelectionModel().getSelectedIndex();
                    if(collaboratorsName.get(index).equals("----------")) app.getServer().updateFiance(finance, title, notes, contractName, type, paymentMethod, date, LocalDateTime.now(), formatValue(value), "NÃO INFORMADO");
                    else app.getServer().updateFiance(finance, title, notes, contractName, type, paymentMethod, date, LocalDateTime.now(), formatValue(value), collaboratorsCpf.get(index));
                } catch (FinanceNullException | ParseException | FinanceDoesNotExistException |
                         ConnectionFailureDbException e) {
                    lbPushMsgEditFinanceWindow.setText(e.getMessage());
                    hbPushMsgEditFinanceWindow.getStyleClass().setAll("push-msg-error");
                    hbPushMsgEditFinanceWindow.setVisible(true);
                    this.delayHidePushMsg();
                } catch (FinanceUpdatedSuccessfullyException e) {
                    lbPushMsgEditFinanceWindow.setText(e.getMessage());
                    hbPushMsgEditFinanceWindow.getStyleClass().setAll("push-msg-success");
                    hbPushMsgEditFinanceWindow.setVisible(true);
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ev) {
                            ev.printStackTrace();
                        }
                        Platform.runLater(() -> {
                            hbPushMsgEditFinanceWindow.setVisible(false);
                            this.resetWindow();
                            ScreenManager sm = ScreenManager.getInstance();
                            sm.getCollaboratorDetailsScreenController().setDataScreen();
                            sm.getCollaboratorDetailsScreenController().initializeFinanceTable();
                            sm.getCollaboratorDetailsScreenController().initializePresenceTable();
                            sm.getCollaboratorDetailsScreenController().initializeComboBoxsWindow();
                            sm.getCollaboratorDetailsScreenController().addOrRemoveListeners(true);
                            sm.changeScreen("collaborator-details-screen.fxml", "Gerenciador de Contratos - Informações do Colaborador");
                        });
                    }).start();
                }
            }
        } catch (ConnectionFailureDbException | CollaboratorDoesNotExistException e) {
            lbPushMsgEditFinanceWindow.setText(e.getMessage());
            hbPushMsgEditFinanceWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgEditFinanceWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    public void resetWindow(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        hbPushMsgEditFinanceWindow.setVisible(false);
        rbYesEditFinanceWindow.setSelected(false);
        rbNoEditFinanceWindow.setSelected(true);
        vbCollaboratorEditFinanceWindow.setVisible(false);
        tfTitileEditFinanceWindow.setText("");
        taNotesEditFinanceWindow.setText("");
        tfValueEditFinanceWindow.setText("");
        dpFinanceDateEditFinanceWindow.setValue(null);
        dpFinanceDateEditFinanceWindow.setPromptText(LocalDate.now().format(dateTimeFormatter));
        cbTypeEditFinanceWindow.getItems().clear();
        cbPaymentMethodEditFinanceWindow.getItems().clear();
        cbContractEditFinanceWindow.getItems().clear();
        cbCollaboratorEditFinanceWindow.getItems().clear();
    }

    public void initializeWindow(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        tfTitileEditFinanceWindow.setPromptText(capitalizeWords(finance.getTitle()));
        taNotesEditFinanceWindow.setText(capitalizeWords(finance.getNotes()));
        tfValueEditFinanceWindow.setPromptText(String.format("%.2f", finance.getValue()));
        dpFinanceDateEditFinanceWindow.setPromptText(finance.getDate().format(dateTimeFormatter));

        this.setCombosBoxOptions();

        if(finance.getCollaboratorCpf().equals("NÃO INFORMADO")){
            rbYesEditFinanceWindow.setSelected(false);
            rbNoEditFinanceWindow.setSelected(true);
            cbCollaboratorEditFinanceWindow.setValue(collaboratorsName.get(0));
        }else{
            rbYesEditFinanceWindow.setSelected(true);
            rbNoEditFinanceWindow.setSelected(false);
            for (int i = 0; i < collaboratorsName.size(); i++){
                try {
                    if(collaboratorsName.get(i).equals(app.getServer().getCollaboratorByCpf(finance.getCollaboratorCpf()).getName())) cbCollaboratorEditFinanceWindow.setValue(collaboratorsName.get(i));
                } catch (ConnectionFailureDbException | CollaboratorDoesNotExistException e) {
                    lbPushMsgEditFinanceWindow.setText(e.getMessage());
                    hbPushMsgEditFinanceWindow.getStyleClass().setAll("push-msg-error");
                    hbPushMsgEditFinanceWindow.setVisible(true);
                    this.delayHidePushMsg();
                }
            }
            vbCollaboratorEditFinanceWindow.setVisible(true);
        }

        if(finance.getType().equals("RECEITA")) cbTypeEditFinanceWindow.setValue(types.get(0));
        else cbTypeEditFinanceWindow.setValue(types.get(1));

        switch (finance.getPaymentMethod()){
            case "DINHEIRO" -> cbPaymentMethodEditFinanceWindow.setValue(paymentMethods.get(0));
            case "PIX" -> cbPaymentMethodEditFinanceWindow.setValue(paymentMethods.get(1));
            case "CARTÃO DÉBITO" -> cbPaymentMethodEditFinanceWindow.setValue(paymentMethods.get(2));
            case "CARTÃO CREDITO" -> cbPaymentMethodEditFinanceWindow.setValue(paymentMethods.get(3));
        }

        for(int i = 0; i < contractsName.size(); i++){
            try {
                if(contractsName.get(i).equals(app.getServer().getContractByName(finance.getContractName()).getName())) cbContractEditFinanceWindow.setValue(contractsName.get(i));
            } catch (ConnectionFailureDbException | ContractDoesNotExistException e) {
                lbPushMsgEditFinanceWindow.setText(e.getMessage());
                hbPushMsgEditFinanceWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgEditFinanceWindow.setVisible(true);
                this.delayHidePushMsg();
            }
        }
    }

    private void setCombosBoxOptions(){
        types.clear();
        types.add("RECEITA");
        types.add("DESPESA");

        paymentMethods.clear();
        paymentMethods.add("DINHEIRO");
        paymentMethods.add("PIX");
        paymentMethods.add("CARTÃO DÉBITO");
        paymentMethods.add("CARTÃO CRÉDITO");

        try {
            List<Contract> listOfAllContracts = app.getServer().listAllContracts();
            contractsName.clear();
            contractsName.add("----------");
            for(Contract contract : listOfAllContracts){
                contractsName.add(contract.getName());
            }
        } catch (ConnectionFailureDbException ignored) {}

        try {
            List<Collaborator> listOfAllCollaborators = app.getServer().listAllCollaborators();
            collaboratorsName.clear();
            collaboratorsName.add("----------");
            collaboratorsCpf.clear();
            collaboratorsCpf.add("----------");
            for(Collaborator collaborator : listOfAllCollaborators){
                collaboratorsName.add(collaborator.getName());
                collaboratorsCpf.add(collaborator.getCpf());
            }
        } catch (ConnectionFailureDbException ignored) {}

        cbTypeEditFinanceWindow.getItems().addAll(types);
        cbPaymentMethodEditFinanceWindow.getItems().addAll(paymentMethods);
        cbContractEditFinanceWindow.getItems().addAll(contractsName);
        cbCollaboratorEditFinanceWindow.getItems().addAll(collaboratorsName);
    }

    private void delayHidePushMsg(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ev) {
                ev.printStackTrace();
            }
            Platform.runLater(() -> {
                hbPushMsgEditFinanceWindow.setVisible(false);
            });
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgEditFinanceWindow.setVisible(false);

        tfValueEditFinanceWindow.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[\\d,.]*$")) {
                tfValueEditFinanceWindow.setText(oldValue);
            }
        });

        taNotesEditFinanceWindow.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > 255) {
                    taNotesEditFinanceWindow.setText(oldValue);
                }
            }
        });

    }

}
