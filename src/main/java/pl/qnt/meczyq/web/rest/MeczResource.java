package pl.qnt.meczyq.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.qnt.meczyq.domain.Mecz;
import pl.qnt.meczyq.service.MeczService;
import pl.qnt.meczyq.web.rest.util.HeaderUtil;
import pl.qnt.meczyq.web.rest.util.PaginationUtil;
import pl.qnt.meczyq.web.rest.dto.MeczDTO;
import pl.qnt.meczyq.web.rest.mapper.MeczMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Mecz.
 */
@RestController
@RequestMapping("/api")
public class MeczResource {

    private final Logger log = LoggerFactory.getLogger(MeczResource.class);
        
    @Inject
    private MeczService meczService;
    
    @Inject
    private MeczMapper meczMapper;
    
    /**
     * POST  /meczs : Create a new mecz.
     *
     * @param meczDTO the meczDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new meczDTO, or with status 400 (Bad Request) if the mecz has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/meczs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MeczDTO> createMecz(@Valid @RequestBody MeczDTO meczDTO) throws URISyntaxException {
        log.debug("REST request to save Mecz : {}", meczDTO);
        if (meczDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("mecz", "idexists", "A new mecz cannot already have an ID")).body(null);
        }
        MeczDTO result = meczService.save(meczDTO);
        return ResponseEntity.created(new URI("/api/meczs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mecz", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /meczs : Updates an existing mecz.
     *
     * @param meczDTO the meczDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated meczDTO,
     * or with status 400 (Bad Request) if the meczDTO is not valid,
     * or with status 500 (Internal Server Error) if the meczDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/meczs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MeczDTO> updateMecz(@Valid @RequestBody MeczDTO meczDTO) throws URISyntaxException {
        log.debug("REST request to update Mecz : {}", meczDTO);
        if (meczDTO.getId() == null) {
            return createMecz(meczDTO);
        }
        MeczDTO result = meczService.save(meczDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mecz", meczDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /meczs : get all the meczs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of meczs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/meczs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<MeczDTO>> getAllMeczs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Meczs");
        Page<Mecz> page = meczService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/meczs");
        return new ResponseEntity<>(meczMapper.meczsToMeczDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /meczs/:id : get the "id" mecz.
     *
     * @param id the id of the meczDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the meczDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/meczs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MeczDTO> getMecz(@PathVariable Long id) {
        log.debug("REST request to get Mecz : {}", id);
        MeczDTO meczDTO = meczService.findOne(id);
        return Optional.ofNullable(meczDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /meczs/:id : delete the "id" mecz.
     *
     * @param id the id of the meczDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/meczs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMecz(@PathVariable Long id) {
        log.debug("REST request to delete Mecz : {}", id);
        meczService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mecz", id.toString())).build();
    }

}
