package com.example.client.controllers;

import com.example.client.data.Data;
import com.example.client.data.OperationContent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;

public class OperationControl {
    public Button buttBack;
    public TableView<OperationContent> table;
    public TableColumn<OperationContent, Integer> id;
    public TableColumn<OperationContent, String> lastAndFirstName;
    public TableColumn<OperationContent, LocalDate> createdAt;
    public TableColumn<OperationContent, Double> timeManufacture;
    public TableColumn<OperationContent, ListView<String>> listShoes;
    public TableColumn<OperationContent, Double> profit;
    public Button buttGraph;
    public Button buttFile;

    private DirectoryChooser directoryChooser;
    private ArrayList<OperationContent> toSaveFileList = new ArrayList<>();
    final int ROW_HEIGHT = 30;

    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        directoryChooser = new DirectoryChooser();
        configuringDirectoryChooser(directoryChooser);
        id.setCellValueFactory(new PropertyValueFactory<OperationContent, Integer>("id"));
        lastAndFirstName.setCellValueFactory(new PropertyValueFactory<OperationContent, String>("lastAndFirstName"));
        createdAt.setCellValueFactory(new PropertyValueFactory<OperationContent, LocalDate>("createdAt"));
        timeManufacture.setCellValueFactory(new PropertyValueFactory<OperationContent, Double>("timeManufacture"));
        listShoes.setCellValueFactory(new PropertyValueFactory<OperationContent, ListView<String>>("listShoes"));
        profit.setCellValueFactory(new PropertyValueFactory<OperationContent, Double>("profit"));
        refreshTable();
    }

    public void toBack(ActionEvent actionEvent) {
        buttBack.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Data data = Data.getInstance();
        if (data.getUser().equals("ADMIN")) {
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

    public void toGraph(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/client/graph.fxml"));
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

    public void toFile(ActionEvent actionEvent) {
        File dir = directoryChooser.showDialog(buttFile.getScene().getWindow());
        if (dir != null) {
            String way = dir.getAbsolutePath() + "/operations.txt";
            try (FileWriter writer = new FileWriter(way, false)) {
                for (OperationContent obj :
                        toSaveFileList) {
                    writer.write(obj.toString());
                    writer.append('\n');
                }
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void refreshTable() throws IOException, ClassNotFoundException {
        Data data = Data.getInstance();
        data.getClient().writeInt(5);
        data.getClient().writeObject("Operation");
        ArrayList<Operation> listOne = (ArrayList<Operation>) data.getClient().getObject();
        data.getClient().writeInt(5);
        data.getClient().writeObject("Content");
        ArrayList<Content> listContent = (ArrayList<Content>) data.getClient().getObject();
        ArrayList<OperationContent> forTable = new ArrayList<>();
        for (Operation obj:
             listOne) {
            if (obj.getId_users() == data.getUser().getIdUser()) {
                getFromDB(listContent, forTable, obj);
            } else  if (data.getUser().getRole().equals("ADMIN")){
                getFromDB(listContent, forTable, obj);
            }

        }
        toSaveFileList = forTable;
        ObservableList<OperationContent> arrayList = FXCollections.observableArrayList(forTable);
        table.setItems(arrayList);
    }

    private void getFromDB(ArrayList<Content> listContent, ArrayList<OperationContent> forTable, Operation obj) throws IOException, ClassNotFoundException {
        Data data = Data.getInstance();
        OperationContent operationContent = new OperationContent();
        operationContent.setId(obj.getId());
        operationContent.setCreatedAt(obj.getCreatedAt());
        operationContent.setTimeManufacture(obj.getHours());

        data.getClient().writeInt(6);
        data.getClient().writeObject("User");
        data.getClient().writeObject(obj.getId_users());
        User user = (User) data.getClient().getObject();
        data.getClient().writeInt(6);
        data.getClient().writeObject("Person");
        data.getClient().writeObject(user.getIdPerson());
        Person person = (Person) data.getClient().getObject();
        operationContent.setLastAndFirstName(person.getLastName() + " " + person.getFirstName());

        double profit = 0;
        double time = 0;
        ArrayList<String> shoes = new ArrayList<>();
        for (Content cont:
                listContent) {
            if (cont.getIdOperation() == obj.getId()) {
                data.getClient().writeInt(6);
                data.getClient().writeObject("Shoes");
                data.getClient().writeObject(cont.getIdShoes());
                Shoes shoes1 = (Shoes) data.getClient().getObject();

                profit += (shoes1.getCosts() - shoes1.getExpenses()) * cont.getCountOfShoes();
                time += shoes1.getTimeManufacture() * cont.getCountOfShoes();
                shoes.add(shoes1.getName() + " - " + cont.getCountOfShoes() + "ед.");
            }
        }
        ObservableList<String> listForView = FXCollections.observableArrayList(shoes);
        operationContent.setProfit(round(profit, 2));
        ListView<String> listView = new ListView<>(listForView);
        listView.setPrefHeight(listForView.size() * ROW_HEIGHT + 2);
        operationContent.setListShoes(listView);
        operationContent.setTimeManufacture(round(time, 2));
        forTable.add(operationContent);
    }

    private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
        directoryChooser.setTitle("Выберите путь расположения файла");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
