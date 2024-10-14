package org.example.gerenciadorcontratos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DeleteContractScreenController {
    private Application app;

    @FXML
    private Button btnNoDeleteContractWindow;

    @FXML
    private Button btnYesDeleteContractWindow;

    public DeleteContractScreenController() {
        this.app = new Application();
    }

    public Button getBtnNoDeleteContractWindow() {
        return btnNoDeleteContractWindow;
    }

    public Button getBtnYesDeleteContractWindow() {
        return btnYesDeleteContractWindow;
    }

}
