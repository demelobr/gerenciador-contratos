package org.example.gerenciadorcontratos;

public class ContractWithThisNameAlreadyExistsException extends Exception{
    public ContractWithThisNameAlreadyExistsException(){super("Já existe um contrato com este nome!");}
}
