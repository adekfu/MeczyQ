package pl.qnt.meczyq.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Typ.
 */
@Entity
@Table(name = "typ")
public class Typ implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "imie", nullable = false)
    private String imie;

    @NotNull
    @Column(name = "nazwisko", nullable = false)
    private String nazwisko;

    @NotNull
    @Column(name = "wynik_druzyna_1", nullable = false)
    private Integer wynikDruzyna1;

    @NotNull
    @Column(name = "wynik_druzyna_2", nullable = false)
    private String wynikDruzyna2;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "data_edycji")
    private LocalDate dataEdycji;

    @ManyToOne
    @NotNull
    private Mecz mecz;

    @ManyToOne
    @NotNull
    private User user;

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

    public String getWynikDruzyna2() {
        return wynikDruzyna2;
    }

    public void setWynikDruzyna2(String wynikDruzyna2) {
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

    public Mecz getMecz() {
        return mecz;
    }

    public void setMecz(Mecz mecz) {
        this.mecz = mecz;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Typ typ = (Typ) o;
        if(typ.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, typ.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Typ{" +
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
