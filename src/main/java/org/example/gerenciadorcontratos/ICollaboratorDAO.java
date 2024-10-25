package org.example.gerenciadorcontratos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ICollaboratorDAO {
    void create(Collaborator collaborator) throws ConnectionFailureDbException, CopyFileFailedException;
    void update(Collaborator collaborator, String name, String cpf, String rg, String address, String telephone, String email, String office, boolean status, LocalDateTime lastPoint, LocalDate admissionDate, LocalDate terminationDate, String photoUrl) throws ConnectionFailureDbException, CopyFileFailedException;
    void delete(Collaborator collaborator) throws ConnectionFailureDbException;
    boolean collaboratorExists(String cpf) throws ConnectionFailureDbException;
    Collaborator getByCpf(String cpf) throws ConnectionFailureDbException;
    List<Collaborator> listAll() throws ConnectionFailureDbException;
}
