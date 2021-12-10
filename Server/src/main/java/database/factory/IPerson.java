package database.factory;

import model.Person;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IPerson {
    int insert(Person obj);
    void update(Person obj, int id);
    Person selectPerson(int id) throws SQLException;
    ArrayList<Person> findAll(String type, String criteria) throws SQLException;
    void delete(int id);
    ArrayList<Person> findAll() throws SQLException;
}
