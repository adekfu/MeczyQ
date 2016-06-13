package pl.qnt.meczyq.web.rest;

import pl.qnt.meczyq.MeczyQApp;
import pl.qnt.meczyq.domain.Mecz;
import pl.qnt.meczyq.repository.MeczRepository;
import pl.qnt.meczyq.service.MeczService;
import pl.qnt.meczyq.web.rest.dto.MeczDTO;
import pl.qnt.meczyq.web.rest.mapper.MeczMapper;

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
 * Test class for the MeczResource REST controller.
 *
 * @see MeczResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MeczyQApp.class)
@WebAppConfiguration
@IntegrationTest
public class MeczResourceIntTest {

    private static final String DEFAULT_DRUZYNA_1 = "AAAAA";
    private static final String UPDATED_DRUZYNA_1 = "BBBBB";
    private static final String DEFAULT_DRUZYNA_2 = "AAAAA";
    private static final String UPDATED_DRUZYNA_2 = "BBBBB";

    private static final Integer DEFAULT_WYNIK_DRUZYNA_1 = 1;
    private static final Integer UPDATED_WYNIK_DRUZYNA_1 = 2;

    private static final Integer DEFAULT_WYNIK_DRUZYNA_2 = 1;
    private static final Integer UPDATED_WYNIK_DRUZYNA_2 = 2;

    private static final LocalDate DEFAULT_DATA_MECZU = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_MECZU = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_ZAMKNIECIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ZAMKNIECIA = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private MeczRepository meczRepository;

    @Inject
    private MeczMapper meczMapper;

    @Inject
    private MeczService meczService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMeczMockMvc;

    private Mecz mecz;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MeczResource meczResource = new MeczResource();
        ReflectionTestUtils.setField(meczResource, "meczService", meczService);
        ReflectionTestUtils.setField(meczResource, "meczMapper", meczMapper);
        this.restMeczMockMvc = MockMvcBuilders.standaloneSetup(meczResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        mecz = new Mecz();
        mecz.setDruzyna1(DEFAULT_DRUZYNA_1);
        mecz.setDruzyna2(DEFAULT_DRUZYNA_2);
        mecz.setWynikDruzyna1(DEFAULT_WYNIK_DRUZYNA_1);
        mecz.setWynikDruzyna2(DEFAULT_WYNIK_DRUZYNA_2);
        mecz.setDataMeczu(DEFAULT_DATA_MECZU);
        mecz.setDataZamkniecia(DEFAULT_DATA_ZAMKNIECIA);
    }

    @Test
    @Transactional
    public void createMecz() throws Exception {
        int databaseSizeBeforeCreate = meczRepository.findAll().size();

        // Create the Mecz
        MeczDTO meczDTO = meczMapper.meczToMeczDTO(mecz);

        restMeczMockMvc.perform(post("/api/meczs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(meczDTO)))
                .andExpect(status().isCreated());

        // Validate the Mecz in the database
        List<Mecz> meczs = meczRepository.findAll();
        assertThat(meczs).hasSize(databaseSizeBeforeCreate + 1);
        Mecz testMecz = meczs.get(meczs.size() - 1);
        assertThat(testMecz.getDruzyna1()).isEqualTo(DEFAULT_DRUZYNA_1);
        assertThat(testMecz.getDruzyna2()).isEqualTo(DEFAULT_DRUZYNA_2);
        assertThat(testMecz.getWynikDruzyna1()).isEqualTo(DEFAULT_WYNIK_DRUZYNA_1);
        assertThat(testMecz.getWynikDruzyna2()).isEqualTo(DEFAULT_WYNIK_DRUZYNA_2);
        assertThat(testMecz.getDataMeczu()).isEqualTo(DEFAULT_DATA_MECZU);
        assertThat(testMecz.getDataZamkniecia()).isEqualTo(DEFAULT_DATA_ZAMKNIECIA);
    }

    @Test
    @Transactional
    public void checkDruzyna1IsRequired() throws Exception {
        int databaseSizeBeforeTest = meczRepository.findAll().size();
        // set the field null
        mecz.setDruzyna1(null);

        // Create the Mecz, which fails.
        MeczDTO meczDTO = meczMapper.meczToMeczDTO(mecz);

        restMeczMockMvc.perform(post("/api/meczs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(meczDTO)))
                .andExpect(status().isBadRequest());

        List<Mecz> meczs = meczRepository.findAll();
        assertThat(meczs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDruzyna2IsRequired() throws Exception {
        int databaseSizeBeforeTest = meczRepository.findAll().size();
        // set the field null
        mecz.setDruzyna2(null);

        // Create the Mecz, which fails.
        MeczDTO meczDTO = meczMapper.meczToMeczDTO(mecz);

        restMeczMockMvc.perform(post("/api/meczs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(meczDTO)))
                .andExpect(status().isBadRequest());

        List<Mecz> meczs = meczRepository.findAll();
        assertThat(meczs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMeczs() throws Exception {
        // Initialize the database
        meczRepository.saveAndFlush(mecz);

        // Get all the meczs
        restMeczMockMvc.perform(get("/api/meczs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mecz.getId().intValue())))
                .andExpect(jsonPath("$.[*].druzyna1").value(hasItem(DEFAULT_DRUZYNA_1.toString())))
                .andExpect(jsonPath("$.[*].druzyna2").value(hasItem(DEFAULT_DRUZYNA_2.toString())))
                .andExpect(jsonPath("$.[*].wynikDruzyna1").value(hasItem(DEFAULT_WYNIK_DRUZYNA_1)))
                .andExpect(jsonPath("$.[*].wynikDruzyna2").value(hasItem(DEFAULT_WYNIK_DRUZYNA_2)))
                .andExpect(jsonPath("$.[*].dataMeczu").value(hasItem(DEFAULT_DATA_MECZU.toString())))
                .andExpect(jsonPath("$.[*].dataZamkniecia").value(hasItem(DEFAULT_DATA_ZAMKNIECIA.toString())));
    }

    @Test
    @Transactional
    public void getMecz() throws Exception {
        // Initialize the database
        meczRepository.saveAndFlush(mecz);

        // Get the mecz
        restMeczMockMvc.perform(get("/api/meczs/{id}", mecz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mecz.getId().intValue()))
            .andExpect(jsonPath("$.druzyna1").value(DEFAULT_DRUZYNA_1.toString()))
            .andExpect(jsonPath("$.druzyna2").value(DEFAULT_DRUZYNA_2.toString()))
            .andExpect(jsonPath("$.wynikDruzyna1").value(DEFAULT_WYNIK_DRUZYNA_1))
            .andExpect(jsonPath("$.wynikDruzyna2").value(DEFAULT_WYNIK_DRUZYNA_2))
            .andExpect(jsonPath("$.dataMeczu").value(DEFAULT_DATA_MECZU.toString()))
            .andExpect(jsonPath("$.dataZamkniecia").value(DEFAULT_DATA_ZAMKNIECIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMecz() throws Exception {
        // Get the mecz
        restMeczMockMvc.perform(get("/api/meczs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMecz() throws Exception {
        // Initialize the database
        meczRepository.saveAndFlush(mecz);
        int databaseSizeBeforeUpdate = meczRepository.findAll().size();

        // Update the mecz
        Mecz updatedMecz = new Mecz();
        updatedMecz.setId(mecz.getId());
        updatedMecz.setDruzyna1(UPDATED_DRUZYNA_1);
        updatedMecz.setDruzyna2(UPDATED_DRUZYNA_2);
        updatedMecz.setWynikDruzyna1(UPDATED_WYNIK_DRUZYNA_1);
        updatedMecz.setWynikDruzyna2(UPDATED_WYNIK_DRUZYNA_2);
        updatedMecz.setDataMeczu(UPDATED_DATA_MECZU);
        updatedMecz.setDataZamkniecia(UPDATED_DATA_ZAMKNIECIA);
        MeczDTO meczDTO = meczMapper.meczToMeczDTO(updatedMecz);

        restMeczMockMvc.perform(put("/api/meczs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(meczDTO)))
                .andExpect(status().isOk());

        // Validate the Mecz in the database
        List<Mecz> meczs = meczRepository.findAll();
        assertThat(meczs).hasSize(databaseSizeBeforeUpdate);
        Mecz testMecz = meczs.get(meczs.size() - 1);
        assertThat(testMecz.getDruzyna1()).isEqualTo(UPDATED_DRUZYNA_1);
        assertThat(testMecz.getDruzyna2()).isEqualTo(UPDATED_DRUZYNA_2);
        assertThat(testMecz.getWynikDruzyna1()).isEqualTo(UPDATED_WYNIK_DRUZYNA_1);
        assertThat(testMecz.getWynikDruzyna2()).isEqualTo(UPDATED_WYNIK_DRUZYNA_2);
        assertThat(testMecz.getDataMeczu()).isEqualTo(UPDATED_DATA_MECZU);
        assertThat(testMecz.getDataZamkniecia()).isEqualTo(UPDATED_DATA_ZAMKNIECIA);
    }

    @Test
    @Transactional
    public void deleteMecz() throws Exception {
        // Initialize the database
        meczRepository.saveAndFlush(mecz);
        int databaseSizeBeforeDelete = meczRepository.findAll().size();

        // Get the mecz
        restMeczMockMvc.perform(delete("/api/meczs/{id}", mecz.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Mecz> meczs = meczRepository.findAll();
        assertThat(meczs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
