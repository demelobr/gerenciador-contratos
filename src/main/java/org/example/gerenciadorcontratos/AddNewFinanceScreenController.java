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
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.example.gerenciadorcontratos.UtilitiesLibrary.formatValue;

public class AddNewFinanceScreenController implements Initializable {
    private Application app;
    private List<String> types;
    private List<String> entriesClasses;
    private List<String> expensesClasses;
    private List<String> paymentMethods;
    private List<String> collaboratorsName;
    private List<String> collaboratorsCpf;
    private List<String> contractsName;

    public AddNewFinanceScreenController(){
        this.app = new Application();
        this.types = new ArrayList<>();
        this.entriesClasses = new ArrayList<>();
        this.expensesClasses = new ArrayList<>();
        this.paymentMethods = new ArrayList<>();
        this.collaboratorsName = new ArrayList<>();
        this.collaboratorsCpf = new ArrayList<>();
        this.contractsName = new ArrayList<>();
    }

    @FXML
    private Button btnBackAddNewFinanceWindow;

    @FXML
    private HBox btnClosePushMsgAddNewFinanceWindow;

    @FXML
    private HBox btnHelpAddNewFinanceWindow;

    @FXML
    private Button btnRegisterAddNewFinanceWindow;

    @FXML
    private ChoiceBox<String> cbCollaboratorAddNewFinanceWindow;

    @FXML
    private ChoiceBox<String> cbPaymentMethodAddNewFinanceWindow;

    @FXML
    private ChoiceBox<String> cbContractAddNewFinanceWindow;

    @FXML
    private ChoiceBox<String> cbTypeAddNewFinanceWindow;

    @FXML
    private ChoiceBox<String> cbCategoryAddNewFinanceWindow;

    @FXML
    private DatePicker dpFinanceDateAddNewFinanceWindow;

    @FXML
    private HBox hbPushMsgAddNewFinanceWindow;

    @FXML
    private ImageView imgDataBaseConnectionWindow;

    @FXML
    private Label lbPushMsgAddNewFinanceWindow;

    @FXML
    private RadioButton rbNoAddNewFinanceWindow;

    @FXML
    private RadioButton rbYesAddNewFinanceWindow;

    @FXML
    private TextField tfTitileAddNewFinanceWindow;

    @FXML
    private TextField tfValueAddNewFinanceWindow;

    @FXML
    private TextArea taNotesAddNewFinanceWindow;

    @FXML
    private VBox vbCollaboratorAddNewFinanceWindow;

    @FXML
    public void closePushMsg(MouseEvent event) {
        hbPushMsgAddNewFinanceWindow.setVisible(false);
    }

    @FXML
    public void goToBackFinanceScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.changeScreen("finance-menu-screen.fxml", "Gerenciador de Contratos - Finanças");
        sm.getFinancesScreenController().setDataWindow();
    }

    @FXML
    public void selectYesRb(){
        rbYesAddNewFinanceWindow.setSelected(true);
        rbNoAddNewFinanceWindow.setSelected(false);
        vbCollaboratorAddNewFinanceWindow.setVisible(true);
    }

    @FXML
    public void selectNoRb(){
        rbYesAddNewFinanceWindow.setSelected(false);
        rbNoAddNewFinanceWindow.setSelected(true);
        vbCollaboratorAddNewFinanceWindow.setVisible(false);
    }

    @FXML
    public void createFinance(){
        String title = tfTitileAddNewFinanceWindow.getText();
        String notes = taNotesAddNewFinanceWindow.getText();
        String type = cbTypeAddNewFinanceWindow.getValue();
        String financeClass = cbCategoryAddNewFinanceWindow.getValue();
        String paymentMethod = cbPaymentMethodAddNewFinanceWindow.getValue();
        String contractName = cbContractAddNewFinanceWindow.getValue();
        String value = tfValueAddNewFinanceWindow.getText();
        LocalDate date = dpFinanceDateAddNewFinanceWindow.getValue();
        String collaboratorName;
        if(rbYesAddNewFinanceWindow.isSelected()){
            collaboratorName = cbCollaboratorAddNewFinanceWindow.getValue();
        }else{
            collaboratorName = "NÃO INFORMADO";
        }

        if(types.isEmpty() || value.isEmpty()){
            lbPushMsgAddNewFinanceWindow.setText("Preencha todos os campos!");
            hbPushMsgAddNewFinanceWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddNewFinanceWindow.setVisible(true);
            this.delayHidePushMsg();
        }else{
            if(cbContractAddNewFinanceWindow.getValue().equals("----------")){
                lbPushMsgAddNewFinanceWindow.setText("Escolha um contrato!");
                hbPushMsgAddNewFinanceWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgAddNewFinanceWindow.setVisible(true);
                this.delayHidePushMsg();
            }else{
                if(cbCollaboratorAddNewFinanceWindow.getValue().equals("----------") && rbYesAddNewFinanceWindow.isSelected()){
                    lbPushMsgAddNewFinanceWindow.setText("Escolha um colaborador!");
                    hbPushMsgAddNewFinanceWindow.getStyleClass().setAll("push-msg-error");
                    hbPushMsgAddNewFinanceWindow.setVisible(true);
                    this.delayHidePushMsg();
                }else{
                    try {
                        try {
                            int index = cbCollaboratorAddNewFinanceWindow.getSelectionModel().getSelectedIndex();
                            if(collaboratorsName.get(index).equals("----------")) app.getServer().createFiance(new Finance(title, notes, contractName, type, financeClass, paymentMethod, date, LocalDateTime.now(), formatValue(value), "NÃO INFORMADO"));
                            else app.getServer().createFiance(new Finance(title, notes, contractName, type, financeClass, paymentMethod, date, LocalDateTime.now(), formatValue(value), collaboratorsCpf.get(index)));
                        } catch (ParseException e) {
                            lbPushMsgAddNewFinanceWindow.setText(e.getMessage());
                            hbPushMsgAddNewFinanceWindow.getStyleClass().setAll("push-msg-error");
                            hbPushMsgAddNewFinanceWindow.setVisible(true);
                            this.delayHidePushMsg();
                        }
                    } catch (FinanceNullException | InvalidFinanceAmountException | EmptyfieldsException |
                             ConnectionFailureDbException e) {
                        System.out.println(e.getMessage());
                        lbPushMsgAddNewFinanceWindow.setText(e.getMessage());
                        hbPushMsgAddNewFinanceWindow.getStyleClass().setAll("push-msg-error");
                        hbPushMsgAddNewFinanceWindow.setVisible(true);
                        this.delayHidePushMsg();
                    } catch (FinanceCreatedSuccessfullyException e) {
                        lbPushMsgAddNewFinanceWindow.setText(e.getMessage());
                        hbPushMsgAddNewFinanceWindow.getStyleClass().setAll("push-msg-success");
                        hbPushMsgAddNewFinanceWindow.setVisible(true);
                        new Thread(() -> {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ev) {
                                ev.printStackTrace();
                            }
                            Platform.runLater(() -> {
                                hbPushMsgAddNewFinanceWindow.setVisible(false);
                                this.resetWindow();
                                ScreenManager sm = ScreenManager.getInstance();
                                sm.changeScreen("finance-menu-screen.fxml", "Gerenciador de Contratos - Finanças");
                                sm.getFinancesScreenController().setDataWindow();
                            });
                        }).start();
                    }
                }
            }
        }
    }

    public void resetWindow(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        hbPushMsgAddNewFinanceWindow.setVisible(false);
        rbYesAddNewFinanceWindow.setSelected(false);
        rbNoAddNewFinanceWindow.setSelected(true);
        vbCollaboratorAddNewFinanceWindow.setVisible(false);
        tfTitileAddNewFinanceWindow.setText("");
        taNotesAddNewFinanceWindow.setText("");
        tfValueAddNewFinanceWindow.setText("");
        dpFinanceDateAddNewFinanceWindow.setValue(null);
        dpFinanceDateAddNewFinanceWindow.setPromptText(LocalDate.now().format(dateTimeFormatter));
        cbTypeAddNewFinanceWindow.getItems().clear();
        cbPaymentMethodAddNewFinanceWindow.getItems().clear();
        cbContractAddNewFinanceWindow.getItems().clear();
        cbCollaboratorAddNewFinanceWindow.getItems().clear();
        this.addOrRemoveListeners(false);
    }

    public void initializeWindow(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        tfTitileAddNewFinanceWindow.setPromptText("Salário do Colaborador João");
        taNotesAddNewFinanceWindow.setPromptText("8 câmeras Intelbras, cada uma no valor de R$135.");
        tfValueAddNewFinanceWindow.setPromptText("100,00");
        dpFinanceDateAddNewFinanceWindow.setPromptText(LocalDate.now().format(dateTimeFormatter));
        rbYesAddNewFinanceWindow.setSelected(false);
        rbNoAddNewFinanceWindow.setSelected(true);
        this.setCombosBoxOptions();
        cbPaymentMethodAddNewFinanceWindow.setValue(paymentMethods.get(0));
        cbTypeAddNewFinanceWindow.setValue(types.get(0));
        cbCategoryAddNewFinanceWindow.setValue(entriesClasses.get(0));
        cbContractAddNewFinanceWindow.setValue(contractsName.get(0));
        cbCollaboratorAddNewFinanceWindow.setValue(collaboratorsName.get(0));
    }

    private void setCombosBoxOptions(){
        types.clear();
        types.add("RECEITA");
        types.add("DESPESA");

        entriesClasses.clear();
        entriesClasses.add("VERBAS CONTRATUAIS");
        entriesClasses.add("ADTIVOS CONTRATUAIS");
        Collections.sort(entriesClasses);

        expensesClasses.clear();
        expensesClasses.add("MÃO DE OBRA");
        expensesClasses.add("MATERIAIS DE CONSTRUÇÃO");
        expensesClasses.add("EQUIPAMENTOS/EPIs");
        expensesClasses.add("IMPOSTOS ESTADUAIS");
        expensesClasses.add("IMPOSTOS FEDERAIS");
        expensesClasses.add("LOGÍSTICA");
        expensesClasses.add("TERCEIRIZAÇÕES");
        expensesClasses.add("MANUNTENÇÃO");
        Collections.sort(expensesClasses);

        paymentMethods.clear();
        paymentMethods.add("DINHEIRO");
        paymentMethods.add("PIX");
        paymentMethods.add("CARTÃO DÉBITO");
        paymentMethods.add("CARTÃO CRÉDITO");
        Collections.sort(paymentMethods);

        try {
            List<Contract> listOfAllContracts = app.getServer().listAllContracts();
            contractsName.clear();
            contractsName.add("----------");
            for(Contract contract : listOfAllContracts){
                contractsName.add(contract.getName());
            }
            Collections.sort(contractsName);
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
            Collections.sort(collaboratorsName);
            Collections.sort(collaboratorsCpf);
        } catch (ConnectionFailureDbException ignored) {}

        cbTypeAddNewFinanceWindow.getItems().addAll(types);
        cbCategoryAddNewFinanceWindow.getItems().addAll(entriesClasses);
        cbPaymentMethodAddNewFinanceWindow.getItems().addAll(paymentMethods);
        cbContractAddNewFinanceWindow.getItems().addAll(contractsName);
        cbCollaboratorAddNewFinanceWindow.getItems().addAll(collaboratorsName);
    }

    public void addOrRemoveListeners(boolean add){
        ChangeListener<String> comboBoxFinanceClassChangeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null){
                    if(newValue.equals("RECEITA")){
                        cbCategoryAddNewFinanceWindow.getItems().clear();
                        cbCategoryAddNewFinanceWindow.getItems().addAll(entriesClasses);
                        cbCategoryAddNewFinanceWindow.setValue(entriesClasses.get(0));
                    }else{
                        cbCategoryAddNewFinanceWindow.getItems().clear();
                        cbCategoryAddNewFinanceWindow.getItems().addAll(expensesClasses);
                        cbCategoryAddNewFinanceWindow.setValue(expensesClasses.get(0));
                    }
                }
            }
        };

        if(add){
            cbTypeAddNewFinanceWindow.valueProperty().addListener(comboBoxFinanceClassChangeListener);
        }else{
            cbTypeAddNewFinanceWindow.valueProperty().removeListener(comboBoxFinanceClassChangeListener);
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
                hbPushMsgAddNewFinanceWindow.setVisible(false);
            });
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgAddNewFinanceWindow.setVisible(false);
        rbYesAddNewFinanceWindow.setSelected(false);
        rbNoAddNewFinanceWindow.setSelected(true);
        vbCollaboratorAddNewFinanceWindow.setVisible(false);

        tfValueAddNewFinanceWindow.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[\\d,]*$")) {
                tfValueAddNewFinanceWindow.setText(oldValue);
            }
        });

        taNotesAddNewFinanceWindow.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > 255) {
                    taNotesAddNewFinanceWindow.setText(oldValue);
                }
            }
        });

    }
}
