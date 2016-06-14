package pl.qnt.meczyq.service.impl;

import pl.qnt.meczyq.service.TypService;
import pl.qnt.meczyq.domain.Typ;
import pl.qnt.meczyq.repository.TypRepository;
import pl.qnt.meczyq.web.rest.dto.TypDTO;
import pl.qnt.meczyq.web.rest.mapper.TypMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Typ.
 */
@Service
@Transactional
public class TypServiceImpl implements TypService{

    private final Logger log = LoggerFactory.getLogger(TypServiceImpl.class);
    
    @Inject
    private TypRepository typRepository;
    
    @Inject
    private TypMapper typMapper;
    
    /**
     * Save a typ.
     * 
     * @param typDTO the entity to save
     * @return the persisted entity
     */
    public TypDTO save(TypDTO typDTO) {
        log.debug("Request to save Typ : {}", typDTO);
        Typ typ = typMapper.typDTOToTyp(typDTO);
        typ = typRepository.save(typ);
        TypDTO result = typMapper.typToTypDTO(typ);
        return result;
    }

    /**
     *  Get all the typs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Typ> findAll(Pageable pageable) {
        log.debug("Request to get all Typs");
        Page<Typ> result = typRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one typ by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypDTO findOne(Long id) {
        log.debug("Request to get Typ : {}", id);
        Typ typ = typRepository.findOne(id);
        TypDTO typDTO = typMapper.typToTypDTO(typ);
        return typDTO;
    }

    /**
     *  Delete the  typ by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Typ : {}", id);
        typRepository.delete(id);
    }
}
