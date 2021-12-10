package database.factory.impl;

import database.MyDatabase;
import database.factory.IOperation;
import model.Operation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SqlOperation implements IOperation {

    private static SqlOperation instance;
    private final MyDatabase dbConnection;

    public SqlOperation() throws SQLException, ClassNotFoundException {
        dbConnection = MyDatabase.getInstance();
    }

    public static synchronized SqlOperation getInstance()
            throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new SqlOperation();
        }
        return instance;
    }

    @Override
    public int insert(Operation obj) {
        String str = "INSERT INTO operation (id_users, created_at, hours) VALUES(" + obj.getId_users()
                + ",'" + obj.getCreatedAt() + "', " + obj.getHours() + ") RETURNING id";
        ArrayList<String[]> result = dbConnection.insert(str);
        return Integer.parseInt(result.get(0)[0]);
    }

    @Override
    public void update(Operation obj, int id) {
        String str = "UPDATE operation SET operation.id_users="
                + obj.getId_users()
                + ", operation.created_at='"
                + obj.getCreatedAt()
                + "', operation.hours="
                + obj.getHours()
                + "  WHERE operation.id=" + id;
        dbConnection.update(str);
    }

    @Override
    public Operation selectOperation(int id) throws SQLException {
        String str = "SELECT * FROM operation WHERE id = " + id;
        ArrayList<String[]> result = dbConnection.select(str);
        Operation operation = new Operation();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (String[] items: result){
            operation.setId(Integer.parseInt(items[0]));
            operation.setId_users(Integer.parseInt(items[1]));
            operation.setCreatedAt(LocalDate.parse(items[2],formatter));
            operation.setHours(Double.parseDouble(items[3]));
        }
        return operation;
    }

    @Override
    public void delete(int id) {
        String str = "DELETE FROM operation WHERE id = " + id;
        dbConnection.delete(str);
    }

    @Override
    public ArrayList<Operation> findAll() throws SQLException {
        String str = "SELECT * FROM operation";
        ArrayList<String[]> result = dbConnection.select(str);
        ArrayList<Operation> operations = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (String[] items: result){
            Operation operation = new Operation();
            operation.setId(Integer.parseInt(items[0]));
            operation.setId_users(Integer.parseInt(items[1]));
            operation.setCreatedAt(LocalDate.parse(items[2],formatter));
            operation.setHours(Double.parseDouble(items[3]));
            operations.add(operation);
        }
        return operations;
    }

    @Override
    public ArrayList<Operation> findAllBetweenDates(LocalDate start, LocalDate finish) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String str = "SELECT * FROM operation WHERE operation.created_at BETWEEN '"
                + start.format(formatter) + "' AND '" + finish.format(formatter) + "' ORDER BY operation.created_at";
        ArrayList<String[]> result = dbConnection.select(str);
        ArrayList<Operation> operations = new ArrayList<>();
        for (String[] items: result){
            Operation operation = new Operation();
            operation.setId(Integer.parseInt(items[0]));
            operation.setId_users(Integer.parseInt(items[1]));
            operation.setCreatedAt(LocalDate.parse(items[2],formatter));
            operation.setHours(Double.parseDouble(items[3]));
            operations.add(operation);
        }
        return operations;
    }

    @Override
    public Operation findByDate(String date) throws SQLException {
        String str = "SELECT * FROM operation WHERE created_at='" + date + "'";
        ArrayList<String[]> result = dbConnection.select(str);
        Operation operation = new Operation();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (String[] items: result){
            operation.setId(Integer.parseInt(items[0]));
            operation.setId_users(Integer.parseInt(items[1]));
            operation.setCreatedAt(LocalDate.parse(items[2],formatter));
            operation.setHours(Double.parseDouble(items[3]));
        }
        return operation;
    }
}
