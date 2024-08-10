package org.example.gerenciadorcontratos;

import java.time.LocalDateTime;
import java.util.List;

public interface IUserDAO {
    void create(User user) throws ConnectionFailureDbException;
    void update(User user, String email, String name, String password, String verificationCode, LocalDateTime codeDateTime) throws ConnectionFailureDbException;
    void delete(User user) throws ConnectionFailureDbException;
    boolean userExists(String email) throws ConnectionFailureDbException;
    User getByEmail(String email) throws ConnectionFailureDbException;
    List<User> listAll() throws ConnectionFailureDbException;
}
