package org.example.gerenciadorcontratos;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private Server server;

    public Application(){this.server = Server.getInstance();}

    public Server getServer(){
        return server;
    }

    @Override
    public void start(Stage stage) throws IOException {
        ScreenManager.setStg(stage);
        Parent root = FXMLLoader.load(getClass().getResource("login-screen.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Gerenciador de Contratos - Login");
        stage.setMinWidth(1000);
        stage.setMinHeight(600);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) throws ConfigurationLoadFailureException {
        Application app = new Application();

//        try {
//            app.getServer().saveSettings(new Settings("jdbc:mysql://localhost/dbgc?characterEncoding=utf-8","admin","admin","smtp-mail.outlook.com","587","gerenciador.contratos@outlook.com","Gerenciadorcontratos"));
//        } catch (SettingsSavedSuccessfullyException | SettingsSavingFailedException e) {
//            throw new RuntimeException(e);
//        }
        try {
//            System.out.println(app.getServer().loadSettings());
            app.getServer().setSettings(app.getServer().loadSettings());

        } catch (ConfigurationLoadFailureException e) {
            throw new RuntimeException(e);
        }
        launch();
    }
}