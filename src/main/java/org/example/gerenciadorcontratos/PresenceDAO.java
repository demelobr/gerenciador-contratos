package org.example.gerenciadorcontratos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PresenceDAO implements IPresenceDAO{
    private static IPresenceDAO instance;

    private PresenceDAO(){}

    public static IPresenceDAO getInstance(){
        if(instance == null){
            instance = new PresenceDAO();
        }
        return instance;
    }

    @Override
    public void create(Presence presence) throws ConnectionFailureDbException {
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "INSERT INTO presences (cpfCollaborator, nameContract, record, status, presenceDateTime, recordDateTime) VALUES (?,?,?,?,?,?)";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
            DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, presence.getCpfCollaborator().toUpperCase());
            ps.setString(2, presence.getNameContract().toUpperCase());
            ps.setString(3, presence.getRecord().toUpperCase());
            ps.setString(4, presence.getStatus().toUpperCase());
            ps.setString(5, presence.getPresenceDateTime().format(dateTimeFormatter));
            ps.setString(6, presence.getRecordDateTime().format(dateTimeFormatterWithSeconds));
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new ConnectionFailureDbException();
        }
    }

    @Override
    public void update(Presence presence, String cpfCollaborator, String nameContract, String record, String status, LocalDate presenceDate, int presenceHour, int presenceMinute) throws ConnectionFailureDbException {
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "UPDATE presences SET cpfCollaborator = ?, nameContract = ?, record = ?, status = ?, presenceDateTime = ?, recordDateTime = ? WHERE cpfCollaborator = ? AND recordDateTime = ?";

            LocalDateTime presenceDateTime = presenceDate.atTime(LocalTime.of(presenceHour, presenceMinute));
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
            DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cpfCollaborator.toUpperCase());
            ps.setString(2, nameContract.toUpperCase());
            ps.setString(3, record.toUpperCase());
            ps.setString(4, status.toUpperCase());
            ps.setString(5, presenceDateTime.format(dateTimeFormatter));
            ps.setString(6, LocalDateTime.now().format(dateTimeFormatterWithSeconds));
            ps.setString(7, presence.getCpfCollaborator().toUpperCase());
            ps.setString(8, presence.getRecordDateTime().format(dateTimeFormatterWithSeconds));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
    }

    @Override
    public void delete(Presence presence) throws ConnectionFailureDbException {
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "DELETE FROM presences WHERE cpfCollaborator = ? AND recordDateTime = ?";

            DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, presence.getCpfCollaborator().toUpperCase());
            ps.setString(2, presence.getRecordDateTime().format(dateTimeFormatterWithSeconds));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
    }

    @Override
    public boolean presenceExists(String cpfCollaborator, LocalDateTime recordDataTime) throws ConnectionFailureDbException {
        boolean presenceExists = false;
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT id, cpfCollaborator, nameContract, record, status, presenceDateTime, recordDateTime FROM presences WHERE cpfCollaborator = ? AND recordDateTime = ?";

            DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cpfCollaborator.toUpperCase());
            ps.setString(2, recordDataTime.format(dateTimeFormatterWithSeconds));

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next())
                    presenceExists = rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
        return presenceExists;
    }

    @Override
    public boolean checkEntryAndExitPresenceExists(String cpfCollaborator, LocalDateTime presenceDateTime, String record) throws ConnectionFailureDbException {
        boolean presenceExists = false;
        List<Presence> listOfAllPresencesByCollaborator = this.listAllByCpf(cpfCollaborator);
        for(Presence presence : listOfAllPresencesByCollaborator){
            if(presence.getPresenceDateTime().toLocalDate().equals(presenceDateTime.toLocalDate()) && presence.getRecord().equals(record)){
                presenceExists = true;
            }
        }
        return presenceExists;
    }

    @Override
    public boolean checkIfThereIsAnExistingPresenceWithTheRecord(String cpfCollaborator, LocalDateTime presenceDateTime, String currentRecord, String newRecord) throws ConnectionFailureDbException {
        boolean presenceExists = false;
        List<Presence> listOfAllPresencesByCollaborator = this.listAllByCpf(cpfCollaborator);
        for(Presence presence : listOfAllPresencesByCollaborator){
            if(presence.getPresenceDateTime().toLocalDate().equals(presenceDateTime.toLocalDate()) && presence.getRecord().equals(newRecord) && !currentRecord.equals(newRecord)){
                presenceExists = true;
            }
        }
        return presenceExists;
    }

    @Override
    public Presence getByCpfAndDateTime(String queryCpfCollaborator, LocalDateTime queryRecordDataTime) throws ConnectionFailureDbException {
        Presence presence = null;
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT id, cpfCollaborator, nameContract, record, status, presenceDateTime, recordDateTime FROM presences WHERE cpfCollaborator = ? AND recordDateTime = ?";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
            DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, queryCpfCollaborator.toUpperCase());
            ps.setString(2, queryRecordDataTime.format(dateTimeFormatterWithSeconds));

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    String cpfCollaborator = rs.getString("cpfCollaborator");
                    String nameContract = rs.getString("nameContract");
                    String record = rs.getString("record");
                    String status = rs.getString("status");
                    LocalDateTime presenceDateTime = LocalDateTime.parse(rs.getString("presenceDateTime"), dateTimeFormatter);
                    LocalDateTime recordDateTime = LocalDateTime.parse(rs.getString("recordDateTime"), dateTimeFormatterWithSeconds);
                    presence = new Presence(cpfCollaborator, nameContract, record, status, presenceDateTime, recordDateTime);
                }
            }

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
        return presence;
    }

    @Override
    public List<Presence> listAllWithFilters(String queryCpf, String queryRecord, String queryStatus, String queryContract, LocalDate queryStartDateTimePeriod, LocalDate queryEndDateTimePeriod) throws ConnectionFailureDbException {
        List<Presence> listOfAllWithFilters = new ArrayList<>();

        List<String> filters = new ArrayList<>();
        filters.add(queryCpf);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT * FROM presences WHERE cpfCollaborator = ?";

            if(queryRecord != null){
                if(!queryRecord.equals("----------")){
                    sql = sql + " AND record = ?";
                    filters.add(queryRecord);
                }
            }

            if(queryStatus != null){
                if(!queryStatus.equals("----------")){
                    sql = sql + " AND status = ?";
                    filters.add(queryStatus);
                }
            }

            if(queryContract != null){
                if(!queryContract.equals("----------")){
                    sql = sql + " AND nameContract = ?";
                    filters.add(queryContract);
                }
            }

            if(queryStartDateTimePeriod != null){
                sql = sql + " AND presenceDateTime >= ?";
                filters.add(queryStartDateTimePeriod.atTime(0,0,0).format(dateTimeFormatterWithSeconds));
            }

            if(queryEndDateTimePeriod != null){
                sql = sql + " AND presenceDateTime <= ?";
                filters.add(queryEndDateTimePeriod.atTime(23,59,59).format(dateTimeFormatterWithSeconds));
            }

            sql = sql + "ORDER BY STR_TO_DATE(presenceDateTime, '%d/%m/%Y - %H:%i')";

            PreparedStatement ps = conn.prepareStatement(sql);
            for(int i = 0; i < filters.size(); i++){
                ps.setString(i+1, filters.get(i).toUpperCase());
            }

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    String cpfCollaborator = rs.getString("cpfCollaborator");
                    String nameContract = rs.getString("nameContract");
                    String record = rs.getString("record");
                    String status = rs.getString("status");
                    LocalDateTime presenceDateTime = LocalDateTime.parse(rs.getString("presenceDateTime"), dateTimeFormatter);
                    LocalDateTime recordDateTime = LocalDateTime.parse(rs.getString("recordDateTime"), dateTimeFormatterWithSeconds);
                    listOfAllWithFilters.add(new Presence(cpfCollaborator, nameContract, record, status, presenceDateTime, recordDateTime));
                }
            }

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
        return listOfAllWithFilters;
    }

    @Override
    public List<Presence> listAllByCpf(String cpf) throws ConnectionFailureDbException {
        List<Presence> listOfAllByCpf = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT id, cpfCollaborator, nameContract, record, status, presenceDateTime, recordDateTime FROM presences WHERE cpfCollaborator = ? ORDER BY STR_TO_DATE(presenceDateTime, '%d/%m/%Y - %H:%i')";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
            DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cpf.toUpperCase());

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    String cpfCollaborator = rs.getString("cpfCollaborator");
                    String nameContract = rs.getString("nameContract");
                    String record = rs.getString("record");
                    String status = rs.getString("status");
                    LocalDateTime presenceDateTime = LocalDateTime.parse(rs.getString("presenceDateTime"), dateTimeFormatter);
                    LocalDateTime recordDateTime = LocalDateTime.parse(rs.getString("recordDateTime"), dateTimeFormatterWithSeconds);
                    listOfAllByCpf.add(new Presence(cpfCollaborator, nameContract, record, status, presenceDateTime, recordDateTime));
                }
            }

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
        return listOfAllByCpf;
    }
}
