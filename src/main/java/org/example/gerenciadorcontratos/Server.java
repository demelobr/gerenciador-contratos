package org.example.gerenciadorcontratos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

public class Server {
    private static Server instance;
    private IUserController userController;
    private ICollaboratorController collaboratorController;
    private IContractController contractController;
    private IPresenceController presenceController;
    private IFinanceController financeController;
    private Settings settings;
//    private Cloud cloud;

    public Server(){
        this.userController = UserController.getInstance();
        this.collaboratorController = CollaboratorController.getInstance();
        this.contractController = ContractController.getInstance();
        this.presenceController = PresenceController.getInstance();
        this.financeController = FinanceController.getInstance();
    }

    public static Server getInstance(){
        if(instance == null){
            instance = new Server();
        }
        return instance;
    }

    // Setting's methods
    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void saveSettings(Settings settings) throws SettingsSavedSuccessfullyException, SettingsSavingFailedException {
        SettingsController.saveSettings(settings);
    }

    public Settings loadSettings() throws ConfigurationLoadFailureException {
        return SettingsController.loadSettings();
    }

    // User's methods
    public void createUser(User user) throws ConnectionFailureDbException, UserNullException, InvalidUserException, UserCreatedSuccessfullyException, AccountWithThisEmailAlreadyExistsException, InvalidEmailException, EmptyfieldsException, WeakPasswordException {
        userController.createUser(user);
    }

    public void updateUser(User user, String email, String name, String password, String verificationCode, LocalDateTime codeDateTime) throws ConnectionFailureDbException, UserUpdatedSuccessfullyException, UserNullException, UserDoesNotExistException {
        userController.updateUser(user, email, name, password, verificationCode, codeDateTime);
    }

    public void deleteUser(User user) throws UserDeletedSuccessfullyException, ConnectionFailureDbException, UserNullException, UserDoesNotExistException {
        userController.deleteUser(user);
    }

    public boolean checkUserCredentials(String email, String password) throws ConnectionFailureDbException, UserDoesNotExistException {
        return userController.checkUserCredentials(email, password);
    }

    public boolean checkUserData(User user) throws ConnectionFailureDbException, AccountWithThisEmailAlreadyExistsException, InvalidEmailException, EmptyfieldsException, WeakPasswordException {
        return userController.checkUserData(user);
    }

    public boolean userExists(String email) throws ConnectionFailureDbException {
        return userController.userExists(email);
    }

    public User getUserByEmail(String email) throws ConnectionFailureDbException, UserDoesNotExistException {
        return userController.getUserByEmail(email);
    }

    public String generateVerificationCode() {
        return userController.generateVerificationCode();
    }

    public void setVerificationCode(User user, String verificationCode) throws ConnectionFailureDbException, UserUpdatedSuccessfullyException, UserNullException, UserDoesNotExistException {
        userController.setVerificationCode(user, verificationCode);
    }

    public boolean isValidVerificationCode(LocalDateTime codeDateTime) {
        return userController.isValidVerificationCode(codeDateTime);
    }

    public List<User> listAllUsers() throws ConnectionFailureDbException {
        return userController.listAllUsers();
    }

    // Collaborators's methods
    public void createCollaborator(Collaborator collaborator) throws InvalidCpfException, ConnectionFailureDbException, InvalidCollaboratorException, CollaboratorWithThisCpfAlreadyExistsException, CollaboratorCreatedSuccessfullyException, CollaboratorNullException, InvalidEmailException, EmptyfieldsException {
        collaboratorController.createCollaborator(collaborator);
    }

    public void updateCollaborator(Collaborator collaborator, String name, String cpf, String rg, String address, String telephone, String email, String office, boolean status, LocalDateTime lastPoint, LocalDate admissionDate, LocalDate terminationDate, String photoUrl) throws ConnectionFailureDbException, CollaboratorUpdatedSuccessfullyException, CollaboratorNullException, CollaboratorDoesNotExistException {
        collaboratorController.updateCollaborator(collaborator, name, cpf, rg, address, telephone, email, office, status, lastPoint, admissionDate, terminationDate, photoUrl);
    }

    public void deleteCollaborator(Collaborator collaborator) throws ConnectionFailureDbException, CollaboratorDeletedSuccessfullyException, CollaboratorNullException, CollaboratorDoesNotExistException {
        collaboratorController.deleteCollaborator(collaborator);
    }

    public boolean collaboratorExists(String cpf) throws ConnectionFailureDbException {
        return collaboratorController.collaboratorExists(cpf);
    }

    public boolean checkCollaboratorData(Collaborator collaborator) throws InvalidCpfException, ConnectionFailureDbException, CollaboratorWithThisCpfAlreadyExistsException, InvalidEmailException, EmptyfieldsException {
        return collaboratorController.checkCollaboratorData(collaborator);
    }

    public Collaborator getCollaboratorByCpf(String cpf) throws ConnectionFailureDbException, CollaboratorDoesNotExistException {
        return collaboratorController.getCollaboratorByCpf(cpf);
    }

    public List<Collaborator> listAllCollaborators() throws ConnectionFailureDbException {
        return collaboratorController.listAllCollaborators();
    }

    // Contract's methods
    public void createContract(Contract contract) throws ContractCreatedSuccessfullyException, InvalidBudgetException, ConnectionFailureDbException, InvalidContractException, ContractNullException, EmptyfieldsException, StartDateAfterEndDateException {
        contractController.createContract(contract);
    }

    public void updateContract(Contract contract, String name, String description, String address, float budget, LocalDate startDate, LocalDate endDate) throws ConnectionFailureDbException, ContractUpdatedSuccessfullyException, ContractDoesNotExistException, ContractNullException {
        contractController.updateContract(contract, name, description, address, budget, startDate, endDate);
    }

    public void deleteContarct(Contract contract) throws ConnectionFailureDbException, ContractDeletedSuccessfullyException, ContractDoesNotExistException, ContractNullException {
        contractController.deleteContarct(contract);
    }

    public boolean contractExists(String name) throws ConnectionFailureDbException {
        return contractController.contractExists(name);
    }

    public boolean checkContractData(Contract contract) throws InvalidBudgetException, EmptyfieldsException, StartDateAfterEndDateException {
        return contractController.checkContractData(contract);
    }

    public Contract getContractByName(String name) throws ConnectionFailureDbException, ContractDoesNotExistException {
        return contractController.getContractByName(name);
    }

    public List<Contract> listAllContracts() throws ConnectionFailureDbException {
        return contractController.listAllContracts();
    }

    // Presence's methods
    public void createPresence(Presence presence) throws InvalidPresenceException, InvalidPresenseOrRecordDateTimeException, ConnectionFailureDbException, PresenceCreatedSuccessfullyException, PresenceNullException, EmptyfieldsException, ThereIsAlreadyARegisteredPresenceException, JustificationRequiredException {
        presenceController.createPresence(presence);
    }

    public void updatePresence(Presence presence, String cpfCollaborator, String nameContract, String record, String status, String justification, String observation, LocalDate presenceDate, int presenceHour, int presenceMinute) throws ConnectionFailureDbException, PresenceDoesNotExistException, PresenceNullException, PresenceUpdatedSuccessfullyException {
        presenceController.updatePresence(presence, cpfCollaborator, nameContract, record, status, justification, observation, presenceDate, presenceHour, presenceMinute);
    }

    public void deletePresence(Presence presence) throws ConnectionFailureDbException, PresenceDoesNotExistException, PresenceNullException, PresenceDeletedSuccessfullyException {
        presenceController.deletePresence(presence);
    }

    public boolean presenceExists(String cpfCollaborator, LocalDateTime recordDataTime) throws ConnectionFailureDbException {
        return presenceController.presenceExists(cpfCollaborator, recordDataTime);
    }

    public boolean checkIfThereIsAnExistingPresenceWithTheRecord(String cpfCollaborator, LocalDateTime presenceDateTime, String currentRecord, String newRecord) throws ConnectionFailureDbException {
        return presenceController.checkIfThereIsAnExistingPresenceWithTheRecord(cpfCollaborator, presenceDateTime, currentRecord, newRecord);
    }

    public boolean checkPresenceData(Presence presence) throws InvalidPresenseOrRecordDateTimeException, EmptyfieldsException, ConnectionFailureDbException, ThereIsAlreadyARegisteredPresenceException, JustificationRequiredException {
        return presenceController.checkPresenceData(presence);
    }

    public Presence getPresenceByCpfAndDateTime(String cpfCollaborator, LocalDateTime recordDataTime) throws ConnectionFailureDbException, PresenceDoesNotExistException {
        return presenceController.getPresenceByCpfAndDateTime(cpfCollaborator, recordDataTime);
    }

    public List<Presence> listAllPresencesWithFilters(String queryCpf, String queryRecord, String queryStatus, String queryContract, LocalDate queryStartDateTimePeriod, LocalDate queryEndDateTimePeriod) throws ConnectionFailureDbException {
        return presenceController.listAllPresencesWithFilters(queryCpf, queryRecord, queryStatus, queryContract, queryStartDateTimePeriod, queryEndDateTimePeriod);
    }

    public List<Presence> listAllPresencesByCpf(String cpf) throws ConnectionFailureDbException {
        return presenceController.listAllPresencesByCpf(cpf);
    }

    // Finance's methods
    public void createFiance(Finance finance) throws FinanceNullException, ConnectionFailureDbException, FinanceCreatedSuccessfullyException, EmptyfieldsException, InvalidFinanceAmountException {
        financeController.createFiance(finance);
    }

    public void updateFiance(Finance finance, String title, String notes, String contractName, String type, String financeClass, String paymentMethod, LocalDate date, LocalDateTime recordDateTime, double value, String collaboratorCpf) throws FinanceNullException, ConnectionFailureDbException, FinanceDoesNotExistException, FinanceUpdatedSuccessfullyException {
        financeController.updateFiance(finance, title, notes, contractName, type, financeClass, paymentMethod, date, recordDateTime, value, collaboratorCpf);
    }

    public void deleteFiance(Finance finance) throws FinanceDeletedSuccessfullyException, FinanceNullException, ConnectionFailureDbException, FinanceDoesNotExistException {
        financeController.deleteFiance(finance);
    }

    public boolean financeExist(String contractName, LocalDateTime recordDateTime) throws ConnectionFailureDbException {
        return financeController.financeExist(contractName, recordDateTime);
    }

    public boolean checkFinanceData(Finance finance) throws EmptyfieldsException, InvalidFinanceAmountException {
        return financeController.checkFinanceData(finance);
    }

    public Finance getFinanceByContractNameAndRecordDateTime(String contractName, LocalDateTime recordDateTime) throws ConnectionFailureDbException, FinanceDoesNotExistException {
        return financeController.getFinanceByContractNameAndRecordDateTime(contractName, recordDateTime);
    }

    public List<Double> getListOfEntriesForTheYearByMonth(Year year) throws ConnectionFailureDbException {
        return financeController.getListOfEntriesForTheYearByMonth(year);
    }

    public List<Double> getListOfExpensesForTheYearByMonth(Year year) throws ConnectionFailureDbException {
        return financeController.getListOfExpensesForTheYearByMonth(year);
    }

    public List<Finance> listAllFinancesWithFilters(String queryType, String queryCpf, LocalDate queryStartDateTimePeriod, LocalDate queryEndDateTimePeriod, String queryContract, String queryMinValue, String queryMaxValue) throws ConnectionFailureDbException {
        return financeController.listAllFinancesWithFilters(queryType, queryCpf, queryStartDateTimePeriod, queryEndDateTimePeriod, queryContract, queryMinValue, queryMaxValue);
    }

    public List<Finance> listAllFinancesByCollaboratorCpf(String cpf) throws ConnectionFailureDbException {
        return financeController.listAllFinancesByCollaboratorCpf(cpf);
    }

    public List<Finance> listAllFinances() throws ConnectionFailureDbException {
        return financeController.listAllFinances();
    }

}
