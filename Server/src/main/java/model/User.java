package model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class User implements Serializable {
    private int idUser;
    private int idPerson;
    private String login;
    private String password;
    private String role;

    public User() {
        this.idUser = 0;
        this.idPerson = 0;
        this.login = "";
        this.password = "";
        this.role = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User) o;

        return Objects.equals(this.idUser, that.idUser) &&
                Objects.equals(this.idPerson, that.idPerson) &&
                Objects.equals(this.login, that.login) &&
                Objects.equals(this.password, that.password) &&
                Objects.equals(this.role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idUser, this.idPerson, this.login, this.password, this.role);
    }

    @Override
    public String toString() {
        return "User = {" +
                "id=" + idUser +
                ", id_person=" + idPerson +
                ", login=" + login +
                ", password=" + password +
                ", role=" + role +
                '}';
    }
}