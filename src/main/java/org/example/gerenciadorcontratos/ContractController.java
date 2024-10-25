package org.example.gerenciadorcontratos;

import java.time.LocalDate;
import java.util.List;

public class ContractController implements IContractController{
    private static IContractController instance;
    private IContractDAO contractRepository;

    public ContractController(){
        this.contractRepository = ContractDAO.getInstance();
    }

    public static IContractController getInstance(){
        if(instance == null){
            instance = new ContractController();
        }
        return instance;
    }

    @Override
    public void createContract(Contract contract) throws ConnectionFailureDbException, ContractCreatedSuccessfullyException, InvalidContractException, ContractNullException, InvalidBudgetException, EmptyfieldsException, StartDateAfterEndDateException, ContractWithThisNameAlreadyExistsException, CopyFileFailedException {
        if(contract != null){
            if(this.checkContractData(contract)){
                if(contract.getStartDate() == null) contract.setStartDate(contract.getExpectedStartDate());
                if(contract.getEndDate() == null) contract.setEndDate(contract.getExpectedEndDate());
                if(contract.getEngineer().equals("----------")) contract.setEngineer("NÃO INFORMADO");
                contractRepository.create(contract);
                throw new ContractCreatedSuccessfullyException();
            }else{
                throw new InvalidContractException();
            }
        }else{
            throw new ContractNullException();
        }
    }

    @Override
    public void updateContract(Contract contract, String name, String description, String address, String engineer, String contractFile, LocalDate expectedStartDate, LocalDate expectedEndDate, LocalDate startDate, LocalDate endDate) throws ConnectionFailureDbException, ContractUpdatedSuccessfullyException, ContractDoesNotExistException, ContractNullException, CopyFileFailedException {
        if(contract != null){
            if(this.contractExists(contract.getName())){
                if(name.isEmpty() || contract.getName().equals(name)){
                    name = contract.getName();
                }
                if(description.isEmpty() || contract.getDescription().equals(description)){
                    description = contract.getDescription();
                }
                if(address.isEmpty() || contract.getAddress().equals(address)){
                    address = contract.getAddress();
                }
                if(engineer.isEmpty() || contract.getEngineer().equals(engineer)){
                    engineer = contract.getEngineer();
                }
                if(contractFile.isEmpty() || contract.equals(contractFile)){
                    contractFile = contract.getContractFile();
                }
                if(contract.getExpectedStartDate().isEqual(expectedStartDate)){
                    expectedStartDate = contract.getExpectedStartDate();
                }
                if(contract.getExpectedEndDate().isEqual(expectedEndDate)){
                    expectedEndDate = contract.getExpectedEndDate();
                }
                if(contract.getStartDate().isEqual(startDate)){
                    startDate = contract.getStartDate();
                }
                if(contract.getEndDate().isEqual(endDate)){
                    endDate = contract.getEndDate();
                }
                contractRepository.update(contract, name, description, address, engineer, contractFile, expectedStartDate, expectedEndDate, startDate, endDate);
                throw new ContractUpdatedSuccessfullyException();
            }else{
                throw new ContractDoesNotExistException();
            }
        }else{
            throw new ContractNullException();
        }
    }

    @Override
    public void deleteContarct(Contract contract) throws ConnectionFailureDbException, ContractDeletedSuccessfullyException, ContractDoesNotExistException, ContractNullException {
        if(contract != null){
            if(this.contractExists(contract.getName())){
                contractRepository.delete(contract);
                throw new ContractDeletedSuccessfullyException();
            }else{
                throw new ContractDoesNotExistException();
            }
        }else{
            throw new ContractNullException();
        }
    }

    @Override
    public boolean contractExists(String name) throws ConnectionFailureDbException {
        return contractRepository.contractExists(name);
    }

    @Override
    public boolean checkContractData(Contract contract) throws EmptyfieldsException, StartDateAfterEndDateException, ConnectionFailureDbException, ContractWithThisNameAlreadyExistsException {
        boolean contractChecked = true;

        if(this.contractExists(contract.getName())){
            contractChecked = false;
            throw new ContractWithThisNameAlreadyExistsException();
        }
        if(contract.getName().isEmpty() || contract.getAddress().isEmpty() || contract.getEngineer().isEmpty() || contract.getExpectedStartDate() == null || contract.getExpectedEndDate() == null){
            contractChecked = false;
            throw new EmptyfieldsException();
        }
        if(contract.getExpectedStartDate().isAfter(contract.getExpectedEndDate())){
            contractChecked = false;
            throw new StartDateAfterEndDateException();
        }
        if(contract.getStartDate() != null && contract.getEndDate() != null){
            if(contract.getStartDate().isAfter(contract.getEndDate())){
                contractChecked = false;
                throw new StartDateAfterEndDateException();
            }
        }
        return contractChecked;
    }

    @Override
    public Contract getContractByName(String name) throws ConnectionFailureDbException, ContractDoesNotExistException {
        Contract contract = contractRepository.getByName(name);
        if(contract == null) throw new ContractDoesNotExistException();
        return contract;
    }

    @Override
    public List<Contract> listAllContracts() throws ConnectionFailureDbException {
        return contractRepository.listAll();
    }

}
