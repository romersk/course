package model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
public class Operation implements Serializable {

    private int id;
    private int id_users;
    private LocalDate createdAt;
    private double hours;

    public Operation() {
        this.id = 0;
        this.id_users = 0;
        this.createdAt = LocalDate.now();
        this.hours = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation that = (Operation) o;

        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.id_users, that.id_users) &&
                Objects.equals(this.createdAt, that.createdAt) &&
                Objects.equals(this.hours, that.hours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.id_users, this.createdAt, this.hours);
    }

    @Override
    public String toString() {
        return "Operation = {" +
                "id=" + id +
                ", id_user=" + id_users +
                ", created_at=" + createdAt +
                ", hours=" + hours +
                '}';
    }
}
