package org.example.gerenciadorcontratos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IPresenceController {
    void createPresence(Presence presence) throws ConnectionFailureDbException, PresenceCreatedSuccessfullyException, InvalidPresenceException, PresenceNullException, InvalidPresenseOrRecordDateTimeException, EmptyfieldsException, thereIsAlreadyARegisteredPresenceException;
    void updatePresence(Presence presence, String cpfCollaborator, String nameContract, String record, String status, LocalDate presenceDate, int presenceHour, int presenceMinute) throws ConnectionFailureDbException, PresenceUpdatedSuccessfullyException, PresenceDoesNotExistException, PresenceNullException;
    void deletePresence(Presence presence) throws ConnectionFailureDbException, PresenceDeletedSuccessfullyException, PresenceDoesNotExistException, PresenceNullException;
    boolean presenceExists(String cpfCollaborator, LocalDateTime recordDataTime) throws ConnectionFailureDbException;
    boolean checkEntryAndExitPresenceExists(String cpfCollaborator, LocalDateTime presenceDateTime, String record) throws ConnectionFailureDbException;
    boolean checkIfThereIsAnExistingPresenceWithTheRecord(String cpfCollaborator, LocalDateTime presenceDateTime, String currentRecord, String newRecord) throws ConnectionFailureDbException;
    boolean checkPresenceData(Presence presence) throws EmptyfieldsException, InvalidPresenseOrRecordDateTimeException, ConnectionFailureDbException, thereIsAlreadyARegisteredPresenceException;
    Presence getPresenceByCpfAndDateTime(String cpfCollaborator, LocalDateTime recordDataTime) throws ConnectionFailureDbException, PresenceDoesNotExistException;
    List<Presence> listAllPresencesWithFilters(String queryCpf, String queryRecord, String queryStatus, String queryContract, LocalDate queryStartDateTimePeriod, LocalDate queryEndDateTimePeriod) throws ConnectionFailureDbException;
    List<Presence> listAllPresencesByCpf(String cpf) throws ConnectionFailureDbException;
}
