package org.example.gerenciadorcontratos;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.mail.internet.ParseException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.example.gerenciadorcontratos.UtilitiesLibrary.capitalizeWords;
import static org.example.gerenciadorcontratos.UtilitiesLibrary.formatValue;

public class FinancesScreenController implements Initializable {
    private Application app;
    private User user;
    private double entries;
    private double expenses;

    public FinancesScreenController() {
        this.app = new Application();
        this.entries = 0.0;
        this.expenses = 0.0;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private Button btnAddFinancesWindow;

    @FXML
    private VBox btnBalanceFinancesWindow;

    @FXML
    private HBox btnClosePushMsgFinancesWindow;

    @FXML
    private VBox btnEntriesFinancesWindow;

    @FXML
    private VBox btnExpensesFinancesWindow;

    @FXML
    private HBox btnHelpCollaboratorsWindow;

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
    private HBox hbPushMsgFinancesWindow;

    @FXML
    private ImageView imgDataBaseConnectionWindow;

    @FXML
    private Label lbBalanceValueFinancesWindow;

    @FXML
    private Label lbDateTimeCollaboratorsWindow;

    @FXML
    private Label lbEntriesValueFinancesWindow;

    @FXML
    private Label lbExpensesValueFinancesWindow;

    @FXML
    private Label lbPushMsgFinancesWindow;

    @FXML
    private Label lbUserNameWindow;

    @FXML
    private AreaChart<String, Double> acGraphcFinancesWindow;

    @FXML
    void closePushMsg(MouseEvent event) {
        hbPushMsgFinancesWindow.setVisible(false);
    }

    @FXML
    public void goToContractsScreen(){
        lbPushMsgFinancesWindow.setText("Em Desenvolvimento!");
        hbPushMsgFinancesWindow.getStyleClass().setAll("push-msg-info");
        hbPushMsgFinancesWindow.setVisible(true);
        this.delayHidePushMsg();
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
        lbPushMsgFinancesWindow.setText("Em Desenvolvimento!");
        hbPushMsgFinancesWindow.getStyleClass().setAll("push-msg-info");
        hbPushMsgFinancesWindow.setVisible(true);
        this.delayHidePushMsg();
    }

    @FXML
    public void goToCloudScreen(){
        lbPushMsgFinancesWindow.setText("Em Desenvolvimento!");
        hbPushMsgFinancesWindow.getStyleClass().setAll("push-msg-info");
        hbPushMsgFinancesWindow.setVisible(true);
        this.delayHidePushMsg();
    }

    @FXML
    public void goToLoginScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.changeScreen("login-screen.fxml", "Gerenciador de Contratos - Login");
    }

    @FXML
    public void goToFinanceDatailsScreenWithFilterComplete(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getFinanceDetailsScreenController().setUser(user);
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
        sm.getFinanceDetailsScreenController().setFilter("expenses");
        sm.getFinanceDetailsScreenController().initializeComboBoxsWindow();
        sm.getFinanceDetailsScreenController().addOrRemoveListeners(true);
        sm.getFinanceDetailsScreenController().initializeTable();
        sm.changeScreen("finance-details-screen.fxml", "Gerenciador de Contratos - Detalhes das Finanças");
    }

    @FXML
    public void goToAddFinanceScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getAddNewFinanceScreenController().initializeWindow();
        sm.changeScreen("add-new-finance-screen.fxml", "Gerenciador de Contratos - Cadastrar Finança");
    }

    @FXML
    public void goToHelpScreen(){
        lbPushMsgFinancesWindow.setText("Em Desenvolvimento!");
        hbPushMsgFinancesWindow.getStyleClass().setAll("push-msg-info");
        hbPushMsgFinancesWindow.setVisible(true);
        this.delayHidePushMsg();
    }

    @FXML
    public void displayLegendOnBalanceSheetChart(){
        try {
            this.displayLegendsOnTheGraph(acGraphcFinancesWindow);
        } catch (ParseException e) {
            lbPushMsgFinancesWindow.setText(e.getMessage());
            hbPushMsgFinancesWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgFinancesWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    private void calculateFinances(){
        entries = 0.0;
        expenses = 0.0;
        List<Finance> listOfFinances = new ArrayList<>();
        try {
            listOfFinances = app.getServer().listAllFinances();
        } catch (ConnectionFailureDbException e) {
            lbPushMsgFinancesWindow.setText(e.getMessage());
            hbPushMsgFinancesWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgFinancesWindow.setVisible(true);
            this.delayHidePushMsg();
        }

        for(Finance finance : listOfFinances){
            if(finance.getType().equals("DESPESA")) expenses += finance.getValue();
            else if(finance.getType().equals("RECEITA")) entries += finance.getValue();
        }

        lbBalanceValueFinancesWindow.setText(String.format("%.2f", entries - expenses));
        if(entries - expenses > 0){
            lbBalanceValueFinancesWindow.setStyle("-fx-text-fill: #55ff7f;");
        }else if(entries - expenses == 0){
            lbBalanceValueFinancesWindow.setStyle("-fx-text-fill: #05a1f5;");
        }else{
            lbBalanceValueFinancesWindow.setStyle("-fx-text-fill: #ff5580;");
        }
        lbEntriesValueFinancesWindow.setText(String.format("%.2f", entries));
        lbExpensesValueFinancesWindow.setText(String.format("%.2f", expenses));
    }

    private void setDataOnGraphc(){

        List<String> months = new ArrayList<>() {{
            add("Jan"); add("Fev"); add("Mar"); add("Abr"); add("Mai"); add("Jun");
            add("Jul"); add("Ago"); add("Set"); add("Out"); add("Nov"); add("Dez");
        }};
        List<Double> listOfEntries = new ArrayList<>();
        List<Double> listOfExpenses = new ArrayList<>();
        try {
            listOfEntries = app.getServer().getListOfEntriesForTheYearByMonth(Year.of(LocalDate.now().getYear()));
            listOfExpenses = app.getServer().getListOfExpensesForTheYearByMonth(Year.of(LocalDate.now().getYear()));
        } catch (ConnectionFailureDbException e) {
            lbPushMsgFinancesWindow.setText(e.getMessage());
            hbPushMsgFinancesWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgFinancesWindow.setVisible(true);
            this.delayHidePushMsg();
        }

        XYChart.Series<String, Double> entriesChart;
        XYChart.Series<String, Double> expensesChart;

        // Verifica se as séries já existem, se não, cria novas
        if (acGraphcFinancesWindow.getData().size() == 0) {
            entriesChart = new XYChart.Series<String, Double>();
            expensesChart = new XYChart.Series<String, Double>();
            if(entries >= expenses) acGraphcFinancesWindow.getData().addAll(entriesChart, expensesChart);
            else acGraphcFinancesWindow.getData().addAll(expensesChart, entriesChart);
        } else {
            entriesChart = (XYChart.Series<String, Double>) acGraphcFinancesWindow.getData().get(0);
            expensesChart = (XYChart.Series<String, Double>) acGraphcFinancesWindow.getData().get(1);
        }

        entriesChart.setName("Entradas");
        expensesChart.setName("Saídas");

        entriesChart.getData().clear();
        expensesChart.getData().clear();

        for (int i = 0; i < months.size(); i++) {
            entriesChart.getData().add(new XYChart.Data<>(months.get(i), listOfEntries.get(i)));
            expensesChart.getData().add(new XYChart.Data<>(months.get(i), listOfExpenses.get(i)));
        }

        for (XYChart.Series<String, Double> serie : acGraphcFinancesWindow.getData()) {
            for (XYChart.Data<String, Double> data : serie.getData()) {
                Node node = data.getNode();

                // Verifica se o nó existe e define um novo estilo para ele
                if (node != null) {
                    node.setStyle("-fx-background-radius: 8px; -fx-background-color: rgba(255,255,255,0.5), white;");
                    node.setScaleX(1.5);
                    node.setScaleY(1.5);
                }
            }
        }
    }

    private void displayLegendsOnTheGraph(XYChart<String, Double> chart) throws ParseException {
        for (XYChart.Series<String, Double> serie : chart.getData()) {
            for (XYChart.Data<String, Double> data : serie.getData()) {
                Tooltip tooltip = new Tooltip("R$"+String.format("%.2f", data.getYValue()));
                Tooltip.install(data.getNode(), tooltip);

                data.getNode().setOnMouseEntered(event -> {
                    Point2D pointInScene = new Point2D(event.getSceneX(), event.getSceneY());
                    tooltip.show(data.getNode(), pointInScene.getX(), pointInScene.getY() + 10);
                });

                data.getNode().setOnMouseExited(event -> tooltip.hide());
            }
        }
    }

    public void setDataWindow(){
        lbUserNameWindow.setText(capitalizeWords(user.getName()+"!"));
        this.calculateFinances();
        this.setDataOnGraphc();
    }

    private void resetWindow(){
        lbBalanceValueFinancesWindow.setText("0");
        lbEntriesValueFinancesWindow.setText("0");
        lbExpensesValueFinancesWindow.setText("0");
    }

    private void delayHidePushMsg(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ev) {
                ev.printStackTrace();
            }
            Platform.runLater(() -> {
                hbPushMsgFinancesWindow.setVisible(false);
            });
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgFinancesWindow.setVisible(false);
    }
}
