package pl.qnt.meczyq.service;

import pl.qnt.meczyq.domain.Typ;
import pl.qnt.meczyq.web.rest.dto.TypDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Typ.
 */
public interface TypService {

    /**
     * Save a typ.
     * 
     * @param typDTO the entity to save
     * @return the persisted entity
     */
    TypDTO save(TypDTO typDTO);

    /**
     *  Get all the typs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Typ> findAll(Pageable pageable);

    /**
     *  Get the "id" typ.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    TypDTO findOne(Long id);

    /**
     *  Delete the "id" typ.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
