package model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Salary implements Serializable {

    private int id;
    private int idUser;
    private int numberMonth;
    private double countTime;
    private double size;

    public Salary() {
        this.id = 0;
        this.countTime = 0;
        this.size = 0;
        this.idUser = 0;
        this.numberMonth = 0;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Salary that = (Salary) o;

        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.size, that.size) &&
                Objects.equals(this.countTime, that.countTime);
    }

    @Override
    public String toString() {
        return "Salary = {" +
                "id=" + id +
                ", count_time=" + countTime +
                ", size=" + size +
                '}';
    }
}
