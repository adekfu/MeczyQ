package pl.qnt.meczyq.web.rest.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Typ entity.
 */
public class TypDTO implements Serializable {

    private Long id;

    @NotNull
    private String imie;

    @NotNull
    private String nazwisko;

    @NotNull
    private Integer wynikDruzyna1;

    @NotNull
    private Integer wynikDruzyna2;


    private LocalDate data;

    private LocalDate dataEdycji;


    private Long meczId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }
    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    public Integer getWynikDruzyna1() {
        return wynikDruzyna1;
    }

    public void setWynikDruzyna1(Integer wynikDruzyna1) {
        this.wynikDruzyna1 = wynikDruzyna1;
    }

    public Integer getWynikDruzyna2() {
        return wynikDruzyna2;
    }

    public void setWynikDruzyna2(Integer wynikDruzyna2) {
        this.wynikDruzyna2 = wynikDruzyna2;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
    public LocalDate getDataEdycji() {
        return dataEdycji;
    }

    public void setDataEdycji(LocalDate dataEdycji) {
        this.dataEdycji = dataEdycji;
    }

    public Long getMeczId() {
        return meczId;
    }

    public void setMeczId(Long meczId) {
        this.meczId = meczId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypDTO typDTO = (TypDTO) o;

        if ( ! Objects.equals(id, typDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TypDTO{" +
            "id=" + id +
            ", imie='" + imie + "'" +
            ", nazwisko='" + nazwisko + "'" +
            ", wynikDruzyna1='" + wynikDruzyna1 + "'" +
            ", wynikDruzyna2='" + wynikDruzyna2 + "'" +
            ", data='" + data + "'" +
            ", dataEdycji='" + dataEdycji + "'" +
            '}';
    }
}
