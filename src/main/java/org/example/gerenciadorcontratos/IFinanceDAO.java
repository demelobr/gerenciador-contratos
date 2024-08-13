package org.example.gerenciadorcontratos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

public interface IFinanceDAO {
    void create(Finance finance) throws ConnectionFailureDbException;
    void update(Finance finance, String title, String notes, String contractName, String type, String paymentMethod, LocalDate date, LocalDateTime recordDateTime, double value, String collaboratorCpf) throws ConnectionFailureDbException;
    void delete(Finance finance) throws ConnectionFailureDbException;
    boolean financeExists(String contractName, LocalDateTime recordDateTime) throws ConnectionFailureDbException;
    Finance getFinanceByContractNameAndRecordDateTime(String contractName, LocalDateTime recordDateTime) throws ConnectionFailureDbException;
    List<Double> getListOfEntriesForTheYearByMonth(Year year) throws ConnectionFailureDbException;
    List<Double> getListOfExpensesForTheYearByMonth(Year year) throws ConnectionFailureDbException;
    List<Finance> listAllWithFilters(String queryCpf, LocalDate queryStartDateTimePeriod, LocalDate queryEndDateTimePeriod, String queryContract, String queryMinValue, String queryMaxValue) throws ConnectionFailureDbException;
    List<Finance> listAllByCollaboratorCpf(String cpf) throws ConnectionFailureDbException;
    List<Finance> listAll() throws ConnectionFailureDbException;
}
