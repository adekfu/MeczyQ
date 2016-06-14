package pl.qnt.meczyq.web.rest;

import pl.qnt.meczyq.MeczyQApp;
import pl.qnt.meczyq.domain.Typ;
import pl.qnt.meczyq.repository.TypRepository;
import pl.qnt.meczyq.service.TypService;
import pl.qnt.meczyq.web.rest.dto.TypDTO;
import pl.qnt.meczyq.web.rest.mapper.TypMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TypResource REST controller.
 *
 * @see TypResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MeczyQApp.class)
@WebAppConfiguration
@IntegrationTest
public class TypResourceIntTest {

    private static final String DEFAULT_IMIE = "AAAAA";
    private static final String UPDATED_IMIE = "BBBBB";
    private static final String DEFAULT_NAZWISKO = "AAAAA";
    private static final String UPDATED_NAZWISKO = "BBBBB";

    private static final Integer DEFAULT_WYNIK_DRUZYNA_1 = 1;
    private static final Integer UPDATED_WYNIK_DRUZYNA_1 = 2;
    private static final String DEFAULT_WYNIK_DRUZYNA_2 = "AAAAA";
    private static final String UPDATED_WYNIK_DRUZYNA_2 = "BBBBB";

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_EDYCJI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_EDYCJI = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private TypRepository typRepository;

    @Inject
    private TypMapper typMapper;

    @Inject
    private TypService typService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTypMockMvc;

    private Typ typ;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TypResource typResource = new TypResource();
        ReflectionTestUtils.setField(typResource, "typService", typService);
        ReflectionTestUtils.setField(typResource, "typMapper", typMapper);
        this.restTypMockMvc = MockMvcBuilders.standaloneSetup(typResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        typ = new Typ();
        typ.setImie(DEFAULT_IMIE);
        typ.setNazwisko(DEFAULT_NAZWISKO);
        typ.setWynikDruzyna1(DEFAULT_WYNIK_DRUZYNA_1);
        typ.setWynikDruzyna2(DEFAULT_WYNIK_DRUZYNA_2);
        typ.setData(DEFAULT_DATA);
        typ.setDataEdycji(DEFAULT_DATA_EDYCJI);
    }

    @Test
    @Transactional
    public void createTyp() throws Exception {
        int databaseSizeBeforeCreate = typRepository.findAll().size();

        // Create the Typ
        TypDTO typDTO = typMapper.typToTypDTO(typ);

        restTypMockMvc.perform(post("/api/typs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(typDTO)))
                .andExpect(status().isCreated());

        // Validate the Typ in the database
        List<Typ> typs = typRepository.findAll();
        assertThat(typs).hasSize(databaseSizeBeforeCreate + 1);
        Typ testTyp = typs.get(typs.size() - 1);
        assertThat(testTyp.getImie()).isEqualTo(DEFAULT_IMIE);
        assertThat(testTyp.getNazwisko()).isEqualTo(DEFAULT_NAZWISKO);
        assertThat(testTyp.getWynikDruzyna1()).isEqualTo(DEFAULT_WYNIK_DRUZYNA_1);
        assertThat(testTyp.getWynikDruzyna2()).isEqualTo(DEFAULT_WYNIK_DRUZYNA_2);
        assertThat(testTyp.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testTyp.getDataEdycji()).isEqualTo(DEFAULT_DATA_EDYCJI);
    }

    @Test
    @Transactional
    public void checkImieIsRequired() throws Exception {
        int databaseSizeBeforeTest = typRepository.findAll().size();
        // set the field null
        typ.setImie(null);

        // Create the Typ, which fails.
        TypDTO typDTO = typMapper.typToTypDTO(typ);

        restTypMockMvc.perform(post("/api/typs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(typDTO)))
                .andExpect(status().isBadRequest());

        List<Typ> typs = typRepository.findAll();
        assertThat(typs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNazwiskoIsRequired() throws Exception {
        int databaseSizeBeforeTest = typRepository.findAll().size();
        // set the field null
        typ.setNazwisko(null);

        // Create the Typ, which fails.
        TypDTO typDTO = typMapper.typToTypDTO(typ);

        restTypMockMvc.perform(post("/api/typs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(typDTO)))
                .andExpect(status().isBadRequest());

        List<Typ> typs = typRepository.findAll();
        assertThat(typs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWynikDruzyna1IsRequired() throws Exception {
        int databaseSizeBeforeTest = typRepository.findAll().size();
        // set the field null
        typ.setWynikDruzyna1(null);

        // Create the Typ, which fails.
        TypDTO typDTO = typMapper.typToTypDTO(typ);

        restTypMockMvc.perform(post("/api/typs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(typDTO)))
                .andExpect(status().isBadRequest());

        List<Typ> typs = typRepository.findAll();
        assertThat(typs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWynikDruzyna2IsRequired() throws Exception {
        int databaseSizeBeforeTest = typRepository.findAll().size();
        // set the field null
        typ.setWynikDruzyna2(null);

        // Create the Typ, which fails.
        TypDTO typDTO = typMapper.typToTypDTO(typ);

        restTypMockMvc.perform(post("/api/typs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(typDTO)))
                .andExpect(status().isBadRequest());

        List<Typ> typs = typRepository.findAll();
        assertThat(typs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = typRepository.findAll().size();
        // set the field null
        typ.setData(null);

        // Create the Typ, which fails.
        TypDTO typDTO = typMapper.typToTypDTO(typ);

        restTypMockMvc.perform(post("/api/typs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(typDTO)))
                .andExpect(status().isBadRequest());

        List<Typ> typs = typRepository.findAll();
        assertThat(typs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTyps() throws Exception {
        // Initialize the database
        typRepository.saveAndFlush(typ);

        // Get all the typs
        restTypMockMvc.perform(get("/api/typs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(typ.getId().intValue())))
                .andExpect(jsonPath("$.[*].imie").value(hasItem(DEFAULT_IMIE.toString())))
                .andExpect(jsonPath("$.[*].nazwisko").value(hasItem(DEFAULT_NAZWISKO.toString())))
                .andExpect(jsonPath("$.[*].wynikDruzyna1").value(hasItem(DEFAULT_WYNIK_DRUZYNA_1)))
                .andExpect(jsonPath("$.[*].wynikDruzyna2").value(hasItem(DEFAULT_WYNIK_DRUZYNA_2.toString())))
                .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
                .andExpect(jsonPath("$.[*].dataEdycji").value(hasItem(DEFAULT_DATA_EDYCJI.toString())));
    }

    @Test
    @Transactional
    public void getTyp() throws Exception {
        // Initialize the database
        typRepository.saveAndFlush(typ);

        // Get the typ
        restTypMockMvc.perform(get("/api/typs/{id}", typ.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(typ.getId().intValue()))
            .andExpect(jsonPath("$.imie").value(DEFAULT_IMIE.toString()))
            .andExpect(jsonPath("$.nazwisko").value(DEFAULT_NAZWISKO.toString()))
            .andExpect(jsonPath("$.wynikDruzyna1").value(DEFAULT_WYNIK_DRUZYNA_1))
            .andExpect(jsonPath("$.wynikDruzyna2").value(DEFAULT_WYNIK_DRUZYNA_2.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.dataEdycji").value(DEFAULT_DATA_EDYCJI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTyp() throws Exception {
        // Get the typ
        restTypMockMvc.perform(get("/api/typs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTyp() throws Exception {
        // Initialize the database
        typRepository.saveAndFlush(typ);
        int databaseSizeBeforeUpdate = typRepository.findAll().size();

        // Update the typ
        Typ updatedTyp = new Typ();
        updatedTyp.setId(typ.getId());
        updatedTyp.setImie(UPDATED_IMIE);
        updatedTyp.setNazwisko(UPDATED_NAZWISKO);
        updatedTyp.setWynikDruzyna1(UPDATED_WYNIK_DRUZYNA_1);
        updatedTyp.setWynikDruzyna2(UPDATED_WYNIK_DRUZYNA_2);
        updatedTyp.setData(UPDATED_DATA);
        updatedTyp.setDataEdycji(UPDATED_DATA_EDYCJI);
        TypDTO typDTO = typMapper.typToTypDTO(updatedTyp);

        restTypMockMvc.perform(put("/api/typs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(typDTO)))
                .andExpect(status().isOk());

        // Validate the Typ in the database
        List<Typ> typs = typRepository.findAll();
        assertThat(typs).hasSize(databaseSizeBeforeUpdate);
        Typ testTyp = typs.get(typs.size() - 1);
        assertThat(testTyp.getImie()).isEqualTo(UPDATED_IMIE);
        assertThat(testTyp.getNazwisko()).isEqualTo(UPDATED_NAZWISKO);
        assertThat(testTyp.getWynikDruzyna1()).isEqualTo(UPDATED_WYNIK_DRUZYNA_1);
        assertThat(testTyp.getWynikDruzyna2()).isEqualTo(UPDATED_WYNIK_DRUZYNA_2);
        assertThat(testTyp.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testTyp.getDataEdycji()).isEqualTo(UPDATED_DATA_EDYCJI);
    }

    @Test
    @Transactional
    public void deleteTyp() throws Exception {
        // Initialize the database
        typRepository.saveAndFlush(typ);
        int databaseSizeBeforeDelete = typRepository.findAll().size();

        // Get the typ
        restTypMockMvc.perform(delete("/api/typs/{id}", typ.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Typ> typs = typRepository.findAll();
        assertThat(typs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
