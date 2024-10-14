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
            String sql = "INSERT INTO finances (title, notes, contractName, type, financeClass, paymentMethod, date, recordDateTime, value, collaboratorCpf) VALUES(?,?,?,?,?,?,?,?,?,?)";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, finance.getTitle().toUpperCase());
            ps.setString(2, finance.getNotes().toUpperCase());
            ps.setString(3, finance.getContractName().toUpperCase());
            ps.setString(4, finance.getType().toUpperCase());
            ps.setString(5, finance.getFinanceClass().toUpperCase());
            ps.setString(6, finance.getPaymentMethod().toUpperCase());
            ps.setString(7, finance.getDate().format(dateTimeFormatter));
            ps.setString(8, finance.getRecordDateTime().format(dateTimeFormatterWithSeconds));
            ps.setDouble(9, finance.getValue());
            ps.setString(10, finance.getCollaboratorCpf().toUpperCase());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new ConnectionFailureDbException();
        }
    }

    @Override
    public void update(Finance finance, String title, String notes, String contractName, String type, String financeClass, String paymentMethod, LocalDate date, LocalDateTime recordDateTime, double value, String collaboratorCpf) throws ConnectionFailureDbException {
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "UPDATE finances SET title = ?, notes = ?, contractName = ?, type = ?, financeClass = ?, paymentMethod = ?, date = ?, recordDateTime = ?, value = ?, collaboratorCpf = ? WHERE contractName = ? AND recordDateTime = ?";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title.toUpperCase());
            ps.setString(2, notes.toUpperCase());
            ps.setString(3, contractName.toUpperCase());
            ps.setString(4, type.toUpperCase());
            ps.setString(5, financeClass.toUpperCase());
            ps.setString(6, paymentMethod.toUpperCase());
            ps.setString(7, date.format(dateTimeFormatter));
            ps.setString(8, recordDateTime.format(dateTimeFormatterWithSeconds));
            ps.setDouble(9, value);
            ps.setString(10, collaboratorCpf.toUpperCase());
            ps.setString(11, finance.getContractName().toUpperCase());
            ps.setString(12, finance.getRecordDateTime().format(dateTimeFormatterWithSeconds));
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
            String sql = "SELECT id, title, notes, contractName, type, financeClass, paymentMethod, date, recordDateTime, value, collaboratorCpf FROM finances WHERE contractName = ? AND recordDateTime = ?";

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
            String sql = "SELECT id, title, notes, contractName, type, financeClass, paymentMethod, date, recordDateTime, value, collaboratorCpf FROM finances WHERE contractName = ? AND recordDateTime = ?";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, queryContractName.toUpperCase());
            ps.setString(2, queryRecordDateTime.format(dateTimeFormatterWithSeconds));

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    String title = rs.getString("title");
                    String notes = rs.getString("notes");
                    String contractName = rs.getString("contractName");
                    String type = rs.getString("type");
                    String financeClass = rs.getString("financeClass");
                    String paymentMethod = rs.getString("paymentMethod");
                    LocalDate date = LocalDate.parse(rs.getString("date"), dateTimeFormatter);
                    LocalDateTime recordDateTime = LocalDateTime.parse(rs.getString("recordDateTime"), dateTimeFormatterWithSeconds);
                    double value = rs.getDouble("value");
                    String collaboratorCpf = rs.getString("collaboratorCpf");
                    finance = new Finance(title, notes, contractName, type, financeClass, paymentMethod, date, recordDateTime, value, collaboratorCpf);
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
                    String notes = rs.getString("notes");
                    String contractName = rs.getString("contractName");
                    String type = rs.getString("type");
                    String financeClass = rs.getString("financeClass");
                    String paymentMethod = rs.getString("paymentMethod");
                    LocalDate date = LocalDate.parse(rs.getString("date"), dateTimeFormatter);
                    LocalDateTime recordDateTime = LocalDateTime.parse(rs.getString("recordDateTime"), dateTimeFormatterWithSeconds);
                    double value = rs.getDouble("value");
                    String collaboratorCpf = rs.getString("collaboratorCpf");
                    listOfFinances.add(new Finance(title, notes, contractName, type, financeClass, paymentMethod, date, recordDateTime, value, collaboratorCpf));
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
                    String notes = rs.getString("notes");
                    String contractName = rs.getString("contractName");
                    String type = rs.getString("type");
                    String financeClass = rs.getString("financeClass");
                    String paymentMethod = rs.getString("paymentMethod");
                    LocalDate date = LocalDate.parse(rs.getString("date"), dateTimeFormatter);
                    LocalDateTime recordDateTime = LocalDateTime.parse(rs.getString("recordDateTime"), dateTimeFormatterWithSeconds);
                    double value = rs.getDouble("value");
                    String collaboratorCpf = rs.getString("collaboratorCpf");
                    listOfFinances.add(new Finance(title, notes, contractName, type, financeClass, paymentMethod, date, recordDateTime, value, collaboratorCpf));
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
    public List<Finance> listAllWithFilters(String queryType, String queryCpf, LocalDate queryStartDateTimePeriod, LocalDate queryEndDateTimePeriod, String queryContract, String queryMinValue, String queryMaxValue) throws ConnectionFailureDbException {
        List<Finance> listOfAllWithFilters = new ArrayList<>();
        List<Finance> listOfWithdrawalsFinances = new ArrayList<>();

        listOfAllWithFilters.clear();
        listOfWithdrawalsFinances.clear();

        List<String> filters = new ArrayList<>();
        if(!queryCpf.equals("")) filters.add(queryCpf);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

        try (Connection conn = ConnectionFactory.getConnection()){
            String sql;
            if(!queryCpf.equals("")) sql = "SELECT * FROM finances WHERE collaboratorCpf = ?";
            else sql = "SELECT * FROM finances";

            if(queryStartDateTimePeriod != null){
                if(filters.size() > 0) sql = sql + " AND STR_TO_DATE(date, '%d/%m/%Y') >= STR_TO_DATE(?, '%d/%m/%Y')";
                else sql = sql + " WHERE STR_TO_DATE(date, '%d/%m/%Y') >= STR_TO_DATE(?, '%d/%m/%Y')";
                filters.add(queryStartDateTimePeriod.atTime(0,0,0).format(dateTimeFormatterWithSeconds));
            }

            if(queryEndDateTimePeriod != null){
                if(filters.size() > 0) sql = sql + " AND STR_TO_DATE(date, '%d/%m/%Y') <= STR_TO_DATE(?, '%d/%m/%Y')";
                else sql = sql + " WHERE STR_TO_DATE(date, '%d/%m/%Y') <= STR_TO_DATE(?, '%d/%m/%Y')";
                filters.add(queryEndDateTimePeriod.atTime(23,59,59).format(dateTimeFormatterWithSeconds));
            }

            if(queryContract != null){
                if(!queryContract.equals("----------")){
                    if(filters.size() > 0) sql = sql + " AND contractName = ?";
                    else sql = sql + " WHERE contractName = ?";
                    filters.add(queryContract);
                }
            }

            if(!queryMinValue.equals("")){
                if(filters.size() > 0) sql = sql + " AND value >= ?";
                else sql = sql + " WHERE value >= ?";
                filters.add(queryMinValue);
            }

            if(!queryMaxValue.equals("")){
                if(filters.size() > 0) sql = sql + " AND value <= ?";
                else sql = sql + " WHERE value <= ?";
                filters.add(queryMaxValue);
            }

            sql = sql + " ORDER BY STR_TO_DATE(date, '%d/%m/%Y')";

            PreparedStatement ps = conn.prepareStatement(sql);
            for(int i = 0; i < filters.size(); i++){
                ps.setString(i+1, filters.get(i).toUpperCase());
            }

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    String title = rs.getString("title");
                    String notes = rs.getString("notes");
                    String contractName = rs.getString("contractName");
                    String type = rs.getString("type");
                    String financeClass = rs.getString("financeClass");
                    String paymentMethod = rs.getString("paymentMethod");
                    LocalDate date = LocalDate.parse(rs.getString("date"), dateFormatter);
                    LocalDateTime recordDateTime = LocalDateTime.parse(rs.getString("recordDateTime"), dateTimeFormatterWithSeconds);
                    double value = rs.getDouble("value");
                    String collaboratorCpf = rs.getString("collaboratorCpf");
                    listOfAllWithFilters.add(new Finance(title, notes, contractName, type, financeClass, paymentMethod, date, recordDateTime, value, collaboratorCpf));
                }
            }

            if(queryType.equals("entries")){
                for(Finance finance : listOfAllWithFilters){
                    if(!finance.getType().equals("RECEITA")) listOfWithdrawalsFinances.add(finance);
                }
            }else if(queryType.equals("expenses")){
                for(Finance finance : listOfAllWithFilters){
                    if(!finance.getType().equals("DESPESA")) listOfWithdrawalsFinances.add(finance);
                }
            }

            listOfAllWithFilters.removeAll(listOfWithdrawalsFinances);

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }

        return listOfAllWithFilters;
    }

    @Override
    public List<Finance> listAllByCollaboratorCpf(String cpf) throws ConnectionFailureDbException {
        List<Finance> listOfAllByCpf = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT id, title, notes, contractName, type, financeClass, paymentMethod, date, recordDateTime, value, collaboratorCpf FROM finances WHERE collaboratorCpf = ? ORDER BY STR_TO_DATE(date, '%d/%m/%Y')";

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cpf.toUpperCase());

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    String title = rs.getString("title");
                    String notes = rs.getString("notes");
                    String contractName = rs.getString("contractName");
                    String type = rs.getString("type");
                    String financeClass = rs.getString("financeClass");
                    String paymentMethod = rs.getString("paymentMethod");
                    LocalDate date = LocalDate.parse(rs.getString("date"), dateFormatter);
                    LocalDateTime recordDateTime = LocalDateTime.parse(rs.getString("recordDateTime"), dateTimeFormatter);
                    double value = rs.getDouble("value");
                    String collaboratorCpf = rs.getString("collaboratorCpf");
                    listOfAllByCpf.add(new Finance(title, notes, contractName, type, financeClass, paymentMethod, date, recordDateTime, value, collaboratorCpf));
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
            String sql = "SELECT id, title, notes, contractName, type, financeClass, paymentMethod, date, recordDateTime, value, collaboratorCpf FROM finances ORDER BY STR_TO_DATE(date, '%d/%m/%Y')";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatterWithSeconds = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            PreparedStatement ps = conn.prepareStatement(sql);
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    String title = rs.getString("title");
                    String notes = rs.getString("notes");
                    String contractName = rs.getString("contractName");
                    String type = rs.getString("type");
                    String financeClass = rs.getString("financeClass");
                    String paymentMethod = rs.getString("paymentMethod");
                    LocalDate date = LocalDate.parse(rs.getString("date"), dateTimeFormatter);
                    LocalDateTime recordDateTime = LocalDateTime.parse(rs.getString("recordDateTime"), dateTimeFormatterWithSeconds);
                    double value = rs.getDouble("value");
                    String collaboratorCpf = rs.getString("collaboratorCpf");
                    listOfFinances.add(new Finance(title, notes, contractName, type, financeClass, paymentMethod, date, recordDateTime, value, collaboratorCpf));
                }
            }

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
        return listOfFinances;
    }
}
