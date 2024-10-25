package org.example.gerenciadorcontratos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.example.gerenciadorcontratos.UtilitiesLibrary.copyFile;
import static org.example.gerenciadorcontratos.UtilitiesLibrary.deleteFile;

public class CollaboratorDAO implements ICollaboratorDAO{
    private static ICollaboratorDAO instance;
    private  CollaboratorDAO(){}
    public static ICollaboratorDAO getInstance(){
        if(instance == null){
            instance = new CollaboratorDAO();
        }
        return instance;
    }

    @Override
    public void create(Collaborator collaborator) throws ConnectionFailureDbException, CopyFileFailedException {
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "INSERT INTO collaborators (name, cpf, rg, address, telephone, email, office, status, lastPoint, admissionDate, terminationDate, photoUrl) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatterWithTime = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            Application app = new Application();
            int index = collaborator.getPhotoUrl().indexOf(".");
            String extension = collaborator.getPhotoUrl().substring(index+1);
            String copyOfPhoto = String.format("%s/%s.%s", app.getServer().getCloud().getFolderPath(), collaborator.getCpf(), extension.toUpperCase());

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, collaborator.getName().toUpperCase());
            ps.setString(2, collaborator.getCpf().toUpperCase());
            ps.setString(3, collaborator.getRg().toUpperCase());
            ps.setString(4, collaborator.getAddress().toUpperCase());
            ps.setString(5, collaborator.getTelephone().toUpperCase());
            ps.setString(6, collaborator.getEmail().toUpperCase());
            ps.setString(7, collaborator.getOffice().toUpperCase());
            ps.setBoolean(8, collaborator.isStatus());

            if(collaborator.getLastPoint() != null) ps.setString(9, collaborator.getLastPoint().format(dateTimeFormatterWithTime));
            else ps.setString(9, "Não informado".toUpperCase());

            ps.setString(10, collaborator.getAdmissionDate().format(dateTimeFormatter));

            if(collaborator.getTerminationDate() != null && !collaborator.isStatus()) ps.setString(11, collaborator.getTerminationDate().format(dateTimeFormatter));
            else ps.setString(11, "Não informado".toUpperCase());

            File path = new File(app.getServer().getCloud().getFolderPath());
            if(path.exists() && path.isDirectory()){
                ps.setString(12, copyOfPhoto.toUpperCase());
                try {
                    copyFile(new File(collaborator.getPhotoUrl()), new File(copyOfPhoto));
                } catch (IOException e) {
                    throw new CopyFileFailedException();
                }
            }else{
                ps.setString(12, collaborator.getPhotoUrl().toUpperCase());
            }
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
    }

    @Override
    public void update(Collaborator collaborator, String name, String cpf, String rg, String address, String telephone, String email, String office, boolean status, LocalDateTime lastPoint, LocalDate admissionDate, LocalDate terminationDate, String photoUrl) throws ConnectionFailureDbException, CopyFileFailedException {
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "UPDATE collaborators SET name = ?, cpf = ?, rg = ?, address = ?, telephone = ?, email = ?, office = ?, status = ?, lastPoint = ?, admissionDate = ?, terminationDate = ?, photoUrl = ? WHERE cpf = ?";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatterWithTime = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            Application app = new Application();
            int index = collaborator.getPhotoUrl().indexOf(".");
            String extension = collaborator.getPhotoUrl().substring(index+1);
            String copyOfPhoto = String.format("%s/%s.%s", app.getServer().getCloud().getFolderPath(), collaborator.getCpf(), extension.toUpperCase());

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name.toUpperCase());
            ps.setString(2, cpf.toUpperCase());
            ps.setString(3, rg.toUpperCase());
            ps.setString(4, address.toUpperCase());
            ps.setString(5, telephone.toUpperCase());
            ps.setString(6, email.toUpperCase());
            ps.setString(7, office.toUpperCase());
            ps.setBoolean(8, status);

            if(lastPoint != null) ps.setString(9, lastPoint.format(dateTimeFormatterWithTime));
            else ps.setString(9, "Não informado".toUpperCase());

            ps.setString(10, admissionDate.format(dateTimeFormatter));

            if(terminationDate != null && !status) ps.setString(11, terminationDate.format(dateTimeFormatter));
            else ps.setString(11, "Não informado".toUpperCase());

            File path = new File(app.getServer().getCloud().getFolderPath());
            if(path.exists() && path.isDirectory() && !collaborator.getPhotoUrl().equals(photoUrl)){
                ps.setString(12, copyOfPhoto.toUpperCase());
                try {
                    copyFile(new File(photoUrl), new File(copyOfPhoto));
                } catch (IOException e) {
                    throw new CopyFileFailedException();
                }
            }else{
                ps.setString(12, photoUrl.toUpperCase());
            }

            ps.setString(13, collaborator.getCpf().toUpperCase());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
    }

    @Override
    public void delete(Collaborator collaborator) throws ConnectionFailureDbException {
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "DELETE FROM collaborators WHERE cpf = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, collaborator.getCpf().toUpperCase());
            ps.executeUpdate();
            deleteFile(collaborator.getPhotoUrl());

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
    }

    @Override
    public boolean collaboratorExists(String cpf) throws ConnectionFailureDbException {
        boolean collaboratorExists = false;
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT id, name, cpf, rg, address, telephone, email, office, status, lastPoint, admissionDate, terminationDate, photoUrl FROM collaborators WHERE cpf = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cpf.toUpperCase());

            try (ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    collaboratorExists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
        return collaboratorExists;
    }

    @Override
    public Collaborator getByCpf(String queryCpf) throws ConnectionFailureDbException {
        Collaborator collaborator = null;
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT id, name, cpf, rg, address, telephone, email, office, status, lastPoint, admissionDate, terminationDate, photoUrl FROM collaborators WHERE cpf = ?";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatterWithTime = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, queryCpf.toUpperCase());

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    String name = rs.getString("name");
                    String cpf = rs.getString("cpf");
                    String rg = rs.getString("rg");
                    String address = rs.getString("address");
                    String telephone = rs.getString("telephone");
                    String email = rs.getString("email");
                    String office = rs.getString("office");
                    boolean status = rs.getBoolean("status");

                    LocalDateTime lastPoint;
                    if(rs.getString("lastPoint").equals("Não informado".toUpperCase())) lastPoint = null;
                    else lastPoint = LocalDateTime.parse(rs.getString("lastPoint"), dateTimeFormatterWithTime);

                    LocalDate admissionDate = LocalDate.parse(rs.getString("admissionDate"), dateTimeFormatter);

                    LocalDate terminationDate;
                    if(rs.getString("terminationDate").equals("Não informado".toUpperCase())) terminationDate = null;
                    else terminationDate = LocalDate.parse(rs.getString("terminationDate"), dateTimeFormatter);

                    String photoUrl = rs.getString("photoUrl");
                    collaborator = new Collaborator(name, cpf, rg, address, telephone, email, office, status, lastPoint, admissionDate, terminationDate, photoUrl);
                }
            }

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
        return collaborator;
    }

    @Override
    public List<Collaborator> listAll() throws ConnectionFailureDbException {
        List<Collaborator> listOfCollaborators = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT id, name, cpf, rg, address, telephone, email, office, status, lastPoint, admissionDate, terminationDate, photoUrl FROM collaborators";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatterWithTime = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    String name = rs.getString("name");
                    String cpf = rs.getString("cpf");
                    String rg = rs.getString("rg");
                    String address = rs.getString("address");
                    String telephone = rs.getString("telephone");
                    String email = rs.getString("email");
                    String office = rs.getString("office");
                    boolean status = rs.getBoolean("status");

                    LocalDateTime lastPoint;
                    if(rs.getString("lastPoint").equals("Não informado".toUpperCase())) lastPoint = null;
                    else lastPoint = LocalDateTime.parse(rs.getString("lastPoint"), dateTimeFormatterWithTime);

                    LocalDate admissionDate = LocalDate.parse(rs.getString("admissionDate"), dateTimeFormatter);

                    LocalDate terminationDate;
                    if(rs.getString("terminationDate").equals("Não informado".toUpperCase())) terminationDate = null;
                    else terminationDate = LocalDate.parse(rs.getString("terminationDate"), dateTimeFormatter);

                    String photoUrl = rs.getString("photoUrl");
                    listOfCollaborators.add(new Collaborator(name, cpf, rg, address, telephone, email, office, status, lastPoint, admissionDate, terminationDate, photoUrl));
                }
            }

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
        return listOfCollaborators;
    }
}
