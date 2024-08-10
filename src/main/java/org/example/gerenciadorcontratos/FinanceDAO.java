package org.example.gerenciadorcontratos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FinanceDAO implements IFinanceDAO {
    private static IFinanceDAO instance;

    private FinanceDAO() {}

    public static IFinanceDAO getInstance() {
        if (instance == null) {
            instance = new FinanceDAO();
        }
        return instance;
    }

    @Override
    public void create(Finance finance) throws ConnectionFailureDbException {
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "INSERT INTO finances (title, contractName, type, date, recordDateTime, value, collaboratorCpf) VALUES(?,?,?,?,?,?,?)";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, finance.getTitle().toUpperCase());
            ps.setString(2, finance.getContractName().toUpperCase());
            ps.setString(3, finance.getType().toUpperCase());
            ps.setString(4, finance.getDate().format(dateTimeFormatter));
            ps.setString(5, finance.getRecordDateTime().format(dateTimeFormatterWithSeconds));
            ps.setDouble(6, finance.getValue());
            ps.setString(7, finance.getCollaboratorCpf().toUpperCase());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
    }

    @Override
    public void update(Finance finance, String title, String contractName, String type, LocalDate date, LocalDateTime recordDateTime, double value, String collaboratorCpf) throws ConnectionFailureDbException {
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "UPDATE finances SET title = ?, contractName = ?, type = ?, date = ?, recordDateTime = ?, value = ?, collaboratorCpf = ? WHERE contractName = ? AND recordDateTime = ?";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title.toUpperCase());
            ps.setString(2, contractName.toUpperCase());
            ps.setString(3, type.toUpperCase());
            ps.setString(4, date.format(dateTimeFormatter));
            ps.setString(5, recordDateTime.format(dateTimeFormatterWithSeconds));
            ps.setDouble(6, value);
            ps.setString(7, collaboratorCpf.toUpperCase());
            ps.setString(8, finance.getContractName().toUpperCase());
            ps.setString(9, finance.getRecordDateTime().format(dateTimeFormatterWithSeconds));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
    }

    @Override
    public void delete(Finance finance) throws ConnectionFailureDbException {
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "DELETE FROM finances WHERE contractName = ? AND recordDateTime = ?";

            DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, finance.getContractName().toUpperCase());
            ps.setString(2, finance.getRecordDateTime().format(dateTimeFormatterWithSeconds));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
    }

    @Override
    public boolean financeExists(String contractName, LocalDateTime recordDateTime) throws ConnectionFailureDbException {
        boolean financeExists = false;
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT id, title, contractName, type, date, recordDateTime, value, collaboratorCpf FROM finances WHERE contractName = ? AND recordDateTime = ?";

            DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, contractName.toUpperCase());
            ps.setString(2, recordDateTime.format(dateTimeFormatterWithSeconds));

            try (ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    financeExists = rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
        return financeExists;
    }

    @Override
    public Finance getFinanceByContractNameAndRecordDateTime(String queryContractName, LocalDateTime queryRecordDateTime) throws ConnectionFailureDbException {
        Finance finance = null;
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT id, title, contractName, type, date, recordDateTime, value, collaboratorCpf FROM finances WHERE contractName = ? AND recordDateTime = ?";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, queryContractName.toUpperCase());
            ps.setString(2, queryRecordDateTime.format(dateTimeFormatterWithSeconds));

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    String title = rs.getString("title");
                    String contractName = rs.getString("contractName");
                    String type = rs.getString("type");
                    LocalDate date = LocalDate.parse(rs.getString("date"), dateTimeFormatter);
                    LocalDateTime recordDateTime = LocalDateTime.parse(rs.getString("recordDateTime"), dateTimeFormatterWithSeconds);
                    double value = rs.getDouble("value");
                    String collaboratorCpf = rs.getString("collaboratorCpf");
                    finance = new Finance(title, contractName, type, date, recordDateTime, value, collaboratorCpf);
                }
            }

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
        return finance;
    }

    @Override
    public List<Double> getListOfEntriesForTheYearByMonth(Year year) throws ConnectionFailureDbException {
        List<Finance> listOfFinances = new ArrayList<>();
        List<Double> listOfEntries = new ArrayList<>() {{add(0.0); add(0.0); add(0.0); add(0.0); add(0.0); add(0.0); add(0.0); add(0.0); add(0.0); add(0.0); add(0.0); add(0.0);}};

        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT * FROM finances WHERE SUBSTRING(date, 7, 4) = ?";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, String.valueOf(year));

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    String title = rs.getString("title");
                    String contractName = rs.getString("contractName");
                    String type = rs.getString("type");
                    LocalDate date = LocalDate.parse(rs.getString("date"), dateTimeFormatter);
                    LocalDateTime recordDateTime = LocalDateTime.parse(rs.getString("recordDateTime"), dateTimeFormatterWithSeconds);
                    double value = rs.getDouble("value");
                    String collaboratorCpf = rs.getString("collaboratorCpf");
                    listOfFinances.add(new Finance(title, contractName, type, date, recordDateTime, value, collaboratorCpf));
                }
            }

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }

        for(Finance finance : listOfFinances){
            if(finance.getType().equals("RECEITA")) listOfEntries.set(finance.getDate().getMonth().getValue() - 1, listOfEntries.get(finance.getDate().getMonth().getValue() - 1) + finance.getValue());
        }

        return listOfEntries;
    }

    @Override
    public List<Double> getListOfExpensesForTheYearByMonth(Year year) throws ConnectionFailureDbException {
        List<Finance> listOfFinances = new ArrayList<>();
        List<Double> listOfExpenses = new ArrayList<>() {{add(0.0); add(0.0); add(0.0); add(0.0); add(0.0); add(0.0); add(0.0); add(0.0); add(0.0); add(0.0); add(0.0); add(0.0);}};

        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT * FROM finances WHERE SUBSTRING(date, 7, 4) = ?";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, String.valueOf(year));

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    String title = rs.getString("title");
                    String contractName = rs.getString("contractName");
                    String type = rs.getString("type");
                    LocalDate date = LocalDate.parse(rs.getString("date"), dateTimeFormatter);
                    LocalDateTime recordDateTime = LocalDateTime.parse(rs.getString("recordDateTime"), dateTimeFormatterWithSeconds);
                    double value = rs.getDouble("value");
                    String collaboratorCpf = rs.getString("collaboratorCpf");
                    listOfFinances.add(new Finance(title, contractName, type, date, recordDateTime, value, collaboratorCpf));
                }
            }

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }

        for(Finance finance : listOfFinances){
            if(finance.getType().equals("DESPESA")) listOfExpenses.set(finance.getDate().getMonth().getValue() - 1, listOfExpenses.get(finance.getDate().getMonth().getValue() - 1) + finance.getValue());
        }

        return listOfExpenses;
    }

    @Override
    public List<Finance> listAllWithFilters(String queryCpf, LocalDate queryStartDateTimePeriod, LocalDate queryEndDateTimePeriod, String queryContract, String queryMinValue, String queryMaxValue) throws ConnectionFailureDbException {
        List<Finance> listOfAllWithFilters = new ArrayList<>();

        List<String> filters = new ArrayList<>();
        filters.add(queryCpf);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT * FROM finances WHERE collaboratorCpf = ?";

            if(queryStartDateTimePeriod != null){
                sql = sql + " AND date >= ?";
                filters.add(queryStartDateTimePeriod.atTime(0,0,0).format(dateTimeFormatterWithSeconds));
            }

            if(queryEndDateTimePeriod != null){
                sql = sql + " AND date <= ?";
                filters.add(queryEndDateTimePeriod.atTime(23,59,59).format(dateTimeFormatterWithSeconds));
            }

            if(queryContract != null){
                if(!queryContract.equals("----------")){
                    sql = sql + " AND contractName = ?";
                    filters.add(queryContract);
                }
            }

            if(!queryMinValue.equals("")){
                sql = sql + " AND value >= ?";
                filters.add(queryMinValue);
            }

            if(!queryMaxValue.equals("")){
                sql = sql + " AND value <= ?";
                filters.add(queryMaxValue);
            }

            sql = sql + "ORDER BY STR_TO_DATE(date, '%d/%m/%Y')";

            PreparedStatement ps = conn.prepareStatement(sql);
            for(int i = 0; i < filters.size(); i++){
                ps.setString(i+1, filters.get(i).toUpperCase());
            }

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    String title = rs.getString("title");
                    String contractName = rs.getString("contractName");
                    String type = rs.getString("type");
                    LocalDate date = LocalDate.parse(rs.getString("date"), dateFormatter);
                    LocalDateTime recordDateTime = LocalDateTime.parse(rs.getString("recordDateTime"), dateTimeFormatterWithSeconds);
                    double value = rs.getDouble("value");
                    String collaboratorCpf = rs.getString("collaboratorCpf");
                    listOfAllWithFilters.add(new Finance(title, contractName, type, date, recordDateTime, value, collaboratorCpf));
                }
            }

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }

        return listOfAllWithFilters;
    }

    @Override
    public List<Finance> listAllByCollaboratorCpf(String cpf) throws ConnectionFailureDbException {
        List<Finance> listOfAllByCpf = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT id, title, contractName, type, date, recordDateTime, value, collaboratorCpf FROM finances WHERE collaboratorCpf = ? ORDER BY STR_TO_DATE(date, '%d/%m/%Y')";

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cpf.toUpperCase());

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    String title = rs.getString("title");
                    String contractName = rs.getString("contractName");
                    String type = rs.getString("type");
                    LocalDate date = LocalDate.parse(rs.getString("date"), dateFormatter);
                    LocalDateTime recordDateTime = LocalDateTime.parse(rs.getString("recordDateTime"), dateTimeFormatter);
                    double value = rs.getDouble("value");
                    String collaboratorCpf = rs.getString("collaboratorCpf");
                    listOfAllByCpf.add(new Finance(title, contractName, type, date, recordDateTime, value, collaboratorCpf));
                }
            }

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }

        return listOfAllByCpf;
    }

    @Override
    public List<Finance> listAll() throws ConnectionFailureDbException {
        List<Finance> listOfFinances = new ArrayList<Finance>();
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT id, title, contractName, type, date, recordDateTime, value, collaboratorCpf FROM finances";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    String title = rs.getString("title");
                    String contractName = rs.getString("contractName");
                    String type = rs.getString("type");
                    LocalDate date = LocalDate.parse(rs.getString("date"), dateTimeFormatter);
                    LocalDateTime recordDateTime = LocalDateTime.parse(rs.getString("recordDateTime"), dateTimeFormatterWithSeconds);
                    double value = rs.getDouble("value");
                    String collaboratorCpf = rs.getString("collaboratorCpf");
                    listOfFinances.add(new Finance(title, contractName, type, date, recordDateTime, value, collaboratorCpf));
                }
            }

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
        return listOfFinances;
    }
}
