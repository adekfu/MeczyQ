package pl.qnt.meczyq.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.qnt.meczyq.domain.Typ;
import pl.qnt.meczyq.service.TypService;
import pl.qnt.meczyq.web.rest.util.HeaderUtil;
import pl.qnt.meczyq.web.rest.util.PaginationUtil;
import pl.qnt.meczyq.web.rest.dto.TypDTO;
import pl.qnt.meczyq.web.rest.mapper.TypMapper;
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
 * REST controller for managing Typ.
 */
@RestController
@RequestMapping("/api")
public class TypResource {

    private final Logger log = LoggerFactory.getLogger(TypResource.class);

    @Inject
    private TypService typService;

    @Inject
    private TypMapper typMapper;

    /**
     * POST  /typs : Create a new typ.
     *
     * @param typDTO the typDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typDTO, or with status 400 (Bad Request) if the typ has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/typs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TypDTO> createTyp(@Valid @RequestBody TypDTO typDTO) throws URISyntaxException {
        log.debug("REST request to save Typ : {}", typDTO);
        if (typDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typ", "idexists", "A new typ cannot already have an ID")).body(null);
        }
        TypDTO result = typService.save(typDTO);
        return ResponseEntity.created(new URI("/api/typs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typ", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /typs : Updates an existing typ.
     *
     * @param typDTO the typDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typDTO,
     * or with status 400 (Bad Request) if the typDTO is not valid,
     * or with status 500 (Internal Server Error) if the typDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/typs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TypDTO> updateTyp(@Valid @RequestBody TypDTO typDTO) throws URISyntaxException {
        log.debug("REST request to update Typ : {}", typDTO);
        if (typDTO.getId() == null) {
            return createTyp(typDTO);
        }
        TypDTO result = typService.save(typDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typ", typDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /typs : get all the typs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/typs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<TypDTO>> getAllTyps(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Typs");
        Page<Typ> page = typService.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/typs");

        List<TypDTO> dtos = typMapper.typsToTypDTOs(page.getContent());
        for (TypDTO dto : dtos) {
            for (Typ typ : page) {
                if (dto.getId().equals(typ.getId())) {
                    dto.setMeczNazwa(typ.getMecz() != null && typ.getMecz().getDruzyna1() != null && typ.getMecz().getDruzyna2() != null ?
                        typ.getMecz().getDruzyna1() + " - " + typ.getMecz().getDruzyna2() : "");
                    break;
                }
            }
        }
        return new ResponseEntity<>(dtos, headers, HttpStatus.OK);
    }

    /**
     * GET  /typs/:id : get the "id" typ.
     *
     * @param id the id of the typDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/typs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TypDTO> getTyp(@PathVariable Long id) {
        log.debug("REST request to get Typ : {}", id);
        TypDTO typDTO = typService.findOne(id);
        return Optional.ofNullable(typDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /typs/:id : delete the "id" typ.
     *
     * @param id the id of the typDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/typs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTyp(@PathVariable Long id) {
        log.debug("REST request to delete Typ : {}", id);
        typService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typ", id.toString())).build();
    }

}
