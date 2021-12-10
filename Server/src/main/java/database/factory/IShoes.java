package database.factory;

import model.Shoes;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IShoes {
    int insert(Shoes obj);
    void update(Shoes obj, int id);
    Shoes selectShoes(int id) throws SQLException;
    void delete(int id);
    ArrayList<Shoes> findAll() throws SQLException;
    ArrayList<Shoes> findByCosts(double rangeOne, double rangeTwo) throws SQLException;
    Shoes findByName(String name) throws SQLException;
}
