package com.hopiestra.site.web.rest;

import com.hopiestra.site.HopiestraWebSiteApp;

import com.hopiestra.site.domain.InternationalTheme;
import com.hopiestra.site.domain.Language;
import com.hopiestra.site.repository.InternationalThemeRepository;
import com.hopiestra.site.service.InternationalThemeService;
import com.hopiestra.site.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hopiestra.site.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InternationalThemeResource REST controller.
 *
 * @see InternationalThemeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HopiestraWebSiteApp.class)
public class InternationalThemeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private InternationalThemeRepository internationalThemeRepository;

    @Autowired
    private InternationalThemeService internationalThemeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInternationalThemeMockMvc;

    private InternationalTheme internationalTheme;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InternationalThemeResource internationalThemeResource = new InternationalThemeResource(internationalThemeService);
        this.restInternationalThemeMockMvc = MockMvcBuilders.standaloneSetup(internationalThemeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InternationalTheme createEntity(EntityManager em) {
        InternationalTheme internationalTheme = new InternationalTheme()
            .name(DEFAULT_NAME);
        // Add required entity
        Language language = LanguageResourceIntTest.createEntity(em);
        em.persist(language);
        em.flush();
        internationalTheme.setLanguage(language);
        return internationalTheme;
    }

    @Before
    public void initTest() {
        internationalTheme = createEntity(em);
    }

    @Test
    @Transactional
    public void createInternationalTheme() throws Exception {
        int databaseSizeBeforeCreate = internationalThemeRepository.findAll().size();

        // Create the InternationalTheme
        restInternationalThemeMockMvc.perform(post("/api/international-themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internationalTheme)))
            .andExpect(status().isCreated());

        // Validate the InternationalTheme in the database
        List<InternationalTheme> internationalThemeList = internationalThemeRepository.findAll();
        assertThat(internationalThemeList).hasSize(databaseSizeBeforeCreate + 1);
        InternationalTheme testInternationalTheme = internationalThemeList.get(internationalThemeList.size() - 1);
        assertThat(testInternationalTheme.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createInternationalThemeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = internationalThemeRepository.findAll().size();

        // Create the InternationalTheme with an existing ID
        internationalTheme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInternationalThemeMockMvc.perform(post("/api/international-themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internationalTheme)))
            .andExpect(status().isBadRequest());

        // Validate the InternationalTheme in the database
        List<InternationalTheme> internationalThemeList = internationalThemeRepository.findAll();
        assertThat(internationalThemeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = internationalThemeRepository.findAll().size();
        // set the field null
        internationalTheme.setName(null);

        // Create the InternationalTheme, which fails.

        restInternationalThemeMockMvc.perform(post("/api/international-themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internationalTheme)))
            .andExpect(status().isBadRequest());

        List<InternationalTheme> internationalThemeList = internationalThemeRepository.findAll();
        assertThat(internationalThemeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInternationalThemes() throws Exception {
        // Initialize the database
        internationalThemeRepository.saveAndFlush(internationalTheme);

        // Get all the internationalThemeList
        restInternationalThemeMockMvc.perform(get("/api/international-themes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(internationalTheme.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getInternationalTheme() throws Exception {
        // Initialize the database
        internationalThemeRepository.saveAndFlush(internationalTheme);

        // Get the internationalTheme
        restInternationalThemeMockMvc.perform(get("/api/international-themes/{id}", internationalTheme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(internationalTheme.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInternationalTheme() throws Exception {
        // Get the internationalTheme
        restInternationalThemeMockMvc.perform(get("/api/international-themes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInternationalTheme() throws Exception {
        // Initialize the database
        internationalThemeService.save(internationalTheme);

        int databaseSizeBeforeUpdate = internationalThemeRepository.findAll().size();

        // Update the internationalTheme
        InternationalTheme updatedInternationalTheme = internationalThemeRepository.findOne(internationalTheme.getId());
        // Disconnect from session so that the updates on updatedInternationalTheme are not directly saved in db
        em.detach(updatedInternationalTheme);
        updatedInternationalTheme
            .name(UPDATED_NAME);

        restInternationalThemeMockMvc.perform(put("/api/international-themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInternationalTheme)))
            .andExpect(status().isOk());

        // Validate the InternationalTheme in the database
        List<InternationalTheme> internationalThemeList = internationalThemeRepository.findAll();
        assertThat(internationalThemeList).hasSize(databaseSizeBeforeUpdate);
        InternationalTheme testInternationalTheme = internationalThemeList.get(internationalThemeList.size() - 1);
        assertThat(testInternationalTheme.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingInternationalTheme() throws Exception {
        int databaseSizeBeforeUpdate = internationalThemeRepository.findAll().size();

        // Create the InternationalTheme

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInternationalThemeMockMvc.perform(put("/api/international-themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internationalTheme)))
            .andExpect(status().isCreated());

        // Validate the InternationalTheme in the database
        List<InternationalTheme> internationalThemeList = internationalThemeRepository.findAll();
        assertThat(internationalThemeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInternationalTheme() throws Exception {
        // Initialize the database
        internationalThemeService.save(internationalTheme);

        int databaseSizeBeforeDelete = internationalThemeRepository.findAll().size();

        // Get the internationalTheme
        restInternationalThemeMockMvc.perform(delete("/api/international-themes/{id}", internationalTheme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InternationalTheme> internationalThemeList = internationalThemeRepository.findAll();
        assertThat(internationalThemeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InternationalTheme.class);
        InternationalTheme internationalTheme1 = new InternationalTheme();
        internationalTheme1.setId(1L);
        InternationalTheme internationalTheme2 = new InternationalTheme();
        internationalTheme2.setId(internationalTheme1.getId());
        assertThat(internationalTheme1).isEqualTo(internationalTheme2);
        internationalTheme2.setId(2L);
        assertThat(internationalTheme1).isNotEqualTo(internationalTheme2);
        internationalTheme1.setId(null);
        assertThat(internationalTheme1).isNotEqualTo(internationalTheme2);
    }
}
