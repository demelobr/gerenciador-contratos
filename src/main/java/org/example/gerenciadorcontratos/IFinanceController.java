package org.example.gerenciadorcontratos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

public interface IFinanceController {
    void createFiance(Finance finance) throws ConnectionFailureDbException, FinanceCreatedSuccessfullyException, EmptyfieldsException, InvalidFinanceAmountException, FinanceNullException;
    void updateFiance(Finance finance, String title, String notes, String contractName, String type, String paymentMethod, LocalDate date, LocalDateTime recordDateTime, double value, String collaboratorCpf) throws ConnectionFailureDbException, FinanceUpdatedSuccessfullyException, FinanceDoesNotExistException, FinanceNullException;
    void deleteFiance(Finance finance) throws ConnectionFailureDbException, FinanceDeletedSuccessfullyException, FinanceDoesNotExistException, FinanceNullException;
    boolean financeExist(String contractName, LocalDateTime recordDateTime) throws ConnectionFailureDbException;
    boolean checkFinanceData(Finance finance) throws EmptyfieldsException, InvalidFinanceAmountException;
    Finance getFinanceByContractNameAndRecordDateTime(String contractName, LocalDateTime recordDateTime) throws ConnectionFailureDbException, FinanceDoesNotExistException;
    List<Double> getListOfEntriesForTheYearByMonth(Year year) throws ConnectionFailureDbException;
    List<Double> getListOfExpensesForTheYearByMonth(Year year) throws ConnectionFailureDbException;
    List<Finance> listAllFinancesWithFilters(String queryType, String queryCpf, LocalDate queryStartDateTimePeriod, LocalDate queryEndDateTimePeriod, String queryContract, String queryMinValue, String queryMaxValue) throws ConnectionFailureDbException;
    List<Finance> listAllFinancesByCollaboratorCpf(String cpf) throws ConnectionFailureDbException;
    List<Finance> listAllFinances() throws ConnectionFailureDbException;
}
