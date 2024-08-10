package org.example.gerenciadorcontratos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CollaboratorController implements ICollaboratorController{
    private static ICollaboratorController instance;
    private ICollaboratorDAO collaboratorRepository;

    public CollaboratorController(){
        this.collaboratorRepository = CollaboratorDAO.getInstance();
    }

    public static ICollaboratorController getInstance(){
        if(instance == null){
            instance = new CollaboratorController();
        }
        return instance;
    }

    @Override
    public void createCollaborator(Collaborator collaborator) throws ConnectionFailureDbException, CollaboratorCreatedSuccessfullyException, InvalidCollaboratorException, CollaboratorNullException, InvalidCpfException, CollaboratorWithThisCpfAlreadyExistsException, InvalidEmailException, EmptyfieldsException {
        if(collaborator != null){
            if(this.checkCollaboratorData(collaborator)){
                collaboratorRepository.create(collaborator);
                throw new CollaboratorCreatedSuccessfullyException();
            }else{
                throw new InvalidCollaboratorException();
            }
        }else{
            throw new CollaboratorNullException();
        }
    }

    @Override
    public void updateCollaborator(Collaborator collaborator, String name, String cpf, String rg, String address, String telephone, String email, String office, boolean status, LocalDateTime lastPoint, LocalDate admissionDate, LocalDate terminationDate, String photoUrl) throws ConnectionFailureDbException, CollaboratorUpdatedSuccessfullyException, CollaboratorDoesNotExistException, CollaboratorNullException {
        if(collaborator != null){
            if(this.collaboratorExists(collaborator.getCpf())){
                if(name.isEmpty() || collaborator.getName().equals(name)){
                    name = collaborator.getName();
                }
                if(cpf.isEmpty() || collaborator.getCpf().equals(cpf)){
                    cpf = collaborator.getCpf();
                }
                if(rg.isEmpty() || collaborator.getRg().equals(rg)){
                    rg = collaborator.getRg();
                }
                if(address.isEmpty() || collaborator.getAddress().equals(address)){
                    address = collaborator.getAddress();
                }
                if(telephone.isEmpty() || collaborator.getTelephone().equals(telephone)){
                    telephone = collaborator.getTelephone();
                }
                if(email.isEmpty() || collaborator.getEmail().equals(email)){
                    email = collaborator.getEmail();
                }
                if(office.isEmpty() || collaborator.getOffice().equals(office)){
                    office = collaborator.getOffice();
                }
                if(status == collaborator.isStatus()){
                    status = collaborator.isStatus();
                }
                if(lastPoint == null){
                    lastPoint = collaborator.getLastPoint();
                }
                if(admissionDate == null || admissionDate.isEqual(collaborator.getAdmissionDate())){
                    admissionDate = collaborator.getAdmissionDate();
                }
                if(terminationDate == null){
                    terminationDate = collaborator.getTerminationDate();
                }
                if(photoUrl.isEmpty() || collaborator.getPhotoUrl().equals(photoUrl)){
                    photoUrl = collaborator.getPhotoUrl();
                }
                collaboratorRepository.update(collaborator, name, cpf, rg, address, telephone, email, office, status, lastPoint, admissionDate, terminationDate, photoUrl);
                throw new CollaboratorUpdatedSuccessfullyException();
            }else{
                throw new CollaboratorDoesNotExistException();
            }
        }else{
            throw new CollaboratorNullException();
        }
    }

    @Override
    public void deleteCollaborator(Collaborator collaborator) throws ConnectionFailureDbException, CollaboratorDeletedSuccessfullyException, CollaboratorDoesNotExistException, CollaboratorNullException {
        if(collaborator != null){
            if(this.collaboratorExists(collaborator.getCpf())){
                collaboratorRepository.delete(collaborator);
                throw new CollaboratorDeletedSuccessfullyException();
            }else{
                throw new CollaboratorDoesNotExistException();
            }
        }else{
            throw new CollaboratorNullException();
        }
    }

    @Override
    public boolean collaboratorExists(String cpf) throws ConnectionFailureDbException {
        return collaboratorRepository.collaboratorExists(cpf);
    }

    @Override
    public boolean checkCollaboratorData(Collaborator collaborator) throws ConnectionFailureDbException, EmptyfieldsException, CollaboratorWithThisCpfAlreadyExistsException, InvalidCpfException, InvalidEmailException {
        boolean collaboratorChecked = true;

        if(collaborator.getName().isEmpty() || collaborator.getCpf().isEmpty() || collaborator.getRg().isEmpty() ||
           collaborator.getAddress().isEmpty() || collaborator.getTelephone().isEmpty() || collaborator.getEmail().isEmpty() ||
           collaborator.getOffice().isEmpty() || collaborator.getAdmissionDate() == null){
            collaboratorChecked = false;
            throw new EmptyfieldsException();
        }
        if(this.collaboratorExists(collaborator.getCpf())){
            collaboratorChecked = false;
            throw new CollaboratorWithThisCpfAlreadyExistsException();
        }
        if(!isValidCpf(collaborator.getCpf())){
            collaboratorChecked = false;
            throw new InvalidCpfException();
        }
        if(!isValidEmail(collaborator.getEmail())){
            collaboratorChecked = false;
            throw new InvalidEmailException();
        }

        return collaboratorChecked;
    }

    @Override
    public Collaborator getCollaboratorByCpf(String cpf) throws ConnectionFailureDbException, CollaboratorDoesNotExistException {
        Collaborator collaborator = collaboratorRepository.getByCpf(cpf);
        if(collaborator == null) throw  new CollaboratorDoesNotExistException();
        return collaborator;
    }

    @Override
    public List<Collaborator> listAllCollaborators() throws ConnectionFailureDbException {
        return collaboratorRepository.listAll();
    }

    @Override
    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean isValidCpf(String cpf) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d+")) {
            return false;
        }
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int soma = 0, peso = 10;
            for (int i = 0; i < 9; i++) {
                int num = Integer.parseInt(cpf.substring(i, i + 1));
                soma += num * peso--;
            }

            int r = 11 - (soma % 11);
            char dig10 = (r == 10 || r == 11) ? '0' : (char) (r + '0');

            soma = 0;
            peso = 11;
            for (int i = 0; i < 10; i++) {
                int num = Integer.parseInt(cpf.substring(i, i + 1));
                soma += num * peso--;
            }

            r = 11 - (soma % 11);
            char dig11 = (r == 10 || r == 11) ? '0' : (char) (r + '0');

            return (dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }

}
