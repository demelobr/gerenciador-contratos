package org.example.gerenciadorcontratos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO{
    private static IUserDAO instance;

    private UserDAO(){}

    public static IUserDAO getInstance(){
        if(instance == null){
            instance = new UserDAO();
        }
        return instance;
    }

    @Override
    public void create(User user) throws ConnectionFailureDbException {
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "INSERT INTO users (email, name, password, verificationCode, codeDateTime) VALUES (?,?,?,?,?)";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getEmail().toUpperCase());
            ps.setString(2, user.getName().toUpperCase());
            ps.setString(3, user.getPassword().toUpperCase());
            ps.setString(4, user.getVerificationCode().toUpperCase());
            ps.setString(5, user.getCodeDateTime().format(dateTimeFormatter));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
    }

    @Override
    public void update(User user, String email, String name, String password, String verificationCode, LocalDateTime codeDateTime) throws ConnectionFailureDbException {
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "UPDATE users SET email = ?, name = ?, password = ?, verificationCode = ?, codeDateTime = ? WHERE email = ?";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email.toUpperCase());
            ps.setString(2, name.toUpperCase());
            ps.setString(3, password.toUpperCase());
            ps.setString(4, verificationCode.toUpperCase());
            ps.setString(5, codeDateTime.format(dateTimeFormatter));
            ps.setString(6, user.getEmail().toUpperCase());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
    }

    @Override
    public void delete(User user) throws ConnectionFailureDbException {
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "DELETE FROM users WHERE email = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getEmail().toUpperCase());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
    }

    @Override
    public boolean userExists(String email) throws ConnectionFailureDbException {
        boolean userExists = false;
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT id, email, name, password, verificationCode, codeDateTime FROM users WHERE email = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email.toUpperCase());

            try (ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    userExists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
        return userExists;
    }

    @Override
    public User getByEmail(String queryEmail) throws ConnectionFailureDbException {
        User user = null;
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT id, email, name, password, verificationCode, codeDateTime FROM users WHERE email = ?";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, queryEmail.toUpperCase());

            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    String email = rs.getString("email");
                    String name = rs.getString("name");
                    String password = rs.getString("password");
                    String verificationCode = rs.getString("verificationCode");
                    LocalDateTime codeDateTime = LocalDateTime.parse(rs.getString("codeDateTime"), dateTimeFormatter);
                    user = new User(email, name, password, verificationCode, codeDateTime);
                }
            }

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
        return user;
    }

    @Override
    public List<User> listAll() throws ConnectionFailureDbException {
        List<User> listOfUsers = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT id, email, name, password, verificationCode, codeDateTime FROM users";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    String email = rs.getString("email");
                    String name = rs.getString("name");
                    String password = rs.getString("password");
                    String verificationCode = rs.getString("verificationCode");
                    LocalDateTime codeDateTime = LocalDateTime.parse(rs.getString("codeDateTime"), dateTimeFormatter);
                    listOfUsers.add(new User(email, name, password, verificationCode, codeDateTime));
                }
            }
        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
        return listOfUsers;
    }

}
