package org.example.gerenciadorcontratos;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import static org.example.gerenciadorcontratos.UtilitiesLibrary.capitalizeWords;

public class EditCollaboratorScreenController implements Initializable {
    private Application app;
    private Collaborator collaborator;

    public EditCollaboratorScreenController(){
        this.app = new Application();
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    @FXML
    private Button btnBackEditCollaboratorWindow;

    @FXML
    private Button btnChoosePhotoEditCollaboratorWindow;

    @FXML
    private HBox btnClosePushMsgEditCollaboratorWindow;

    @FXML
    private HBox btnHelpEditCollaboratorWindow;

    @FXML
    private Button btnRegisterEditCollaboratorWindow;

    @FXML
    private DatePicker dpAdmissionDateEditCollaboratorWindow;

    @FXML
    private DatePicker dpTerminationDateEditCollaboratorWindow;

    @FXML
    private HBox hbPushMsgEditCollaboratorWindow;

    @FXML
    private ImageView imgDataBaseConnectionWindow;

    @FXML
    private Label lbPhotoUrlEditCollaboratorWindow;

    @FXML
    private Label lbPushMsgEditCollaboratorWindow;

    @FXML
    private RadioButton rbActiveEditCollaboratorWindow;

    @FXML
    private RadioButton rbInativeEditCollaboratorWindow;

    @FXML
    private TextField tfAddressEditCollaboratorWindow;

    @FXML
    private TextField tfCpfEditCollaboratorWindow;

    @FXML
    private TextField tfEmailEditCollaboratorWindow;

    @FXML
    private TextField tfNameEditCollaboratorWindow;

    @FXML
    private TextField tfOfficeEditCollaboratorWindow;

    @FXML
    private TextField tfRgEditCollaboratorWindow;

    @FXML
    private TextField tfTelephoneEditCollaboratorWindow;

    @FXML
    public void closePushMsg(MouseEvent event) {
        hbPushMsgEditCollaboratorWindow.setVisible(false);
    }

    @FXML
    public void goBackCollaboratorDetailsScreen() {
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getCollaboratorDetailsScreenController().setCollaborator(this.collaborator);
        sm.getCollaboratorDetailsScreenController().setDataScreen();
        sm.getCollaboratorDetailsScreenController().initializePresenceTable();
        sm.getCollaboratorDetailsScreenController().initializeComboBoxsWindow();
        sm.getCollaboratorDetailsScreenController().addOrRemoveListeners(true);
        sm.changeScreen("collaborator-details-screen.fxml", "Gerenciador de Contratos - Informações do Colaborador");
    }

    @FXML
    public void selectRbActive(){
        rbActiveEditCollaboratorWindow.setSelected(true);
        rbInativeEditCollaboratorWindow.setSelected(false);
    }

    @FXML
    public void selectRbInative(){
        rbActiveEditCollaboratorWindow.setSelected(false);
        rbInativeEditCollaboratorWindow.setSelected(true);
    }

    @FXML
    public void editCollaborator(){
        String name = tfNameEditCollaboratorWindow.getText();
        String cpf = tfCpfEditCollaboratorWindow.getText();
        String rg = tfRgEditCollaboratorWindow.getText();
        String address = tfAddressEditCollaboratorWindow.getText();
        String telephone = tfTelephoneEditCollaboratorWindow.getText();
        String email = tfEmailEditCollaboratorWindow.getText();
        String office = tfOfficeEditCollaboratorWindow.getText();
        boolean status = rbActiveEditCollaboratorWindow.isSelected();
        LocalDate admissionDate = dpAdmissionDateEditCollaboratorWindow.getValue();
        LocalDate terminationDate = dpTerminationDateEditCollaboratorWindow.getValue();
        String photoUrl = lbPhotoUrlEditCollaboratorWindow.getText();

        if(name.isEmpty() && cpf.isEmpty() && rg.isEmpty() && address.isEmpty() && telephone.isEmpty() && email.isEmpty() && office.isEmpty() &&
           status == collaborator.isStatus() && (admissionDate == null || admissionDate == collaborator.getAdmissionDate()) && (terminationDate == null || terminationDate == collaborator.getTerminationDate()) &&
           (photoUrl.isEmpty()) || photoUrl.equals(collaborator.getPhotoUrl())){
            lbPushMsgEditCollaboratorWindow.setText("Não houve alterações!");
            hbPushMsgEditCollaboratorWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgEditCollaboratorWindow.setVisible(true);
            this.delayHidePushMsg();
        }else{
            if(name.isEmpty() || name.equals(collaborator.getName())){
                name = collaborator.getName();
            }
            if(cpf.isEmpty() || cpf.equals(collaborator.getCpf())){
                cpf = collaborator.getCpf();
            }
            if(rg.isEmpty() || rg.equals(collaborator.getRg())){
                rg = collaborator.getRg();
            }
            if(address.isEmpty() || address.equals(collaborator.getAddress())){
                address = collaborator.getAddress();
            }
            if(telephone.isEmpty() || telephone.equals(collaborator.getTelephone())){
                telephone = collaborator.getTelephone();
            }
            if(email.isEmpty() || email.equals(collaborator.getEmail())){
                email = collaborator.getEmail();
            }
            if(office.isEmpty() || office.equals(collaborator.getOffice())){
                office = collaborator.getOffice();
            }
            if(status == collaborator.isStatus()){
                status = collaborator.isStatus();
            }
            if(admissionDate == null || admissionDate == collaborator.getAdmissionDate()){
                admissionDate = collaborator.getAdmissionDate();
            }
            if(terminationDate == null || terminationDate == collaborator.getTerminationDate()){
                terminationDate = collaborator.getTerminationDate();
            }
            if(photoUrl.isEmpty() || photoUrl.equals(collaborator.getPhotoUrl())){
                photoUrl = collaborator.getPhotoUrl();
            }

            try {
                if((rbInativeEditCollaboratorWindow.isSelected() && terminationDate != null) || status){
                    app.getServer().updateCollaborator(collaborator, name, cpf, rg, address, telephone, email, office, status, collaborator.getLastPoint(), admissionDate, terminationDate, photoUrl);
                }else{
                    lbPushMsgEditCollaboratorWindow.setText("Necessária uma data de desligamento!");
                    hbPushMsgEditCollaboratorWindow.getStyleClass().setAll("push-msg-error");
                    hbPushMsgEditCollaboratorWindow.setVisible(true);
                    this.delayHidePushMsg();
                }
            } catch (ConnectionFailureDbException | CollaboratorDoesNotExistException | CollaboratorNullException e) {
                lbPushMsgEditCollaboratorWindow.setText(e.getMessage());
                hbPushMsgEditCollaboratorWindow.getStyleClass().setAll("push-msg-error");
                hbPushMsgEditCollaboratorWindow.setVisible(true);
                this.delayHidePushMsg();
            } catch (CollaboratorUpdatedSuccessfullyException e) {
                lbPushMsgEditCollaboratorWindow.setText(e.getMessage());
                hbPushMsgEditCollaboratorWindow.getStyleClass().setAll("push-msg-success");
                hbPushMsgEditCollaboratorWindow.setVisible(true);

                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ev) {
                        ev.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        this.resetWindow();
                        ScreenManager sm = ScreenManager.getInstance();
                        try {
                            sm.getCollaboratorDetailsScreenController().setCollaborator(app.getServer().getCollaboratorByCpf(collaborator.getCpf()));
                        } catch (ConnectionFailureDbException | CollaboratorDoesNotExistException ex) {
                            lbPushMsgEditCollaboratorWindow.setText(ex.getMessage());
                            hbPushMsgEditCollaboratorWindow.getStyleClass().setAll("push-msg-error");
                            hbPushMsgEditCollaboratorWindow.setVisible(true);
                        }
                        sm.getCollaboratorDetailsScreenController().setDataScreen();
                        sm.getCollaboratorDetailsScreenController().initializePresenceTable();
                        sm.getCollaboratorDetailsScreenController().initializeComboBoxsWindow();
                        sm.getCollaboratorDetailsScreenController().addOrRemoveListeners(true);
                        sm.changeScreen("collaborator-details-screen.fxml", "Gerenciador de Contratos - Informações do Colaborador");
                    });
                }).start();
            }
        }
    }

    @FXML
    public void chooseImage(ActionEvent event){
        Stage stg = ScreenManager.getStg();
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.png", "*.jpeg"));
        List<File> fotos = fc.showOpenMultipleDialog(stg);
        if(fotos != null && fotos.size() == 1){
            lbPhotoUrlEditCollaboratorWindow.setText(fotos.get(0).getAbsolutePath());
        }else{
            lbPushMsgEditCollaboratorWindow.setText("Necessário escolher 1 foto!");
            hbPushMsgEditCollaboratorWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgEditCollaboratorWindow.setVisible(true);
            this.delayHidePushMsg();
        }
    }

    public void setCollaboratorData(Collaborator collaborator){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        tfNameEditCollaboratorWindow.setPromptText(capitalizeWords(collaborator.getName()));
        tfCpfEditCollaboratorWindow.setPromptText(capitalizeWords(collaborator.getCpf()));
        tfRgEditCollaboratorWindow.setPromptText(capitalizeWords(collaborator.getRg()));
        tfAddressEditCollaboratorWindow.setPromptText(capitalizeWords(collaborator.getAddress()));
        tfTelephoneEditCollaboratorWindow.setPromptText(capitalizeWords(collaborator.getTelephone()));
        tfEmailEditCollaboratorWindow.setPromptText(capitalizeWords(collaborator.getEmail()));
        tfOfficeEditCollaboratorWindow.setPromptText(capitalizeWords(collaborator.getOffice()));
        if(collaborator.isStatus()){
            rbActiveEditCollaboratorWindow.setSelected(true);
            rbInativeEditCollaboratorWindow.setSelected(false);
        }else{
            rbActiveEditCollaboratorWindow.setSelected(false);
            rbInativeEditCollaboratorWindow.setSelected(true);
        }
        dpAdmissionDateEditCollaboratorWindow.setPromptText(collaborator.getAdmissionDate().format(dateTimeFormatter));
        if(collaborator.getTerminationDate() != null) dpTerminationDateEditCollaboratorWindow.setPromptText(collaborator.getTerminationDate().format(dateTimeFormatter));
        else dpTerminationDateEditCollaboratorWindow.setPromptText("");
        lbPhotoUrlEditCollaboratorWindow.setText(collaborator.getPhotoUrl());
    }

    private void delayHidePushMsg(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ev) {
                ev.printStackTrace();
            }
            Platform.runLater(() -> {
                hbPushMsgEditCollaboratorWindow.setVisible(false);
            });
        }).start();
    }

    public void resetWindow(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        hbPushMsgEditCollaboratorWindow.setVisible(false);
        tfNameEditCollaboratorWindow.setPromptText("");
        tfNameEditCollaboratorWindow.setText("");
        tfCpfEditCollaboratorWindow.setPromptText("");
        tfCpfEditCollaboratorWindow.setText("");
        tfRgEditCollaboratorWindow.setPromptText("");
        tfRgEditCollaboratorWindow.setText("");
        tfAddressEditCollaboratorWindow.setPromptText("");
        tfAddressEditCollaboratorWindow.setText("");
        tfTelephoneEditCollaboratorWindow.setPromptText("");
        tfTelephoneEditCollaboratorWindow.setText("");
        tfEmailEditCollaboratorWindow.setPromptText("");
        tfEmailEditCollaboratorWindow.setText("");
        tfOfficeEditCollaboratorWindow.setPromptText("");
        tfOfficeEditCollaboratorWindow.setText("");

        rbActiveEditCollaboratorWindow.setSelected(true);
        rbInativeEditCollaboratorWindow.setSelected(false);

        dpAdmissionDateEditCollaboratorWindow.setPromptText("");
        dpAdmissionDateEditCollaboratorWindow.setValue(null);
        dpTerminationDateEditCollaboratorWindow.setPromptText("");
        dpTerminationDateEditCollaboratorWindow.setValue(null);
        lbPhotoUrlEditCollaboratorWindow.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgEditCollaboratorWindow.setVisible(false);
        rbActiveEditCollaboratorWindow.setSelected(true);
        rbInativeEditCollaboratorWindow.setSelected(false);
    }
}
