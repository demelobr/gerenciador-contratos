package org.example.gerenciadorcontratos;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.example.gerenciadorcontratos.UtilitiesLibrary.capitalizeWords;

public class CollaboratorsScreenController implements Initializable {
    private Application app;
    private User user;
    private ObservableList<Collaborator> listOfCollaborators;

    public CollaboratorsScreenController(){
        this.app = new Application();
        this.listOfCollaborators = FXCollections.observableArrayList();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private Button btnAddCallaboratorsWindow;

    @FXML
    private HBox btnClosePushMsgCollaboratorsWindow;

    @FXML
    private HBox btnHelpCollaboratorsWindow;

    @FXML
    private Button btnMoreCollaboratorsWindow;

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
    private Button btnPresenceCollaboratorsWindow;

    @FXML
    private HBox hbPushMsgCollaboratorsWindow;

    @FXML
    private HBox hbSearchCollaboratorsWindow;

    @FXML
    private ImageView imgCollaboratorsWindow;

    @FXML
    private ImageView imgDataBaseConnectionWindow;

    @FXML
    private Label lbAddressCollaboratorsWindow;

    @FXML
    private Label lbCpfCollaboratorsWindow;

    @FXML
    private Label lbDateTimeCollaboratorsWindow;

    @FXML
    private Label lbEmailCollaboratorsWindow;

    @FXML
    private Label lbFullNameCollaboratorsWindow;

    @FXML
    private Label lbLastPointCollaboratorsWindow;

    @FXML
    private Label lbNameCollaboratorsWindow;

    @FXML
    private Label lbOfficeCollaboratorsWindow;

    @FXML
    private Label lbPushMsgCollaboratorsWindow;

    @FXML
    private Label lbResultsFoundCollaboratorsWindow;

    @FXML
    private Label lbRgCollaboratorsWindow;

    @FXML
    private Label lbTelephoneCollaboratorsWindow;

    @FXML
    private Label lbUserNameWindow;

    @FXML
    private TableColumn<Collaborator, String> tcEmailCallaboratorsWindow;

    @FXML
    private TableColumn<Collaborator, String> tcLastPointCallaboratorsWindow;

    @FXML
    private TableColumn<Collaborator, String> tcNameCallaboratorsWindow;

    @FXML
    private TableColumn<Collaborator, String> tcOfficeCallaboratorsWindow;

    @FXML
    private TableColumn<Collaborator, String> tcTelephoneCallaboratorsWindow;

    @FXML
    private TableView<Collaborator> tvCallaboratorsWindow;

    @FXML
    private TextField tfSearchCollaboratorsWindow;

    @FXML
    void closePushMsg(MouseEvent event) {
        hbPushMsgCollaboratorsWindow.setVisible(false);
    }

    @FXML
    public void goToContractsScreen(){
        lbPushMsgCollaboratorsWindow.setText("Em Desenvolvimento!");
        hbPushMsgCollaboratorsWindow.getStyleClass().setAll("push-msg-info");
        hbPushMsgCollaboratorsWindow.setVisible(true);
        this.delayHidePushMsg();
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
    public void goToSettingsScreen(){
        lbPushMsgCollaboratorsWindow.setText("Em Desenvolvimento!");
        hbPushMsgCollaboratorsWindow.getStyleClass().setAll("push-msg-info");
        hbPushMsgCollaboratorsWindow.setVisible(true);
        this.delayHidePushMsg();
    }

    @FXML
    public void goToCloudScreen(){
        lbPushMsgCollaboratorsWindow.setText("Em Desenvolvimento!");
        hbPushMsgCollaboratorsWindow.getStyleClass().setAll("push-msg-info");
        hbPushMsgCollaboratorsWindow.setVisible(true);
        this.delayHidePushMsg();
    }

    @FXML
    public void goToLoginScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.changeScreen("login-screen.fxml", "Gerenciador de Contratos - Login");
    }

    @FXML
    public void goToAddCollaboratorScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.changeScreen("add-new-collaborator-screen.fxml", "Gerenciador de Contratos - Cadastrar Colaborador");
    }

    @FXML
    public void goToMoreInfoScreen(){
        Collaborator selectCollaborator = tvCallaboratorsWindow.getSelectionModel().getSelectedItem();
        if(selectCollaborator != null){
            this.resetWindow();
            ScreenManager sm = ScreenManager.getInstance();
            sm.getCollaboratorDetailsScreenController().setCollaborator(selectCollaborator);
            sm.getCollaboratorDetailsScreenController().setDataScreen();
            sm.getCollaboratorDetailsScreenController().initializePresenceTable();
            sm.getCollaboratorDetailsScreenController().initializeFinanceTable();
            sm.getCollaboratorDetailsScreenController().initializeComboBoxsWindow();
            sm.getCollaboratorDetailsScreenController().addOrRemoveListeners(true);
            sm.changeScreen("collaborator-details-screen.fxml", "Gerenciador de Contratos - Informações do Colaborador");
        }else{
            lbPushMsgCollaboratorsWindow.setText("Selecione um colaborador!");
            hbPushMsgCollaboratorsWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgCollaboratorsWindow.setVisible(true);
            this.delayHidePushMsg();
        }

    }

    @FXML
    public void goToAddPresenceScreen(){
        Collaborator selectCollaborator = tvCallaboratorsWindow.getSelectionModel().getSelectedItem();
        if(selectCollaborator != null){
            ScreenManager sm = ScreenManager.getInstance();
            sm.getAddNewPresenceScreenController().setCollaborator(selectCollaborator);
            sm.getAddNewPresenceScreenController().initializeWindow();

            Scene scene = sm.getAddNewPresenceScreenScene();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            dialogStage.setTitle(String.format("Adicionar Presença para %s", capitalizeWords(selectCollaborator.getName())));
//            dialogStage.getIcons().add(new Image("file:" + "src/main/resources/org/example/bzreboques/icon-16x16.png"));
            dialogStage.setScene(scene);
            dialogStage.setOnCloseRequest(event -> {
                sm.getAddNewPresenceScreenController().clearFiels();
            });
            dialogStage.centerOnScreen();
            dialogStage.showAndWait();

        }else{
            lbPushMsgCollaboratorsWindow.setText("Selecione um colaborador!");
            hbPushMsgCollaboratorsWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgCollaboratorsWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    @FXML
    public void goToHelpScreen(){
        lbPushMsgCollaboratorsWindow.setText("Em Desenvolvimento!");
        hbPushMsgCollaboratorsWindow.getStyleClass().setAll("push-msg-info");
        hbPushMsgCollaboratorsWindow.setVisible(true);
        this.delayHidePushMsg();
    }

    private void updateClock() {
        LocalDateTime dateTimeAtual = LocalDateTime.now();
        Locale locale = new Locale("pt", "BR");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, HH:mm", locale);
        String dataHoraFormatada = dateTimeAtual.format(formatter);
        lbDateTimeCollaboratorsWindow.setText(dataHoraFormatada);
    }

    public void initializeTable(){
        tvCallaboratorsWindow.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        tcNameCallaboratorsWindow.setCellValueFactory(new PropertyValueFactory<Collaborator, String>("name"));
        tcOfficeCallaboratorsWindow.setCellValueFactory(new PropertyValueFactory<Collaborator, String>("office"));
        tcTelephoneCallaboratorsWindow.setCellValueFactory(new PropertyValueFactory<Collaborator, String>("telephone"));
        tcEmailCallaboratorsWindow.setCellValueFactory(new PropertyValueFactory<Collaborator, String>("email"));
        tcLastPointCallaboratorsWindow.setCellValueFactory(new PropertyValueFactory<Collaborator, String>("lastPoint"));

        tcLastPointCallaboratorsWindow.setCellValueFactory(cellData -> {
            if(cellData.getValue().getLastPoint() != null){
                LocalDateTime dateTime = cellData.getValue().getLastPoint();
                DateTimeFormatter dateTimeFormatterWithTime = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
                return  new javafx.beans.property.SimpleStringProperty(dateTime.format(dateTimeFormatterWithTime));
            }else{
                return  new javafx.beans.property.SimpleStringProperty("NÃO INFORMADO");
            }
        });

        try (Connection conn = ConnectionFactory.getConnection()){
            imgDataBaseConnectionWindow.setVisible(true);
            try {
                tvCallaboratorsWindow.setItems(this.updateTable());
                lbResultsFoundCollaboratorsWindow.setText(String.format("%d resultado(s) encontrado(s)", tvCallaboratorsWindow.getItems().size()));
                this.clearFields();
            } catch (Exception e) {
                lbPushMsgCollaboratorsWindow.setText(e.getMessage());
                hbPushMsgCollaboratorsWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgCollaboratorsWindow.setVisible(true);
                this.delayHidePushMsg();
            }
        } catch (ConnectionFailureDbException | SQLException e) {
            imgDataBaseConnectionWindow.setVisible(false);
            for(int i = 0; i < tvCallaboratorsWindow.getItems().size(); i++){
                tvCallaboratorsWindow.getItems().clear();
            }
            lbResultsFoundCollaboratorsWindow.setText(String.format("%d resultado(s) encontrado(s)", tvCallaboratorsWindow.getItems().size()));
            this.clearFields();
        }

    }

    public ObservableList<Collaborator> updateTable(){
        try {
            listOfCollaborators = FXCollections.observableArrayList(app.getServer().listAllCollaborators());
        } catch (ConnectionFailureDbException e) {
            lbPushMsgCollaboratorsWindow.setText(e.getMessage());
            hbPushMsgCollaboratorsWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgCollaboratorsWindow.setVisible(true);
            this.delayHidePushMsg();
        }
        return listOfCollaborators;
    }

    private ObservableList<Collaborator> searchCollaborator(){
        ObservableList<Collaborator> searchResult = FXCollections.observableArrayList();
        for(Collaborator collaborator : listOfCollaborators){
            if(collaborator.getCpf().toUpperCase().contains(tfSearchCollaboratorsWindow.getText().toUpperCase()) ||
               collaborator.getEmail().toUpperCase().contains(tfSearchCollaboratorsWindow.getText().toUpperCase()) ||
               collaborator.getName().toUpperCase().contains(tfSearchCollaboratorsWindow.getText().toUpperCase())){
                searchResult.add(collaborator);
            }
        }
        return searchResult;
    }

    public void setFielsOfCard(Collaborator collaborator){
        btnMoreCollaboratorsWindow.setDisable(false);
        btnPresenceCollaboratorsWindow.setDisable(false);

        imgCollaboratorsWindow.setImage(new Image("file:" + collaborator.getPhotoUrl()));
        double radius = Math.min(imgCollaboratorsWindow.getFitWidth(), imgCollaboratorsWindow.getFitHeight()) / 2;
        Circle clip = new Circle(radius, radius, radius);
        imgCollaboratorsWindow.setClip(clip);
        DateTimeFormatter dateTimeFormatterWithTime = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        lbNameCollaboratorsWindow.setText(capitalizeWords(collaborator.getName()));
        lbOfficeCollaboratorsWindow.setText(capitalizeWords(collaborator.getOffice()));
        lbFullNameCollaboratorsWindow.setText(capitalizeWords(collaborator.getName()));
        lbCpfCollaboratorsWindow.setText(capitalizeWords(collaborator.getCpf()));
        lbRgCollaboratorsWindow.setText(capitalizeWords(collaborator.getRg()));
        lbAddressCollaboratorsWindow.setText(capitalizeWords(collaborator.getAddress()));
        lbTelephoneCollaboratorsWindow.setText(capitalizeWords(collaborator.getTelephone()));
        lbEmailCollaboratorsWindow.setText(capitalizeWords(collaborator.getEmail()));
        if(collaborator.getLastPoint() != null) lbLastPointCollaboratorsWindow.setText(capitalizeWords(collaborator.getLastPoint().format(dateTimeFormatterWithTime)));
        else lbLastPointCollaboratorsWindow.setText(capitalizeWords("NÃO INFORMADO"));
    }

    public void selectAndSetCollaborator(){
        Collaborator selectCollaborator = tvCallaboratorsWindow.getSelectionModel().getSelectedItem();
        if(selectCollaborator != null){
            this.setFielsOfCard(selectCollaborator);
        }else{
            lbPushMsgCollaboratorsWindow.setText("Selecione um colaborador!");
            hbPushMsgCollaboratorsWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgCollaboratorsWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    private void clearFields(){
        imgCollaboratorsWindow.setImage(new Image("file:"));
        lbNameCollaboratorsWindow.setText("");
        lbOfficeCollaboratorsWindow.setText("");
        lbFullNameCollaboratorsWindow.setText("");
        lbCpfCollaboratorsWindow.setText("");
        lbRgCollaboratorsWindow.setText("");
        lbAddressCollaboratorsWindow.setText("");
        lbTelephoneCollaboratorsWindow.setText("");
        lbEmailCollaboratorsWindow.setText("");
        lbLastPointCollaboratorsWindow.setText("");
        tfSearchCollaboratorsWindow.setText("");
    }

    private void resetWindow(){
        hbPushMsgCollaboratorsWindow.setVisible(false);
        imgCollaboratorsWindow.setImage(new Image("file:"));
        lbNameCollaboratorsWindow.setText("");
        lbOfficeCollaboratorsWindow.setText("");
        lbFullNameCollaboratorsWindow.setText("");
        lbCpfCollaboratorsWindow.setText("");
        lbRgCollaboratorsWindow.setText("");
        lbAddressCollaboratorsWindow.setText("");
        lbTelephoneCollaboratorsWindow.setText("");
        lbEmailCollaboratorsWindow.setText("");
        lbLastPointCollaboratorsWindow.setText("");
        tfSearchCollaboratorsWindow.setText("");
        btnMoreCollaboratorsWindow.setDisable(true);
        btnPresenceCollaboratorsWindow.setDisable(true);
        this.initializeTable();
    }

    public void setDataScreen(String name){
        lbUserNameWindow.setText(String.format("%s!", capitalizeWords(name)));
    }

    private void delayHidePushMsg(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ev) {
                ev.printStackTrace();
            }
            Platform.runLater(() -> {
                hbPushMsgCollaboratorsWindow.setVisible(false);
            });
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgCollaboratorsWindow.setVisible(false);
        btnMoreCollaboratorsWindow.setDisable(true);
        btnPresenceCollaboratorsWindow.setDisable(true);

        this.clearFields();
        this.initializeTable();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> this.updateClock()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        tfSearchCollaboratorsWindow.focusedProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue){
                hbSearchCollaboratorsWindow.getStyleClass().setAll("shadow","input-box");
            }else{
                hbSearchCollaboratorsWindow.getStyleClass().remove("shadow");
            }
        });

        tfSearchCollaboratorsWindow.setOnKeyReleased((KeyEvent event) -> {
            tvCallaboratorsWindow.setItems(this.searchCollaborator());
            lbResultsFoundCollaboratorsWindow.setText(String.format("%d resultado(s) encontrado(s)", tvCallaboratorsWindow.getItems().size()));
        });

        tvCallaboratorsWindow.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()){
                case UP, DOWN -> this.selectAndSetCollaborator();
                case M -> this.goToMoreInfoScreen();
                case P -> this.goToAddPresenceScreen();
                case R -> this.goToAddCollaboratorScreen();
                case H -> this.goToHelpScreen();
            }
        });

        tvCallaboratorsWindow.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                this.selectAndSetCollaborator();
            }
        });
    }

}
