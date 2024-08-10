package org.example.gerenciadorcontratos;

public class WeakPasswordException extends Exception{
    public WeakPasswordException(){super("Senha precisa ao menos de 8 dígitos.");}
}
