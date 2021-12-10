package server;

import database.MyDatabase;
import database.factory.*;
import database.factory.impl.*;
import model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServerWork {

    private ObjectInputStream in;
    private MyDatabase database;
    private ObjectOutputStream out;

    public ServerWork (ObjectInputStream in, MyDatabase database, ObjectOutputStream outputStream){
        this.in = in;
        this.database = database;
        this.out = outputStream;
    }

    public void getId (int idOperation) throws IOException, SQLException, ClassNotFoundException {
        switch(idOperation){
            case 1:
                singIn();
                break;
            case 2:
                findUserByLogin();
                break;
            case 3:
                saveEntityAndGetId();
                break;
            case 4:
                updateEntity();
                break;
            case 5:
                getAllEntities();
                break;
            case 6:
                getEnityById();
                break;
            case 7:
                deleteEntityById();
                break;
            case 8:
                searchEntity();
                break;
            case 10:
                getAllEntitiesById();
                break;
            case 11:
                getOperationBetweenDates();
                break;
            case 12:
                getSalaryByIdAndMonth();
                break;
            case 13:
                findByNameShoes();
                break;
            default:
                break;
        }
    }

    private void findByNameShoes() throws IOException, ClassNotFoundException, SQLException {
        String name = (String) in.readObject();
        IShoes iShoes = new SqlShoes();
        Shoes shoes = iShoes.findByName(name);
        out.writeObject(shoes);
    }

    private void getSalaryByIdAndMonth() throws IOException, ClassNotFoundException, SQLException{
        int id = (int) in.readObject();
        int month = (int) in.readObject();
        ISalary iSalary = new SqlSalary();
        Salary salary = iSalary.select(id, month);
        out.writeObject(salary);
    }

    private void getOperationBetweenDates() throws IOException, ClassNotFoundException, SQLException {
        LocalDate start = (LocalDate) in.readObject();
        LocalDate finish = (LocalDate) in.readObject();
        IOperation iOperation = new SqlOperation();
        ArrayList<Operation> list = iOperation.findAllBetweenDates(start, finish);
        out.writeObject(list);
    }


    private void getAllEntitiesById() throws SQLException, ClassNotFoundException, IOException{
        String type = (String) in.readObject();
        switch (type) {
            case "Content":
            {
                int id = (int) in.readObject();
                IContent iContent = new SqlContent();
                ArrayList<Content> list = iContent.findById(id);
                out.writeObject(list);
                break;
            }
        }
    }


    private void searchEntity() throws IOException, ClassNotFoundException, SQLException {
        String type = (String) in.readObject();
        switch (type) {
            case "User":
            {
                String login = (String) in.readObject();
                IUser iUser = new SqlUser();
                ArrayList<User> list = iUser.findByLogin(login);
                out.writeObject(list);
                break;
            }
            case "Person":
            {
                String typeS = (String) in.readObject();
                String criteria = (String) in.readObject();
                IPerson iPerson = new SqlPerson();
                ArrayList<Person> list = iPerson.findAll(typeS, criteria);
                out.writeObject(list);
                break;
            }
            case "Shoes" :
            {
                double criteria = (double) in.readObject();
                double rangeTwo = (double) in.readObject();
                IShoes iShoes = new SqlShoes();
                ArrayList<Shoes> list = iShoes.findByCosts(criteria, rangeTwo);
                out.writeObject(list);
                break;
            }
            default:
                break;
        }
    }

    private void deleteEntityById() throws IOException, ClassNotFoundException, SQLException {
        String type = (String) in.readObject();
        int id = (int) in.readObject();
        switch (type) {
            case "User":
            {
                IUser iUser = new SqlUser();
                iUser.delete(id);
                break;
            }
            case "Shoes":
            {
                IShoes iShoes = new SqlShoes();
                iShoes.delete(id);
                break;
            }
            default:
                break;
        }
    }

    private void getEnityById() throws IOException, ClassNotFoundException, SQLException{
        String type = (String) in.readObject();
        switch (type) {
            case "Person":
            {
                int id = (int) in.readObject();
                IPerson iPerson = new SqlPerson();
                Person person = iPerson.selectPerson(id);
                out.writeObject(person);
                break;
            }
            case "User":
            {
                int id = (int) in.readObject();
                IUser iUser = new SqlUser();
                User user = iUser.selectUserById(id);
                out.writeObject(user);
                break;
            }
            case "Shoes":
            {
                int id = (int) in.readObject();
                IShoes iShoes = new SqlShoes();
                Shoes shoes = iShoes.selectShoes(id);
                out.writeObject(shoes);
                break;
            }
            case "Operation":
            {
                String date = (String) in.readObject();
                IOperation iOperation = new SqlOperation();
                Operation operation = iOperation.findByDate(date);
                out.writeObject(operation);
                break;
            }
            default:
                break;
        }
    }

    private void getAllEntities() throws SQLException, ClassNotFoundException, IOException{
        String type = (String) in.readObject();
        switch (type) {
            case "User": {
                IUser iUser = new SqlUser();
                ArrayList<User> list = iUser.findAll();
                out.writeObject(list);
                break;
            }
            case "Shoes": {
                IShoes iShoes = new SqlShoes();
                ArrayList<Shoes> list = iShoes.findAll();
                out.writeObject(list);
                break;
            }
            case "Content": {
                IContent iContent = new SqlContent();
                ArrayList<Content> list = iContent.findAll();
                out.writeObject(list);
                break;
            }
            case "Operation":
            {
                IOperation iOperation = new SqlOperation();
                ArrayList<Operation> list = iOperation.findAll();
                out.writeObject(list);
                break;
            }
            default:
                break;
        }
    }

    private void updateEntity() throws IOException, ClassNotFoundException, SQLException{
        String type = (String) in.readObject();
        switch (type) {
            case "User":
            {
                User user = (User) in.readObject();
                int idUser = (int) in.readObject();
                int idPerson = (int) in.readObject();
                user.setIdPerson(idPerson);
                user.setIdUser(idUser);
                IUser iUser = new SqlUser();
                iUser.update(user, idUser);
                break;
            }
            case "Person":
            {
                Person person = (Person) in.readObject();
                int idPerson = (int) in.readObject();
                int idUser = (int) in.readObject();
                person.setIdPerson(idPerson);
                person.setIdUser(idUser);
                IPerson iPerson = new SqlPerson();
                iPerson.update(person, idPerson);
                break;
            }
            case "Shoes":
            {
                Shoes shoes = (Shoes) in.readObject();
                IShoes iShoes = new SqlShoes();
                iShoes.update(shoes, shoes.getId());
                break;
            }
            case "Salary":
            {
                Salary salary = (Salary) in.readObject();
                ISalary iSalary = new SqlSalary();
                iSalary.update(salary, salary.getId());
                break;
            }
            default:
                break;
        }

    }

    private void saveEntityAndGetId() throws IOException, ClassNotFoundException, SQLException {
        String type = (String) in.readObject();
        switch (type) {
            case "User":
            {
                User user = (User) in.readObject();
                IUser iUser = new SqlUser();
                int id = iUser.insert(user);
                out.writeObject(id);
                break;
            }
            case "Person":
            {
                Person person = (Person) in.readObject();
                IPerson iPerson = new SqlPerson();
                int id = iPerson.insert(person);
                out.writeObject(id);
                break;
            }
            case "Content":
            {
                Content content = (Content) in.readObject();
                IContent iContent = new SqlContent();
                int id = iContent.insert(content);
                out.writeObject(id);
                break;
            }
            case "Shoes":
            {
                Shoes shoes = (Shoes) in.readObject();
                IShoes iShoes = new SqlShoes();
                int id = iShoes.insert(shoes);
                out.writeObject(id);
                break;
            }
            case "Salary":
            {
                Salary salary = (Salary) in.readObject();
                ISalary iSalary = new SqlSalary();
                int id = iSalary.insert(salary);
                out.writeObject(id);
                break;
            }
            case "Operation":
            {
                Operation operation = (Operation) in.readObject();
                IOperation iOperation = new SqlOperation();
                int id = iOperation.insert(operation);
                out.writeObject(id);
                break;
            }
            default:
                break;
        }
    }

    private void findUserByLogin() throws IOException, ClassNotFoundException, SQLException {
        String login = (String) in.readObject();
        IUser iUser = new SqlUser();
        User user = iUser.selectUserByLogin(login);
        out.writeObject(user);
    }

    private void singIn() throws IOException, ClassNotFoundException, SQLException {
        String login = (String) in.readObject();
        String password = (String) in.readObject();
        IUser iUser = new SqlUser();

        User user = iUser.selectUser(login, password);
        out.writeObject(user);
    }
}
