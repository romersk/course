package model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class Shoes implements Serializable {

    private int id;
    private String name;
    private double expenses;
    private double costs;
    private double timeManufacture;
    private LocalDateTime deleteAt;

    public Shoes() {
        this.id = 0;
        this.name = "";
        this.expenses = 0;
        this.timeManufacture = 0;
        this.deleteAt = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shoes that = (Shoes) o;

        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.expenses, that.expenses) &&
                Objects.equals(this.costs, that.costs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.expenses, this.costs);
    }

    @Override
    public String toString() {
        return "Shoes = {" +
                "id=" + id +
                ", name=" + name +
                ", expenses=" + expenses +
                ", costs=" + costs +
                ", time=" + timeManufacture +
                '}';
    }
}
