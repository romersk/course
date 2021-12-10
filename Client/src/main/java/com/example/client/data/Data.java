package com.example.client.data;

import com.example.client.Client;
import model.Operation;
import model.User;

import java.util.ArrayList;

public class Data {

    private User user;
    private User editUser;
    private Client client;

    private final static Data instance = new Data();

    private Data() {}

    public static Data getInstance() {
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getEditUser() {
        return editUser;
    }

    public void setEditUser(User editUser) {
        this.editUser = editUser;
    }
}
