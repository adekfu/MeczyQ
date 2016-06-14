package pl.qnt.meczyq.service.impl;

import pl.qnt.meczyq.domain.Mecz;
import pl.qnt.meczyq.domain.User;
import pl.qnt.meczyq.repository.UserRepository;
import pl.qnt.meczyq.security.SecurityUtils;
import pl.qnt.meczyq.service.TypService;
import pl.qnt.meczyq.domain.Typ;
import pl.qnt.meczyq.repository.TypRepository;
import pl.qnt.meczyq.web.rest.UserResource;
import pl.qnt.meczyq.web.rest.dto.TypDTO;
import pl.qnt.meczyq.web.rest.mapper.TypMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Typ.
 */
@Service
@Transactional
public class TypServiceImpl implements TypService {

    private final Logger log = LoggerFactory.getLogger(TypServiceImpl.class);

    @Inject
    private TypRepository typRepository;

    @Inject
    private TypMapper typMapper;

    @Inject
    private UserRepository userRepository;

    /**
     * Save a typ.
     *
     * @param typDTO the entity to save
     * @return the persisted entity
     */
    public TypDTO save(TypDTO typDTO) {
        log.debug("Request to save Typ : {}", typDTO);
        Typ typ = typMapper.typDTOToTyp(typDTO);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> userByLogin = userRepository.findOneByLogin(currentUserLogin);
        if (userByLogin.isPresent()) {
            typ.setUser(userByLogin.get());
            typ.setImie(userByLogin.get().getFirstName());
            typ.setNazwisko(userByLogin.get().getLastName());
        }
        if (typ.getId() == null) {
            typ.setData(Calendar.getInstance().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        } else {
            typ.setDataEdycji(Calendar.getInstance().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }

        typ = typRepository.save(typ);
        TypDTO result = typMapper.typToTypDTO(typ);
        return result;
    }

    /**
     * Get all the typs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Typ> findAll(Pageable pageable) {
        log.debug("Request to get all Typs");
        Page<Typ> result = typRepository.findAll(pageable);
        for (Typ typ : result.getContent()) {
            typ.setMeczNazwa(typ.getMecz() != null ? typ.getMecz().getNazwaMeczu() : "");
        }
        return result;
    }

    /**
     * Get one typ by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TypDTO findOne(Long id) {
        log.debug("Request to get Typ : {}", id);
        Typ typ = typRepository.findOne(id);
        TypDTO typDTO = typMapper.typToTypDTO(typ);
        typDTO.setMeczNazwa(typ.getMecz() != null ? typ.getMecz().getNazwaMeczu() : "");
        return typDTO;
    }

    /**
     * Delete the  typ by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Typ : {}", id);
        typRepository.delete(id);
    }

    public void stworzWyniki() {
        Map<Long, Integer> mapaWynikow = new HashMap<>();
        List<Typ> typy = typRepository.znajdzWszystkieZWynikiem();

        for (Typ typ : typy) {
            Mecz mecz = typ.getMecz();
            if (typ.getUser() != null && mecz != null && mecz.getWynikDruzyna1() != null && mecz.getWynikDruzyna2() != null &&
                typ.getWynikDruzyna1() != null && typ.getWynikDruzyna2() != null) {

                Integer wynik = obliczWynik(mecz.getWynikDruzyna1(), mecz.getWynikDruzyna2(), typ.getWynikDruzyna1(), typ.getWynikDruzyna2());

                if (mapaWynikow.containsKey(typ.getUser().getId())) {
                    mapaWynikow.put(typ.getUser().getId(), mapaWynikow.get(typ.getUser().getId()) + wynik);
                } else {
                    mapaWynikow.put(typ.getUser().getId(), wynik);
                }

            }
        }
    }

    private Integer obliczWynik(Integer wynik1, Integer wynik2, Integer typWynik1, Integer typWynik2) {
        boolean typRemis = typWynik1.equals(typWynik2);
        boolean typWygrana1 = typWynik1 > typWynik2;

        boolean wynikRemis = wynik1.equals(wynik2);
        boolean wynikWygrana1 = wynik1 > wynik2;

        boolean trafionyWynik1 = wynik1.equals(typWynik1);
        boolean trafionyWynik2 = wynik2.equals(typWynik2);

        if (wynikRemis && typRemis) {
            if (trafionyWynik1 && trafionyWynik2) {
                return 3;
            } else
                return 1;
        } else if (wynikWygrana1 && typWygrana1) {
            if (trafionyWynik1 && trafionyWynik2) {
                return 3;
            } else
                return 1;
        } else if (!wynikWygrana1 && !typWygrana1) {
            if (trafionyWynik1 && trafionyWynik2) {
                return 3;
            } else
                return 1;
        } else {
            return 0;
        }
    }
}
