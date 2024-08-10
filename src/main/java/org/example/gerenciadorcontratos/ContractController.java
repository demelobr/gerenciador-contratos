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
    public void createContract(Contract contract) throws ConnectionFailureDbException, ContractCreatedSuccessfullyException, InvalidContractException, ContractNullException, InvalidBudgetException, EmptyfieldsException, StartDateAfterEndDateException {
        if(contract != null){
            if(this.checkContractData(contract)){
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
    public void updateContract(Contract contract, String name, String description, String address, float budget, LocalDate startDate, LocalDate endDate) throws ConnectionFailureDbException, ContractUpdatedSuccessfullyException, ContractDoesNotExistException, ContractNullException {
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
                if(budget < 0 || contract.getBudget() == budget){
                    budget = contract.getBudget();
                }
                if(contract.getStartDate().isEqual(startDate)){
                    startDate = contract.getStartDate();
                }
                if(contract.getEndDate().isEqual(endDate)){
                    endDate = contract.getEndDate();
                }
                contractRepository.update(contract, name, description, address, budget, startDate, endDate);
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
    public boolean checkContractData(Contract contract) throws EmptyfieldsException, InvalidBudgetException, StartDateAfterEndDateException {
        boolean contractChecked = true;
        if(contract.getName().isEmpty() || contract.getAddress().isEmpty()){
            contractChecked = false;
            throw new EmptyfieldsException();
        }
        if(contract.getBudget() <= 0){
            contractChecked = false;
            throw new InvalidBudgetException();
        }
        if(contract.getStartDate().isAfter(contract.getEndDate())){
            contractChecked = false;
            throw new StartDateAfterEndDateException();
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
