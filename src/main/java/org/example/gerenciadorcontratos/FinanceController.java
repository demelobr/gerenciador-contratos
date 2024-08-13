package org.example.gerenciadorcontratos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

public class FinanceController implements IFinanceController{
    private static IFinanceController instance;
    private IFinanceDAO financeRepository;

    public FinanceController() {
        this.financeRepository = FinanceDAO.getInstance();
    }

    public static IFinanceController getInstance() {
        if (instance == null) {
            instance = new FinanceController();
        }
        return instance;
    }

    @Override
    public void createFiance(Finance finance) throws ConnectionFailureDbException, FinanceCreatedSuccessfullyException, EmptyfieldsException, InvalidFinanceAmountException, FinanceNullException {
        if(finance != null){
            if(this.checkFinanceData(finance)){
                financeRepository.create(finance);
                throw new FinanceCreatedSuccessfullyException();
            }
        }else{
            throw new FinanceNullException();
        }
    }

    @Override
    public void updateFiance(Finance finance, String title, String notes, String contractName, String type, String paymentMethod, LocalDate date, LocalDateTime recordDateTime, double value, String collaboratorCpf) throws ConnectionFailureDbException, FinanceUpdatedSuccessfullyException, FinanceDoesNotExistException, FinanceNullException {
        if(finance != null){
           if(this.financeExist(finance.getContractName(), finance.getRecordDateTime())){
               if(title.isEmpty() || finance.getTitle().equals(title)){
                   title = finance.getTitle();
               }
               if(finance.getNotes().equals(notes)){
                   notes = finance.getNotes();
               }
               if(contractName.isEmpty() || finance.getContractName().equals(contractName)){
                   contractName = finance.getContractName();
               }
               if(type.isEmpty() || finance.getType().equals(type)){
                   type = finance.getType();
               }
               if(paymentMethod.isEmpty() || finance.getPaymentMethod().equals(paymentMethod)){
                   paymentMethod = finance.getPaymentMethod();
               }
               if(finance.getDate().isEqual(date)){
                    date = finance.getDate();
               }
               if(finance.getRecordDateTime().isEqual(recordDateTime)){
                   recordDateTime = finance.getRecordDateTime();
               }
               if(finance.getValue() == value){
                   value = finance.getValue();
               }
               if(collaboratorCpf.isEmpty() || finance.getCollaboratorCpf().equals(collaboratorCpf)){
                   collaboratorCpf = finance.getCollaboratorCpf();
               }
               financeRepository.update(finance, title, notes, contractName, type, paymentMethod, date, recordDateTime, value, collaboratorCpf);
               throw new FinanceUpdatedSuccessfullyException();
           }else{
               throw new FinanceDoesNotExistException();
           }
        }else{
            throw new FinanceNullException();
        }
    }

    @Override
    public void deleteFiance(Finance finance) throws ConnectionFailureDbException, FinanceDeletedSuccessfullyException, FinanceDoesNotExistException, FinanceNullException {
        if(finance != null){
            if(this.financeExist(finance.getContractName(), finance.getRecordDateTime())){
                financeRepository.delete(finance);
                throw new FinanceDeletedSuccessfullyException();
            }else{
                throw new FinanceDoesNotExistException();
            }
        }else{
            throw new FinanceNullException();
        }
    }

    @Override
    public boolean financeExist(String contractName, LocalDateTime recordDateTime) throws ConnectionFailureDbException {
        return financeRepository.financeExists(contractName, recordDateTime);
    }

    @Override
    public boolean checkFinanceData(Finance finance) throws EmptyfieldsException, InvalidFinanceAmountException {
        boolean financeChecked = true;
        if(finance.getTitle().isEmpty() || finance.getContractName().isEmpty() || finance.getType().isEmpty()){
            financeChecked = false;
            throw new EmptyfieldsException();
        }
        if(finance.getValue() < 0){
            financeChecked = false;
            throw new InvalidFinanceAmountException();
        }
        return financeChecked;
    }

    @Override
    public Finance getFinanceByContractNameAndRecordDateTime(String contractName, LocalDateTime recordDateTime) throws ConnectionFailureDbException, FinanceDoesNotExistException {
        Finance finance = financeRepository.getFinanceByContractNameAndRecordDateTime(contractName, recordDateTime);
        if(finance == null) throw new FinanceDoesNotExistException();
        return finance;
    }

    @Override
    public List<Double> getListOfEntriesForTheYearByMonth(Year year) throws ConnectionFailureDbException {
        return financeRepository.getListOfEntriesForTheYearByMonth(year);
    }

    @Override
    public List<Double> getListOfExpensesForTheYearByMonth(Year year) throws ConnectionFailureDbException {
        return financeRepository.getListOfExpensesForTheYearByMonth(year);
    }

    @Override
    public List<Finance> listAllFinancesWithFilters(String queryCpf, LocalDate queryStartDateTimePeriod, LocalDate queryEndDateTimePeriod, String queryContract, String queryMinValue, String queryMaxValue) throws ConnectionFailureDbException {
        return financeRepository.listAllWithFilters(queryCpf, queryStartDateTimePeriod, queryEndDateTimePeriod, queryContract, queryMinValue, queryMaxValue);
    }

    @Override
    public List<Finance> listAllFinancesByCollaboratorCpf(String cpf) throws ConnectionFailureDbException {
        return financeRepository.listAllByCollaboratorCpf(cpf);
    }

    @Override
    public List<Finance> listAllFinances() throws ConnectionFailureDbException {
        return financeRepository.listAll();
    }

}
