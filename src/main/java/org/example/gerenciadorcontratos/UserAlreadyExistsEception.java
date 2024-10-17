package org.example.gerenciadorcontratos;

public class UserAlreadyExistsEception extends Exception {
    public UserAlreadyExistsEception() {super("Usúario com este email já existe!");}
}
