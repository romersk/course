package database.factory;

import model.Operation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface IOperation {
    int insert(Operation obj);
    void update(Operation obj, int id);
    Operation selectOperation(int id) throws SQLException;
    void delete(int id);
    ArrayList<Operation> findAll() throws SQLException;
    ArrayList<Operation> findAllBetweenDates(LocalDate start, LocalDate finish) throws SQLException;
    Operation findByDate(String date) throws SQLException;
}
