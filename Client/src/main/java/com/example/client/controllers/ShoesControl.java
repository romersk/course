package com.example.client.controllers;

import com.example.client.data.Data;
import com.example.client.data.UserPerson;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Shoes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ShoesControl {
    public Button buttBack;
    public Button buttAdd;
    public Button buttEdit;
    public Button buttDelete;
    public TableView<Shoes> table;
    public TableColumn<Shoes, String> columnName;
    public TableColumn<Shoes, Double> columnExpenses;
    public TableColumn<Shoes, Double> columnCosts;
    public TableColumn<Shoes, Double> columnTime;
    public ComboBox<String> combBx;
    public TextField name;
    public TextField expenses;
    public TextField costs;
    public Label labelName;
    public Label labelGenre;
    public Label labelCount;
    public TextField time;
    public Label labelTime;
    public Button buttFile;

    private int nowId = 0;
    private DirectoryChooser directoryChooser;
    private final String NUMBER_REGEX = "[0-9]{1,4}?[.]?[0-9]{1,2}";
    private ArrayList<Shoes> toSaveFileList = new ArrayList<>();

    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        directoryChooser = new DirectoryChooser();
        configuringDirectoryChooser(directoryChooser);
        columnName.setCellValueFactory(new PropertyValueFactory<Shoes, String>("name"));
        columnExpenses.setCellValueFactory(new PropertyValueFactory<Shoes, Double>("expenses"));
        columnCosts.setCellValueFactory(new PropertyValueFactory<Shoes, Double>("costs"));
        columnTime.setCellValueFactory(new PropertyValueFactory<Shoes, Double>("timeManufacture"));
        refreshTable();

        ObservableList<String> langs = FXCollections.observableArrayList("Все","0-50р", "50-100р", "100-200р", "200р+");
        combBx.setItems(langs);
        combBx.setValue("Все");

        combBx.setOnAction(event -> {
            try {
                filterByCosts(combBx.getValue());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        TableView.TableViewSelectionModel<Shoes> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Shoes>() {

            @Override
            public void changed(ObservableValue<? extends Shoes> observableValue, Shoes shop, Shoes t1) {
                if (t1 != null) {
                    nowId = t1.getId();
                    name.setText(t1.getName());
                    expenses.setText(String.valueOf(t1.getExpenses()));
                    costs.setText(String.valueOf(t1.getCosts()));
                    time.setText(String.valueOf(t1.getTimeManufacture()));
                }
            }
        });
    }

    private void filterByCosts(String value) throws IOException, ClassNotFoundException {
        if (!value.equals("")) {
            Data data = Data.getInstance();
            switch (value) {
                case "0-50р":
                {
                    data.getClient().writeInt(8);
                    data.getClient().writeObject("Shoes");
                    data.getClient().writeObject(0.0);
                    data.getClient().writeObject(50.0);
                    break;
                }
                case "50-100р":
                {
                    data.getClient().writeInt(8);
                    data.getClient().writeObject("Shoes");
                    data.getClient().writeObject(50.0);
                    data.getClient().writeObject(100.0);
                    break;
                }
                case "100-200р":
                {
                    data.getClient().writeInt(8);
                    data.getClient().writeObject("Shoes");
                    data.getClient().writeObject(100.0);
                    data.getClient().writeObject(200.0);
                    break;
                }
                case "200р+":
                {
                    data.getClient().writeInt(8);
                    data.getClient().writeObject("Shoes");
                    data.getClient().writeObject(200.0);
                    data.getClient().writeObject(100000.0);
                    break;
                }
                default:
                {
                    data.getClient().writeInt(5);
                    data.getClient().writeObject("Shoes");
                    break;
                }
            }
            ArrayList<Shoes> list = (ArrayList<Shoes>) data.getClient().getObject();
            ArrayList<Shoes> forTable = new ArrayList<>();
            for (Shoes obj:
                    list) {
                if (obj.getDeleteAt() == null) {
                    forTable.add(obj);
                }
            }

            ObservableList<Shoes> arrayList = FXCollections.observableArrayList(forTable);

            table.setItems(arrayList);
            nowId = 0;
        } else {
            refreshTable();
        }
    }

    public void toBack(ActionEvent actionEvent) {
        Data data = Data.getInstance();
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

    public void toAdd(ActionEvent actionEvent) {
        buttAdd.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/client/add-shoes.fxml"));
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

    public void toEdit(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (nowId != 0) {
            if (isFilled()) {
                Shoes shoes = new Shoes();
                shoes.setId(nowId);
                shoes.setName(name.getText());
                shoes.setExpenses(Double.parseDouble(expenses.getText()));
                shoes.setCosts(Double.parseDouble(costs.getText()));
                shoes.setTimeManufacture(Double.parseDouble(time.getText()));

                Data data = Data.getInstance();
                data.getClient().writeInt(4);
                data.getClient().writeObject("Shoes");
                data.getClient().writeObject(shoes);

                initialize();
                name.setText("");
                expenses.setText("");
                costs.setText("");
                time.setText("");
            }
        }
    }

    public void toDelete(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (nowId != 0) {
            Data data = Data.getInstance();
            data.getClient().writeInt(7);
            data.getClient().writeObject("Shoes");
            data.getClient().writeObject(nowId);

            refreshTable();
        }
    }

    private void refreshTable() throws IOException, ClassNotFoundException {
        Data data = Data.getInstance();
        data.getClient().writeInt(5);
        data.getClient().writeObject("Shoes");
        ArrayList<Shoes> listOne = (ArrayList<Shoes>) data.getClient().getObject();
        ArrayList<Shoes> forTable = new ArrayList<>();
        for (Shoes obj:
             listOne) {
            if (obj.getDeleteAt() == null) {
                forTable.add(obj);
            }
        }
        toSaveFileList = forTable;
        ObservableList<Shoes> arrayList = FXCollections.observableArrayList(forTable);

        table.setItems(arrayList);
        nowId = 0;
    }

    private boolean isFilled() {
        boolean answer = true;
        if (name.getText().equals("")) {
            labelName.setText("Заполните поле корректно");
            answer = false;
        } else {
            labelName.setText("");
        }
        if (expenses.getText().equals("") || !expenses.getText().matches(NUMBER_REGEX)) {
            labelGenre.setText("Заполните поле корректно");
            answer = false;
        } else {
            labelGenre.setText("");
        }
        if (costs.getText().equals("") || !costs.getText().matches(NUMBER_REGEX)) {
            labelCount.setText("Заполните поле корректно");
            answer = false;
        } else {
            labelCount.setText("");
        }
        if (time.getText().equals("") || !time.getText().matches(NUMBER_REGEX)) {
            labelTime.setText("Заполните поле корректно");
            answer = false;
        } else {
            labelTime.setText("");
        }
        return answer;
    }

    public void toFile(ActionEvent actionEvent) {
        File dir = directoryChooser.showDialog(buttFile.getScene().getWindow());
        if (dir != null) {
            String way = dir.getAbsolutePath() + "/shoes.txt";
            try (FileWriter writer = new FileWriter(way, false)) {
                for (Shoes obj :
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

    private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
        directoryChooser.setTitle("Выберите путь расположения файла");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }
}
