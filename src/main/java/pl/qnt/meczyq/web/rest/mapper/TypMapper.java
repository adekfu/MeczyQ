package pl.qnt.meczyq.web.rest.mapper;

import pl.qnt.meczyq.domain.*;
import pl.qnt.meczyq.web.rest.dto.TypDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Typ and its DTO TypDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypMapper {

    @Mapping(source = "mecz.id", target = "meczId")
    TypDTO typToTypDTO(Typ typ);

    List<TypDTO> typsToTypDTOs(List<Typ> typs);

    @Mapping(source = "meczId", target = "mecz")
    Typ typDTOToTyp(TypDTO typDTO);

    List<Typ> typDTOsToTyps(List<TypDTO> typDTOs);

    default Mecz meczFromId(Long id) {
        if (id == null) {
            return null;
        }
        Mecz mecz = new Mecz();
        mecz.setId(id);
        return mecz;
    }
}
