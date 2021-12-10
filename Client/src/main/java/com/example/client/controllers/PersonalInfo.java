package com.example.client.controllers;

import com.example.client.data.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Person;
import model.User;

import java.io.IOException;
import java.util.Objects;

public class PersonalInfo {
    public TextField category;
    public Button buttToBack;
    public Button buttToSave;
    public TextField surname;
    public TextField name;
    public TextField login;
    public PasswordField password;
    public Label labelForSurname;
    public Label labelForName;
    public Label labelForAddress;
    public Label labelForLogin;
    public Label labelForPass;

    private final String TEXT_REGEX = "[А-Яа-яё]{2,30}";
    private final String NUM_REGEX = "[0-9]{1,2}";
    private final String REGEX_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";


    public void toBack(ActionEvent actionEvent) {
        Data data = Data.getInstance();
        buttToBack.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        if (data.getEditUser() == null) {
            if (Objects.equals(data.getUser().getRole(), "ADMIN")) {
                fxmlLoader.setLocation(getClass().getResource("/com/example/client/admin-page.fxml"));
            } else {
                fxmlLoader.setLocation(getClass().getResource("/com/example/client/user-page.fxml"));
            }
        } else {
            fxmlLoader.setLocation(getClass().getResource("/com/example/client/user-control.fxml"));
        }
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void toSave(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (isFilled()) {
            Data data = Data.getInstance();
            User user = new User();
            if (data.getEditUser() == null) {
                user.setIdUser(data.getUser().getIdUser());
                user.setLogin(login.getText());
                user.setPassword(password.getText());
                user.setRole(data.getUser().getRole());

                data.getUser().setLogin(login.getText());
                data.getUser().setPassword(password.getText());

                data.getClient().writeInt(6);
                data.getClient().writeObject("Person");
                data.getClient().writeObject(data.getUser().getIdPerson());
            } else {
                user.setIdUser(data.getEditUser().getIdUser());
                user.setLogin(login.getText());
                user.setPassword(password.getText());
                user.setRole(data.getEditUser().getRole());

                data.getEditUser().setLogin(login.getText());
                data.getEditUser().setPassword(password.getText());

                data.getClient().writeInt(6);
                data.getClient().writeObject("Person");
                data.getClient().writeObject(data.getEditUser().getIdPerson());
            }

            Person person = (Person) data.getClient().getObject();
            person.setFirstName(name.getText());
            person.setLastName(surname.getText());
            person.setCategory(Integer.parseInt(category.getText()));

            data.getClient().writeInt(4);
            data.getClient().writeObject("Person");
            data.getClient().writeObject(person);
            data.getClient().writeObject(person.getIdPerson());
            data.getClient().writeObject(user.getIdUser());

            data.getClient().writeInt(4);
            data.getClient().writeObject("User");
            data.getClient().writeObject(user);
            data.getClient().writeObject(user.getIdUser());
            data.getClient().writeObject(person.getIdPerson());
            this.toBack(actionEvent);
        }
    }

    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        Data data = Data.getInstance();
        if (data.getEditUser() == null) {
            login.setText(data.getUser().getLogin());
            password.setText(data.getUser().getPassword());
            data.getClient().writeInt(6);
            data.getClient().writeObject("Person");
            data.getClient().writeObject(data.getUser().getIdPerson());

        } else {
            login.setText(data.getEditUser().getLogin());
            password.setText(data.getEditUser().getPassword());
            data.getClient().writeInt(6);
            data.getClient().writeObject("Person");
            data.getClient().writeObject(data.getEditUser().getIdPerson());
        }
        Person person = (Person) data.getClient().getObject();
        surname.setText(person.getLastName());
        name.setText(person.getFirstName());
        category.setText(String.valueOf(person.getCategory()));
    }

    private boolean isFilled() throws IOException, ClassNotFoundException {
        boolean answer = true;
        if (surname.getText().equals("") || !surname.getText().matches(TEXT_REGEX)) {
            labelForSurname.setText("Заполните поле корректно");
            answer = false;
        } else {
            labelForSurname.setText("");
        }
        if (name.getText().equals("") || !name.getText().matches(TEXT_REGEX)) {
            labelForName.setText("Заполните поле корректно");
            answer = false;
        } else {
            labelForName.setText("");
        }
        if (category.getText().equals("") || !category.getText().matches(NUM_REGEX)) {
            labelForAddress.setText("Заполните поле корректно");
            answer = false;
        } else {
            labelForAddress.setText("");
        }
        if (login.getText().equals("") || !login.getText().matches(REGEX_EMAIL)) {
            labelForLogin.setText("Заполните поле корректно");
            answer = false;
        } else  {
            Data data = Data.getInstance();
            data.getClient().writeInt(2);
            data.getClient().writeLine(login.getText());
            User user = (User) data.getClient().getObject();
            if ((user.getIdUser() > 0 && !data.getUser().getLogin().equals(user.getLogin()))
                    && (!data.getEditUser().getLogin().equals(user.getLogin()))) {
                labelForLogin.setText("Никнейм занят");
                answer = false;
            } else  {
                labelForLogin.setText("");
            }
        }
        if (password.getText().equals("")) {
            labelForPass.setText("Заполните поле");
            answer = false;
        } else if (password.getText().length() < 6){
            labelForPass.setText("Пароль менее 6 символов");
            answer = false;
        } else {
            labelForPass.setText("");
        }
        return answer;
    }
}
