package com.hopiestra.site.web.rest;

import com.hopiestra.site.HopiestraWebSiteApp;

import com.hopiestra.site.domain.ThemeSubscription;
import com.hopiestra.site.repository.ThemeSubscriptionRepository;
import com.hopiestra.site.service.ThemeSubscriptionService;
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
 * Test class for the ThemeSubscriptionResource REST controller.
 *
 * @see ThemeSubscriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HopiestraWebSiteApp.class)
public class ThemeSubscriptionResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SUBSCRIPITON_FOR_ALL = false;
    private static final Boolean UPDATED_SUBSCRIPITON_FOR_ALL = true;

    @Autowired
    private ThemeSubscriptionRepository themeSubscriptionRepository;

    @Autowired
    private ThemeSubscriptionService themeSubscriptionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restThemeSubscriptionMockMvc;

    private ThemeSubscription themeSubscription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ThemeSubscriptionResource themeSubscriptionResource = new ThemeSubscriptionResource(themeSubscriptionService);
        this.restThemeSubscriptionMockMvc = MockMvcBuilders.standaloneSetup(themeSubscriptionResource)
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
    public static ThemeSubscription createEntity(EntityManager em) {
        ThemeSubscription themeSubscription = new ThemeSubscription()
            .email(DEFAULT_EMAIL)
            .subscripitonForAll(DEFAULT_SUBSCRIPITON_FOR_ALL);
        return themeSubscription;
    }

    @Before
    public void initTest() {
        themeSubscription = createEntity(em);
    }

    @Test
    @Transactional
    public void createThemeSubscription() throws Exception {
        int databaseSizeBeforeCreate = themeSubscriptionRepository.findAll().size();

        // Create the ThemeSubscription
        restThemeSubscriptionMockMvc.perform(post("/api/theme-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(themeSubscription)))
            .andExpect(status().isCreated());

        // Validate the ThemeSubscription in the database
        List<ThemeSubscription> themeSubscriptionList = themeSubscriptionRepository.findAll();
        assertThat(themeSubscriptionList).hasSize(databaseSizeBeforeCreate + 1);
        ThemeSubscription testThemeSubscription = themeSubscriptionList.get(themeSubscriptionList.size() - 1);
        assertThat(testThemeSubscription.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testThemeSubscription.isSubscripitonForAll()).isEqualTo(DEFAULT_SUBSCRIPITON_FOR_ALL);
    }

    @Test
    @Transactional
    public void createThemeSubscriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = themeSubscriptionRepository.findAll().size();

        // Create the ThemeSubscription with an existing ID
        themeSubscription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThemeSubscriptionMockMvc.perform(post("/api/theme-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(themeSubscription)))
            .andExpect(status().isBadRequest());

        // Validate the ThemeSubscription in the database
        List<ThemeSubscription> themeSubscriptionList = themeSubscriptionRepository.findAll();
        assertThat(themeSubscriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = themeSubscriptionRepository.findAll().size();
        // set the field null
        themeSubscription.setEmail(null);

        // Create the ThemeSubscription, which fails.

        restThemeSubscriptionMockMvc.perform(post("/api/theme-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(themeSubscription)))
            .andExpect(status().isBadRequest());

        List<ThemeSubscription> themeSubscriptionList = themeSubscriptionRepository.findAll();
        assertThat(themeSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThemeSubscriptions() throws Exception {
        // Initialize the database
        themeSubscriptionRepository.saveAndFlush(themeSubscription);

        // Get all the themeSubscriptionList
        restThemeSubscriptionMockMvc.perform(get("/api/theme-subscriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(themeSubscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].subscripitonForAll").value(hasItem(DEFAULT_SUBSCRIPITON_FOR_ALL.booleanValue())));
    }

    @Test
    @Transactional
    public void getThemeSubscription() throws Exception {
        // Initialize the database
        themeSubscriptionRepository.saveAndFlush(themeSubscription);

        // Get the themeSubscription
        restThemeSubscriptionMockMvc.perform(get("/api/theme-subscriptions/{id}", themeSubscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(themeSubscription.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.subscripitonForAll").value(DEFAULT_SUBSCRIPITON_FOR_ALL.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingThemeSubscription() throws Exception {
        // Get the themeSubscription
        restThemeSubscriptionMockMvc.perform(get("/api/theme-subscriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThemeSubscription() throws Exception {
        // Initialize the database
        themeSubscriptionService.save(themeSubscription);

        int databaseSizeBeforeUpdate = themeSubscriptionRepository.findAll().size();

        // Update the themeSubscription
        ThemeSubscription updatedThemeSubscription = themeSubscriptionRepository.findOne(themeSubscription.getId());
        // Disconnect from session so that the updates on updatedThemeSubscription are not directly saved in db
        em.detach(updatedThemeSubscription);
        updatedThemeSubscription
            .email(UPDATED_EMAIL)
            .subscripitonForAll(UPDATED_SUBSCRIPITON_FOR_ALL);

        restThemeSubscriptionMockMvc.perform(put("/api/theme-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedThemeSubscription)))
            .andExpect(status().isOk());

        // Validate the ThemeSubscription in the database
        List<ThemeSubscription> themeSubscriptionList = themeSubscriptionRepository.findAll();
        assertThat(themeSubscriptionList).hasSize(databaseSizeBeforeUpdate);
        ThemeSubscription testThemeSubscription = themeSubscriptionList.get(themeSubscriptionList.size() - 1);
        assertThat(testThemeSubscription.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testThemeSubscription.isSubscripitonForAll()).isEqualTo(UPDATED_SUBSCRIPITON_FOR_ALL);
    }

    @Test
    @Transactional
    public void updateNonExistingThemeSubscription() throws Exception {
        int databaseSizeBeforeUpdate = themeSubscriptionRepository.findAll().size();

        // Create the ThemeSubscription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restThemeSubscriptionMockMvc.perform(put("/api/theme-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(themeSubscription)))
            .andExpect(status().isCreated());

        // Validate the ThemeSubscription in the database
        List<ThemeSubscription> themeSubscriptionList = themeSubscriptionRepository.findAll();
        assertThat(themeSubscriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteThemeSubscription() throws Exception {
        // Initialize the database
        themeSubscriptionService.save(themeSubscription);

        int databaseSizeBeforeDelete = themeSubscriptionRepository.findAll().size();

        // Get the themeSubscription
        restThemeSubscriptionMockMvc.perform(delete("/api/theme-subscriptions/{id}", themeSubscription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ThemeSubscription> themeSubscriptionList = themeSubscriptionRepository.findAll();
        assertThat(themeSubscriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThemeSubscription.class);
        ThemeSubscription themeSubscription1 = new ThemeSubscription();
        themeSubscription1.setId(1L);
        ThemeSubscription themeSubscription2 = new ThemeSubscription();
        themeSubscription2.setId(themeSubscription1.getId());
        assertThat(themeSubscription1).isEqualTo(themeSubscription2);
        themeSubscription2.setId(2L);
        assertThat(themeSubscription1).isNotEqualTo(themeSubscription2);
        themeSubscription1.setId(null);
        assertThat(themeSubscription1).isNotEqualTo(themeSubscription2);
    }
}
