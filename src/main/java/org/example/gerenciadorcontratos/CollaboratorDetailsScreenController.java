package org.example.gerenciadorcontratos;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.example.gerenciadorcontratos.UtilitiesLibrary.capitalizeWords;

public class CollaboratorDetailsScreenController implements Initializable {
    private Application app;
    private Collaborator collaborator;
    private List<String> records;
    private List<String> status;
    private List<String> contracts;
    private ObservableList<Presence> listOfPresences;
    private ObservableList<Finance> listOfFinances;

    public CollaboratorDetailsScreenController(){
        this.app = new Application();
        this.records = new ArrayList<>();
        this.status = new ArrayList<>();
        this.contracts = new ArrayList<>();
        this.listOfPresences = FXCollections.observableArrayList();
        this.listOfFinances = FXCollections.observableArrayList();
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    @FXML
    private Button btnBackDetailsCollaboratorWindow;

    @FXML
    private HBox btnClosePushMsgDetailsCollaboratorWindow;

    @FXML
    private Button btnCompleteDetailsCollaboratorWindow;

    @FXML
    private Button btnDeleteCollaboratorDetailsCollaboratorWindow;

    @FXML
    private Button btnDeleteFinanceDetailsCollaboratorWindow;

    @FXML
    private Button btnDeletePresenceDetailsCollaboratorWindow;


    @FXML
    private Button btnEditCollaboratorDetailsCollaboratorWindow;

    @FXML
    private Button btnEditFinanceDetailsCollaboratorWindow;

    @FXML
    private Button btnEditPresenceDetailsCollaboratorWindow;

    @FXML
    private Button btnFinancesDetailsCollaboratorWindow;

    @FXML
    private HBox btnHelpDetailsCollaboratorWindow;

    @FXML
    private Button btnPresencesDetailsCollaboratorWindow;

    @FXML
    private ChoiceBox<String> cbContractFinanceDetailsCollaboratorWindow;

    @FXML
    private ChoiceBox<String> cbContractPresencesDetailsCollaboratorWindow;

    @FXML
    private ChoiceBox<String> cbRegisterPresencesDetailsCollaboratorWindow;

    @FXML
    private ChoiceBox<String> cbStatusPresencesDetailsCollaboratorWindow;

    @FXML
    private DatePicker dpEndPeriodFinanceDetailsCollaboratorWindow;

    @FXML
    private DatePicker dpEndPeriodPresencesDetailsCollaboratorWindow;

    @FXML
    private DatePicker dpStartPeriodFinanceDetailsCollaboratorWindow;

    @FXML
    private DatePicker dpStartPeriodPresencesDetailsCollaboratorWindow;

    @FXML
    private HBox hbPushMsgAddDetailsCollaboratorWindow;

    @FXML
    private ImageView imgDataBaseConnectionDetailsCollaboratorWindow;

    @FXML
    private ImageView imgDetailsCollaboratorWindow;

    @FXML
    private Label lbAddressDetailsCollaboratorWindow;

    @FXML
    private Label lbAdmissionDateDetailsCollaboratorWindow;

    @FXML
    private Label lbContractDetailsCollaboratorWindow;

    @FXML
    private Label lbContractPresencesDetailsCollaboratorWindow;

    @FXML
    private Label lbCpfDetailsCollaboratorWindow;

    @FXML
    private Label lbEmailDetailsCollaboratorWindow;

    @FXML
    private Label lbLastPointDetailsCollaboratorWindow;

    @FXML
    private Label lbNameDetailsCollaboratorWindow;

    @FXML
    private Label lbOfficeDetailsCollaboratorWindow;

    @FXML
    private Label lbPaymenteDateDetailsCollaboratorWindow;

    @FXML
    private Label lbPresenceDateTimePresencesDetailsCollaboratorWindow;

    @FXML
    private Label lbPushMsgDetailsCollaboratorWindow;

    @FXML
    private Label lbRegisterDateTimePresencesDetailsCollaboratorWindow;

    @FXML
    private Label lbRegisterPresencesDetailsCollaboratorWindow;

    @FXML
    private Label lbResultsFoundFinancesDetailsCollaboratorWindow;

    @FXML
    private Label lbResultsFoundPresencesDetailsCollaboratorWindow;

    @FXML
    private Label lbRgDetailsCollaboratorWindow;

    @FXML
    private Label lbStatusDetailsCollaboratorWindow;

    @FXML
    private Label lbStatusPresencesDetailsCollaboratorWindow;

    @FXML
    private Label lbTelephoneDetailsCollaboratorWindow;

    @FXML
    private Label lbTerminationDateDetailsCollaboratorWindow;

    @FXML
    private Label lbTitleDetailsCollaboratorWindow;

    @FXML
    private Label lbTypeDetailsCollaboratorWindow;

    @FXML
    private Label lbValueDetailsCollaboratorWindow;

    @FXML
    private TableColumn<Finance, String> tcContractFinancesDetailsCollaboratorWindow;

    @FXML
    private TableColumn<Presence, String> tcContractPresencesDetailsCollaboratorWindow;

    @FXML
    private TableColumn<Finance, String> tcPaymentDateFinancesDetailsCollaboratorWindow;

    @FXML
    private TableColumn<Presence, String> tcPresenceDateTimePresencesDetailsCollaboratorWindow;

    @FXML
    private TableColumn<Finance, String> tcTitleFinancesDetailsCollaboratorWindow;

    @FXML
    private TableColumn<Presence, String> tcRegisterDateTimePresencesDetailsCollaboratorWindow;

    @FXML
    private TableColumn<Presence, String> tcRegisterPresencesDetailsCollaboratorWindow;

    @FXML
    private TableColumn<Presence, String> tcStatusPresencesDetailsCollaboratorWindow;

    @FXML
    private TableColumn<Finance, String> tcValueFinancesDetailsCollaboratorWindow;

    @FXML
    private TableColumn<Finance, String> tcTypeFinancesDetailsCollaboratorWindow;

    @FXML
    private TextField tfMinValueFinanceDetailsCollaboratorWindow;

    @FXML
    private TextField tfMaxValueFinanceDetailsCollaboratorWindow;

    @FXML
    private TableView<Finance> tvFinancesDetailsCollaboratorWindow;

    @FXML
    private TableView<Presence> tvPresencesDetailsCollaboratorWindow;

    @FXML
    public void closePushMsg(MouseEvent event) {
        hbPushMsgAddDetailsCollaboratorWindow.setVisible(false);
    }

    @FXML
    public void goBackCollaboratorScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getCollaboratorsScreenController().setDataScreen(sm.getCollaboratorsScreenController().getUser().getName());
        sm.getCollaboratorsScreenController().initializeTable();
        sm.changeScreen("collaborator-menu-screen.fxml", "Gerenciador de Contratos - Colaboradores");
    }

    @FXML
    public void goToEditCollaboratorScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getEditCollaboratorScreenController().setCollaborator(collaborator);
        sm.getEditCollaboratorScreenController().setCollaboratorData(collaborator);
        sm.changeScreen("edit-collaborator-screen.fxml", "Gerenciador de Contratos - Editar Colaborador");
    }

    @FXML
    public void goToEditPresenceScreen(){
        Presence selectPresence = tvPresencesDetailsCollaboratorWindow.getSelectionModel().getSelectedItem();
        if(selectPresence != null){
            ScreenManager sm = ScreenManager.getInstance();
            sm.getEditPresenceScreenController().setPresence(selectPresence);
            sm.getEditPresenceScreenController().initializeWindow();

            Scene scene = sm.getEditPresenceScreenScene();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            dialogStage.setTitle("Editar Presença");
            //            dialogStage.getIcons().add(new Image("file:" + "src/main/resources/org/example/bzreboques/icon-16x16.png"));
            dialogStage.setScene(scene);
            dialogStage.setOnCloseRequest(event -> {
                sm.getEditPresenceScreenController().clearFiels();
            });
            dialogStage.centerOnScreen();
            dialogStage.showAndWait();

        }else{
            lbPushMsgDetailsCollaboratorWindow.setText("Selecione uma presença!");
            hbPushMsgAddDetailsCollaboratorWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddDetailsCollaboratorWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    @FXML
    public void goToEditFinanceScreen(){
        Finance selectFinance = tvFinancesDetailsCollaboratorWindow.getSelectionModel().getSelectedItem();
        if(selectFinance != null){
            ScreenManager sm = ScreenManager.getInstance();
            sm.getEditFinanceCollaboratorScreenController().setFinance(selectFinance);
            sm.getEditFinanceCollaboratorScreenController().initializeWindow();
            sm.changeScreen("edit-finance-collaborator-screen.fxml", "Gerenciador de Contratos - Editar Finança");
        }else{
            lbPushMsgDetailsCollaboratorWindow.setText("Selecione uma finança!");
            hbPushMsgAddDetailsCollaboratorWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddDetailsCollaboratorWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    @FXML
    public void goToHelpScreen(){
        lbPushMsgDetailsCollaboratorWindow.setText("Em Desenvolvimento!");
        hbPushMsgAddDetailsCollaboratorWindow.getStyleClass().setAll("push-msg-info");
        hbPushMsgAddDetailsCollaboratorWindow.setVisible(true);
        this.delayHidePushMsg();
    }

    @FXML
    public void generateDataReport(){
        CollaboratorReportGenerator generator = new CollaboratorReportGenerator();
        generator.reportGenerator(collaborator, "", "", cbContractFinanceDetailsCollaboratorWindow.getValue(), dpStartPeriodFinanceDetailsCollaboratorWindow.getValue(), dpEndPeriodFinanceDetailsCollaboratorWindow.getValue(), tfMinValueFinanceDetailsCollaboratorWindow.getText(), tfMaxValueFinanceDetailsCollaboratorWindow.getText(), "data");
    }

    @FXML
    public void generateFinanceReport(){
        CollaboratorReportGenerator generator = new CollaboratorReportGenerator();
        generator.reportGenerator(collaborator, "", "", cbContractFinanceDetailsCollaboratorWindow.getValue(), dpStartPeriodFinanceDetailsCollaboratorWindow.getValue(), dpEndPeriodFinanceDetailsCollaboratorWindow.getValue(), tfMinValueFinanceDetailsCollaboratorWindow.getText(), tfMaxValueFinanceDetailsCollaboratorWindow.getText(), "finances");
    }

    @FXML
    public void generatePresenceReport(){
        CollaboratorReportGenerator generator = new CollaboratorReportGenerator();
        generator.reportGenerator(collaborator, cbRegisterPresencesDetailsCollaboratorWindow.getValue(), cbStatusPresencesDetailsCollaboratorWindow.getValue(), cbContractPresencesDetailsCollaboratorWindow.getValue(), dpStartPeriodPresencesDetailsCollaboratorWindow.getValue(), dpEndPeriodPresencesDetailsCollaboratorWindow.getValue(), "", "", "presences");
    }

    @FXML
    public void confirmCollaboratorDeletion(){
        ScreenManager sm = ScreenManager.getInstance();
        Scene scene = sm.getDeleteCollaboratorScreenScene();
        Button yesBtn = sm.getDeleteCollaboratorScreenController().getBtnYesDeleteCollaboratorWindow();
        Button noBtn = sm.getDeleteCollaboratorScreenController().getBtnNoDeleteCollaboratorWindow();

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setResizable(false);
        dialogStage.setTitle("Deletear Collaborator?");
        dialogStage.getIcons().add(new Image("file:"));
        dialogStage.setScene(scene);

        yesBtn.setOnAction(e -> {
            this.deleteCollaborator();
            dialogStage.close();
            this.goBackCollaboratorScreen();
        });

        noBtn.setOnAction(e -> {
            dialogStage.close();
        });

        dialogStage.centerOnScreen();
        dialogStage.showAndWait();
    }

    public void deleteCollaborator(){
        try {
            app.getServer().deleteCollaborator(app.getServer().getCollaboratorByCpf(collaborator.getCpf()));
        } catch (ConnectionFailureDbException | CollaboratorDoesNotExistException | CollaboratorNullException e) {
            lbPushMsgDetailsCollaboratorWindow.setText(e.getMessage());
            hbPushMsgAddDetailsCollaboratorWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddDetailsCollaboratorWindow.setVisible(true);
            this.delayHidePushMsg();
        } catch (CollaboratorDeletedSuccessfullyException e) {
            lbPushMsgDetailsCollaboratorWindow.setText(e.getMessage());
            hbPushMsgAddDetailsCollaboratorWindow.getStyleClass().setAll("push-msg-success");
            hbPushMsgAddDetailsCollaboratorWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    @FXML
    public void confirmFinanceDeletion(){
        Finance selectFinance = tvFinancesDetailsCollaboratorWindow.getSelectionModel().getSelectedItem();
        if(selectFinance != null){
            ScreenManager sm = ScreenManager.getInstance();
            Scene scene = sm.getDeleteFinanceScreenScene();
            Button yesBtn = sm.getDeleteFinanceScreenController().getBtnYesDeleteFinanceWindow();
            Button noBtn = sm.getDeleteFinanceScreenController().getBtnNoDeleteFinanceWindow();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            dialogStage.setTitle("Deletar Finança?");
            dialogStage.getIcons().add(new Image("file:"));
            dialogStage.setScene(scene);

            yesBtn.setOnAction(e -> {
                this.deleteFinance(selectFinance);
                dialogStage.close();
            });

            noBtn.setOnAction(e -> {
                dialogStage.close();
            });

            dialogStage.centerOnScreen();
            dialogStage.showAndWait();
        }else{
            lbPushMsgDetailsCollaboratorWindow.setText("Selecione uma finança!");
            hbPushMsgAddDetailsCollaboratorWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddDetailsCollaboratorWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    public void deleteFinance(Finance selectFinance){
        try {
            app.getServer().deleteFiance(selectFinance);
        }catch (FinanceNullException | FinanceDoesNotExistException | ConnectionFailureDbException e) {
            lbPushMsgDetailsCollaboratorWindow.setText(e.getMessage());
            hbPushMsgAddDetailsCollaboratorWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddDetailsCollaboratorWindow.setVisible(true);
            this.delayHidePushMsg();
        } catch (FinanceDeletedSuccessfullyException e) {
            int idSelectFinance = tvFinancesDetailsCollaboratorWindow.getSelectionModel().getSelectedIndex();
            tvFinancesDetailsCollaboratorWindow.getItems().remove(idSelectFinance);

            lbPushMsgDetailsCollaboratorWindow.setText(e.getMessage());
            hbPushMsgAddDetailsCollaboratorWindow.getStyleClass().setAll("push-msg-success");
            hbPushMsgAddDetailsCollaboratorWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    public void initializeFinanceTable() {
        tvFinancesDetailsCollaboratorWindow.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        tcTitleFinancesDetailsCollaboratorWindow.setCellValueFactory(new PropertyValueFactory<Finance, String>("title"));
        tcContractFinancesDetailsCollaboratorWindow.setCellValueFactory(new PropertyValueFactory<Finance, String>("contractName"));
        tcTypeFinancesDetailsCollaboratorWindow.setCellValueFactory(new PropertyValueFactory<Finance, String>("type"));
        tcValueFinancesDetailsCollaboratorWindow.setCellValueFactory(new PropertyValueFactory<Finance, String>("value"));
        tcPaymentDateFinancesDetailsCollaboratorWindow.setCellValueFactory(new PropertyValueFactory<Finance, String>("date"));

        tcValueFinancesDetailsCollaboratorWindow.setCellValueFactory(cellData -> {
            String value = String.format("%.2f", cellData.getValue().getValue());
            return new SimpleStringProperty(value);
        });

        tcPaymentDateFinancesDetailsCollaboratorWindow.setCellValueFactory(cellData -> {
            LocalDate date = cellData.getValue().getDate();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return  new SimpleStringProperty(date.format(dateTimeFormatter));
        });

        try (Connection conn = ConnectionFactory.getConnection()){
            imgDataBaseConnectionDetailsCollaboratorWindow.setVisible(true);
            try {
                tvFinancesDetailsCollaboratorWindow.setItems(this.updateFinanceTable());
                lbResultsFoundFinancesDetailsCollaboratorWindow.setText(String.format("%d resultado(s) encontrado(s)", tvFinancesDetailsCollaboratorWindow.getItems().size()));
                this.clearFinanceFields();
            } catch (Exception e) {
                lbPushMsgDetailsCollaboratorWindow.setText(e.getMessage());
                hbPushMsgAddDetailsCollaboratorWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgAddDetailsCollaboratorWindow.setVisible(true);
                this.delayHidePushMsg();
            }
        } catch (ConnectionFailureDbException | SQLException e) {
            imgDataBaseConnectionDetailsCollaboratorWindow.setVisible(false);
            for(int i = 0; i < tvFinancesDetailsCollaboratorWindow.getItems().size(); i++){
                tvFinancesDetailsCollaboratorWindow.getItems().clear();
            }
            lbResultsFoundFinancesDetailsCollaboratorWindow.setText(String.format("%d resultado(s) encontrado(s)", tvFinancesDetailsCollaboratorWindow.getItems().size()));
            this.clearFinanceFields();
        }
    }

    public ObservableList<Finance> updateFinanceTable(){
        try {
            listOfFinances = FXCollections.observableArrayList(app.getServer().listAllFinancesByCollaboratorCpf(collaborator.getCpf()));
        } catch (ConnectionFailureDbException e) {
            lbPushMsgDetailsCollaboratorWindow.setText(e.getMessage());
            hbPushMsgAddDetailsCollaboratorWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddDetailsCollaboratorWindow.setVisible(true);
            this.delayHidePushMsg();
        }

        return listOfFinances;
    }

    private ObservableList<Finance> searchFinance(String filter){
        ObservableList<Finance> searchResult;
        try {
            searchResult = FXCollections.observableArrayList(app.getServer().listAllFinancesWithFilters(filter, collaborator.getCpf(), dpStartPeriodFinanceDetailsCollaboratorWindow.getValue(), dpEndPeriodFinanceDetailsCollaboratorWindow.getValue(), cbContractFinanceDetailsCollaboratorWindow.getValue(), tfMinValueFinanceDetailsCollaboratorWindow.getText(), tfMaxValueFinanceDetailsCollaboratorWindow.getText()));
        } catch (ConnectionFailureDbException e) {
            throw new RuntimeException(e);
        }
        return searchResult;
    }

    public void setFieldsOfFinanceCard(Finance finance){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lbTitleDetailsCollaboratorWindow.setText(capitalizeWords(finance.getTitle()));
        lbContractDetailsCollaboratorWindow.setText(capitalizeWords(finance.getContractName()));
        lbTypeDetailsCollaboratorWindow.setText(capitalizeWords(finance.getType()));
        lbValueDetailsCollaboratorWindow.setText(capitalizeWords(String.format("R$%.2f", finance.getValue())));
        lbPaymenteDateDetailsCollaboratorWindow.setText(capitalizeWords(finance.getDate().format(dateFormatter)));
        btnEditFinanceDetailsCollaboratorWindow.setDisable(false);
        btnDeleteFinanceDetailsCollaboratorWindow.setDisable(false);
    }

    public void selectAndSetFinance(){
        Finance selectFinance = tvFinancesDetailsCollaboratorWindow.getSelectionModel().getSelectedItem();
        if(selectFinance != null){
            this.setFieldsOfFinanceCard(selectFinance);
        }else{
            lbPushMsgDetailsCollaboratorWindow.setText("Selecione uma finança!");
            hbPushMsgAddDetailsCollaboratorWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddDetailsCollaboratorWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    public void clearFinanceFields(){
        lbTitleDetailsCollaboratorWindow.setText("");
        lbContractDetailsCollaboratorWindow.setText("");
        lbTypeDetailsCollaboratorWindow.setText("");
        lbValueDetailsCollaboratorWindow.setText("");
        lbPaymenteDateDetailsCollaboratorWindow.setText("");
    }
    
    @FXML
    public void confirmPresenceDeletion(){
        Presence selectPresence = tvPresencesDetailsCollaboratorWindow.getSelectionModel().getSelectedItem();
        if(selectPresence != null){
            ScreenManager sm = ScreenManager.getInstance();
            Scene scene = sm.getDeletePresenceScreenScene();
            Button yesBtn = sm.getDeletePresenceScreenController().getBtnYesDeletePresenceWindow();
            Button noBtn = sm.getDeletePresenceScreenController().getBtnNoDeletePresenceWindow();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            dialogStage.setTitle("Deletar Presença?");
            dialogStage.getIcons().add(new Image("file:"));
            dialogStage.setScene(scene);

            yesBtn.setOnAction(e -> {
                this.deletePresence(selectPresence);
                dialogStage.close();
            });

            noBtn.setOnAction(e -> {
                dialogStage.close();
            });

            dialogStage.centerOnScreen();
            dialogStage.showAndWait();
        }else{
            lbPushMsgDetailsCollaboratorWindow.setText("Selecione uma presença!");
            hbPushMsgAddDetailsCollaboratorWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddDetailsCollaboratorWindow.setVisible(true);
            this.delayHidePushMsg();
        }

    }

    public void deletePresence(Presence selectPresence){
        try {
            app.getServer().deletePresence(selectPresence);
        } catch (ConnectionFailureDbException | PresenceNullException | PresenceDoesNotExistException e) {
            lbPushMsgDetailsCollaboratorWindow.setText(e.getMessage());
            hbPushMsgAddDetailsCollaboratorWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddDetailsCollaboratorWindow.setVisible(true);
            this.delayHidePushMsg();
        } catch (PresenceDeletedSuccessfullyException e) {
            int idSelectPresence = tvPresencesDetailsCollaboratorWindow.getSelectionModel().getSelectedIndex();
            tvPresencesDetailsCollaboratorWindow.getItems().remove(idSelectPresence);

            lbPushMsgDetailsCollaboratorWindow.setText(e.getMessage());
            hbPushMsgAddDetailsCollaboratorWindow.getStyleClass().setAll("push-msg-success");
            hbPushMsgAddDetailsCollaboratorWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    public void initializePresenceTable(){
        tvFinancesDetailsCollaboratorWindow.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        tcContractPresencesDetailsCollaboratorWindow.setCellValueFactory(new PropertyValueFactory<Presence, String>("nameContract"));
        tcRegisterPresencesDetailsCollaboratorWindow.setCellValueFactory(new PropertyValueFactory<Presence, String>("record"));
        tcStatusPresencesDetailsCollaboratorWindow.setCellValueFactory(new PropertyValueFactory<Presence, String>("status"));
        tcPresenceDateTimePresencesDetailsCollaboratorWindow.setCellValueFactory(new PropertyValueFactory<Presence, String>("presenceDateTime"));
        tcRegisterDateTimePresencesDetailsCollaboratorWindow.setCellValueFactory(new PropertyValueFactory<Presence, String>("recordDateTime"));

        tcPresenceDateTimePresencesDetailsCollaboratorWindow.setCellValueFactory(cellData -> {
            LocalDateTime dateTime = cellData.getValue().getPresenceDateTime();
            DateTimeFormatter dateTimeFormatterWithTime = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
            return  new SimpleStringProperty(dateTime.format(dateTimeFormatterWithTime));
        });

        tcRegisterDateTimePresencesDetailsCollaboratorWindow.setCellValueFactory(cellData -> {
            LocalDateTime dateTime = cellData.getValue().getRecordDateTime();
            DateTimeFormatter dateTimeFormatterWithTime = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
            return  new SimpleStringProperty(dateTime.format(dateTimeFormatterWithTime));
        });

        try (Connection conn = ConnectionFactory.getConnection()){
            imgDataBaseConnectionDetailsCollaboratorWindow.setVisible(true);
            try {
                tvPresencesDetailsCollaboratorWindow.setItems(this.updatePresenceTable());
                lbResultsFoundPresencesDetailsCollaboratorWindow.setText(String.format("%d resultado(s) encontrado(s)", tvPresencesDetailsCollaboratorWindow.getItems().size()));
                this.clearPresenceFields();
            } catch (Exception e) {
                lbPushMsgDetailsCollaboratorWindow.setText(e.getMessage());
                hbPushMsgAddDetailsCollaboratorWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgAddDetailsCollaboratorWindow.setVisible(true);
                this.delayHidePushMsg();
            }
        } catch (ConnectionFailureDbException | SQLException e) {
            imgDataBaseConnectionDetailsCollaboratorWindow.setVisible(false);
            for(int i = 0; i < tvPresencesDetailsCollaboratorWindow.getItems().size(); i++){
                tvPresencesDetailsCollaboratorWindow.getItems().clear();
            }
            lbResultsFoundPresencesDetailsCollaboratorWindow.setText(String.format("%d resultado(s) encontrado(s)", tvPresencesDetailsCollaboratorWindow.getItems().size()));
            this.clearPresenceFields();
        }
    }

    public ObservableList<Presence> updatePresenceTable(){
        try {
            listOfPresences = FXCollections.observableArrayList(app.getServer().listAllPresencesByCpf(collaborator.getCpf()));
        } catch (ConnectionFailureDbException e) {
            lbPushMsgDetailsCollaboratorWindow.setText(e.getMessage());
            hbPushMsgAddDetailsCollaboratorWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddDetailsCollaboratorWindow.setVisible(true);
            this.delayHidePushMsg();
        }
        return listOfPresences;
    }

    private ObservableList<Presence> searchPresence(){
        ObservableList<Presence> searchResult;

        try {
            searchResult = FXCollections.observableArrayList(app.getServer().listAllPresencesWithFilters(collaborator.getCpf(), cbRegisterPresencesDetailsCollaboratorWindow.getValue(), cbStatusPresencesDetailsCollaboratorWindow.getValue(), cbContractPresencesDetailsCollaboratorWindow.getValue(), dpStartPeriodPresencesDetailsCollaboratorWindow.getValue(), dpEndPeriodPresencesDetailsCollaboratorWindow.getValue()));
        } catch (ConnectionFailureDbException e) {
            throw new RuntimeException(e);
        }

        return searchResult;
    }

    public void setFieldsOfPresenceCard(Presence presence){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        btnEditPresenceDetailsCollaboratorWindow.setDisable(false);
        btnDeletePresenceDetailsCollaboratorWindow.setDisable(false);
        lbContractPresencesDetailsCollaboratorWindow.setText(capitalizeWords(presence.getNameContract()));
        lbRegisterPresencesDetailsCollaboratorWindow.setText(capitalizeWords(presence.getRecord()));
        lbStatusPresencesDetailsCollaboratorWindow.setText(capitalizeWords(presence.getStatus()));
        lbPresenceDateTimePresencesDetailsCollaboratorWindow.setText(capitalizeWords(presence.getPresenceDateTime().format(dateTimeFormatter)));
        lbRegisterDateTimePresencesDetailsCollaboratorWindow.setText(capitalizeWords(presence.getRecordDateTime().format(dateTimeFormatterWithSeconds)));
    }

    public void selectAndSetPresence(){
        Presence selectPresence = tvPresencesDetailsCollaboratorWindow.getSelectionModel().getSelectedItem();
        if(selectPresence != null){
            this.setFieldsOfPresenceCard(selectPresence);
        }else{
            lbPushMsgDetailsCollaboratorWindow.setText("Selecione uma presença!");
            hbPushMsgAddDetailsCollaboratorWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddDetailsCollaboratorWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    public void clearPresenceFields(){
        lbContractPresencesDetailsCollaboratorWindow.setText("");
        lbRegisterPresencesDetailsCollaboratorWindow.setText("");
        lbStatusPresencesDetailsCollaboratorWindow.setText("");
        lbPresenceDateTimePresencesDetailsCollaboratorWindow.setText("");
        lbRegisterDateTimePresencesDetailsCollaboratorWindow.setText("");
    }

    public void initializeComboBoxsWindow(){
        this.setCombosBoxOptions();
        cbRegisterPresencesDetailsCollaboratorWindow.setValue(records.getFirst());
        cbStatusPresencesDetailsCollaboratorWindow.setValue(status.getFirst());
        cbContractPresencesDetailsCollaboratorWindow.setValue(contracts.getFirst());
        cbContractFinanceDetailsCollaboratorWindow.setValue(contracts.getFirst());
    }

    private void setCombosBoxOptions(){
        records.clear();
        records.add("----------");
        records.add("CHEGADA");
        records.add("SAÍDA");

        status.clear();
        status.add("----------");
        status.add("PRESENTE");
        status.add("NÃO PRESENTE");

        contracts.clear();
        contracts.add("----------");
        try {
            List<Contract> listOfAllContracts = app.getServer().listAllContracts();
            for(Presence presence : listOfPresences){
                for(Contract contract : listOfAllContracts){
                    if(presence.getNameContract().equals(contract.getName()) && !contracts.contains(contract.getName())){
                        contracts.add(contract.getName());
                    }
                }
            }
        } catch (ConnectionFailureDbException ignored) {}

        cbRegisterPresencesDetailsCollaboratorWindow.getItems().clear();
        cbStatusPresencesDetailsCollaboratorWindow.getItems().clear();
        cbContractPresencesDetailsCollaboratorWindow.getItems().clear();

        cbRegisterPresencesDetailsCollaboratorWindow.getItems().addAll(records);
        cbStatusPresencesDetailsCollaboratorWindow.getItems().addAll(status);
        cbContractPresencesDetailsCollaboratorWindow.getItems().addAll(contracts);
        cbContractFinanceDetailsCollaboratorWindow.getItems().addAll(contracts);
    }

    private void resetWindow(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        hbPushMsgAddDetailsCollaboratorWindow.setVisible(false);

        imgDetailsCollaboratorWindow.setImage(new Image("file:"));
        lbNameDetailsCollaboratorWindow.setText("");
        lbOfficeDetailsCollaboratorWindow.setText("");
        lbCpfDetailsCollaboratorWindow.setText("");
        lbRgDetailsCollaboratorWindow.setText("");
        lbAddressDetailsCollaboratorWindow.setText("");
        lbTelephoneDetailsCollaboratorWindow.setText("");
        lbEmailDetailsCollaboratorWindow.setText("");
        lbLastPointDetailsCollaboratorWindow.setText("");
        lbAdmissionDateDetailsCollaboratorWindow.setText("");
        lbTerminationDateDetailsCollaboratorWindow.setText("");

        lbTitleDetailsCollaboratorWindow.setText("");
        lbContractDetailsCollaboratorWindow.setText("");
        lbTypeDetailsCollaboratorWindow.setText("");
        lbValueDetailsCollaboratorWindow.setText("");
        lbPaymenteDateDetailsCollaboratorWindow.setText("");
        btnEditFinanceDetailsCollaboratorWindow.setDisable(true);
        btnDeleteFinanceDetailsCollaboratorWindow.setDisable(true);
        dpStartPeriodFinanceDetailsCollaboratorWindow.setValue(null);
        dpEndPeriodFinanceDetailsCollaboratorWindow.setValue(null);
        dpStartPeriodFinanceDetailsCollaboratorWindow.setPromptText(LocalDate.now().format(dateTimeFormatter));
        dpEndPeriodFinanceDetailsCollaboratorWindow.setPromptText(LocalDate.now().format(dateTimeFormatter));
        tfMinValueFinanceDetailsCollaboratorWindow.setText("");
        tfMaxValueFinanceDetailsCollaboratorWindow.setText("");
        lbResultsFoundFinancesDetailsCollaboratorWindow.setText("");


        lbContractPresencesDetailsCollaboratorWindow.setText("");
        lbRegisterPresencesDetailsCollaboratorWindow.setText("");
        lbStatusPresencesDetailsCollaboratorWindow.setText("");
        lbPresenceDateTimePresencesDetailsCollaboratorWindow.setText("");
        lbRegisterDateTimePresencesDetailsCollaboratorWindow.setText("");
        btnEditPresenceDetailsCollaboratorWindow.setDisable(true);
        btnDeletePresenceDetailsCollaboratorWindow.setDisable(true);
        dpStartPeriodPresencesDetailsCollaboratorWindow.setValue(null);
        dpEndPeriodPresencesDetailsCollaboratorWindow.setValue(null);
        dpStartPeriodPresencesDetailsCollaboratorWindow.setPromptText(LocalDate.now().format(dateTimeFormatter));
        dpEndPeriodPresencesDetailsCollaboratorWindow.setPromptText(LocalDate.now().format(dateTimeFormatter));
        lbResultsFoundFinancesDetailsCollaboratorWindow.setText("");
        this.addOrRemoveListeners(false);
        this.initializeFinanceTable();
        this.initializePresenceTable();
    }

    public void setDataScreen(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        imgDetailsCollaboratorWindow.setImage(new Image("file:" + collaborator.getPhotoUrl()));
        double radius = Math.min(imgDetailsCollaboratorWindow.getFitWidth(), imgDetailsCollaboratorWindow.getFitHeight()) / 2;
        Circle clip = new Circle(radius, radius, radius);
        imgDetailsCollaboratorWindow.setClip(clip);

        lbNameDetailsCollaboratorWindow.setText(capitalizeWords(collaborator.getName()));
        lbOfficeDetailsCollaboratorWindow.setText(capitalizeWords(collaborator.getOffice()));
        lbCpfDetailsCollaboratorWindow.setText(capitalizeWords(collaborator.getCpf()));
        lbRgDetailsCollaboratorWindow.setText(capitalizeWords(collaborator.getRg()));
        lbAddressDetailsCollaboratorWindow.setText(capitalizeWords(collaborator.getAddress()));
        lbTelephoneDetailsCollaboratorWindow.setText(capitalizeWords(collaborator.getTelephone()));
        lbEmailDetailsCollaboratorWindow.setText(capitalizeWords(collaborator.getEmail()));
        lbAdmissionDateDetailsCollaboratorWindow.setText(capitalizeWords(collaborator.getAdmissionDate().format(dateFormatter)));
        if(collaborator.getTerminationDate() != null) lbTerminationDateDetailsCollaboratorWindow.setText(capitalizeWords(collaborator.getTerminationDate().format(dateFormatter)));
        else lbTerminationDateDetailsCollaboratorWindow.setText(capitalizeWords("NÃO INFORMADO"));
        if(collaborator.getLastPoint() != null) lbLastPointDetailsCollaboratorWindow.setText(capitalizeWords(collaborator.getLastPoint().format(dateTimeFormatter)));
        else lbLastPointDetailsCollaboratorWindow.setText(capitalizeWords("NÃO INFORMADO"));
        if (collaborator.isStatus()) lbStatusDetailsCollaboratorWindow.setText(capitalizeWords("ATIVO"));
        else lbStatusDetailsCollaboratorWindow.setText(capitalizeWords("INATIVO"));
    }

    private void delayHidePushMsg(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ev) {
                ev.printStackTrace();
            }
            Platform.runLater(() -> {
                hbPushMsgAddDetailsCollaboratorWindow.setVisible(false);
            });
        }).start();
    }

    public void addOrRemoveListeners(boolean add){

        ChangeListener<String> comboBoxFinanceChangeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tvFinancesDetailsCollaboratorWindow.setItems(searchFinance("complete"));
                lbResultsFoundFinancesDetailsCollaboratorWindow.setText(String.format("%d resultado(s) encontrado(s)", tvFinancesDetailsCollaboratorWindow.getItems().size()));
            }
        };

        ChangeListener<LocalDate> dateChangeFinanceListener = new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                tvFinancesDetailsCollaboratorWindow.setItems(searchFinance("complete"));
                lbResultsFoundFinancesDetailsCollaboratorWindow.setText(String.format("%d resultado(s) encontrado(s)", tvFinancesDetailsCollaboratorWindow.getItems().size()));
            }
        };

        ChangeListener<String> comboBoxPresenceChangeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tvPresencesDetailsCollaboratorWindow.setItems(searchPresence());
                lbResultsFoundPresencesDetailsCollaboratorWindow.setText(String.format("%d resultado(s) encontrado(s)", tvPresencesDetailsCollaboratorWindow.getItems().size()));
            }
        };

        ChangeListener<LocalDate> dateChangePresenceListener = new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                tvPresencesDetailsCollaboratorWindow.setItems(searchPresence());
                lbResultsFoundPresencesDetailsCollaboratorWindow.setText(String.format("%d resultado(s) encontrado(s)", tvPresencesDetailsCollaboratorWindow.getItems().size()));
            }
        };

        if(add){
            cbContractFinanceDetailsCollaboratorWindow.valueProperty().addListener(comboBoxFinanceChangeListener);
            dpStartPeriodFinanceDetailsCollaboratorWindow.valueProperty().addListener(dateChangeFinanceListener);
            dpEndPeriodFinanceDetailsCollaboratorWindow.valueProperty().addListener(dateChangeFinanceListener);

            cbRegisterPresencesDetailsCollaboratorWindow.valueProperty().addListener(comboBoxPresenceChangeListener);
            cbStatusPresencesDetailsCollaboratorWindow.valueProperty().addListener(comboBoxPresenceChangeListener);
            cbContractPresencesDetailsCollaboratorWindow.valueProperty().addListener(comboBoxPresenceChangeListener);
            dpStartPeriodPresencesDetailsCollaboratorWindow.valueProperty().addListener(dateChangePresenceListener);
            dpEndPeriodPresencesDetailsCollaboratorWindow.valueProperty().addListener(dateChangePresenceListener);

        }else{
            cbContractFinanceDetailsCollaboratorWindow.valueProperty().removeListener(comboBoxFinanceChangeListener);
            dpStartPeriodFinanceDetailsCollaboratorWindow.valueProperty().removeListener(dateChangeFinanceListener);
            dpEndPeriodFinanceDetailsCollaboratorWindow.valueProperty().removeListener(dateChangeFinanceListener);

            cbRegisterPresencesDetailsCollaboratorWindow.valueProperty().removeListener(comboBoxPresenceChangeListener);
            cbStatusPresencesDetailsCollaboratorWindow.valueProperty().removeListener(comboBoxPresenceChangeListener);
            cbContractPresencesDetailsCollaboratorWindow.valueProperty().removeListener(comboBoxPresenceChangeListener);
            dpStartPeriodPresencesDetailsCollaboratorWindow.valueProperty().removeListener(dateChangePresenceListener);
            dpEndPeriodPresencesDetailsCollaboratorWindow.valueProperty().removeListener(dateChangePresenceListener);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgAddDetailsCollaboratorWindow.setVisible(false);
        btnEditFinanceDetailsCollaboratorWindow.setDisable(true);
        btnDeleteFinanceDetailsCollaboratorWindow.setDisable(true);
        btnEditPresenceDetailsCollaboratorWindow.setDisable(true);
        btnDeletePresenceDetailsCollaboratorWindow.setDisable(true);

        this.clearFinanceFields();
        this.initializeFinanceTable();

        this.clearPresenceFields();
        this.initializePresenceTable();

        tfMinValueFinanceDetailsCollaboratorWindow.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[\\d,.]*$")) {
                tfMinValueFinanceDetailsCollaboratorWindow.setText(oldValue);
            }
        });

        tfMaxValueFinanceDetailsCollaboratorWindow.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[\\d,.]*$")) {
                tfMaxValueFinanceDetailsCollaboratorWindow.setText(oldValue);
            }
        });

        tfMinValueFinanceDetailsCollaboratorWindow.setOnKeyReleased((KeyEvent event) -> {
            tvFinancesDetailsCollaboratorWindow.setItems(this.searchFinance("complete"));
            lbResultsFoundFinancesDetailsCollaboratorWindow.setText(String.format("%d resultado(s) encontrado(s)", tvFinancesDetailsCollaboratorWindow.getItems().size()));
        });

        tfMaxValueFinanceDetailsCollaboratorWindow.setOnKeyReleased((KeyEvent event) -> {
            tvFinancesDetailsCollaboratorWindow.setItems(this.searchFinance("complete"));
            lbResultsFoundFinancesDetailsCollaboratorWindow.setText(String.format("%d resultado(s) encontrado(s)", tvFinancesDetailsCollaboratorWindow.getItems().size()));
        });

        tvFinancesDetailsCollaboratorWindow.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()){
                case UP, DOWN -> this.selectAndSetFinance();
                case H -> this.goToHelpScreen();
            }
        });

        tvFinancesDetailsCollaboratorWindow.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                this.selectAndSetFinance();
            }
        });

        tvPresencesDetailsCollaboratorWindow.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()){
                case UP, DOWN -> this.selectAndSetPresence();
                case H -> this.goToHelpScreen();
            }
        });

        tvPresencesDetailsCollaboratorWindow.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                this.selectAndSetPresence();
            }
        });

    }

}
