package pl.qnt.meczyq.service;

import pl.qnt.meczyq.domain.Mecz;
import pl.qnt.meczyq.web.rest.dto.MeczDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Mecz.
 */
public interface MeczService {

    /**
     * Save a mecz.
     * 
     * @param meczDTO the entity to save
     * @return the persisted entity
     */
    MeczDTO save(MeczDTO meczDTO);

    /**
     *  Get all the meczs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Mecz> findAll(Pageable pageable);

    /**
     *  Get the "id" mecz.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    MeczDTO findOne(Long id);

    /**
     *  Delete the "id" mecz.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
