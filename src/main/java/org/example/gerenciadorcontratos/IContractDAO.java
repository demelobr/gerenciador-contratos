package org.example.gerenciadorcontratos;

import java.time.LocalDate;
import java.util.List;

public interface IContractDAO {
    void create(Contract contract) throws ConnectionFailureDbException;
    void update(Contract contract, String name, String description, String address, float budget, LocalDate startDate, LocalDate endDate) throws ConnectionFailureDbException;
    void delete(Contract contract) throws ConnectionFailureDbException;
    boolean contractExists(String name) throws ConnectionFailureDbException;
    Contract getByName(String name) throws ConnectionFailureDbException;
    List<Contract> listAll() throws ConnectionFailureDbException;
}
