package org.example.gerenciadorcontratos;

public class StartDateAfterEndDateException extends Exception{
    public StartDateAfterEndDateException(){super("A data de início é posterior a data do término!");}
}
