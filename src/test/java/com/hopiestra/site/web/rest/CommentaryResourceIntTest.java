package com.hopiestra.site.web.rest;

import com.hopiestra.site.HopiestraWebSiteApp;

import com.hopiestra.site.domain.Commentary;
import com.hopiestra.site.domain.User;
import com.hopiestra.site.domain.Article;
import com.hopiestra.site.repository.CommentaryRepository;
import com.hopiestra.site.service.CommentaryService;
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
 * Test class for the CommentaryResource REST controller.
 *
 * @see CommentaryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HopiestraWebSiteApp.class)
public class CommentaryResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private CommentaryRepository commentaryRepository;

    @Autowired
    private CommentaryService commentaryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommentaryMockMvc;

    private Commentary commentary;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommentaryResource commentaryResource = new CommentaryResource(commentaryService);
        this.restCommentaryMockMvc = MockMvcBuilders.standaloneSetup(commentaryResource)
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
    public static Commentary createEntity(EntityManager em) {
        Commentary commentary = new Commentary()
            .content(DEFAULT_CONTENT);
        // Add required entity
        User author = UserResourceIntTest.createEntity(em);
        em.persist(author);
        em.flush();
        commentary.setAuthor(author);
        // Add required entity
        Article article = ArticleResourceIntTest.createEntity(em);
        em.persist(article);
        em.flush();
        commentary.setArticle(article);
        return commentary;
    }

    @Before
    public void initTest() {
        commentary = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommentary() throws Exception {
        int databaseSizeBeforeCreate = commentaryRepository.findAll().size();

        // Create the Commentary
        restCommentaryMockMvc.perform(post("/api/commentaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentary)))
            .andExpect(status().isCreated());

        // Validate the Commentary in the database
        List<Commentary> commentaryList = commentaryRepository.findAll();
        assertThat(commentaryList).hasSize(databaseSizeBeforeCreate + 1);
        Commentary testCommentary = commentaryList.get(commentaryList.size() - 1);
        assertThat(testCommentary.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createCommentaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentaryRepository.findAll().size();

        // Create the Commentary with an existing ID
        commentary.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentaryMockMvc.perform(post("/api/commentaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentary)))
            .andExpect(status().isBadRequest());

        // Validate the Commentary in the database
        List<Commentary> commentaryList = commentaryRepository.findAll();
        assertThat(commentaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = commentaryRepository.findAll().size();
        // set the field null
        commentary.setContent(null);

        // Create the Commentary, which fails.

        restCommentaryMockMvc.perform(post("/api/commentaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentary)))
            .andExpect(status().isBadRequest());

        List<Commentary> commentaryList = commentaryRepository.findAll();
        assertThat(commentaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommentaries() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList
        restCommentaryMockMvc.perform(get("/api/commentaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentary.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }

    @Test
    @Transactional
    public void getCommentary() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get the commentary
        restCommentaryMockMvc.perform(get("/api/commentaries/{id}", commentary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commentary.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommentary() throws Exception {
        // Get the commentary
        restCommentaryMockMvc.perform(get("/api/commentaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommentary() throws Exception {
        // Initialize the database
        commentaryService.save(commentary);

        int databaseSizeBeforeUpdate = commentaryRepository.findAll().size();

        // Update the commentary
        Commentary updatedCommentary = commentaryRepository.findOne(commentary.getId());
        // Disconnect from session so that the updates on updatedCommentary are not directly saved in db
        em.detach(updatedCommentary);
        updatedCommentary
            .content(UPDATED_CONTENT);

        restCommentaryMockMvc.perform(put("/api/commentaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommentary)))
            .andExpect(status().isOk());

        // Validate the Commentary in the database
        List<Commentary> commentaryList = commentaryRepository.findAll();
        assertThat(commentaryList).hasSize(databaseSizeBeforeUpdate);
        Commentary testCommentary = commentaryList.get(commentaryList.size() - 1);
        assertThat(testCommentary.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingCommentary() throws Exception {
        int databaseSizeBeforeUpdate = commentaryRepository.findAll().size();

        // Create the Commentary

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommentaryMockMvc.perform(put("/api/commentaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentary)))
            .andExpect(status().isCreated());

        // Validate the Commentary in the database
        List<Commentary> commentaryList = commentaryRepository.findAll();
        assertThat(commentaryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommentary() throws Exception {
        // Initialize the database
        commentaryService.save(commentary);

        int databaseSizeBeforeDelete = commentaryRepository.findAll().size();

        // Get the commentary
        restCommentaryMockMvc.perform(delete("/api/commentaries/{id}", commentary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Commentary> commentaryList = commentaryRepository.findAll();
        assertThat(commentaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commentary.class);
        Commentary commentary1 = new Commentary();
        commentary1.setId(1L);
        Commentary commentary2 = new Commentary();
        commentary2.setId(commentary1.getId());
        assertThat(commentary1).isEqualTo(commentary2);
        commentary2.setId(2L);
        assertThat(commentary1).isNotEqualTo(commentary2);
        commentary1.setId(null);
        assertThat(commentary1).isNotEqualTo(commentary2);
    }
}
