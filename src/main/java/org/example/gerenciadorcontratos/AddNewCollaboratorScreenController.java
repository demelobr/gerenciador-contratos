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
import java.util.List;
import java.util.ResourceBundle;

public class AddNewCollaboratorScreenController implements Initializable {
    Application app;

    public AddNewCollaboratorScreenController(){
        this.app = new Application();
    }

    @FXML
    private Button btnBackAddNewCollaboratorWindow;

    @FXML
    private Button btnChoosePhotoAddNewCollaboratorWindow;

    @FXML
    private HBox btnClosePushMsgAddNewCollaboratorWindow;

    @FXML
    private HBox btnHelpAddNewCollaboratorWindow;

    @FXML
    private Button btnRegisterAddNewCollaboratorWindow;

    @FXML
    private DatePicker dpAdmissionDateAddNewCollaboratorWindow;

    @FXML
    private DatePicker dpTerminationDateAddNewCollaboratorWindow;

    @FXML
    private HBox hbPushMsgAddNewCollaboratorWindow;

    @FXML
    private ImageView imgDataBaseConnectionWindow;

    @FXML
    private Label lbPhotoUrlAddNewCollaboratorWindow;

    @FXML
    private Label lbPushMsgAddNewCollaboratorWindow;

    @FXML
    private RadioButton rbActiveAddNewCollaborator;

    @FXML
    private RadioButton rbInativeAddNewCollaborator;

    @FXML
    private TextField tfAddressAddNewCollaboratorWindow;

    @FXML
    private TextField tfCpfAddNewCollaboratorWindow;

    @FXML
    private TextField tfEmailAddNewCollaboratorWindow;

    @FXML
    private TextField tfNameAddNewCollaboratorWindow;

    @FXML
    private TextField tfOfficeAddNewCollaboratorWindow;

    @FXML
    private TextField tfRgAddNewCollaboratorWindow;

    @FXML
    private TextField tfTelephoneAddNewCollaboratorWindow;

    @FXML
    public void closePushMsg(MouseEvent event) {
        hbPushMsgAddNewCollaboratorWindow.setVisible(false);
    }

    @FXML
    public void goToBackCollaboratorScreen(){
        this.resetWindow();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getCollaboratorsScreenController().setDataScreen(sm.getCollaboratorsScreenController().getUser().getName());
        sm.getCollaboratorsScreenController().initializeTable();
        sm.changeScreen("collaborator-menu-screen.fxml", "Gerenciador de Contratos - Colaboradores");
    }

    @FXML
    public void selectRbActive(){
        rbActiveAddNewCollaborator.setSelected(true);
        rbInativeAddNewCollaborator.setSelected(false);
    }

    @FXML
    public void selectRbInative(){
        rbActiveAddNewCollaborator.setSelected(false);
        rbInativeAddNewCollaborator.setSelected(true);
    }

    @FXML
    public void createCollaborator(){
        String name = tfNameAddNewCollaboratorWindow.getText();
        String cpf = tfCpfAddNewCollaboratorWindow.getText();
        String rg = tfRgAddNewCollaboratorWindow.getText();
        String address = tfAddressAddNewCollaboratorWindow.getText();
        String telephone = tfTelephoneAddNewCollaboratorWindow.getText();
        String email = tfEmailAddNewCollaboratorWindow.getText();
        String office = tfOfficeAddNewCollaboratorWindow.getText();
        boolean status = rbActiveAddNewCollaborator.isSelected();
        LocalDate admissionDate = dpAdmissionDateAddNewCollaboratorWindow.getValue();
        LocalDate terminationDate = dpTerminationDateAddNewCollaboratorWindow.getValue();
        String photoUrl = lbPhotoUrlAddNewCollaboratorWindow.getText();

        try {
            Collaborator collaborator = new Collaborator(name, cpf, rg, address, telephone, email, office, status, null, admissionDate, terminationDate, photoUrl);
            if(app.getServer().checkCollaboratorData(collaborator)){
                app.getServer().createCollaborator(collaborator);
            }
        } catch (InvalidCpfException | ConnectionFailureDbException | CollaboratorWithThisCpfAlreadyExistsException |
                 InvalidEmailException | EmptyfieldsException | InvalidCollaboratorException |
                 CollaboratorNullException | CopyFileFailedException e) {
            lbPushMsgAddNewCollaboratorWindow.setText(e.getMessage());
            hbPushMsgAddNewCollaboratorWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddNewCollaboratorWindow.setVisible(true);
            this.delayHidePushMsg();
        } catch (CollaboratorCreatedSuccessfullyException e) {
            lbPushMsgAddNewCollaboratorWindow.setText(e.getMessage());
            hbPushMsgAddNewCollaboratorWindow.getStyleClass().setAll("push-msg-success");
            hbPushMsgAddNewCollaboratorWindow.setVisible(true);
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ev) {
                    ev.printStackTrace();
                }
                Platform.runLater(() -> {
                    hbPushMsgAddNewCollaboratorWindow.setVisible(false);
                    this.resetWindow();
                    ScreenManager sm = ScreenManager.getInstance();
                    sm.getCollaboratorsScreenController().setDataScreen(sm.getCollaboratorsScreenController().getUser().getName());
                    sm.getCollaboratorsScreenController().initializeTable();
                    sm.changeScreen("collaborator-menu-screen.fxml", "Gerenciador de Contratos - Colaboradores");
                });
            }).start();
        }
    }

    @FXML
    public void chooseImage(ActionEvent event){
        Stage stg = ScreenManager.getStg();
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.png", "*.jpeg"));
        List<File> fotos = fc.showOpenMultipleDialog(stg);
        if(fotos != null && fotos.size() == 1){
            lbPhotoUrlAddNewCollaboratorWindow.setText(fotos.get(0).getAbsolutePath());
        }else{
            lbPushMsgAddNewCollaboratorWindow.setText("Necessário escolher 1 foto!");
            hbPushMsgAddNewCollaboratorWindow.getStyleClass().setAll("push-msg-error");
            hbPushMsgAddNewCollaboratorWindow.setVisible(true);
            this.delayHidePushMsg();
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
                hbPushMsgAddNewCollaboratorWindow.setVisible(false);
            });
        }).start();
    }

    public void resetWindow(){
        hbPushMsgAddNewCollaboratorWindow.setVisible(false);
        tfNameAddNewCollaboratorWindow.setText("");
        tfCpfAddNewCollaboratorWindow.setText("");
        tfRgAddNewCollaboratorWindow.setText("");
        tfAddressAddNewCollaboratorWindow.setText("");
        tfTelephoneAddNewCollaboratorWindow.setText("");
        tfEmailAddNewCollaboratorWindow.setText("");
        tfOfficeAddNewCollaboratorWindow.setText("");
        rbActiveAddNewCollaborator.setSelected(true);
        rbInativeAddNewCollaborator.setSelected(false);
        lbPhotoUrlAddNewCollaboratorWindow.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgAddNewCollaboratorWindow.setVisible(false);
        rbActiveAddNewCollaborator.setSelected(true);
        rbInativeAddNewCollaborator.setSelected(false);
        lbPhotoUrlAddNewCollaboratorWindow.setText("");
    }
}
