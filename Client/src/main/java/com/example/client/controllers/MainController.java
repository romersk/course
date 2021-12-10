package com.example.client.controllers;

import com.example.client.Client;
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
import model.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

public class MainController {

    public Button enterButton;
    public Button registrationButton;
    public PasswordField password;
    public TextField login;
    public Label toHelp;
    public Label isAccountExist;

    private final String REGEX_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public void toEnter(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        boolean isFilled = true;

        if (password.getText().equals("")) {
            toHelp.setText("Введены не все данные");
            isFilled = false;
        } else {
            toHelp.setText("");
        }

        if(!login.getText().matches(REGEX_EMAIL) || login.getText().equals("")) {
            isAccountExist.setText("Введите e-mail");
            isFilled = false;
        } else {
            isAccountExist.setText("");
        }

        if (!isFilled)
            return;

        Data data = Data.getInstance();
        data.getClient().writeInt(1);
        data.getClient().writeLine(login.getText());
        data.getClient().writeLine(password.getText());
        User user = (User) data.getClient().getObject();

        if (user.getIdUser() == 0) {
            isAccountExist.setText("Такого пользователя не существует");
            return;
        } else {
            data.setUser(user);
            enterButton.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            if (Objects.equals(data.getUser().getRole(), "ADMIN")) {
                fxmlLoader.setLocation(getClass().getResource("/com/example/client/admin-page.fxml"));
            } else {
                fxmlLoader.setLocation(getClass().getResource("/com/example/client/user-page.fxml"));
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

        toHelp.setText("");
    }

    public void toRegistration(ActionEvent actionEvent) {
        registrationButton.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/client/registration.fxml"));
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

    @FXML
    public void initialize() throws UnknownHostException {
        InetAddress address = InetAddress.getByName(null);
        Data data = Data.getInstance();
        if (data.getClient() == null) {
            Client client = new Client(address, 8020);
            data.setClient(client);
            System.out.println("Connected to Server");
        }
    }
}