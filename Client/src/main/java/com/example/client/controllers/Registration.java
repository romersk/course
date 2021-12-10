package com.example.client.controllers;

import com.example.client.data.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Person;
import model.Salary;
import model.User;

import java.io.IOException;

public class Registration {
    public Button buttToBack;
    public Button buttReg;
    public TextField surname;
    public TextField name;
    public TextField address;
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
        buttToBack.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Data data = Data.getInstance();
        if (data.getEditUser() == null) {
            fxmlLoader.setLocation(getClass().getResource("/com/example/client/hello-view.fxml"));
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

    public void toReg(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (isFilled()) {
            Data data = Data.getInstance();

            User user = new User();
            user.setLogin(login.getText());
            user.setPassword(password.getText());
            user.setRole("USER");

            data.getClient().writeInt(3);
            data.getClient().writeObject("User");
            data.getClient().writeObject(user);
            int idUser = (int) data.getClient().getObject();
            user.setIdUser(idUser);

            Person person = new Person();
            person.setFirstName(name.getText());
            person.setLastName(surname.getText());
            person.setCategory(Integer.parseInt(address.getText()));
            person.setIdUser(idUser);

            data.getClient().writeInt(3);
            data.getClient().writeObject("Person");
            data.getClient().writeObject(person);
            int idPerson = (int) data.getClient().getObject();
            person.setIdPerson(idPerson);
            user.setIdPerson(idPerson);

            data.getClient().writeObject(4);
            data.getClient().writeObject("User");
            data.getClient().writeObject(user);
            data.getClient().writeObject(idUser);
            data.getClient().writeObject(idPerson);

            buttReg.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            if (data.getEditUser() == null) {
                fxmlLoader.setLocation(getClass().getResource("/com/example/client/user-page.fxml"));
                data.setUser(user);
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
        if (address.getText().equals("") || !address.getText().matches(NUM_REGEX)) {
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
            if (user.getIdUser() > 0) {
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
