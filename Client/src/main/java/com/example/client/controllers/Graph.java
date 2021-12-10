package com.example.client.controllers;

import com.example.client.data.Data;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Graph {
    public DatePicker dateOne;
    public DatePicker dateTwo;
    public Label labelForText;
    public Button buttShow;
    public LineChart<String, Number> chart;

    private XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<>();

    public void toShow(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (dateOne.getValue() == null || dateTwo.getValue() == null) {
            labelForText.setText("Заполните поля");
            return;
        } else {
            labelForText.setText("");
        }

        if (dateOne.getValue().compareTo(dateTwo.getValue()) > 0) {
            labelForText.setText("Неверно введены данные");
            return;
        } else {
            labelForText.setText("");
        }

        chart.getData().clear();
        dataSeries1.getData().clear();

        Data data = Data.getInstance();
        data.getClient().writeInt(11);
        data.getClient().writeObject(dateOne.getValue());
        data.getClient().writeObject(dateTwo.getValue());
        ArrayList<Operation> list = (ArrayList<Operation>) data.getClient().getObject();

        data.getClient().writeInt(5);
        data.getClient().writeObject("Content");
        ArrayList<Content> listContent = (ArrayList<Content>) data.getClient().getObject();

        ArrayList<LocalDate> dates = new ArrayList<>();
        for (Operation obj :
                list) {
            dates.add(obj.getCreatedAt());
        }
        Set<LocalDate> uniqueList = new HashSet<LocalDate>(dates);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dataSeries1.setName("Данные за период " + dateOne.getValue().format(formatter) + " - " + dateTwo.getValue().format(formatter));
        for (LocalDate day :
                uniqueList) {
            int count = 0;
            for (Operation obj :
                    list) {
                for (Content cont :
                        listContent) {
                    if (cont.getIdOperation() == obj.getId()) {
                        count += cont.getCountOfShoes();
                    }
                }
            }
            if (count != 0) {
                XYChart.Data<String, Number> dp = new XYChart.Data<>(day.format(formatter), count);
                dataSeries1.getData().add(dp);
            }
        }

        if (!dataSeries1.getData().isEmpty()) {
            chart.getData().add(dataSeries1);
        }
    }
}
