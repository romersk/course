package database.factory.impl;

import database.MyDatabase;
import database.factory.IShoes;
import model.Shoes;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SqlShoes implements IShoes {

    private static SqlShoes instance;
    private final MyDatabase dbConnection;

    public SqlShoes() throws SQLException, ClassNotFoundException {
        dbConnection = MyDatabase.getInstance();
    }

    public static synchronized SqlShoes getInstance()
            throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new SqlShoes();
        }
        return instance;
    }

    @Override
    public int insert(Shoes obj) {
        String str = "INSERT INTO shoes (name, expenses, costs, time_manufacture) VALUES('" + obj.getName()
                + "'," + obj.getExpenses() + ", " + obj.getCosts() + ", "
                + obj.getTimeManufacture() + ") RETURNING id";
        ArrayList<String[]> result = dbConnection.insert(str);
        return Integer.parseInt(result.get(0)[0]);
    }

    @Override
    public void update(Shoes obj, int id) {
        String str = "UPDATE shoes SET shoes.name='"
                + obj.getName()
                + "', shoes.expenses="
                + obj.getExpenses()
                + ", shoes.costs="
                + obj.getCosts()
                + ", shoes.time_manufacture="
                + obj.getTimeManufacture()
                + "  WHERE shoes.id=" + id;
        dbConnection.update(str);
    }

    @Override
    public Shoes selectShoes(int id) throws SQLException {
        String str = "SELECT * FROM shoes WHERE id = " + id;
        ArrayList<String[]> result = dbConnection.select(str);
        Shoes shoes = new Shoes();
        for (String[] items: result){
            shoes.setId(Integer.parseInt(items[0]));
            shoes.setName(items[1]);
            shoes.setExpenses(Double.parseDouble(items[2]));
            shoes.setCosts(Double.parseDouble(items[3]));
            shoes.setTimeManufacture(Double.parseDouble(items[4]));
        }
        return shoes;
    }

    @Override
    public void delete(int id) {
        String str = "UPDATE shoes SET shoes.deleted_at='"
                + LocalDateTime.now()
                + "'  WHERE shoes.id=" + id;
        dbConnection.update(str);
    }

    @Override
    public ArrayList<Shoes> findAll() throws SQLException {
        String str = "SELECT * FROM shoes";
        ArrayList<String[]> result = dbConnection.select(str);
        ArrayList<Shoes> list = new ArrayList<>();
        for (String[] items: result){
            Shoes shoes = new Shoes();
            shoes.setId(Integer.parseInt(items[0]));
            shoes.setName(items[1]);
            shoes.setExpenses(Double.parseDouble(items[2]));
            shoes.setCosts(Double.parseDouble(items[3]));
            shoes.setTimeManufacture(Double.parseDouble(items[4]));
            list.add(shoes);
        }
        return list;
    }

    @Override
    public ArrayList<Shoes> findByCosts(double rangeOne, double rangeTwo) throws SQLException {
        String str = "SELECT * FROM shoes WHERE costs >= " + rangeOne + " AND costs <= " + rangeTwo;
        ArrayList<String[]> result = dbConnection.select(str);
        ArrayList<Shoes> list = new ArrayList<>();
        for (String[] items: result){
            Shoes shoes = new Shoes();
            shoes.setId(Integer.parseInt(items[0]));
            shoes.setName(items[1]);
            shoes.setExpenses(Double.parseDouble(items[2]));
            shoes.setCosts(Double.parseDouble(items[3]));
            shoes.setTimeManufacture(Double.parseDouble(items[4]));
            list.add(shoes);
        }
        return list;
    }

    @Override
    public Shoes findByName(String name) throws SQLException {
        String str = "SELECT * FROM shoes WHERE name ='" + name + "'";
        ArrayList<String[]> result = dbConnection.select(str);
        Shoes shoes = new Shoes();
        for (String[] items: result){
            shoes.setId(Integer.parseInt(items[0]));
            shoes.setName(items[1]);
            shoes.setExpenses(Double.parseDouble(items[2]));
            shoes.setCosts(Double.parseDouble(items[3]));
            shoes.setTimeManufacture(Double.parseDouble(items[4]));
        }
        return shoes;
    }
}
