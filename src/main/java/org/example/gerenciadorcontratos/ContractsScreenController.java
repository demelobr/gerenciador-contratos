package org.example.gerenciadorcontratos;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.example.gerenciadorcontratos.UtilitiesLibrary.capitalizeWords;

public class ContractsScreenController implements Initializable {
    private Application app;
    private User user;
    private ObservableList<Contract> listOfContracts;

    public ContractsScreenController() {
        this.app = new Application();
        this.listOfContracts = FXCollections.observableArrayList();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private Button btnAddContractsWindow;

    @FXML
    private HBox btnClosePushMsgContractsWindow;

    @FXML
    private Button btnContractFileContractsWindow;

    @FXML
    private HBox btnHelpContractsWindow;

    @FXML
    private Button btnMoreContractsWindow;

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
    private HBox hbPushMsgContractsWindow;

    @FXML
    private HBox hbSearchContractsWindow;

    @FXML
    private VBox btnBalanceContractsWindow;

    @FXML
    private VBox btnEntriesContractsWindow;

    @FXML
    private VBox btnExpensesContractsWindow;

    @FXML
    private ImageView imgDataBaseConnectionContractsWindow;

    @FXML
    private Label lbAddressContractsWindow;

    @FXML
    private Label lbBalanceValueContractsWindow;

    @FXML
    private Label lbDateTimeContractsWindow;

    @FXML
    private Label lbEndDateContractsWindow;

    @FXML
    private Label lbEngineerContractsWindow;

    @FXML
    private Label lbEntriesValueContractsWindow;

    @FXML
    private Label lbExpectedEndDateContractsWindow;

    @FXML
    private Label lbExpectedStartDateContractsWindow;

    @FXML
    private Label lbExpensesValueContractsWindow;

    @FXML
    private Label lbNameContractsWindow;

    @FXML
    private Label lbPushMsgContractsWindow;

    @FXML
    private Label lbResultsFoundContractsWindow;

    @FXML
    private Label lbStartDateContractsWindow;

    @FXML
    private Label lbUserNameWindow;

    @FXML
    private TableColumn<Contract, String> tcAddressContractsWindow;

    @FXML
    private TableColumn<Contract, String> tcEndDateContractsWindow;

    @FXML
    private TableColumn<Contract, String> tcEngineerContractsWindow;

    @FXML
    private TableColumn<Contract, String> tcNameContractsWindow;

    @FXML
    private TableColumn<Contract, String> tcStartDateContractsWindow;

    @FXML
    private TextField tfSearchContractsWindow;

    @FXML
    private TableView<Contract> tvContractsWindow;

    @FXML
    void closePushMsg(MouseEvent event) {
        hbPushMsgContractsWindow.setVisible(false);
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
    public void goToAddContractScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getAddNewContractScreenController().initializeWindow();
        sm.changeScreen("add-new-contract-screen.fxml", "Gerenciador de Contratos - Registrar Contrato");
    }

    @FXML
    public void goToMoreInfoScreen(){
        Contract selectedContract = tvContractsWindow.getSelectionModel().getSelectedItem();
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getContractDetailsScreenController().setContract(selectedContract);
        sm.getContractDetailsScreenController().setUser(user);
        try {
            sm.getContractDetailsScreenController().setDataWindow();
        } catch (ConnectionFailureDbException e) {
            lbPushMsgContractsWindow.setText(e.getMessage());
            hbPushMsgContractsWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgContractsWindow.setVisible(true);
            this.delayHidePushMsg();
        }
        sm.changeScreen("contract-details-screen.fxml", "Gerenciador de Contratos - Contratos");
    }

    @FXML
    public void goToFinanceDatailsScreenWithFilterComplete(){
        Contract selectContract = tvContractsWindow.getSelectionModel().getSelectedItem();
        if(selectContract != null){
            this.resetWindow();
            ScreenManager sm = ScreenManager.getInstance();
            sm.getFinanceDetailsScreenController().setUser(user);
            sm.getFinanceDetailsScreenController().setContractName(selectContract.getName());
            sm.getFinanceDetailsScreenController().setYear(null);
            sm.getFinanceDetailsScreenController().setFilter("complete");
            sm.getFinanceDetailsScreenController().initializeComboBoxsWindow();
            sm.getFinanceDetailsScreenController().addOrRemoveListeners(true);
            sm.getFinanceDetailsScreenController().initializeTable();
            sm.getFinanceDetailsScreenController().setFiltersSearch();
            sm.changeScreen("finance-details-screen.fxml", "Gerenciador de Contratos - Detalhes das Finanças");

        }else{
            lbPushMsgContractsWindow.setText("Selecione um contrato!");
            hbPushMsgContractsWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgContractsWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    @FXML
    public void goToFinanceDatailsScreenWithFilterEntries(){
        Contract selectContract = tvContractsWindow.getSelectionModel().getSelectedItem();
        if(selectContract != null){
            this.resetWindow();
            ScreenManager sm = ScreenManager.getInstance();
            sm.getFinanceDetailsScreenController().setUser(user);
            sm.getFinanceDetailsScreenController().setContractName(selectContract.getName());
            sm.getFinanceDetailsScreenController().setYear(null);
            sm.getFinanceDetailsScreenController().setFilter("entries");
            sm.getFinanceDetailsScreenController().initializeComboBoxsWindow();
            sm.getFinanceDetailsScreenController().addOrRemoveListeners(true);
            sm.getFinanceDetailsScreenController().initializeTable();
            sm.getFinanceDetailsScreenController().setFiltersSearch();
            sm.changeScreen("finance-details-screen.fxml", "Gerenciador de Contratos - Detalhes das Finanças");
        }else{
            lbPushMsgContractsWindow.setText("Selecione um contrato!");
            hbPushMsgContractsWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgContractsWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    @FXML
    public void goToFinanceDatailsScreenWithFilterExpenses(){
        Contract selectContract = tvContractsWindow.getSelectionModel().getSelectedItem();
        if(selectContract != null){
            this.resetWindow();
            ScreenManager sm = ScreenManager.getInstance();
            sm.getFinanceDetailsScreenController().setUser(user);
            sm.getFinanceDetailsScreenController().setContractName(selectContract.getName());
            sm.getFinanceDetailsScreenController().setYear(null);
            sm.getFinanceDetailsScreenController().setFilter("expenses");
            sm.getFinanceDetailsScreenController().initializeComboBoxsWindow();
            sm.getFinanceDetailsScreenController().addOrRemoveListeners(true);
            sm.getFinanceDetailsScreenController().initializeTable();
            sm.getFinanceDetailsScreenController().setFiltersSearch();
            sm.changeScreen("finance-details-screen.fxml", "Gerenciador de Contratos - Detalhes das Finanças");

        }else{
            lbPushMsgContractsWindow.setText("Selecione um contrato!");
            hbPushMsgContractsWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgContractsWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    @FXML
    public void goToViewContractFile(){
        Contract selectedContract = tvContractsWindow.getSelectionModel().getSelectedItem();
        String filePath = selectedContract.getContractFile();

        if (filePath != null && !filePath.isEmpty()) {
            File file = new File(filePath);
            if (file.exists()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    lbPushMsgContractsWindow.setText("Erro ao abrir o arquivo!");
                    hbPushMsgContractsWindow.getStyleClass().setAll("push-msg-error");
                    hbPushMsgContractsWindow.setVisible(true);
                    this.delayHidePushMsg();
                }
            } else {
                lbPushMsgContractsWindow.setText("Arquivo não encontrado!");
                hbPushMsgContractsWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgContractsWindow.setVisible(true);
                this.delayHidePushMsg();
            }
        } else {
            lbPushMsgContractsWindow.setText("Nenhum contrato foi anexado!");
            hbPushMsgContractsWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgContractsWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    @FXML
    public void goToHelpScreen(){
        lbPushMsgContractsWindow.setText("Em Desenvolvimento!");
        hbPushMsgContractsWindow.getStyleClass().setAll("push-msg-info");
        hbPushMsgContractsWindow.setVisible(true);
        this.delayHidePushMsg();
    }

    public void setDataScreen(String name){
        lbUserNameWindow.setText(String.format("%s!", capitalizeWords(name)));
    }

    private double getContractEntriesValue(String contractName) throws ConnectionFailureDbException {
        double entriesValue = 0.0;
        for(Finance finance : app.getServer().listAllFinancesWithFilters("entries", "", null, null, contractName, "", "")){
            entriesValue += finance.getValue();
        }
        return entriesValue;
    }

    private double getContractExpensesValue(String contractName) throws ConnectionFailureDbException {
        double expensesValue = 0.0;
        for(Finance finance : app.getServer().listAllFinancesWithFilters("expenses", "", null, null, contractName, "", "")){
            expensesValue += finance.getValue();
        }
        return expensesValue;
    }

    private void updateClock() {
        LocalDateTime dateTimeAtual = LocalDateTime.now();
        Locale locale = new Locale("pt", "BR");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, HH:mm", locale);
        String dataHoraFormatada = dateTimeAtual.format(formatter);
        lbDateTimeContractsWindow.setText(dataHoraFormatada);
    }

    public void initializeTable(){
        tvContractsWindow.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        tcNameContractsWindow.setCellValueFactory(new PropertyValueFactory<Contract, String>("name"));
        tcAddressContractsWindow.setCellValueFactory(new PropertyValueFactory<Contract, String>("address"));
        tcEngineerContractsWindow.setCellValueFactory(new PropertyValueFactory<Contract, String>("engineer"));
        tcStartDateContractsWindow.setCellValueFactory(new PropertyValueFactory<Contract, String>("startDate"));
        tcEndDateContractsWindow.setCellValueFactory(new PropertyValueFactory<Contract, String>("endDate"));

        tcEngineerContractsWindow.setCellValueFactory(cellData ->{
            if(cellData.getValue().getEngineer().equals("----------")){
                return  new javafx.beans.property.SimpleStringProperty("NÃO INFORMADO");
            }else{
                return  new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEngineer().toUpperCase());
            }
        });

        tcStartDateContractsWindow.setCellValueFactory(cellData -> {
            if(cellData.getValue().getStartDate() != null){
                LocalDate dateTime = cellData.getValue().getStartDate();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return  new javafx.beans.property.SimpleStringProperty(dateTime.format(dateTimeFormatter));
            }else{
                return  new javafx.beans.property.SimpleStringProperty("NÃO INFORMADO");
            }
        });

        tcEndDateContractsWindow.setCellValueFactory(cellData -> {
            if(cellData.getValue().getEndDate() != null){
                LocalDate dateTime = cellData.getValue().getEndDate();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return  new javafx.beans.property.SimpleStringProperty(dateTime.format(dateTimeFormatter));
            }else{
                return  new javafx.beans.property.SimpleStringProperty("NÃO INFORMADO");
            }
        });

        try (Connection conn = ConnectionFactory.getConnection()){
            imgDataBaseConnectionContractsWindow.setVisible(true);
            try {
                tvContractsWindow.setItems(this.updateTable());
                lbResultsFoundContractsWindow.setText(String.format("%d resultado(s) encontrado(s)", tvContractsWindow.getItems().size()));
                this.clearFields();
            } catch (Exception e) {
                lbPushMsgContractsWindow.setText(e.getMessage());
                hbPushMsgContractsWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgContractsWindow.setVisible(true);
                this.delayHidePushMsg();
            }
        } catch (ConnectionFailureDbException | SQLException e) {
            imgDataBaseConnectionContractsWindow.setVisible(false);
            for(int i = 0; i < tvContractsWindow.getItems().size(); i++){
                tvContractsWindow.getItems().clear();
            }
            lbResultsFoundContractsWindow.setText(String.format("%d resultado(s) encontrado(s)", tvContractsWindow.getItems().size()));
            this.clearFields();
        }

    }

    public ObservableList<Contract> updateTable(){
        try {
            listOfContracts = FXCollections.observableArrayList(app.getServer().listAllContracts());
        } catch (ConnectionFailureDbException e) {
            lbPushMsgContractsWindow.setText(e.getMessage());
            hbPushMsgContractsWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgContractsWindow.setVisible(true);
            this.delayHidePushMsg();
        }
        return listOfContracts;
    }

    private ObservableList<Contract> searchContract(){
        ObservableList<Contract> searchResult = FXCollections.observableArrayList();
        for(Contract contract : listOfContracts){
            if(contract.getName().toUpperCase().contains(tfSearchContractsWindow.getText().toUpperCase()) ||
                    contract.getAddress().toUpperCase().contains(tfSearchContractsWindow.getText().toUpperCase()) ||
                    contract.getEngineer().toUpperCase().contains(tfSearchContractsWindow.getText().toUpperCase())){
                searchResult.add(contract);
            }
        }
        return searchResult;
    }

    private void setFieldsOfCard(Contract contract) throws ConnectionFailureDbException {
        btnMoreContractsWindow.setDisable(false);
        btnContractFileContractsWindow.setDisable(false);
        btnEntriesContractsWindow.setDisable(false);
        btnExpensesContractsWindow.setDisable(false);
        btnBalanceContractsWindow.setDisable(false);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lbNameContractsWindow.setText(capitalizeWords(contract.getName()));
        lbAddressContractsWindow.setText(capitalizeWords(contract.getAddress()));
        if(contract.getEngineer().equals("----------")) lbEngineerContractsWindow.setText("Não Informado");
        else lbEngineerContractsWindow.setText(capitalizeWords(contract.getEngineer()));
        lbStartDateContractsWindow.setText(dateTimeFormatter.format(contract.getStartDate()));
        lbExpectedStartDateContractsWindow.setText(dateTimeFormatter.format(contract.getExpectedStartDate()));
        lbEndDateContractsWindow.setText(dateTimeFormatter.format(contract.getEndDate()));
        lbExpectedEndDateContractsWindow.setText(dateTimeFormatter.format(contract.getExpectedEndDate()));
        lbEntriesValueContractsWindow.setText(String.format("%.2f", this.getContractEntriesValue(contract.getName())));
        lbExpensesValueContractsWindow.setText(String.format("%.2f", this.getContractExpensesValue(contract.getName())));
        double balance = this.getContractEntriesValue(contract.getName()) - this.getContractExpensesValue(contract.getName());
        lbBalanceValueContractsWindow.setText(String.format("%.2f", balance));
        if(balance > 0){
            lbBalanceValueContractsWindow.setStyle("-fx-text-fill: #55ff7f;");
        }else if(balance == 0){
            lbBalanceValueContractsWindow.setStyle("-fx-text-fill: #05a1f5;");
        }else{
            lbBalanceValueContractsWindow.setStyle("-fx-text-fill: #ff5580;");
        }
    }

    private void selectAndSetContract() throws ConnectionFailureDbException {
        Contract selectContract = tvContractsWindow.getSelectionModel().getSelectedItem();
        if(selectContract != null){
            this.setFieldsOfCard(selectContract);
        }else{
            lbPushMsgContractsWindow.setText("Selecione um contrato!");
            hbPushMsgContractsWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgContractsWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    private void clearFields(){
        lbNameContractsWindow.setText("");
        lbAddressContractsWindow.setText("");
        lbEngineerContractsWindow.setText("");
        lbStartDateContractsWindow.setText("");
        lbExpectedStartDateContractsWindow.setText("");
        lbEndDateContractsWindow.setText("");
        lbExpectedEndDateContractsWindow.setText("");
        lbEntriesValueContractsWindow.setText("");
        lbExpensesValueContractsWindow.setText("");
        lbBalanceValueContractsWindow.setText("");
    }

    private void resetWindow(){
        hbPushMsgContractsWindow.setVisible(false);
        lbNameContractsWindow.setText("");
        lbAddressContractsWindow.setText("");
        lbEngineerContractsWindow.setText("");
        lbStartDateContractsWindow.setText("");
        lbExpectedStartDateContractsWindow.setText("");
        lbEndDateContractsWindow.setText("");
        lbExpectedEndDateContractsWindow.setText("");
        lbEntriesValueContractsWindow.setText("");
        lbExpensesValueContractsWindow.setText("");
        lbBalanceValueContractsWindow.setText("");
        btnMoreContractsWindow.setDisable(true);
        btnContractFileContractsWindow.setDisable(true);
        btnEntriesContractsWindow.setDisable(true);
        btnExpensesContractsWindow.setDisable(true);
        btnBalanceContractsWindow.setDisable(true);
        this.initializeTable();
    }

    private void delayHidePushMsg(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ev) {
                ev.printStackTrace();
            }
            Platform.runLater(() -> {
                hbPushMsgContractsWindow.setVisible(false);
            });
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgContractsWindow.setVisible(false);
        btnMoreContractsWindow.setDisable(true);
        btnContractFileContractsWindow.setDisable(true);
        btnEntriesContractsWindow.setDisable(true);
        btnExpensesContractsWindow.setDisable(true);
        btnBalanceContractsWindow.setDisable(true);

        this.clearFields();
        this.initializeTable();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> this.updateClock()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        tfSearchContractsWindow.focusedProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue){
                hbSearchContractsWindow.getStyleClass().setAll("shadow","input-box");
            }else{
                hbSearchContractsWindow.getStyleClass().remove("shadow");
            }
        });

        tfSearchContractsWindow.setOnKeyReleased((KeyEvent event) -> {
            tvContractsWindow.setItems(this.searchContract());
            lbResultsFoundContractsWindow.setText(String.format("%d resultado(s) encontrado(s)", tvContractsWindow.getItems().size()));
        });

        tvContractsWindow.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()){
                case UP, DOWN -> {
                    try {
                        this.selectAndSetContract();
                    } catch (ConnectionFailureDbException e) {
                        throw new RuntimeException(e);
                    }
                }
                case M -> this.goToMoreInfoScreen();
                case V -> this.goToViewContractFile();
                case R -> this.goToAddContractScreen();
                case H -> this.goToHelpScreen();
            }
        });

        tvContractsWindow.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                try {
                    this.selectAndSetContract();
                } catch (ConnectionFailureDbException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
