package org.example.gerenciadorcontratos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DeleteCollaboratorScreenController {
    private Application app;

    @FXML
    private Button btnNoDeleteCollaboratorWindow;

    @FXML
    private Button btnYesDeleteCollaboratorWindow;

    public DeleteCollaboratorScreenController(){
        this.app = new Application();
    }

    public Button getBtnNoDeleteCollaboratorWindow() {
        return btnNoDeleteCollaboratorWindow;
    }

    public Button getBtnYesDeleteCollaboratorWindow() {
        return btnYesDeleteCollaboratorWindow;
    }

}
