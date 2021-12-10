package com.example.client.controllers;

import com.example.client.data.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Salary;

import java.io.IOException;

public class SalaryCount {
    public Button buttBack;
    public TextField month;
    public Button buttShow;
    public TextField countTime;
    public TextField countSalary;
    public Label labelText;

    private String NUM_TEXT = "[0-9]{1,2}";

    public void toBack(ActionEvent actionEvent) {
        buttBack.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/client/user-page.fxml"));
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

    public void toShow(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (month.getText() == null || !month.getText().matches(NUM_TEXT)) {
            labelText.setText("Введите число");
            return;
        } else
        {
            labelText.setText("");
        }

        if (Integer.parseInt(month.getText()) > 12) {
            labelText.setText("Значение от 1 до 12");
            return;
        } else
            labelText.setText("");

        Data data = Data.getInstance();
        data.getClient().writeInt(12);
        data.getClient().writeObject(data.getUser().getIdUser());
        data.getClient().writeObject(Integer.parseInt(month.getText()));
        Salary salary = (Salary) data.getClient().getObject();

        countTime.setText(String.valueOf(salary.getCountTime()));
        countSalary.setText(String.valueOf(salary.getSize()));
    }
}
