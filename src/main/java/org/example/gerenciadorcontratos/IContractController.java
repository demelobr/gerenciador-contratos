package org.example.gerenciadorcontratos;

import java.time.LocalDate;
import java.util.List;

public interface IContractController {
    void createContract(Contract contract) throws ConnectionFailureDbException, ContractCreatedSuccessfullyException, InvalidContractException, ContractNullException, InvalidBudgetException, EmptyfieldsException, StartDateAfterEndDateException;
    void updateContract(Contract contract, String name, String description, String address, float budget, LocalDate startDate, LocalDate endDate) throws ConnectionFailureDbException, ContractUpdatedSuccessfullyException, ContractDoesNotExistException, ContractNullException;
    void deleteContarct(Contract contract) throws ConnectionFailureDbException, ContractDeletedSuccessfullyException, ContractDoesNotExistException, ContractNullException;
    boolean contractExists(String name) throws ConnectionFailureDbException;
    boolean checkContractData(Contract contract) throws EmptyfieldsException, InvalidBudgetException, StartDateAfterEndDateException;
    Contract getContractByName(String name) throws ConnectionFailureDbException, ContractDoesNotExistException;
    List<Contract> listAllContracts() throws ConnectionFailureDbException;
}
