package org.example.gerenciadorcontratos;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.example.gerenciadorcontratos.UtilitiesLibrary.capitalizeWords;

public class FinanceDetailsScreenController implements Initializable {
    private Application app;
    private User user;
    private String filter;
    private List<String> contracts;
    private ObservableList<Finance> listOfFinances;
    private ObservableList<Finance> listOfWithdrawalsFinances;
    private double entries;
    private double expenses;

    public FinanceDetailsScreenController() {
        this.app = new Application();
        this.contracts = new ArrayList<>();
        this.listOfFinances = FXCollections.observableArrayList();
        this.listOfWithdrawalsFinances = FXCollections.observableArrayList();
        this.entries = 0.0;
        this.expenses = 0.0;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @FXML
    private Button btnBackDetailsFinancesWindow;

    @FXML
    private HBox btnClosePushMsgDetailsFinancesWindow;

    @FXML
    private Button btnDeleteDetailsFinancesWindow;

    @FXML
    private Button btnEditDetailsFinancesWindow;

    @FXML
    private Button btnGenerateReportDetailsFinancesWindow;

    @FXML
    private HBox btnHelpDetailsFinancesWindow;

    @FXML
    private ChoiceBox<String> cbContractDetailsFinancesWindow;

    @FXML
    private DatePicker dpEndPeriodDetailsFinancesWindow;

    @FXML
    private DatePicker dpStartPeriodDetailsFinancesWindow;

    @FXML
    private HBox hbPushMsgDetailsFinancesWindow;

    @FXML
    private ImageView imgDataBaseConnectionDetailsFinancesWindow;

    @FXML
    private Label lbBalanceTypeDetailsFinancesWindow;

    @FXML
    private Label lbBalanceValueDetailsFinancesWindow;

    @FXML
    private Label lbContractDetailsFinancesWindow;

    @FXML
    private Label lbPaymentDateDetailsFinancesWindow;

    @FXML
    private Label lbPaymentMethodDetailsFinancesWindow;

    @FXML
    private Label lbPushMsgDetailsFinancesWindow;

    @FXML
    private Label lbResultsFoundDetailsFinancesWindow;

    @FXML
    private Label lbTitleDetailsFinancesWindow;

    @FXML
    private Label lbTypeDetailsFinancesWindow;

    @FXML
    private Label lbValueDetailsFinancesWindow;

    @FXML
    private TableColumn<Finance, String> tcContractDetailsFinancesWindow;

    @FXML
    private TableColumn<Finance, String> tcPaymentDateDetailsFinancesWindow;

    @FXML
    private TableColumn<Finance, String> tcTitleDetailsFinancesWindow;

    @FXML
    private TableColumn<Finance, String> tcTypeDetailsFinancesWindow;

    @FXML
    private TableColumn<Finance, String> tcValueDetailsFinancesWindow;

    @FXML
    private TextField tfMaxValueDetailsFinancesWindow;

    @FXML
    private TextField tfMinValueDetailsFinancesWindow;

    @FXML
    private TableView<Finance> tvDetailsFinancesWindow;

    @FXML
    public void closePushMsg(MouseEvent event) {
        hbPushMsgDetailsFinancesWindow.setVisible(false);
    }

    @FXML
    public void goBackFinancesScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getFinancesScreenController().setUser(user);
        sm.changeScreen("finance-menu-screen.fxml", "Gerenciador de Contratos - Finanças");
        sm.getFinancesScreenController().setDataWindow();
    }

    @FXML
    public void goToEditFinanceScreen(){
        Finance selectFinance = tvDetailsFinancesWindow.getSelectionModel().getSelectedItem();
        if(selectFinance != null){
            System.out.println("Editing finance screen");
        }else{
            lbPushMsgDetailsFinancesWindow.setText("Selecione uma finança!");
            hbPushMsgDetailsFinancesWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgDetailsFinancesWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    @FXML
    public void goToHelpScreen(){
        lbPushMsgDetailsFinancesWindow.setText("Em Desenvolvimento!");
        hbPushMsgDetailsFinancesWindow.getStyleClass().setAll("push-msg-info");
        hbPushMsgDetailsFinancesWindow.setVisible(true);
        this.delayHidePushMsg();
    }

    @FXML
    public void generateFinanceReport(){
        FinanceReportGenerator generator = new FinanceReportGenerator();
        generator.reportGenarator(filter, entries, expenses, entries-expenses, dpStartPeriodDetailsFinancesWindow.getValue(), dpEndPeriodDetailsFinancesWindow.getValue(), cbContractDetailsFinancesWindow.getValue(), tfMinValueDetailsFinancesWindow.getText(), tfMaxValueDetailsFinancesWindow.getText());
    }

    @FXML
    public void confirmFinanceDeletion(){
        Finance selectFinance = tvDetailsFinancesWindow.getSelectionModel().getSelectedItem();
        if(selectFinance != null){
            System.out.println("Confirming finance deletion");
        }else{
            lbPushMsgDetailsFinancesWindow.setText("Selecione uma finança!");
            hbPushMsgDetailsFinancesWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgDetailsFinancesWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    public void deleteFinance(Finance selectFinance){
        System.out.println("Deleting finance");
    }

    public void initializeTable(){
        tvDetailsFinancesWindow.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        tcTitleDetailsFinancesWindow.setCellValueFactory(new PropertyValueFactory<Finance, String>("title"));
        tcContractDetailsFinancesWindow.setCellValueFactory(new PropertyValueFactory<Finance, String>("contractName"));
        tcTypeDetailsFinancesWindow.setCellValueFactory(new PropertyValueFactory<Finance, String>("type"));
        tcValueDetailsFinancesWindow.setCellValueFactory(new PropertyValueFactory<Finance, String>("value"));
        tcPaymentDateDetailsFinancesWindow.setCellValueFactory(new PropertyValueFactory<Finance, String>("date"));

        tcValueDetailsFinancesWindow.setCellValueFactory(cellData -> {
            String value = String.format("%.2f", cellData.getValue().getValue());
            return new SimpleStringProperty(value);
        });

        tcPaymentDateDetailsFinancesWindow.setCellValueFactory(cellData -> {
            LocalDate date = cellData.getValue().getDate();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return  new SimpleStringProperty(date.format(dateTimeFormatter));
        });

        try (Connection conn = ConnectionFactory.getConnection()){
            imgDataBaseConnectionDetailsFinancesWindow.setVisible(true);
            try {
                tvDetailsFinancesWindow.getItems().clear();
                tvDetailsFinancesWindow.setItems(this.updateTable(filter));
                lbResultsFoundDetailsFinancesWindow.setText(String.format("%d resultado(s) encontrado(s)", tvDetailsFinancesWindow.getItems().size()));
                setBalanceValue();
                this.clearFields();
            } catch (Exception e) {
                lbPushMsgDetailsFinancesWindow.setText(e.getMessage());
                hbPushMsgDetailsFinancesWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgDetailsFinancesWindow.setVisible(true);
                this.delayHidePushMsg();
            }
        } catch (ConnectionFailureDbException | SQLException e) {
            imgDataBaseConnectionDetailsFinancesWindow.setVisible(false);
            for(int i = 0; i < tvDetailsFinancesWindow.getItems().size(); i++){
                tvDetailsFinancesWindow.getItems().clear();
            }
            lbResultsFoundDetailsFinancesWindow.setText(String.format("%d resultado(s) encontrado(s)", tvDetailsFinancesWindow.getItems().size()));
            this.clearFields();
        }
    }

    public ObservableList<Finance> updateTable(String filter){
        try {
            listOfWithdrawalsFinances.clear();
            listOfFinances.clear();
            listOfFinances = FXCollections.observableArrayList(app.getServer().listAllFinances());

            if(filter.equals("entries")){
                for(Finance finance : listOfFinances){
                    if(!finance.getType().equals("RECEITA")) listOfWithdrawalsFinances.add(finance);
                }
            }else if(filter.equals("expenses")){
                for(Finance finance : listOfFinances){
                    if(!finance.getType().equals("DESPESA")) listOfWithdrawalsFinances.add(finance);
                }
            }

            listOfFinances.removeAll(listOfWithdrawalsFinances);

        } catch (ConnectionFailureDbException e) {
            lbPushMsgDetailsFinancesWindow.setText(e.getMessage());
            hbPushMsgDetailsFinancesWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgDetailsFinancesWindow.setVisible(true);
            this.delayHidePushMsg();
        }

        return listOfFinances;
    }

    private ObservableList<Finance> searchFinance(){
        ObservableList<Finance> searchResult;
        try {
            searchResult = FXCollections.observableArrayList(app.getServer().listAllFinancesWithFilters(filter,"", dpStartPeriodDetailsFinancesWindow.getValue(), dpEndPeriodDetailsFinancesWindow.getValue(), cbContractDetailsFinancesWindow.getValue(), tfMinValueDetailsFinancesWindow.getText(), tfMaxValueDetailsFinancesWindow.getText()));
        } catch (ConnectionFailureDbException e) {
            throw new RuntimeException(e);
        }
        return searchResult;
    }

    public void setFieldsOfCard(Finance finance){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lbTitleDetailsFinancesWindow.setText(capitalizeWords(finance.getTitle()));
        lbContractDetailsFinancesWindow.setText(capitalizeWords(finance.getContractName()));
        lbTypeDetailsFinancesWindow.setText(capitalizeWords(finance.getType()));
        lbValueDetailsFinancesWindow.setText(capitalizeWords(String.format("R$%.2f", finance.getValue())));
        lbPaymentMethodDetailsFinancesWindow.setText(capitalizeWords(finance.getPaymentMethod()));
        lbPaymentDateDetailsFinancesWindow.setText(capitalizeWords(finance.getDate().format(dateFormatter)));
        btnEditDetailsFinancesWindow.setDisable(false);
        btnDeleteDetailsFinancesWindow.setDisable(false);
    }

    public void selectAndSetFields(){
        Finance selectFinance = tvDetailsFinancesWindow.getSelectionModel().getSelectedItem();
        if(selectFinance != null){
            this.setFieldsOfCard(selectFinance);
        }else{
            lbPushMsgDetailsFinancesWindow.setText("Selecione uma finança!");
            hbPushMsgDetailsFinancesWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgDetailsFinancesWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    public void clearFields(){
        lbTitleDetailsFinancesWindow.setText("");
        lbContractDetailsFinancesWindow.setText("");
        lbTypeDetailsFinancesWindow.setText("");
        lbValueDetailsFinancesWindow.setText("");
        lbPaymentMethodDetailsFinancesWindow.setText("");
        lbPaymentDateDetailsFinancesWindow.setText("");
    }

    public void initializeComboBoxsWindow(){
        this.setComboBoxOptions();
        cbContractDetailsFinancesWindow.setValue(contracts.getFirst());
    }

    private void setComboBoxOptions(){
        contracts.clear();
        contracts.add("----------");
        try {
            List<Contract> listOfAllContracts = app.getServer().listAllContracts();
            for(Contract contract : listOfAllContracts){
                if(!contracts.contains(contract.getName())) contracts.add(contract.getName());
            }
        } catch (ConnectionFailureDbException ignored) {}
        cbContractDetailsFinancesWindow.getItems().clear();
        cbContractDetailsFinancesWindow.getItems().addAll(contracts);
    }

    private void setBalanceValue(){
        entries = 0.0;
        expenses = 0.0;
        for(Finance finance : tvDetailsFinancesWindow.getItems()){
            if(finance.getType().equals("RECEITA")) entries += finance.getValue();
            else expenses += finance.getValue();
        }
        if(entries - expenses > 0){
            lbBalanceValueDetailsFinancesWindow.setStyle("-fx-text-fill: #55ff7f;");
        }else if(entries - expenses == 0){
            lbBalanceValueDetailsFinancesWindow.setStyle("-fx-text-fill: #05a1f5;");
        }else{
            lbBalanceValueDetailsFinancesWindow.setStyle("-fx-text-fill: #ff5580;");
        }
        lbBalanceValueDetailsFinancesWindow.setText(String.format("%.2f", entries - expenses));
    }

    private void resetWindow(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        hbPushMsgDetailsFinancesWindow.setVisible(false);
        lbTitleDetailsFinancesWindow.setText("");
        lbContractDetailsFinancesWindow.setText("");
        lbTypeDetailsFinancesWindow.setText("");
        lbValueDetailsFinancesWindow.setText("");
        lbPaymentMethodDetailsFinancesWindow.setText("");
        lbPaymentDateDetailsFinancesWindow.setText("");
        btnEditDetailsFinancesWindow.setDisable(true);
        btnDeleteDetailsFinancesWindow.setDisable(true);

        dpStartPeriodDetailsFinancesWindow.setValue(null);
        dpEndPeriodDetailsFinancesWindow.setValue(null);
        dpStartPeriodDetailsFinancesWindow.setPromptText(LocalDate.now().format(dateTimeFormatter));
        dpEndPeriodDetailsFinancesWindow.setPromptText(LocalDate.now().format(dateTimeFormatter));
        lbResultsFoundDetailsFinancesWindow.setText("");

        listOfFinances.clear();
        listOfWithdrawalsFinances.clear();
        tvDetailsFinancesWindow.getItems().clear();

        this.addOrRemoveListeners(false);
    }

    public void addOrRemoveListeners(boolean add){

        ChangeListener<String> comboBoxFinanceChangeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tvDetailsFinancesWindow.setItems(searchFinance());
                lbResultsFoundDetailsFinancesWindow.setText(String.format("%d resultado(s) encontrado(s)", tvDetailsFinancesWindow.getItems().size()));
                setBalanceValue();
            }
        };

        ChangeListener<LocalDate> dateChangeFinanceListener = new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                tvDetailsFinancesWindow.setItems(searchFinance());
                lbResultsFoundDetailsFinancesWindow.setText(String.format("%d resultado(s) encontrado(s)", tvDetailsFinancesWindow.getItems().size()));
                setBalanceValue();
            }
        };

        if(add){
            cbContractDetailsFinancesWindow.valueProperty().addListener(comboBoxFinanceChangeListener);
            dpStartPeriodDetailsFinancesWindow.valueProperty().addListener(dateChangeFinanceListener);
            dpEndPeriodDetailsFinancesWindow.valueProperty().addListener(dateChangeFinanceListener);
        }else{
            cbContractDetailsFinancesWindow.valueProperty().removeListener(comboBoxFinanceChangeListener);
            dpStartPeriodDetailsFinancesWindow.valueProperty().removeListener(dateChangeFinanceListener);
            dpEndPeriodDetailsFinancesWindow.valueProperty().removeListener(dateChangeFinanceListener);
        }

    }

    private void delayHidePushMsg(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ev) {
                ev.printStackTrace();
            }
            Platform.runLater(() -> {
                hbPushMsgDetailsFinancesWindow.setVisible(false);
            });
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgDetailsFinancesWindow.setVisible(false);
        btnEditDetailsFinancesWindow.setDisable(true);
        btnDeleteDetailsFinancesWindow.setDisable(true);

        this.clearFields();

        tfMinValueDetailsFinancesWindow.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[\\d,.]*$")) {
                tfMinValueDetailsFinancesWindow.setText(oldValue);
            }
        });

        tfMaxValueDetailsFinancesWindow.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[\\d,.]*$")) {
                tfMaxValueDetailsFinancesWindow.setText(oldValue);
            }
        });

        tfMinValueDetailsFinancesWindow.setOnKeyReleased((KeyEvent event) -> {
            tvDetailsFinancesWindow.setItems(this.searchFinance());
            lbResultsFoundDetailsFinancesWindow.setText(String.format("%d resultado(s) encontrado(s)", tvDetailsFinancesWindow.getItems().size()));
            setBalanceValue();
        });

        tfMaxValueDetailsFinancesWindow.setOnKeyReleased((KeyEvent event) -> {
            tvDetailsFinancesWindow.setItems(this.searchFinance());
            lbResultsFoundDetailsFinancesWindow.setText(String.format("%d resultado(s) encontrado(s)", tvDetailsFinancesWindow.getItems().size()));
            setBalanceValue();
        });

        tvDetailsFinancesWindow.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()){
                case UP, DOWN -> this.selectAndSetFields();
                case E -> this.goToEditFinanceScreen();
                case D -> this.confirmFinanceDeletion();
                case G -> this.generateFinanceReport();
                case H -> this.goToHelpScreen();
            }
        });

        tvDetailsFinancesWindow.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                this.selectAndSetFields();
            }
        });

    }

}
