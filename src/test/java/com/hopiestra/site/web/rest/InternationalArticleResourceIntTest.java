package com.hopiestra.site.web.rest;

import com.hopiestra.site.HopiestraWebSiteApp;

import com.hopiestra.site.domain.InternationalArticle;
import com.hopiestra.site.domain.Language;
import com.hopiestra.site.domain.Article;
import com.hopiestra.site.repository.InternationalArticleRepository;
import com.hopiestra.site.service.InternationalArticleService;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hopiestra.site.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InternationalArticleResource REST controller.
 *
 * @see InternationalArticleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HopiestraWebSiteApp.class)
public class InternationalArticleResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private InternationalArticleRepository internationalArticleRepository;

    @Autowired
    private InternationalArticleService internationalArticleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInternationalArticleMockMvc;

    private InternationalArticle internationalArticle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InternationalArticleResource internationalArticleResource = new InternationalArticleResource(internationalArticleService);
        this.restInternationalArticleMockMvc = MockMvcBuilders.standaloneSetup(internationalArticleResource)
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
    public static InternationalArticle createEntity(EntityManager em) {
        InternationalArticle internationalArticle = new InternationalArticle()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT);
        // Add required entity
        Language language = LanguageResourceIntTest.createEntity(em);
        em.persist(language);
        em.flush();
        internationalArticle.setLanguage(language);
        // Add required entity
        Article article = ArticleResourceIntTest.createEntity(em);
        em.persist(article);
        em.flush();
        internationalArticle.setArticle(article);
        return internationalArticle;
    }

    @Before
    public void initTest() {
        internationalArticle = createEntity(em);
    }

    @Test
    @Transactional
    public void createInternationalArticle() throws Exception {
        int databaseSizeBeforeCreate = internationalArticleRepository.findAll().size();

        // Create the InternationalArticle
        restInternationalArticleMockMvc.perform(post("/api/international-articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internationalArticle)))
            .andExpect(status().isCreated());

        // Validate the InternationalArticle in the database
        List<InternationalArticle> internationalArticleList = internationalArticleRepository.findAll();
        assertThat(internationalArticleList).hasSize(databaseSizeBeforeCreate + 1);
        InternationalArticle testInternationalArticle = internationalArticleList.get(internationalArticleList.size() - 1);
        assertThat(testInternationalArticle.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testInternationalArticle.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createInternationalArticleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = internationalArticleRepository.findAll().size();

        // Create the InternationalArticle with an existing ID
        internationalArticle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInternationalArticleMockMvc.perform(post("/api/international-articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internationalArticle)))
            .andExpect(status().isBadRequest());

        // Validate the InternationalArticle in the database
        List<InternationalArticle> internationalArticleList = internationalArticleRepository.findAll();
        assertThat(internationalArticleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = internationalArticleRepository.findAll().size();
        // set the field null
        internationalArticle.setTitle(null);

        // Create the InternationalArticle, which fails.

        restInternationalArticleMockMvc.perform(post("/api/international-articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internationalArticle)))
            .andExpect(status().isBadRequest());

        List<InternationalArticle> internationalArticleList = internationalArticleRepository.findAll();
        assertThat(internationalArticleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = internationalArticleRepository.findAll().size();
        // set the field null
        internationalArticle.setContent(null);

        // Create the InternationalArticle, which fails.

        restInternationalArticleMockMvc.perform(post("/api/international-articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internationalArticle)))
            .andExpect(status().isBadRequest());

        List<InternationalArticle> internationalArticleList = internationalArticleRepository.findAll();
        assertThat(internationalArticleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInternationalArticles() throws Exception {
        // Initialize the database
        internationalArticleRepository.saveAndFlush(internationalArticle);

        // Get all the internationalArticleList
        restInternationalArticleMockMvc.perform(get("/api/international-articles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(internationalArticle.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }

    @Test
    @Transactional
    public void getInternationalArticle() throws Exception {
        // Initialize the database
        internationalArticleRepository.saveAndFlush(internationalArticle);

        // Get the internationalArticle
        restInternationalArticleMockMvc.perform(get("/api/international-articles/{id}", internationalArticle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(internationalArticle.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInternationalArticle() throws Exception {
        // Get the internationalArticle
        restInternationalArticleMockMvc.perform(get("/api/international-articles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInternationalArticle() throws Exception {
        // Initialize the database
        internationalArticleService.save(internationalArticle);

        int databaseSizeBeforeUpdate = internationalArticleRepository.findAll().size();

        // Update the internationalArticle
        InternationalArticle updatedInternationalArticle = internationalArticleRepository.findOne(internationalArticle.getId());
        // Disconnect from session so that the updates on updatedInternationalArticle are not directly saved in db
        em.detach(updatedInternationalArticle);
        updatedInternationalArticle
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT);

        restInternationalArticleMockMvc.perform(put("/api/international-articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInternationalArticle)))
            .andExpect(status().isOk());

        // Validate the InternationalArticle in the database
        List<InternationalArticle> internationalArticleList = internationalArticleRepository.findAll();
        assertThat(internationalArticleList).hasSize(databaseSizeBeforeUpdate);
        InternationalArticle testInternationalArticle = internationalArticleList.get(internationalArticleList.size() - 1);
        assertThat(testInternationalArticle.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testInternationalArticle.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingInternationalArticle() throws Exception {
        int databaseSizeBeforeUpdate = internationalArticleRepository.findAll().size();

        // Create the InternationalArticle

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInternationalArticleMockMvc.perform(put("/api/international-articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internationalArticle)))
            .andExpect(status().isCreated());

        // Validate the InternationalArticle in the database
        List<InternationalArticle> internationalArticleList = internationalArticleRepository.findAll();
        assertThat(internationalArticleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInternationalArticle() throws Exception {
        // Initialize the database
        internationalArticleService.save(internationalArticle);

        int databaseSizeBeforeDelete = internationalArticleRepository.findAll().size();

        // Get the internationalArticle
        restInternationalArticleMockMvc.perform(delete("/api/international-articles/{id}", internationalArticle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InternationalArticle> internationalArticleList = internationalArticleRepository.findAll();
        assertThat(internationalArticleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InternationalArticle.class);
        InternationalArticle internationalArticle1 = new InternationalArticle();
        internationalArticle1.setId(1L);
        InternationalArticle internationalArticle2 = new InternationalArticle();
        internationalArticle2.setId(internationalArticle1.getId());
        assertThat(internationalArticle1).isEqualTo(internationalArticle2);
        internationalArticle2.setId(2L);
        assertThat(internationalArticle1).isNotEqualTo(internationalArticle2);
        internationalArticle1.setId(null);
        assertThat(internationalArticle1).isNotEqualTo(internationalArticle2);
    }
}
