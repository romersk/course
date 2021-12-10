package com.example.client.controllers;

import com.example.client.data.Data;
import com.example.client.data.ShoesContent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CreateOperation {
    public Button buttBack;
    public ComboBox<String> comboBoxShoes;
    public TextField count;
    public DatePicker date;
    public Button butAdd;
    public ListView<String> listShoes;
    public Button buttSaveAll;
    public Button buttDelete;
    public Label labelShoes;
    public Label labelCount;
    public Label labelDate;
    public Label labelOut;

    private String NUM_REGEX = "[0-9]{1,5}";
    private ArrayList<ShoesContent> listAll = new ArrayList<>();
    private ArrayList<String> listForView = new ArrayList<>();
    private double size = 0;
    private int nowId = -1;
    private String dateValue = "";

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

    public void toAdd(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Data data = Data.getInstance();
        if (date.getValue() == null) {
            labelDate.setText("Введите дату");
            return;
        } else {
            labelDate.setText("");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dateValue = date.getValue().format(formatter);

            data.getClient().writeInt(6);
            data.getClient().writeObject("Operation");
            data.getClient().writeObject(dateValue);

            Operation operation = (Operation) data.getClient().getObject();
            if (operation.getId() > 0) {
                labelDate.setText("Отчетность за этот день имеется");
                return;
            } else {
                labelDate.setText("");
            }
        }

        if (comboBoxShoes.getValue().equals("Выберете обувь")) {
            labelShoes.setText("Выберете обувь");
            return;
        } else
        {
            labelShoes.setText("");
        }

        if (count.getText() == null || !count.getText().matches(NUM_REGEX)) {
            labelCount.setText("Введите число");
            return;
        } else {
            labelCount.setText("");
            if (Integer.parseInt(count.getText()) == 0) {
                labelCount.setText("Введите число больше 0");
            } else {
                labelCount.setText("");
            }
        }

        data.getClient().writeInt(13);
        data.getClient().writeObject(comboBoxShoes.getValue());
        Shoes shoes = (Shoes) data.getClient().getObject();
        ShoesContent shoesContent = new ShoesContent();
        shoesContent.setIdShoes(shoes.getId());
        shoesContent.setCountOfShoes(Integer.parseInt(count.getText()));
        shoesContent.setHours(shoesContent.getCountOfShoes() * shoes.getTimeManufacture());

        listForView.add(shoes.getName() + " - " + count.getText());
        listAll.add(shoesContent);
        ObservableList<String> observableList = FXCollections.observableList(listForView);
        listShoes.setItems(observableList);

        MultipleSelectionModel<String> langsSelectionModel = listShoes.getSelectionModel();
        langsSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>(){

            public void changed(ObservableValue<? extends String> changed, String oldValue, String newValue){
                nowId = langsSelectionModel.getSelectedIndex();
            }
        });
    }

    public void toSaveAll(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (!listAll.isEmpty()) {
            Data data = Data.getInstance();
            data.getClient().writeInt(6);
            data.getClient().writeObject("Person");
            data.getClient().writeObject(data.getUser().getIdPerson());
            Person person = (Person) data.getClient().getObject();
            size = 3.42 * (1 + (person.getCategory() - 1)*0.07);

            double hours = 0;
            for (ShoesContent obj:
                    listAll) {
                hours += obj.getHours();
            }
            double sizeSalary = hours * size;
            Operation operation = new Operation();
            operation.setHours(hours);
            operation.setId_users(data.getUser().getIdUser());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            operation.setCreatedAt(LocalDate.parse(dateValue, formatter));

            data.getClient().writeInt(3);
            data.getClient().writeObject("Operation");
            data.getClient().writeObject(operation);
            int idOperation = (int) data.getClient().getObject();

            for (ShoesContent obj:
                    listAll) {
                Content content = new Content();
                content.setIdOperation(idOperation);
                content.setIdShoes(obj.getIdShoes());
                content.setCountOfShoes(obj.getCountOfShoes());

                data.getClient().writeInt(3);
                data.getClient().writeObject("Content");
                data.getClient().writeObject(content);
                int idC = (int) data.getClient().getObject();
            }
            String month = dateValue.substring(5,7);
            data.getClient().writeInt(12);
            data.getClient().writeObject(data.getUser().getIdUser());
            data.getClient().writeObject(Integer.parseInt(month));
            Salary salary = (Salary) data.getClient().getObject();

            if (salary.getId() > 0) {
                salary.setSize(salary.getSize() + sizeSalary);
                salary.setCountTime(salary.getSize() + hours);

                data.getClient().writeInt(4);
                data.getClient().writeObject("Salary");
                data.getClient().writeObject(salary);
            } else {
                salary.setNumberMonth(Integer.parseInt(month));
                salary.setSize(sizeSalary);
                salary.setCountTime(hours);
                salary.setIdUser(data.getUser().getIdUser());

                data.getClient().writeInt(3);
                data.getClient().writeObject("Salary");
                data.getClient().writeObject(salary);
                int id = (int) data.getClient().getObject();
            }
            comboBoxShoes.setValue("Выберете обувь");
            listAll.clear();
            listForView.clear();
            ObservableList<String> observableList = FXCollections.observableList(listForView);
            listShoes.setItems(observableList);
            count.setText("");
            labelOut.setText("Все данные добавлены");
        } else {
            labelOut.setText("");
        }
    }

    public void toDelete(ActionEvent actionEvent) {
        if (nowId != -1) {
            listForView.remove(nowId);
            listAll.remove(nowId);
            ObservableList<String> observableList = FXCollections.observableList(listForView);
            listShoes.setItems(observableList);
        }
    }

    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        Data data = Data.getInstance();
        data.getClient().writeInt(5);
        data.getClient().writeObject("Shoes");
        ArrayList<Shoes> listOne = (ArrayList<Shoes>) data.getClient().getObject();
        ArrayList<String> nameShoes = new ArrayList<>();
        comboBoxShoes.setValue("Выберете обувь");
        for (Shoes obj:
             listOne) {
            nameShoes.add(obj.getName());
        }

        ObservableList<String> list = FXCollections.observableArrayList(nameShoes);
        comboBoxShoes.setItems(list);
    }
}
