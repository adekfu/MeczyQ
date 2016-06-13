package pl.qnt.meczyq.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Mecz.
 */
@Entity
@Table(name = "mecz")
public class Mecz implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "druzyna_1", nullable = false)
    private String druzyna1;

    @NotNull
    @Column(name = "druzyna_2", nullable = false)
    private String druzyna2;

    @Column(name = "wynik_druzyna_1")
    private Integer wynikDruzyna1;

    @Column(name = "wynik_druzyna_2")
    private Integer wynikDruzyna2;

    @Column(name = "data_meczu")
    private LocalDate dataMeczu;

    @Column(name = "data_zamkniecia")
    private LocalDate dataZamkniecia;

    @OneToMany(mappedBy = "mecz")
    @JsonIgnore
    private Set<Typ> typies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDruzyna1() {
        return druzyna1;
    }

    public void setDruzyna1(String druzyna1) {
        this.druzyna1 = druzyna1;
    }

    public String getDruzyna2() {
        return druzyna2;
    }

    public void setDruzyna2(String druzyna2) {
        this.druzyna2 = druzyna2;
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

    public LocalDate getDataMeczu() {
        return dataMeczu;
    }

    public void setDataMeczu(LocalDate dataMeczu) {
        this.dataMeczu = dataMeczu;
    }

    public LocalDate getDataZamkniecia() {
        return dataZamkniecia;
    }

    public void setDataZamkniecia(LocalDate dataZamkniecia) {
        this.dataZamkniecia = dataZamkniecia;
    }

    public Set<Typ> getTypies() {
        return typies;
    }

    public void setTypies(Set<Typ> typs) {
        this.typies = typs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mecz mecz = (Mecz) o;
        if(mecz.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, mecz.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Mecz{" +
            "id=" + id +
            ", druzyna1='" + druzyna1 + "'" +
            ", druzyna2='" + druzyna2 + "'" +
            ", wynikDruzyna1='" + wynikDruzyna1 + "'" +
            ", wynikDruzyna2='" + wynikDruzyna2 + "'" +
            ", dataMeczu='" + dataMeczu + "'" +
            ", dataZamkniecia='" + dataZamkniecia + "'" +
            '}';
    }
}
