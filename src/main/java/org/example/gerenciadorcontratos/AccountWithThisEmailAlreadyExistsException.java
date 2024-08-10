package org.example.gerenciadorcontratos;

public class AccountWithThisEmailAlreadyExistsException extends Exception{
    public AccountWithThisEmailAlreadyExistsException(){super("Já existe uma conta com este e-mail!");}
}
