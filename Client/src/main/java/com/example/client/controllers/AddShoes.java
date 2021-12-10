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
import model.Shoes;

import java.io.IOException;

public class AddShoes {
    public Button buttToBack;
    public TextField name;
    public TextField expenses;
    public Button buttToSave;
    public Label labelName;
    public Label labelGenre;
    public Label labelCount;
    public TextField time;
    public Label labelTime;

    private final String NUMBER_REGEX = "[0-9]{1,4}?[.]?[0-9]{1,2}";
    public TextField costs;

    public void toBack(ActionEvent actionEvent) {
        Data data = Data.getInstance();
        buttToBack.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/client/shoes-control.fxml"));
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
            Shoes shoes = new Shoes();
            shoes.setName(name.getText());
            shoes.setExpenses(Double.parseDouble(expenses.getText()));
            shoes.setCosts(Double.parseDouble(costs.getText()));
            shoes.setTimeManufacture(Double.parseDouble(time.getText()));

            Data data = Data.getInstance();
            data.getClient().writeInt(3);
            data.getClient().writeObject("Shoes");
            data.getClient().writeObject(shoes);
            int id = (int) data.getClient().getObject();

            buttToSave.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/client/shoes-control.fxml"));
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

    private boolean isFilled() {
        boolean answer = true;
        if (name.getText().equals("") ) {
            labelName.setText("Заполните поле корректно");
            answer = false;
        } else {
            labelName.setText("");
        }
        if (costs.getText().equals("") || !costs.getText().matches(NUMBER_REGEX)) {
            labelCount.setText("Заполните поле корректно");
            answer = false;
        } else {
            labelCount.setText("");
        }
        if (expenses.getText().equals("") || !expenses.getText().matches(NUMBER_REGEX)) {
            labelGenre.setText("Заполните поле корректно");
            answer = false;
        } else {
            labelGenre.setText("");
        }
        if (time.getText().equals("") || !time.getText().matches(NUMBER_REGEX)) {
            labelTime.setText("Заполните поле корректно");
            answer = false;
        } else {
            labelTime.setText("");
        }
        return answer;
    }
}
