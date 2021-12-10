package com.example.client.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPerson {

    private int id;
    private String login;
    private String password;
    private int idPerson;
    private String lastName;
    private String firstName;
    private int category;

    @Override
    public String toString() {
        return "User { id=" + id +
                ", login=" + login +
                ", password=" + password +
                ", lastName=" + lastName +
                ", firstName=" + firstName +
                ", category=" + category +
                " }";
    }
}
