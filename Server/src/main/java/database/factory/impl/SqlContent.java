package database.factory.impl;

import database.MyDatabase;
import database.factory.IContent;
import model.Content;

import java.sql.SQLException;
import java.util.ArrayList;

public class SqlContent implements IContent {

    private static SqlContent instance;
    private final MyDatabase dbConnection;

    public SqlContent() throws SQLException, ClassNotFoundException {
        dbConnection = MyDatabase.getInstance();
    }

    public static synchronized SqlContent getInstance()
            throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new SqlContent();
        }
        return instance;
    }

    @Override
    public int insert(Content obj) {
        String str = "INSERT INTO content (id_operation, id_shoes, count_of_shoes) VALUES(" + obj.getIdOperation()
                + ", " + obj.getIdShoes() + ", " + obj.getCountOfShoes() + ") RETURNING id";
        ArrayList<String[]> result = dbConnection.insert(str);
        return Integer.parseInt(result.get(0)[0]);
    }

    @Override
    public Content selectOrder(int id) throws SQLException {
        String str = "SELECT * FROM content WHERE id = " + id;
        ArrayList<String[]> result = dbConnection.select(str);
        Content content = new Content();
        for (String[] items: result){
            content.setId(Integer.parseInt(items[0]));
            content.setIdOperation(Integer.parseInt(items[1]));
            content.setIdShoes(Integer.parseInt(items[2]));
        }
        return content;
    }

    @Override
    public void delete(int id) {
        String str = "DELETE FROM content WHERE id = " + id;
        dbConnection.delete(str);
    }

    @Override
    public ArrayList<Content> findAll() throws SQLException {
        String str = "SELECT * FROM content";
        return getContentsFromDB(str);
    }

    @Override
    public ArrayList<Content> findById(int id) throws SQLException {
        String str = "SELECT * FROM content WHERE id_operation = " + id;
        return getContentsFromDB(str);
    }

    private ArrayList<Content> getContentsFromDB(String str) throws SQLException {
        ArrayList<String[]> result = dbConnection.select(str);
        ArrayList<Content> contents = new ArrayList<>();
        for (String[] items: result){
            Content content = new Content();
            content.setId(Integer.parseInt(items[0]));
            content.setIdOperation(Integer.parseInt(items[1]));
            content.setIdShoes(Integer.parseInt(items[2]));
            content.setCountOfShoes(Integer.parseInt(items[3]));
            contents.add(content);
        }
        return contents;
    }
}

