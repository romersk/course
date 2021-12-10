package database.factory.impl;

import database.MyDatabase;
import database.factory.IPerson;
import model.Person;

import java.sql.SQLException;
import java.util.ArrayList;

public class SqlPerson implements IPerson {

    private static SqlPerson instance;
    private final MyDatabase dbConnection;

    public SqlPerson() throws SQLException, ClassNotFoundException {
        dbConnection = MyDatabase.getInstance();
    }

    public static synchronized SqlPerson getInstance()
            throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new SqlPerson();
        }
        return instance;
    }

    @Override
    public int insert(Person obj) {
        String str = "INSERT INTO person (id_user, first_name, last_name, category) VALUES("
                + obj.getIdUser() + ",'" + obj.getFirstName() + "','"
                + obj.getLastName() + "'," + obj.getCategory() + ") RETURNING id";
        ArrayList<String[]> result = dbConnection.insert(str);
        return Integer.parseInt(result.get(0)[0]);
    }

    @Override
    public void update(Person obj, int id) {
        String str = "UPDATE person SET person.first_name='"
                + obj.getFirstName()
                + "', person.last_name='"
                + obj.getLastName()
                + "', person.category='"
                + obj.getCategory()
                + "'  WHERE person.id=" + id;
        dbConnection.update(str);
    }

    @Override
    public Person selectPerson(int id) throws SQLException {
        String str = "SELECT * FROM person WHERE id = " + id;
        ArrayList<String[]> result = dbConnection.select(str);
        Person person = new Person();
        for (String[] items: result){
            person.setIdPerson(Integer.parseInt(items[0]));
            person.setIdUser(Integer.parseInt(items[1]));
            person.setFirstName(items[2]);
            person.setLastName(items[3]);
            person.setCategory(Integer.parseInt(items[4]));
        }
        return person;
    }

    @Override
    public ArrayList<Person> findAll(String type, String criteria) throws SQLException {
        String str = "SELECT * FROM person WHERE ";
        switch (type) {
            case "Имя":
                str += "first_name ";
                break;
            case "Фамилия":
                str += "last_name ";
                break;
            case "Адрес":
                str += "address ";
                break;
            default:
                break;
        }
        str += "LIKE '%" + criteria + "%'";
        return getPeopleAll(str);
    }

    private ArrayList<Person> getPeopleAll(String str) throws SQLException {
        ArrayList<String[]> result = dbConnection.select(str);
        ArrayList<Person> people = new ArrayList<>();
        for (String[] items: result){
            Person person = new Person();
            person.setIdPerson(Integer.parseInt(items[0]));
            person.setIdUser(Integer.parseInt(items[1]));
            person.setFirstName(items[2]);
            person.setLastName(items[3]);
            person.setCategory(Integer.parseInt(items[4]));
            people.add(person);
        }
        return people;
    }

    @Override
    public void delete(int id) {
        String str = "DELETE FROM person WHERE id = " + id;
        dbConnection.delete(str);
    }

    @Override
    public ArrayList<Person> findAll() throws SQLException {
        String str = "SELECT * FROM person";
        return getPeopleAll(str);
    }
}
