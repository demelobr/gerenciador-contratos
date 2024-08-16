package org.example.gerenciadorcontratos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PresenceController implements IPresenceController{
    private static IPresenceController instance;
    private IPresenceDAO presenceRepository;

    public PresenceController(){
        this.presenceRepository = PresenceDAO.getInstance();
    }

    public static IPresenceController getInstance(){
        if(instance == null){
            instance = new PresenceController();
        }
        return instance;
    }

    @Override
    public void createPresence(Presence presence) throws ConnectionFailureDbException, PresenceCreatedSuccessfullyException, InvalidPresenceException, PresenceNullException, InvalidPresenseOrRecordDateTimeException, EmptyfieldsException, ThereIsAlreadyARegisteredPresenceException, JustificationRequiredException {
        if(presence != null){
            if(this.checkPresenceData(presence)){
                presenceRepository.create(presence);
                throw new PresenceCreatedSuccessfullyException();
            }else{
                throw new InvalidPresenceException();
            }
        }else{
            throw new PresenceNullException();
        }
    }

    @Override
    public void updatePresence(Presence presence, String cpfCollaborator, String nameContract, String record, String status, String justification, String observation, LocalDate presenceDate, int presenceHour, int presenceMinute) throws ConnectionFailureDbException, PresenceUpdatedSuccessfullyException, PresenceDoesNotExistException, PresenceNullException {
        if(presence != null){
            if(this.presenceExists(presence.getCpfCollaborator(), presence.getRecordDateTime())){
                LocalDateTime presenceDateTime = presenceDate.atTime(presenceHour, presenceMinute);

                if(cpfCollaborator.isEmpty() || presence.getCpfCollaborator().equals(cpfCollaborator)){
                    cpfCollaborator = presence.getCpfCollaborator();
                }
                if(nameContract.isEmpty() || presence.getNameContract().equals(nameContract)){
                    nameContract = presence.getNameContract();
                }
                if(record.isEmpty() || presence.getRecord().equals(record)){
                    record = presence.getRecord();
                }
                if(status.isEmpty() || presence.getStatus().equals(status)){
                    status = presence.getStatus();
                }
                if(presence.getPresenceDateTime().isEqual(presenceDateTime)){
                    presenceDateTime = presence.getPresenceDateTime();
                }
                presenceRepository.update(presence, cpfCollaborator, nameContract, record, status, justification, observation, presenceDate, presenceHour, presenceMinute);
                throw new PresenceUpdatedSuccessfullyException();
            }else{
                throw new PresenceDoesNotExistException();
            }
        }else{
            throw new PresenceNullException();
        }
    }

    @Override
    public void deletePresence(Presence presence) throws ConnectionFailureDbException, PresenceDeletedSuccessfullyException, PresenceDoesNotExistException, PresenceNullException {
        if(presence != null){
            if(this.presenceExists(presence.getCpfCollaborator(), presence.getRecordDateTime())){
                presenceRepository.delete(presence);
                throw new PresenceDeletedSuccessfullyException();
            }else{
                throw new PresenceDoesNotExistException();
            }
        }else{
            throw new PresenceNullException();
        }
    }

    @Override
    public boolean presenceExists(String cpfCollaborator, LocalDateTime recordDataTime) throws ConnectionFailureDbException {
        return presenceRepository.presenceExists(cpfCollaborator, recordDataTime);
    }

    @Override
    public boolean checkEntryAndExitPresenceExists(String cpfCollaborator, LocalDateTime presenceDateTime, String record) throws ConnectionFailureDbException {
        return presenceRepository.checkEntryAndExitPresenceExists(cpfCollaborator, presenceDateTime, record);
    }

    @Override
    public boolean checkIfThereIsAnExistingPresenceWithTheRecord(String cpfCollaborator, LocalDateTime presenceDateTime, String currentRecord, String newRecord) throws ConnectionFailureDbException {
        return presenceRepository.checkIfThereIsAnExistingPresenceWithTheRecord(cpfCollaborator, presenceDateTime, currentRecord, newRecord);
    }

    @Override
    public boolean checkPresenceData(Presence presence) throws ConnectionFailureDbException, EmptyfieldsException, InvalidPresenseOrRecordDateTimeException, ThereIsAlreadyARegisteredPresenceException, JustificationRequiredException {
        boolean presenceChecked = true;
        if(presence.getCpfCollaborator().isEmpty() || presence.getNameContract().isEmpty() ||
           presence.getRecord().isEmpty() || presence.getStatus().isEmpty()){
            presenceChecked = false;
            throw new EmptyfieldsException();
        }
        if(presence.getPresenceDateTime() == null || presence.getRecordDateTime() == null){
            presenceChecked = false;
            throw new InvalidPresenseOrRecordDateTimeException();
        }
        if(presence.getStatus().equals("NÃO PRESENTE") && presence.getJustification().isEmpty()){
            presenceChecked = false;
            throw new JustificationRequiredException();
        }
        if(this.checkEntryAndExitPresenceExists(presence.getCpfCollaborator(), presence.getPresenceDateTime(), presence.getRecord())){
            presenceChecked = false;
            throw new ThereIsAlreadyARegisteredPresenceException();
        }

        return presenceChecked;
    }

    @Override
    public Presence getPresenceByCpfAndDateTime(String cpfCollaborator, LocalDateTime recordDataTime) throws ConnectionFailureDbException, PresenceDoesNotExistException {
        Presence presence = presenceRepository.getByCpfAndDateTime(cpfCollaborator, recordDataTime);
        if(presence == null) throw new PresenceDoesNotExistException();
        return presence;
    }

    @Override
    public List<Presence> listAllPresencesWithFilters(String queryCpf, String queryRecord, String queryStatus, String queryContract, LocalDate queryStartDateTimePeriod, LocalDate queryEndDateTimePeriod) throws ConnectionFailureDbException {
        return presenceRepository.listAllWithFilters(queryCpf, queryRecord, queryStatus, queryContract, queryStartDateTimePeriod, queryEndDateTimePeriod);
    }

    @Override
    public List<Presence> listAllPresencesByCpf(String cpf) throws ConnectionFailureDbException {
        return presenceRepository.listAllByCpf(cpf);
    }

}
