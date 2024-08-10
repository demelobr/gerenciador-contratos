package org.example.gerenciadorcontratos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DeleteFinanceScreenController {
    private Application app;

    public DeleteFinanceScreenController(){
        app = new Application();
    }

    @FXML
    private Button btnNoDeleteFinanceWindow;

    @FXML
    private Button btnYesDeleteFinanceWindow;

    public Button getBtnNoDeleteFinanceWindow() {
        return btnNoDeleteFinanceWindow;
    }

    public Button getBtnYesDeleteFinanceWindow() {
        return btnYesDeleteFinanceWindow;
    }

}
