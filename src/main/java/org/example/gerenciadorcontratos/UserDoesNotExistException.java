package org.example.gerenciadorcontratos;

public class UserDoesNotExistException extends Exception{
    public UserDoesNotExistException(){super("User não existe!");}
}
