package org.example.gerenciadorcontratos;

public class CollaboratorWithThisCpfAlreadyExistsException extends Exception{
    public CollaboratorWithThisCpfAlreadyExistsException(){super("Colaborador com este CPF já existe!");}
}
