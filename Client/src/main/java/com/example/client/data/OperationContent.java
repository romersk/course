package com.example.client.data;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OperationContent {
    private int id;
    private String lastAndFirstName;
    private LocalDate createdAt;
    private double timeManufacture;
    private ListView<String> listShoes;
    private double profit;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Операция №" + id
                + "\nДата операции: " + createdAt
                + "\nИсполнитель: " + lastAndFirstName
                + "\nВремя, затраченное на производство: " + timeManufacture
                + "\nПрибыль: " + listShoes
                + "\nПроизведенная обувь: ");
        ObservableList<String> list = listShoes.getItems();
        for (String obj:
                list) {
            str.append("\n   ").append(obj);
        }
        return str.toString();
    }
}
