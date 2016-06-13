package pl.qnt.meczyq.web.rest.mapper;

import pl.qnt.meczyq.domain.*;
import pl.qnt.meczyq.web.rest.dto.MeczDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Mecz and its DTO MeczDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MeczMapper {

    MeczDTO meczToMeczDTO(Mecz mecz);

    List<MeczDTO> meczsToMeczDTOs(List<Mecz> meczs);

    @Mapping(target = "typies", ignore = true)
    Mecz meczDTOToMecz(MeczDTO meczDTO);

    List<Mecz> meczDTOsToMeczs(List<MeczDTO> meczDTOs);
}
