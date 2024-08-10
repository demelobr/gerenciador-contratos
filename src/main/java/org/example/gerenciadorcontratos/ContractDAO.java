package org.example.gerenciadorcontratos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ContractDAO implements IContractDAO{
    private static IContractDAO instance;

    private ContractDAO(){}

    public static IContractDAO getInstance(){
        if(instance == null){
            instance = new ContractDAO();
        }
        return instance;
    }

    @Override
    public void create(Contract contract) throws ConnectionFailureDbException {
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "INSERT INTO contracts (name, description, budget, address, startDate, endDate) VALUES(?,?,?,?,?,?)";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, contract.getName().toUpperCase());
            ps.setString(2, contract.getDescription().toUpperCase());
            ps.setFloat(3, contract.getBudget());
            ps.setString(4, contract.getAddress().toUpperCase());
            ps.setString(5, contract.getStartDate().format(dateTimeFormatter));
            ps.setString(6, contract.getEndDate().format(dateTimeFormatter));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
    }

    @Override
    public void update(Contract contract, String name, String description, String address, float budget, LocalDate startDate, LocalDate endDate) throws ConnectionFailureDbException {
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "UPDATE contracts SET name = ?, description = ?, budget = ?, address = ?, startDate = ?, endDate = ? WHERE name = ?";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name.toUpperCase());
            ps.setString(2, description.toUpperCase());
            ps.setFloat(3, budget);
            ps.setString(4, address.toUpperCase());
            ps.setString(5, startDate.format(dateTimeFormatter));
            ps.setString(6, endDate.format(dateTimeFormatter));
            ps.setString(7, contract.getName().toUpperCase());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
    }

    @Override
    public void delete(Contract contract) throws ConnectionFailureDbException {
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "DELETE FROM contracts WHERE name = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, contract.getName().toUpperCase());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
    }

    @Override
    public boolean contractExists(String name) throws ConnectionFailureDbException {
        boolean contractExists = false;
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT id, name, description, budget, address, startDate, endDate FROM contracts WHERE name = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name.toUpperCase());

            try (ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    contractExists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
        return contractExists;
    }

    @Override
    public Contract getByName(String queryName) throws ConnectionFailureDbException {
        Contract contract = null;
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT id, name, description, budget, address, startDate, endDate FROM contracts WHERE name = ?";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, queryName.toUpperCase());

            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    float budget = rs.getFloat("budget");
                    String address = rs.getString("address");
                    LocalDate startDate = LocalDate.parse(rs.getString("startDate"), dateTimeFormatter);
                    LocalDate endDate = LocalDate.parse(rs.getString("endDate"), dateTimeFormatter);
                    contract = new Contract(name, description, address, budget, startDate, endDate);
                }
            }

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
        return contract;
    }

    @Override
    public List<Contract> listAll() throws ConnectionFailureDbException {
        List<Contract> listOfContracts = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT id, name, description, budget, address, startDate, endDate FROM contracts";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            PreparedStatement ps = conn.prepareStatement(sql);
            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()) {
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    float budget = rs.getFloat("budget");
                    String address = rs.getString("address");
                    LocalDate startDate = LocalDate.parse(rs.getString("startDate"), dateTimeFormatter);
                    LocalDate endDate = LocalDate.parse(rs.getString("endDate"), dateTimeFormatter);
                    listOfContracts.add(new Contract(name, description, address, budget, startDate, endDate));
                }
            }

        } catch (SQLException e) {
            throw new ConnectionFailureDbException();
        }
        return listOfContracts;
    }
}
