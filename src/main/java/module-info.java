module org.example.gerenciadorcontratos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;
    requires jasperreports;


    opens org.example.gerenciadorcontratos to javafx.fxml;
    exports org.example.gerenciadorcontratos;
}