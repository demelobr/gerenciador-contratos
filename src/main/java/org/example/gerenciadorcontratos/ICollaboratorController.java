package org.example.gerenciadorcontratos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ICollaboratorController {
    void createCollaborator(Collaborator collaborator) throws ConnectionFailureDbException, CollaboratorCreatedSuccessfullyException, InvalidCollaboratorException, CollaboratorNullException, InvalidCpfException, CollaboratorWithThisCpfAlreadyExistsException, InvalidEmailException, EmptyfieldsException, CopyFileFailedException;
    void updateCollaborator(Collaborator collaborator, String name, String cpf, String rg, String address, String telephone, String email, String office, boolean status, LocalDateTime lastPoint, LocalDate admissionDate, LocalDate terminationDate, String photoUrl) throws ConnectionFailureDbException, CollaboratorUpdatedSuccessfullyException, CollaboratorDoesNotExistException, CollaboratorNullException, CopyFileFailedException;
    void deleteCollaborator(Collaborator collaborator) throws ConnectionFailureDbException, CollaboratorDeletedSuccessfullyException, CollaboratorDoesNotExistException, CollaboratorNullException;
    boolean collaboratorExists(String cpf) throws ConnectionFailureDbException;
    boolean checkCollaboratorData(Collaborator collaborator) throws ConnectionFailureDbException, EmptyfieldsException, CollaboratorWithThisCpfAlreadyExistsException, InvalidCpfException, InvalidEmailException;
    Collaborator getCollaboratorByCpf(String cpf) throws ConnectionFailureDbException, CollaboratorDoesNotExistException;
    List<Collaborator> listAllCollaborators() throws ConnectionFailureDbException;
    boolean isValidEmail(String email);
    boolean isValidCpf(String cpf);
}
