package database.factory.impl;

import database.MyDatabase;
import database.factory.IUser;
import model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class SqlUser implements IUser {

    private static SqlUser instance;
    private final MyDatabase dbConnection;

    public SqlUser() throws SQLException, ClassNotFoundException {
        dbConnection = MyDatabase.getInstance();
    }

    public static synchronized SqlUser getInstance()
            throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new SqlUser();
        }
        return instance;
    }

    @Override
    public void update(User obj, int id) {
        String str = "UPDATE users SET users.id_person="
                + obj.getIdPerson()
                + ", users.login='"
                + obj.getLogin()
                + "', users.pass='"
                + obj.getPassword()
                + "', users.role='"
                + obj.getRole()
                + "'  WHERE users.id=" + obj.getIdUser();
        dbConnection.update(str);
    }

    @Override
    public int insert(User obj) {
        String str = "INSERT INTO users (login, pass, role) VALUES('"
                + obj.getLogin() + "','" + obj.getPassword() + "','"
                + obj.getRole() + "') RETURNING id";
        ArrayList<String[]> result = dbConnection.insert(str);
        return Integer.parseInt(result.get(0)[0]);
    }

    @Override
    public User selectUser(String login, String password) throws SQLException {
        String str = "SELECT * FROM users JOIN person ON users.id = person.id WHERE login='" + login
                + "' AND pass='" + password + "\'";
        return setUserFromDB(str);
    }

    @Override
    public User selectUserByLogin(String login) throws SQLException {
        String str = "SELECT * FROM users WHERE login='" + login + "'";
        return setUserFromDB(str);
    }

    @Override
    public ArrayList<User> findByLogin(String criteria) throws SQLException {
        String str = "SELECT * FROM users WHERE login LIKE '%" + criteria + "%'";
        return getUsersAll(str);
    }

    private ArrayList<User> getUsersAll(String str) throws SQLException {
        ArrayList<String[]> result = dbConnection.select(str);
        ArrayList<User> users = new ArrayList<>();
        for (String[] items: result){
            User user = new User();
            user.setIdUser(Integer.parseInt(items[0]));
            user.setIdPerson(Integer.parseInt(items[1]));
            user.setLogin(items[2]);
            user.setPassword(items[3]);
            user.setRole(items[4]);
            users.add(user);
        }
        return users;
    }

    @Override
    public void delete(int id) {
        String str = "DELETE FROM users WHERE id = " + id;
        dbConnection.delete(str);
    }

    @Override
    public User selectUserById(int id) throws SQLException {
        String str = "SELECT * FROM users WHERE id=" + id;
        return setUserFromDB(str);
    }

    @Override
    public ArrayList<User> findAll() throws SQLException {
        String str = "SELECT * FROM users";
        return getUsersAll(str);
    }

    private User setUserFromDB(String str) throws SQLException {
        ArrayList<String[]> result = dbConnection.select(str);
        User user = new User();
        for (String[] items: result){
            user.setIdUser(Integer.parseInt(items[0]));
            user.setIdPerson(Integer.parseInt(items[1]));
            user.setLogin(items[2]);
            user.setPassword(items[3]);
            user.setRole(items[4]);
        }
        return user;
    }
}
