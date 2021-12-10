package database.factory;

import model.Content;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IContent {
    int insert(Content obj);
    Content selectOrder(int id) throws SQLException;
    void delete(int id);
    ArrayList<Content> findAll() throws SQLException;
    ArrayList<Content> findById(int id) throws SQLException;
}
