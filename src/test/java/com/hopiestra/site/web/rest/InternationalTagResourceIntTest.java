package com.hopiestra.site.web.rest;

import com.hopiestra.site.HopiestraWebSiteApp;

import com.hopiestra.site.domain.InternationalTag;
import com.hopiestra.site.domain.Language;
import com.hopiestra.site.repository.InternationalTagRepository;
import com.hopiestra.site.service.InternationalTagService;
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
 * Test class for the InternationalTagResource REST controller.
 *
 * @see InternationalTagResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HopiestraWebSiteApp.class)
public class InternationalTagResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Autowired
    private InternationalTagRepository internationalTagRepository;

    @Autowired
    private InternationalTagService internationalTagService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInternationalTagMockMvc;

    private InternationalTag internationalTag;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InternationalTagResource internationalTagResource = new InternationalTagResource(internationalTagService);
        this.restInternationalTagMockMvc = MockMvcBuilders.standaloneSetup(internationalTagResource)
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
    public static InternationalTag createEntity(EntityManager em) {
        InternationalTag internationalTag = new InternationalTag()
            .title(DEFAULT_TITLE);
        // Add required entity
        Language language = LanguageResourceIntTest.createEntity(em);
        em.persist(language);
        em.flush();
        internationalTag.setLanguage(language);
        return internationalTag;
    }

    @Before
    public void initTest() {
        internationalTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createInternationalTag() throws Exception {
        int databaseSizeBeforeCreate = internationalTagRepository.findAll().size();

        // Create the InternationalTag
        restInternationalTagMockMvc.perform(post("/api/international-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internationalTag)))
            .andExpect(status().isCreated());

        // Validate the InternationalTag in the database
        List<InternationalTag> internationalTagList = internationalTagRepository.findAll();
        assertThat(internationalTagList).hasSize(databaseSizeBeforeCreate + 1);
        InternationalTag testInternationalTag = internationalTagList.get(internationalTagList.size() - 1);
        assertThat(testInternationalTag.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createInternationalTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = internationalTagRepository.findAll().size();

        // Create the InternationalTag with an existing ID
        internationalTag.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInternationalTagMockMvc.perform(post("/api/international-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internationalTag)))
            .andExpect(status().isBadRequest());

        // Validate the InternationalTag in the database
        List<InternationalTag> internationalTagList = internationalTagRepository.findAll();
        assertThat(internationalTagList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = internationalTagRepository.findAll().size();
        // set the field null
        internationalTag.setTitle(null);

        // Create the InternationalTag, which fails.

        restInternationalTagMockMvc.perform(post("/api/international-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internationalTag)))
            .andExpect(status().isBadRequest());

        List<InternationalTag> internationalTagList = internationalTagRepository.findAll();
        assertThat(internationalTagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInternationalTags() throws Exception {
        // Initialize the database
        internationalTagRepository.saveAndFlush(internationalTag);

        // Get all the internationalTagList
        restInternationalTagMockMvc.perform(get("/api/international-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(internationalTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())));
    }

    @Test
    @Transactional
    public void getInternationalTag() throws Exception {
        // Initialize the database
        internationalTagRepository.saveAndFlush(internationalTag);

        // Get the internationalTag
        restInternationalTagMockMvc.perform(get("/api/international-tags/{id}", internationalTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(internationalTag.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInternationalTag() throws Exception {
        // Get the internationalTag
        restInternationalTagMockMvc.perform(get("/api/international-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInternationalTag() throws Exception {
        // Initialize the database
        internationalTagService.save(internationalTag);

        int databaseSizeBeforeUpdate = internationalTagRepository.findAll().size();

        // Update the internationalTag
        InternationalTag updatedInternationalTag = internationalTagRepository.findOne(internationalTag.getId());
        // Disconnect from session so that the updates on updatedInternationalTag are not directly saved in db
        em.detach(updatedInternationalTag);
        updatedInternationalTag
            .title(UPDATED_TITLE);

        restInternationalTagMockMvc.perform(put("/api/international-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInternationalTag)))
            .andExpect(status().isOk());

        // Validate the InternationalTag in the database
        List<InternationalTag> internationalTagList = internationalTagRepository.findAll();
        assertThat(internationalTagList).hasSize(databaseSizeBeforeUpdate);
        InternationalTag testInternationalTag = internationalTagList.get(internationalTagList.size() - 1);
        assertThat(testInternationalTag.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingInternationalTag() throws Exception {
        int databaseSizeBeforeUpdate = internationalTagRepository.findAll().size();

        // Create the InternationalTag

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInternationalTagMockMvc.perform(put("/api/international-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internationalTag)))
            .andExpect(status().isCreated());

        // Validate the InternationalTag in the database
        List<InternationalTag> internationalTagList = internationalTagRepository.findAll();
        assertThat(internationalTagList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInternationalTag() throws Exception {
        // Initialize the database
        internationalTagService.save(internationalTag);

        int databaseSizeBeforeDelete = internationalTagRepository.findAll().size();

        // Get the internationalTag
        restInternationalTagMockMvc.perform(delete("/api/international-tags/{id}", internationalTag.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InternationalTag> internationalTagList = internationalTagRepository.findAll();
        assertThat(internationalTagList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InternationalTag.class);
        InternationalTag internationalTag1 = new InternationalTag();
        internationalTag1.setId(1L);
        InternationalTag internationalTag2 = new InternationalTag();
        internationalTag2.setId(internationalTag1.getId());
        assertThat(internationalTag1).isEqualTo(internationalTag2);
        internationalTag2.setId(2L);
        assertThat(internationalTag1).isNotEqualTo(internationalTag2);
        internationalTag1.setId(null);
        assertThat(internationalTag1).isNotEqualTo(internationalTag2);
    }
}
