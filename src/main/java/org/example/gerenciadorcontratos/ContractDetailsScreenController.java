package org.example.gerenciadorcontratos;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.example.gerenciadorcontratos.UtilitiesLibrary.capitalizeWords;

public class ContractDetailsScreenController implements Initializable {
    private Application app;
    private Contract contract;
    private User user;

    public ContractDetailsScreenController() {
        this.app = new Application();
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
    private Button btnBackDetailsContractWindow;

    @FXML
    private VBox btnBalanceDetailsContractWindow;

    @FXML
    private HBox btnClosePushMsgDetailsContractWindow;

    @FXML
    private Button btnCompleteDetailsCollaboratorWindow;

    @FXML
    private Button btnDeleteContractDetailsContractWindow;

    @FXML
    private Button btnEditContractDetailsContractWindow;

    @FXML
    private VBox btnEntriesDetailsContractWindow;

    @FXML
    private VBox btnExpensesDetailsContractWindow;

    @FXML
    private Button btnFinancesDetailsContractWindow;

    @FXML
    private Button btnPresencesDetailsContractWindow;

    @FXML
    private HBox hbPushMsgAddDetailsContractWindow;

    @FXML
    private Label lbAddressDetailsContractWindow;

    @FXML
    private Label lbBalanceValueDetailsContractWindow;

    @FXML
    private Label lbEndDateDetailsContractWindow;

    @FXML
    private Label lbEngineerDetailsContractWindow;

    @FXML
    private Label lbEntriesValueDetailsContractWindow;

    @FXML
    private Label lbExpectedEndDateDetailsContractWindow;

    @FXML
    private Label lbExpectedStartDateDetailsContractWindow;

    @FXML
    private Label lbExpensesValueDetailsContractWindow;

    @FXML
    private Label lbNameDetailsContractWindow;

    @FXML
    private Label lbPushMsgDetailsContractWindow;

    @FXML
    private Label lbStartDateDetailsContractWindow;

    @FXML
    private PieChart pcEntriesGraphcContractWindow;

    @FXML
    private PieChart pcExpensesGraphcContractWindow;

    @FXML
    private Text txtDescriptionDetailsContractWindow;

    @FXML
    public void closePushMsg(MouseEvent event) {
        hbPushMsgAddDetailsContractWindow.setVisible(false);
    }

    @FXML
    public void goToBackContractScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getContractsScreenController().setDataScreen(capitalizeWords(sm.getContractsScreenController().getUser().getName()));
        sm.getContractsScreenController().initializeTable();
        sm.changeScreen("contract-menu-screen.fxml", "Gerenciador de Contratos - Contratos");
    }

    @FXML
    public void goToEditContractScreen(){
        System.out.println("Editing Contract");
    }

    @FXML
    public void confirmContractDeletion(){
        ScreenManager sm = ScreenManager.getInstance();
        Scene scene = sm.getDeleteContractScreenScene();
        Button yesBtn = sm.getDeleteContractScreenController().getBtnYesDeleteContractWindow();
        Button noBtn = sm.getDeleteContractScreenController().getBtnNoDeleteContractWindow();

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setResizable(false);
        dialogStage.setTitle("Deletar Contrato?");
        dialogStage.getIcons().add(new Image("file:"));
        dialogStage.setScene(scene);

        yesBtn.setOnAction(e -> {
            this.deleteContract();
            dialogStage.close();
            this.goToBackContractScreen();
        });

        noBtn.setOnAction(e -> {
            dialogStage.close();
        });

        dialogStage.centerOnScreen();
        dialogStage.showAndWait();
    }

    public void deleteContract(){
        try {
            app.getServer().deleteContarct(contract);
        } catch (ConnectionFailureDbException | ContractDoesNotExistException | ContractNullException e) {
            lbPushMsgDetailsContractWindow.setText(e.getMessage());
            hbPushMsgAddDetailsContractWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddDetailsContractWindow.setVisible(true);
            this.delayHidePushMsg();
        } catch (ContractDeletedSuccessfullyException e) {
            lbPushMsgDetailsContractWindow.setText(e.getMessage());
            hbPushMsgAddDetailsContractWindow.getStyleClass().setAll("push-msg-success");
            hbPushMsgAddDetailsContractWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    @FXML
    public void goToViewContractFile(){
        String filePath = contract.getContractFile();

        if (filePath != null && !filePath.isEmpty()) {
            File file = new File(filePath);
            if (file.exists()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    lbPushMsgDetailsContractWindow.setText("Erro ao abrir o arquivo!");
                    hbPushMsgAddDetailsContractWindow.getStyleClass().setAll("push-msg-error");
                    hbPushMsgAddDetailsContractWindow.setVisible(true);
                    this.delayHidePushMsg();
                }
            } else {
                lbPushMsgDetailsContractWindow.setText("Arquivo não encontrado!");
                hbPushMsgAddDetailsContractWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgAddDetailsContractWindow.setVisible(true);
                this.delayHidePushMsg();
            }
        } else {
            lbPushMsgDetailsContractWindow.setText("Nenhum contrato foi anexado!");
            hbPushMsgAddDetailsContractWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddDetailsContractWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    @FXML
    public void goToFinanceDatailsScreenWithFilterComplete(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getFinanceDetailsScreenController().setUser(user);
        sm.getFinanceDetailsScreenController().setContractName(contract.getName());
        sm.getFinanceDetailsScreenController().setYear(null);
        sm.getFinanceDetailsScreenController().setFilter("complete");
        sm.getFinanceDetailsScreenController().initializeComboBoxsWindow();
        sm.getFinanceDetailsScreenController().addOrRemoveListeners(true);
        sm.getFinanceDetailsScreenController().initializeTable();
        sm.changeScreen("finance-details-screen.fxml", "Gerenciador de Contratos - Detalhes das Finanças");
    }

    @FXML
    public void goToFinanceDatailsScreenWithFilterEntries(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getFinanceDetailsScreenController().setUser(user);
        sm.getFinanceDetailsScreenController().setContractName(contract.getName());
        sm.getFinanceDetailsScreenController().setYear(null);
        sm.getFinanceDetailsScreenController().setFilter("entries");
        sm.getFinanceDetailsScreenController().initializeComboBoxsWindow();
        sm.getFinanceDetailsScreenController().addOrRemoveListeners(true);
        sm.getFinanceDetailsScreenController().initializeTable();
        sm.changeScreen("finance-details-screen.fxml", "Gerenciador de Contratos - Detalhes das Finanças");
    }

    @FXML
    public void goToFinanceDatailsScreenWithFilterExpenses(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getFinanceDetailsScreenController().setUser(user);
        sm.getFinanceDetailsScreenController().setContractName(contract.getName());
        sm.getFinanceDetailsScreenController().setYear(null);
        sm.getFinanceDetailsScreenController().setFilter("expenses");
        sm.getFinanceDetailsScreenController().initializeComboBoxsWindow();
        sm.getFinanceDetailsScreenController().addOrRemoveListeners(true);
        sm.getFinanceDetailsScreenController().initializeTable();
        sm.changeScreen("finance-details-screen.fxml", "Gerenciador de Contratos - Detalhes das Finanças");
    }

    @FXML
    public void goToHelpScreen(){
        lbPushMsgDetailsContractWindow.setText("Em Desenvolvimento!");
        hbPushMsgAddDetailsContractWindow.getStyleClass().setAll("push-msg-info");
        hbPushMsgAddDetailsContractWindow.setVisible(true);
        this.delayHidePushMsg();
    }

    private List<String> getFinanceClasses(String type, String contractName){
        List<String> listOfFinanceClasses = new ArrayList<>();
        try {
            for(Finance finance : app.getServer().listAllFinancesWithFilters(type, "", null, null, contractName, "", "")){
                if(!listOfFinanceClasses.contains(finance.getFinanceClass())) listOfFinanceClasses.add(finance.getFinanceClass());
            }
        } catch (ConnectionFailureDbException e) {
            lbPushMsgDetailsContractWindow.setText("Não foi possível recuperar os dados!");
            hbPushMsgAddDetailsContractWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddDetailsContractWindow.setVisible(true);
            this.delayHidePushMsg();
        }
        return listOfFinanceClasses;
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

    private List<Double> getListOfPercentagesOfClasses(String type, String contractName, List<String> listOfFinanceClasses){
        List<Double> listOfSumValuesOfClasses = new ArrayList<>();
        List<Double> listOfPercentagesOfClasses = new ArrayList<>();

        listOfSumValuesOfClasses.clear();
        listOfPercentagesOfClasses.clear();

        for(int i = 0; i<listOfFinanceClasses.size(); i++){
            listOfSumValuesOfClasses.add(0.0);
            listOfPercentagesOfClasses.add(0.0);
        }

        try {
            for(Finance finance : app.getServer().listAllFinancesWithFilters(type, "", null, null, contractName, "", "")){
                for(int i = 0; i < listOfFinanceClasses.size(); i++){
                    if(finance.getFinanceClass().equalsIgnoreCase(listOfFinanceClasses.get(i))) listOfSumValuesOfClasses.set(i, listOfSumValuesOfClasses.get(i) + finance.getValue());
                }
            }

            double totalSumEntries = listOfSumValuesOfClasses.stream().mapToDouble(Double::doubleValue).sum();
            for(int i = 0; i < listOfSumValuesOfClasses.size(); i++){
                listOfPercentagesOfClasses.set(i, listOfSumValuesOfClasses.get(i) / totalSumEntries);
            }

        } catch (ConnectionFailureDbException e) {
            lbPushMsgDetailsContractWindow.setText("Não foi possível recuperar os dados!");
            hbPushMsgAddDetailsContractWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddDetailsContractWindow.setVisible(true);
            this.delayHidePushMsg();
        }
        return listOfPercentagesOfClasses;
    }

    private void setDataOnEntriesGraphc(){
        List<String> entriesClasses = new ArrayList<>();
        entriesClasses.clear();
        entriesClasses = this.getFinanceClasses("entries", contract.getName());

        List<Double> percentagesOfEnriesClasses = new ArrayList<>();
        percentagesOfEnriesClasses.clear();
        percentagesOfEnriesClasses = this.getListOfPercentagesOfClasses("entries", contract.getName(), entriesClasses);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for(int i = 0; i < percentagesOfEnriesClasses.size(); i++){
            pieChartData.add(new PieChart.Data(entriesClasses.get(i), percentagesOfEnriesClasses.get(i)*100));
        }

        pieChartData.forEach(data ->
            data.nameProperty().bind(
                Bindings.concat(data.getName(), ": ", String.format("%.2f", data.pieValueProperty().doubleValue()), "%")
            )
        );

        pcEntriesGraphcContractWindow.setTitle("Entradas do Contrato");
        pcEntriesGraphcContractWindow.setLegendVisible(false);
        pcEntriesGraphcContractWindow.setLabelsVisible(true);
        pcEntriesGraphcContractWindow.getData().addAll(pieChartData);

        List<String> colors = new ArrayList<>() {{
            add("#AFFFCB"); add("#93F2B3"); add("#77E49B"); add("#5BD782"); add("#3FC96A");
        }};

        for(int i = 0; i < pieChartData.size(); i++) {
            pieChartData.get(i).getNode().setStyle("-fx-pie-color: " + colors.get(i) + ";");
        }

    }

    private void setDataOnExpensesGraphc(){
        List<String> expensesClasses = this.getFinanceClasses("expenses", contract.getName());
        List<Double> percentageOfExpensesClasses = this.getListOfPercentagesOfClasses("expenses", contract.getName(), expensesClasses);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for(int i = 0; i < percentageOfExpensesClasses.size(); i++){
            pieChartData.add(new PieChart.Data(expensesClasses.get(i), percentageOfExpensesClasses.get(i)*100));
        }

        pieChartData.forEach(data ->
                data.nameProperty().bind(
                    Bindings.concat(data.getName(), ": ", String.format("%.2f", data.pieValueProperty().doubleValue()), "%")
                )
        );

        pcExpensesGraphcContractWindow.setTitle("Despesas do Contrato");
        pcExpensesGraphcContractWindow.setLegendVisible(false);
        pcExpensesGraphcContractWindow.setLabelsVisible(true);
        pcExpensesGraphcContractWindow.getData().addAll(pieChartData);

        List<String> colors = new ArrayList<>() {{
            add("#FFE2D4"); add("#E8BAAE"); add("#D19388"); add("#BA6B63"); add("#A2433D"); add("#DB002E"); add("#C60023"); add("#B00017"); add("#9B000C"); add("#850000");
        }};

        for(int i = 0; i < pieChartData.size(); i++) {
            pieChartData.get(i).getNode().setStyle("-fx-pie-color: " + colors.get(i) + ";");
        }

    }

    public void setDataWindow() throws ConnectionFailureDbException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lbNameDetailsContractWindow.setText(capitalizeWords(contract.getName()));
        txtDescriptionDetailsContractWindow.setText(capitalizeWords(contract.getDescription()));
        lbAddressDetailsContractWindow.setText(capitalizeWords(contract.getAddress()));
        if(contract.getEngineer().equals("----------")) lbEngineerDetailsContractWindow.setText("Não Informado");
        else lbEngineerDetailsContractWindow.setText(capitalizeWords(contract.getEngineer()));
        lbExpectedStartDateDetailsContractWindow.setText(dateTimeFormatter.format(contract.getExpectedStartDate()));
        lbStartDateDetailsContractWindow.setText(dateTimeFormatter.format(contract.getStartDate()));
        lbExpectedEndDateDetailsContractWindow.setText(dateTimeFormatter.format(contract.getExpectedEndDate()));
        lbEndDateDetailsContractWindow.setText(dateTimeFormatter.format(contract.getEndDate()));
        lbEntriesValueDetailsContractWindow.setText(String.format("%.2f", this.getContractEntriesValue(contract.getName())));
        lbExpensesValueDetailsContractWindow.setText(String.format("%.2f", this.getContractExpensesValue(contract.getName())));
        double balance = this.getContractEntriesValue(contract.getName()) - this.getContractExpensesValue(contract.getName());
        lbBalanceValueDetailsContractWindow.setText(String.format("%.2f", balance));
        if(balance > 0){
            lbBalanceValueDetailsContractWindow.setStyle("-fx-text-fill: #55ff7f;");
        }else if(balance == 0){
            lbBalanceValueDetailsContractWindow.setStyle("-fx-text-fill: #05a1f5;");
        }else{
            lbBalanceValueDetailsContractWindow.setStyle("-fx-text-fill: #ff5580;");
        }
        this.setDataOnEntriesGraphc();
        this.setDataOnExpensesGraphc();
    }

    private void resetWindow(){
        pcEntriesGraphcContractWindow.getData().clear();
        pcEntriesGraphcContractWindow.setTitle(null);
        pcEntriesGraphcContractWindow.setLabelsVisible(false);

        pcExpensesGraphcContractWindow.getData().clear();
        pcExpensesGraphcContractWindow.setTitle(null);
        pcExpensesGraphcContractWindow.setLabelsVisible(false);
    }

    private void delayHidePushMsg(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ev) {
                ev.printStackTrace();
            }
            Platform.runLater(() -> {
                hbPushMsgAddDetailsContractWindow.setVisible(false);
            });
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgAddDetailsContractWindow.setVisible(false);
    }

}
