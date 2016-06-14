package pl.qnt.meczyq.service.impl;

import pl.qnt.meczyq.domain.Typ;
import pl.qnt.meczyq.service.MeczService;
import pl.qnt.meczyq.domain.Mecz;
import pl.qnt.meczyq.repository.MeczRepository;
import pl.qnt.meczyq.web.rest.dto.MeczDTO;
import pl.qnt.meczyq.web.rest.dto.TypDTO;
import pl.qnt.meczyq.web.rest.mapper.MeczMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import pl.qnt.meczyq.web.rest.mapper.TypMapper;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Mecz.
 */
@Service
@Transactional
public class MeczServiceImpl implements MeczService {

    private final Logger log = LoggerFactory.getLogger(MeczServiceImpl.class);

    @Inject
    private MeczRepository meczRepository;

    @Inject
    private MeczMapper meczMapper;

    @Inject
    private TypMapper typMapper;

    /**
     * Save a mecz.
     *
     * @param meczDTO the entity to save
     * @return the persisted entity
     */
    public MeczDTO save(MeczDTO meczDTO) {
        log.debug("Request to save Mecz : {}", meczDTO);
        Mecz mecz = meczMapper.meczDTOToMecz(meczDTO);
        mecz = meczRepository.save(mecz);
        MeczDTO result = meczMapper.meczToMeczDTO(mecz);
        return result;
    }

    /**
     * Get all the meczs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Mecz> findAll(Pageable pageable) {
        log.debug("Request to get all Meczs");
        Page<Mecz> result = meczRepository.findAll(pageable);
        return result;
    }

    /**
     * Get one mecz by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MeczDTO findOne(Long id) {
        log.debug("Request to get Mecz : {}", id);
        Mecz mecz = meczRepository.findOne(id);
        MeczDTO meczDTO = meczMapper.meczToMeczDTO(mecz);
        createTypyMeczu(meczDTO, mecz);

        return meczDTO;
    }

    private void createTypyMeczu(MeczDTO dto, Mecz mecz) {
        dto.setTypy(new ArrayList<>());
        if (mecz != null && mecz.getTypies() != null) {
            for (Typ typ : mecz.getTypies()) {
                TypDTO typDTO = typMapper.typToTypDTO(typ);
                typDTO.setMeczNazwa(mecz.getNazwaMeczu());

            }
            dto.getTypy().addAll(typMapper.typsToTypDTOs(new ArrayList<>(mecz.getTypies())));
        }

    }

    /**
     * Delete the  mecz by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Mecz : {}", id);
        meczRepository.delete(id);
    }
}
