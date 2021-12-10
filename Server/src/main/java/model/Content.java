package model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Content implements Serializable {
    private int id;
    private int idShoes;
    private int idOperation;
    private int countOfShoes;

    public Content() {
        this.id = 0;
        this.idOperation = 0;
        this.idShoes = 0;
        this.countOfShoes = 0;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Content that = (Content) o;

        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.idOperation, that.idOperation) &&
                Objects.equals(this.idShoes, that.idShoes);
    }

    @Override
    public String toString() {
        return "Content = {" +
                "id=" + id +
                ", id_operation=" + idOperation +
                ", id_shoes=" + idShoes +
                ", count_of_shoes=" + countOfShoes +
                '}';
    }
}
