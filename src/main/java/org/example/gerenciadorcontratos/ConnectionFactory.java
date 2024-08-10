package org.example.gerenciadorcontratos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection() throws ConnectionFailureDbException {
        try {
            Server server = Server.getInstance();
            Settings settings = server.getSettings();
            return DriverManager.getConnection(settings.getUrlDB(), settings.getUserDB(), settings.getPasswordDB());
        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
    }
}
