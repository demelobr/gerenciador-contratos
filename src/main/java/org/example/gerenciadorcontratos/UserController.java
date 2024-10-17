package org.example.gerenciadorcontratos;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserController implements IUserController{
    private static IUserController instance;
    private  IUserDAO userRepository;

    public  UserController(){
        this.userRepository = UserDAO.getInstance();
    }

    public static IUserController getInstance(){
        if(instance == null){
            instance = new UserController();
        }
        return instance;
    }

    @Override
    public void createUser(User user) throws ConnectionFailureDbException, UserCreatedSuccessfullyException, InvalidUserException, UserNullException, AccountWithThisEmailAlreadyExistsException, InvalidEmailException, EmptyfieldsException, WeakPasswordException {
        if(user != null){
            if(this.checkUserData(user)){
                userRepository.create(user);
                throw new UserCreatedSuccessfullyException();
            }else{
                throw new InvalidUserException();
            }
        }else{
            throw new UserNullException();
        }
    }

    @Override
    public void updateUser(User user, String email, String name, String password, String verificationCode, LocalDateTime codeDateTime) throws ConnectionFailureDbException, UserUpdatedSuccessfullyException, UserNullException, UserAlreadyExistsEception, UserDoesNotExistException {
        if(user != null){
            if(this.userExists(user.getEmail())){
                if(user.getEmail().equals(email) || !this.userExists(email)){
                    if(email.isEmpty() || user.getEmail().equals(email)){
                        email = user.getEmail();
                    }
                    if(name.isEmpty() || user.getName().equals(name)){
                        name = user.getName();
                    }
                    if(password.isEmpty() || user.getPassword().equals(password)){
                        password = user.getPassword();
                    }
                    if(verificationCode.isEmpty() || user.getVerificationCode().equals(verificationCode)){
                        verificationCode = user.getVerificationCode();
                    }
                    if(user.getCodeDateTime().isEqual(codeDateTime)){
                        codeDateTime = user.getCodeDateTime();
                    }
                    userRepository.update(user, email, name, password, verificationCode, codeDateTime);
                    throw new UserUpdatedSuccessfullyException();
                }else{
                    throw new UserAlreadyExistsEception();
                }
            }else{
                throw new UserDoesNotExistException();
            }
        }else{
            throw new UserNullException();
        }
    }

    @Override
    public void deleteUser(User user) throws ConnectionFailureDbException, UserDeletedSuccessfullyException, UserDoesNotExistException, UserNullException {
        if(user != null){
            if(this.userExists(user.getEmail())){
                userRepository.delete(user);
                throw new UserDeletedSuccessfullyException();
            }else{
                throw new UserDoesNotExistException();
            }
        }else{
            throw new UserNullException();
        }
    }

    @Override
    public boolean checkUserCredentials(String email, String password) throws ConnectionFailureDbException, UserDoesNotExistException {
        User user = this.getUserByEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public boolean checkUserData(User user) throws ConnectionFailureDbException, EmptyfieldsException, AccountWithThisEmailAlreadyExistsException, InvalidEmailException, WeakPasswordException {
        boolean userChecked = true;

        if(user.getEmail().isEmpty() || user.getName().isEmpty() || user.getPassword().isEmpty()){
            userChecked = false;
            throw new EmptyfieldsException();
        }
        if(this.userExists(user.getEmail())){
            userChecked = false;
            throw new AccountWithThisEmailAlreadyExistsException();
        }
        if(!isValidEmail(user.getEmail())){
            userChecked = false;
            throw new InvalidEmailException();
        }
        if(user.getPassword().length() < 8){
            userChecked = false;
            throw new WeakPasswordException();
        }

        return userChecked;
    }

    @Override
    public boolean userExists(String email) throws ConnectionFailureDbException {
        return userRepository.userExists(email);
    }

    @Override
    public User getUserByEmail(String email) throws ConnectionFailureDbException, UserDoesNotExistException {
        User user = userRepository.getByEmail(email);
        if(user == null) throw new UserDoesNotExistException();
        return user;
    }

    @Override
    public List<User> listAllUsers() throws ConnectionFailureDbException {
        return userRepository.listAll();
    }

    @Override
    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public String generateVerificationCode() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(5);
        for(int i = 0; i < 5; i++){
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

    @Override
    public void setVerificationCode(User user, String verificationCode) throws ConnectionFailureDbException, UserUpdatedSuccessfullyException, UserNullException, UserAlreadyExistsEception, UserDoesNotExistException {
        this.updateUser(user, user.getEmail(), user.getName(), user.getPassword(), verificationCode, LocalDateTime.now());
    }

    @Override
    public boolean isValidVerificationCode(LocalDateTime codeDateTime){
        Duration duration = Duration.between(codeDateTime, LocalDateTime.now());
        long seconds = duration.getSeconds();
        return Math.abs(seconds) < 90;
    }

}
