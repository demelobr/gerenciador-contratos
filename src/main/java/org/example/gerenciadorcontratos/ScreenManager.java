package org.example.gerenciadorcontratos;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenManager {
    private static ScreenManager instance;
    private static Stage stg;

    private Scene loginScreenScene;
    private Scene registrationScrennScene;
    private Scene recoverAccountSendEmailScreenScene;
    private Scene recoverAccountSendCodeScreenScene;
    private Scene recoverAccountNewPasswordScreenScene;
    private Scene collaboratorsScreenScene;
    private Scene addNewCollaboratorScreenScene;
    private Scene addNewPresenceScreenScene;
    private Scene collaboratorDatailsScreenScene;
    private Scene editCollaboratorScreenScene;
    private Scene deleteCollaboratorScreenScene;
    private Scene editPresenceScreenScene;
    private Scene deletePresenceScreenScene;
    private Scene deleteFinanceScreenScene;
    private Scene financesScreenScene;
    private Scene addNewFinanceScreenScene;

    private LoginScreenController loginScreenController;
    private RegistrationScreenController registrationScreenController;
    private RecoverAccountEmailSendScreenController recoverAccountEmailSendScreenController;
    private RecoverAccountCodeSendScreenController recoverAccountCodeSendScreenController;
    private RecoverAccountNewPasswordScreenController recoverAccountNewPasswordScreenController;
    private CollaboratorsScreenController collaboratorsScreenController;
    private AddNewCollaboratorScreenController addNewCollaboratorScreenController;
    private AddNewPresenceScreenController addNewPresenceScreenController;
    private CollaboratorDetailsScreenController collaboratorDetailsScreenController;
    private EditCollaboratorScreenController editCollaboratorScreenController;
    private DeleteCollaboratorScreenController deleteCollaboratorScreenController;
    private EditPresenceScreenController editPresenceScreenController;
    private DeletePresenceScreenController deletePresenceScreenController;
    private DeleteFinanceScreenController deleteFinanceScreenController;
    private FinancesScreenController financesScreenController;
    private AddNewFinanceScreenController addNewFinanceScreenController;

    public ScreenManager(){
        this.screenLoader();
    }

    public static ScreenManager getInstance(){
        if(instance == null){
            instance = new ScreenManager();
        }
        return instance;
    }

    // GET/SET Stage
    public static Stage getStg() {
        return stg;
    }

    public static void setStg(Stage stg) {
        ScreenManager.stg = stg;
    }

    // GETTERS das Scenes
    public Scene getLoginScreenScene() {
        return loginScreenScene;
    }

    public Scene getRegistrationScrennScene() {
        return registrationScrennScene;
    }

    public Scene getRecoverAccountSendEmailScreenScene() {
        return recoverAccountSendEmailScreenScene;
    }

    public Scene getRecoverAccountSendCodeScreenScene() {
        return recoverAccountSendCodeScreenScene;
    }

    public Scene getRecoverAccountNewPasswordScreenScene() {
        return recoverAccountNewPasswordScreenScene;
    }

    public Scene getCollaboratorsScreenScene() {
        return collaboratorsScreenScene;
    }

    public Scene getAddNewCollaboratorScreenScene() {
        return addNewCollaboratorScreenScene;
    }

    public Scene getAddNewPresenceScreenScene() {
        return addNewPresenceScreenScene;
    }

    public Scene getCollaboratorDatailsScreenScene() {
        return collaboratorDatailsScreenScene;
    }

    public Scene getEditCollaboratorScreenScene() {
        return editCollaboratorScreenScene;
    }

    public Scene getDeleteCollaboratorScreenScene() {
        return deleteCollaboratorScreenScene;
    }

    public Scene getEditPresenceScreenScene() {
        return editPresenceScreenScene;
    }

    public Scene getDeletePresenceScreenScene() {
        return deletePresenceScreenScene;
    }

    public Scene getDeleteFinanceScreenScene() {
        return deleteFinanceScreenScene;
    }

    public Scene getFinancesScreenScene() {
        return financesScreenScene;
    }

    public Scene getAddNewFinanceScreenScene() {
        return addNewFinanceScreenScene;
    }

    // GETTERS dos Controllers
    public LoginScreenController getLoginScreenController() {
        return loginScreenController;
    }

    public RegistrationScreenController getRegistrationScreenController() {
        return registrationScreenController;
    }

    public RecoverAccountEmailSendScreenController getRecoverAccountEmailSendController() {
        return recoverAccountEmailSendScreenController;
    }

    public RecoverAccountCodeSendScreenController getRecoverAccountCodeSendController() {
        return recoverAccountCodeSendScreenController;
    }

    public RecoverAccountNewPasswordScreenController getRecoverAccountNewPasswordController() {
        return recoverAccountNewPasswordScreenController;
    }

    public CollaboratorsScreenController getCollaboratorsScreenController() {
        return collaboratorsScreenController;
    }

    public AddNewCollaboratorScreenController getAddNewCollaboratorScreenController() {
        return addNewCollaboratorScreenController;
    }

    public AddNewPresenceScreenController getAddNewPresenceScreenController() {
        return addNewPresenceScreenController;
    }

    public CollaboratorDetailsScreenController getCollaboratorDetailsScreenController() {
        return collaboratorDetailsScreenController;
    }

    public EditCollaboratorScreenController getEditCollaboratorScreenController() {
        return editCollaboratorScreenController;
    }

    public DeleteCollaboratorScreenController getDeleteCollaboratorScreenController() {
        return deleteCollaboratorScreenController;
    }

    public EditPresenceScreenController getEditPresenceScreenController() {
        return editPresenceScreenController;
    }

    public DeletePresenceScreenController getDeletePresenceScreenController() {
        return deletePresenceScreenController;
    }

    public DeleteFinanceScreenController getDeleteFinanceScreenController() {
        return deleteFinanceScreenController;
    }

    public FinancesScreenController getFinancesScreenController() {
        return financesScreenController;
    }

    public AddNewFinanceScreenController getAddNewFinanceScreenController() {
        return addNewFinanceScreenController;
    }

    private void screenLoader(){
        try {
            FXMLLoader loginSreenPane = new FXMLLoader(getClass().getResource("login-screen.fxml"));
            this.loginScreenScene = new Scene(loginSreenPane.load());
            this.loginScreenController = loginSreenPane.getController();

            FXMLLoader registrationScreenPane = new FXMLLoader(getClass().getResource("registration-screen.fxml"));
            this.registrationScrennScene = new Scene(registrationScreenPane.load());
            this.registrationScreenController = registrationScreenPane.getController();

            FXMLLoader recoverAccountSendEmailScreenPane = new FXMLLoader(getClass().getResource("recover-account-email-screen.fxml"));
            this.recoverAccountSendEmailScreenScene = new Scene(recoverAccountSendEmailScreenPane.load());
            this.recoverAccountEmailSendScreenController = recoverAccountSendEmailScreenPane.getController();

            FXMLLoader recoverAccountSendCodeScreenPane = new FXMLLoader(getClass().getResource("recover-account-code.fxml"));
            this.recoverAccountSendCodeScreenScene = new Scene(recoverAccountSendCodeScreenPane.load());
            this.recoverAccountCodeSendScreenController = recoverAccountSendCodeScreenPane.getController();

            FXMLLoader recoverAccountNewPasswordScreenPane = new FXMLLoader(getClass().getResource("recover-account-new-password.fxml"));
            this.recoverAccountNewPasswordScreenScene = new Scene(recoverAccountNewPasswordScreenPane.load());
            this.recoverAccountNewPasswordScreenController = recoverAccountNewPasswordScreenPane.getController();

            FXMLLoader collaboratorsScreenPane = new FXMLLoader(getClass().getResource("collaborator-menu-screen.fxml"));
            this.collaboratorsScreenScene = new Scene(collaboratorsScreenPane.load());
            this.collaboratorsScreenController = collaboratorsScreenPane.getController();

            FXMLLoader addNewCollaboratorScreenPane = new FXMLLoader(getClass().getResource("add-new-collaborator-screen.fxml"));
            this.addNewCollaboratorScreenScene = new Scene(addNewCollaboratorScreenPane.load());
            this.addNewCollaboratorScreenController = addNewCollaboratorScreenPane.getController();

            FXMLLoader addNewPresenceScreenPane = new FXMLLoader(getClass().getResource("presence-registration-screen.fxml"));
            this.addNewPresenceScreenScene = new Scene(addNewPresenceScreenPane.load());
            this.addNewPresenceScreenController = addNewPresenceScreenPane.getController();

            FXMLLoader collaboratorDetailsScreenPane = new FXMLLoader(getClass().getResource("collaborator-details-screen.fxml"));
            this.collaboratorDatailsScreenScene = new Scene(collaboratorDetailsScreenPane.load());
            this.collaboratorDetailsScreenController = collaboratorDetailsScreenPane.getController();

            FXMLLoader editCollaboratorScreenPane = new FXMLLoader(getClass().getResource("edit-collaborator-screen.fxml"));
            this.editCollaboratorScreenScene = new Scene(editCollaboratorScreenPane.load());
            this.editCollaboratorScreenController = editCollaboratorScreenPane.getController();

            FXMLLoader deleteCollaboratorScreenPane = new FXMLLoader(getClass().getResource("delete-collaborator-screen.fxml"));
            this.deleteCollaboratorScreenScene = new Scene(deleteCollaboratorScreenPane.load());
            this.deleteCollaboratorScreenController = deleteCollaboratorScreenPane.getController();

            FXMLLoader editPresenceScreenPane = new FXMLLoader(getClass().getResource("edit-presence-screen.fxml"));
            this.editPresenceScreenScene = new Scene(editPresenceScreenPane.load());
            this.editPresenceScreenController = editPresenceScreenPane.getController();

            FXMLLoader deletePresenceScreenPane = new FXMLLoader(getClass().getResource("delete-presence-screen.fxml"));
            this.deletePresenceScreenScene = new Scene(deletePresenceScreenPane.load());
            this.deletePresenceScreenController = deletePresenceScreenPane.getController();

            FXMLLoader deleteFinanceScreenPane = new FXMLLoader(getClass().getResource("delete-finance-screen.fxml"));
            this.deleteFinanceScreenScene = new Scene(deleteFinanceScreenPane.load());
            this.deleteFinanceScreenController = deleteFinanceScreenPane.getController();

            FXMLLoader financesScreenPane = new FXMLLoader(getClass().getResource("finance-menu-screen.fxml"));
            this.financesScreenScene = new Scene(financesScreenPane.load());
            this.financesScreenController = financesScreenPane.getController();

            FXMLLoader addNewFinanceScreenPane = new FXMLLoader(getClass().getResource("add-new-finance-screen.fxml"));
            this.addNewFinanceScreenScene = new Scene(addNewFinanceScreenPane.load());
            this.addNewFinanceScreenController = addNewFinanceScreenPane.getController();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void changeScreen(String fileNameFxml, String titleScreen){
        boolean max = stg.isMaximized();
        if(max) stg.setMaximized(false);
        switch (fileNameFxml){
            case "login-screen.fxml" -> stg.setScene(loginScreenScene);
            case "registration-screen.fxml" -> stg.setScene(registrationScrennScene);
            case "recover-account-email-screen.fxml" -> stg.setScene(recoverAccountSendEmailScreenScene);
            case "recover-account-code.fxml" -> stg.setScene(recoverAccountSendCodeScreenScene);
            case "recover-account-new-password.fxml" -> stg.setScene(recoverAccountNewPasswordScreenScene);
            case "collaborator-menu-screen.fxml" -> stg.setScene(collaboratorsScreenScene);
            case "add-new-collaborator-screen.fxml" -> stg.setScene(addNewCollaboratorScreenScene);
            case "collaborator-details-screen.fxml" -> stg.setScene(collaboratorDatailsScreenScene);
            case "edit-collaborator-screen.fxml" -> stg.setScene(editCollaboratorScreenScene);
            case "finance-menu-screen.fxml" -> stg.setScene(financesScreenScene);
            case "add-new-finance-screen.fxml" -> stg.setScene(addNewFinanceScreenScene);
        }
        stg.setTitle(titleScreen);
        if(max) stg.setMaximized(true);
    }

}
