package database.factory;

import model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IUser {
    void update(User obj, int id);
    int insert(User obj);
    User selectUser(String login, String password) throws SQLException;
    User selectUserByLogin(String login) throws SQLException;
    ArrayList<User> findByLogin(String criteria) throws SQLException;
    void delete(int id);
    User selectUserById(int id) throws SQLException;
    ArrayList<User> findAll() throws SQLException;
}
