package com.example.client.controllers;

import com.example.client.data.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class CalProfit {
    public Button buttBack;
    public TextField count;
    public TextField expenses;
    public TextField profit;
    public DatePicker dateOne;
    public DatePicker dateTwo;
    public Button buttCalc;
    public Label labelText;

    public void buttToBack(ActionEvent actionEvent) {
        buttBack.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/client/admin-page.fxml"));
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

    public void toCalculate(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (dateOne.getValue() == null || dateTwo.getValue() == null) {
            labelText.setText("Заполните поля");
            return;
        } else {
            labelText.setText("");
        }

        if (dateOne.getValue().compareTo(dateTwo.getValue()) > 0) {
            labelText.setText("Неверно введены данные");
            return;
        } else {
            labelText.setText("");
        }

        Data data = Data.getInstance();
        data.getClient().writeInt(11);
        data.getClient().writeObject(dateOne.getValue());
        data.getClient().writeObject(dateTwo.getValue());
        ArrayList<Operation> list = (ArrayList<Operation>) data.getClient().getObject();

        data.getClient().writeInt(5);
        data.getClient().writeObject("Content");
        ArrayList<Content> listContent = (ArrayList<Content>) data.getClient().getObject();
        double profitD = 0;
        double expensesD = 0;
        int countI = 0;
        for (Operation obj:
                list) {
            for (Content cont:
                    listContent) {
                if (cont.getIdOperation() == obj.getId()) {
                    data.getClient().writeInt(6);
                    data.getClient().writeObject("Shoes");
                    data.getClient().writeObject(cont.getIdShoes());
                    Shoes shoes1 = (Shoes) data.getClient().getObject();

                    profitD += (shoes1.getCosts() - shoes1.getExpenses()) * cont.getCountOfShoes();
                    expensesD += shoes1.getExpenses() * cont.getCountOfShoes();
                    countI += cont.getCountOfShoes();
                }
            }
        }
        count.setText(String.valueOf(countI));
        expenses.setText(String.valueOf(round(expensesD,2)));
        profit.setText(String.valueOf(round(profitD, 2)));
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
