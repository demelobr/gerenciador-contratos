package org.example.gerenciadorcontratos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DeletePresenceScreenController {
    private Application app;

    public DeletePresenceScreenController(){
        this.app = new Application();
    }

    @FXML
    private Button btnNoDeletePresenceWindow;

    @FXML
    private Button btnYesDeletePresenceWindow;

    public Button getBtnNoDeletePresenceWindow() {
        return btnNoDeletePresenceWindow;
    }

    public Button getBtnYesDeletePresenceWindow() {
        return btnYesDeletePresenceWindow;
    }
}
