package org.example.gerenciadorcontratos;

import java.time.LocalDateTime;
import java.util.List;

public interface IUserController {
    void createUser(User user) throws ConnectionFailureDbException, UserCreatedSuccessfullyException, InvalidUserException, UserNullException, AccountWithThisEmailAlreadyExistsException, InvalidEmailException, EmptyfieldsException, WeakPasswordException;
    void updateUser(User user, String email, String name, String password, String verificationCode, LocalDateTime codeDateTime) throws ConnectionFailureDbException, UserUpdatedSuccessfullyException, UserNullException, UserAlreadyExistsEception, UserDoesNotExistException;
    void deleteUser(User user) throws ConnectionFailureDbException, UserDeletedSuccessfullyException, UserDoesNotExistException, UserNullException;
    boolean checkUserCredentials(String email, String password) throws ConnectionFailureDbException, UserDoesNotExistException;
    boolean checkUserData(User user) throws ConnectionFailureDbException, EmptyfieldsException, AccountWithThisEmailAlreadyExistsException, InvalidEmailException, WeakPasswordException;
    boolean userExists(String email) throws ConnectionFailureDbException;
    User getUserByEmail(String email) throws ConnectionFailureDbException, UserDoesNotExistException;
    List<User> listAllUsers() throws ConnectionFailureDbException;
    boolean isValidEmail(String email);
    String generateVerificationCode();
    void setVerificationCode(User user, String verificationCode) throws ConnectionFailureDbException, UserUpdatedSuccessfullyException, UserNullException, UserAlreadyExistsEception, UserDoesNotExistException;
    boolean isValidVerificationCode(LocalDateTime codeDateTime);
}
