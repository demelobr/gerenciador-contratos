package org.example.gerenciadorcontratos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IPresenceDAO {
    void create(Presence presence) throws ConnectionFailureDbException;
    void update(Presence presence, String cpfCollaborator, String nameContract, String record, String status, String justification, String observation, LocalDate presenceDate, int presenceHour, int presenceMinute) throws ConnectionFailureDbException;
    void delete(Presence presence) throws ConnectionFailureDbException;
    boolean presenceExists(String cpfCollaborator, LocalDateTime recordDataTime) throws ConnectionFailureDbException;
    boolean checkEntryAndExitPresenceExists(String cpfCollaborator, LocalDateTime presenceDateTime, String record) throws ConnectionFailureDbException;
    boolean checkIfThereIsAnExistingPresenceWithTheRecord(String cpfCollaborator, LocalDateTime presenceDateTime, String currentRecord, String newRecord) throws ConnectionFailureDbException;
    Presence getByCpfAndDateTime(String cpfCollaborator, LocalDateTime recordDataTime) throws ConnectionFailureDbException;
    List<Presence> listAllWithFilters(String queryCpf, String queryRecord, String queryStatus, String queryContract, LocalDate queryStartDateTimePeriod, LocalDate queryEndDateTimePeriod) throws ConnectionFailureDbException;
    List<Presence> listAllByCpf(String cpf) throws ConnectionFailureDbException;

}
