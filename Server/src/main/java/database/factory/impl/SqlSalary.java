package database.factory.impl;

import database.MyDatabase;
import database.factory.ISalary;
import model.Person;
import model.Salary;
import model.Shoes;

import java.sql.SQLException;
import java.util.ArrayList;

public class SqlSalary implements ISalary {

    private static SqlSalary instance;
    private final MyDatabase dbConnection;

    public SqlSalary() throws SQLException, ClassNotFoundException {
        dbConnection = MyDatabase.getInstance();
    }

    public static synchronized SqlSalary getInstance()
            throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new SqlSalary();
        }
        return instance;
    }

    @Override
    public int insert(Salary obj) {
        String str = "INSERT INTO salary (id_users, number_month, count_time, size) VALUES(" + obj.getIdUser()
               + ", " + obj.getNumberMonth() + ", " + obj.getCountTime() + ", " + obj.getSize() + ") RETURNING id";
        ArrayList<String[]> result = dbConnection.insert(str);
        return Integer.parseInt(result.get(0)[0]);
    }

    @Override
    public void update(Salary obj, int id) {
        String str = "UPDATE salary SET salary.count_time="
                + obj.getCountTime()
                + ", salary.size="
                + obj.getSize()
                + "  WHERE salary.id=" + id;
        dbConnection.update(str);
    }

    @Override
    public Salary selectShoes(int id) throws SQLException {
        String str = "SELECT * FROM salary WHERE id = " + id;
        ArrayList<String[]> result = dbConnection.select(str);
        Salary salary = new Salary();
        for (String[] items: result){
            salary.setId(Integer.parseInt(items[0]));
            salary.setIdUser(Integer.parseInt(items[1]));
            salary.setNumberMonth(Integer.parseInt(items[2]));
            salary.setCountTime(Double.parseDouble(items[3]));
            salary.setSize(Double.parseDouble(items[4]));
        }
        return salary;
    }

    @Override
    public void delete(int id) {
        String str = "DELETE FROM salary WHERE id = " + id;
        dbConnection.delete(str);
    }

    @Override
    public ArrayList<Salary> findAll() throws SQLException {
        String str = "SELECT * FROM salary";
        ArrayList<String[]> result = dbConnection.select(str);
        ArrayList<Salary> salaries = new ArrayList<>();
        for (String[] items: result){
            Salary salary = new Salary();
            salary.setId(Integer.parseInt(items[0]));
            salary.setIdUser(Integer.parseInt(items[1]));
            salary.setNumberMonth(Integer.parseInt(items[2]));
            salary.setCountTime(Double.parseDouble(items[3]));
            salary.setSize(Double.parseDouble(items[4]));
            salaries.add(salary);
        }
        return salaries;
    }

    @Override
    public Salary select(int id, int month) throws SQLException {
        String str = "SELECT * FROM salary WHERE id_user = " + id + " AND number_month = " + month;
        ArrayList<String[]> result = dbConnection.select(str);
        Salary salary = new Salary();
        for (String[] items: result){
            salary.setId(Integer.parseInt(items[0]));
            salary.setIdUser(Integer.parseInt(items[3]));
            salary.setNumberMonth(Integer.parseInt(items[4]));
            salary.setCountTime(Double.parseDouble(items[1]));
            salary.setSize(Double.parseDouble(items[2]));
        }
        return salary;
    }
}
