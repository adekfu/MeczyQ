package pl.qnt.meczyq.web.rest.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Mecz entity.
 */
public class MeczDTO implements Serializable {

    private Long id;

    @NotNull
    private String druzyna1;

    @NotNull
    private String druzyna2;

    private Integer wynikDruzyna1;

    private Integer wynikDruzyna2;

    private LocalDate dataMeczu;

    private LocalDate dataZamkniecia;

    private List<TypDTO> typy;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MeczDTO meczDTO = (MeczDTO) o;

        if ( ! Objects.equals(id, meczDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MeczDTO{" +
            "id=" + id +
            ", druzyna1='" + druzyna1 + "'" +
            ", druzyna2='" + druzyna2 + "'" +
            ", wynikDruzyna1='" + wynikDruzyna1 + "'" +
            ", wynikDruzyna2='" + wynikDruzyna2 + "'" +
            ", dataMeczu='" + dataMeczu + "'" +
            ", dataZamkniecia='" + dataZamkniecia + "'" +
            '}';
    }

    public List<TypDTO> getTypy() {
        return typy;
    }

    public void setTypy(List<TypDTO> typy) {
        this.typy = typy;
    }
}
