package model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Person implements Serializable {
    private int idPerson;
    private int idUser;
    private String firstName;
    private String lastName;
    private int category;

    public Person() {
        this.idPerson = 0;
        this.idUser = 0;
        this.firstName = "";
        this.lastName = "";
        this.category = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person that = (Person) o;

        return Objects.equals(this.idUser, that.idUser) &&
                Objects.equals(this.idPerson, that.idPerson) &&
                Objects.equals(this.firstName, that.firstName) &&
                Objects.equals(this.lastName, that.lastName) &&
                Objects.equals(this.category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idUser, this.idPerson,
                this.firstName, this.lastName, this.category);
    }

    @Override
    public String toString() {
        return "Person = {" +
                "id=" + idPerson +
                ", id_user=" + idUser +
                ", first_name=" + firstName +
                ", last_name=" + lastName +
                ", category=" + category +
                '}';
    }
}
