package org.example.gerenciadorcontratos;

public class ConnectionFailureDbException extends Exception{
    public ConnectionFailureDbException(){
        super("Falha na conexão com o db!");
    }
}
