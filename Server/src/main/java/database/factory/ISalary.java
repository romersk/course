package database.factory;

import model.Salary;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ISalary {
    int insert(Salary obj);
    void update(Salary obj, int id);
    Salary selectShoes(int id) throws SQLException;
    void delete(int id);
    ArrayList<Salary> findAll() throws SQLException;
    Salary select(int id, int month) throws SQLException;
}
