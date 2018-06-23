package com.hopiestra.site.web.rest;

import com.hopiestra.site.HopiestraWebSiteApp;

import com.hopiestra.site.domain.Article;
import com.hopiestra.site.domain.User;
import com.hopiestra.site.repository.ArticleRepository;
import com.hopiestra.site.service.ArticleService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.hopiestra.site.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ArticleResource REST controller.
 *
 * @see ArticleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HopiestraWebSiteApp.class)
public class ArticleResourceIntTest {

    private static final byte[] DEFAULT_BACKGROUND_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BACKGROUND_PICTURE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_BACKGROUND_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BACKGROUND_PICTURE_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_PUBLICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PUBLICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ADMIN_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_ADMIN_TITLE = "BBBBBBBBBB";

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArticleMockMvc;

    private Article article;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArticleResource articleResource = new ArticleResource(articleService);
        this.restArticleMockMvc = MockMvcBuilders.standaloneSetup(articleResource)
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
    public static Article createEntity(EntityManager em) {
        Article article = new Article()
            .backgroundPicture(DEFAULT_BACKGROUND_PICTURE)
            .backgroundPictureContentType(DEFAULT_BACKGROUND_PICTURE_CONTENT_TYPE)
            .publicationDate(DEFAULT_PUBLICATION_DATE)
            .updateDate(DEFAULT_UPDATE_DATE)
            .creationDate(DEFAULT_CREATION_DATE)
            .deleteDate(DEFAULT_DELETE_DATE)
            .adminTitle(DEFAULT_ADMIN_TITLE);
        // Add required entity
        User author = UserResourceIntTest.createEntity(em);
        em.persist(author);
        em.flush();
        article.setAuthor(author);
        return article;
    }

    @Before
    public void initTest() {
        article = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticle() throws Exception {
        int databaseSizeBeforeCreate = articleRepository.findAll().size();

        // Create the Article
        restArticleMockMvc.perform(post("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isCreated());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeCreate + 1);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getBackgroundPicture()).isEqualTo(DEFAULT_BACKGROUND_PICTURE);
        assertThat(testArticle.getBackgroundPictureContentType()).isEqualTo(DEFAULT_BACKGROUND_PICTURE_CONTENT_TYPE);
        assertThat(testArticle.getPublicationDate()).isEqualTo(DEFAULT_PUBLICATION_DATE);
        assertThat(testArticle.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testArticle.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testArticle.getDeleteDate()).isEqualTo(DEFAULT_DELETE_DATE);
        assertThat(testArticle.getAdminTitle()).isEqualTo(DEFAULT_ADMIN_TITLE);
    }

    @Test
    @Transactional
    public void createArticleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articleRepository.findAll().size();

        // Create the Article with an existing ID
        article.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleMockMvc.perform(post("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPublicationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleRepository.findAll().size();
        // set the field null
        article.setPublicationDate(null);

        // Create the Article, which fails.

        restArticleMockMvc.perform(post("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isBadRequest());

        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdminTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleRepository.findAll().size();
        // set the field null
        article.setAdminTitle(null);

        // Create the Article, which fails.

        restArticleMockMvc.perform(post("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isBadRequest());

        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArticles() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get all the articleList
        restArticleMockMvc.perform(get("/api/articles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(article.getId().intValue())))
            .andExpect(jsonPath("$.[*].backgroundPictureContentType").value(hasItem(DEFAULT_BACKGROUND_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].backgroundPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_BACKGROUND_PICTURE))))
            .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].deleteDate").value(hasItem(DEFAULT_DELETE_DATE.toString())))
            .andExpect(jsonPath("$.[*].adminTitle").value(hasItem(DEFAULT_ADMIN_TITLE.toString())));
    }

    @Test
    @Transactional
    public void getArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get the article
        restArticleMockMvc.perform(get("/api/articles/{id}", article.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(article.getId().intValue()))
            .andExpect(jsonPath("$.backgroundPictureContentType").value(DEFAULT_BACKGROUND_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.backgroundPicture").value(Base64Utils.encodeToString(DEFAULT_BACKGROUND_PICTURE)))
            .andExpect(jsonPath("$.publicationDate").value(DEFAULT_PUBLICATION_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.deleteDate").value(DEFAULT_DELETE_DATE.toString()))
            .andExpect(jsonPath("$.adminTitle").value(DEFAULT_ADMIN_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArticle() throws Exception {
        // Get the article
        restArticleMockMvc.perform(get("/api/articles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticle() throws Exception {
        // Initialize the database
        articleService.save(article);

        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Update the article
        Article updatedArticle = articleRepository.findOne(article.getId());
        // Disconnect from session so that the updates on updatedArticle are not directly saved in db
        em.detach(updatedArticle);
        updatedArticle
            .backgroundPicture(UPDATED_BACKGROUND_PICTURE)
            .backgroundPictureContentType(UPDATED_BACKGROUND_PICTURE_CONTENT_TYPE)
            .publicationDate(UPDATED_PUBLICATION_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .creationDate(UPDATED_CREATION_DATE)
            .deleteDate(UPDATED_DELETE_DATE)
            .adminTitle(UPDATED_ADMIN_TITLE);

        restArticleMockMvc.perform(put("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticle)))
            .andExpect(status().isOk());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getBackgroundPicture()).isEqualTo(UPDATED_BACKGROUND_PICTURE);
        assertThat(testArticle.getBackgroundPictureContentType()).isEqualTo(UPDATED_BACKGROUND_PICTURE_CONTENT_TYPE);
        assertThat(testArticle.getPublicationDate()).isEqualTo(UPDATED_PUBLICATION_DATE);
        assertThat(testArticle.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testArticle.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testArticle.getDeleteDate()).isEqualTo(UPDATED_DELETE_DATE);
        assertThat(testArticle.getAdminTitle()).isEqualTo(UPDATED_ADMIN_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Create the Article

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArticleMockMvc.perform(put("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isCreated());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArticle() throws Exception {
        // Initialize the database
        articleService.save(article);

        int databaseSizeBeforeDelete = articleRepository.findAll().size();

        // Get the article
        restArticleMockMvc.perform(delete("/api/articles/{id}", article.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Article.class);
        Article article1 = new Article();
        article1.setId(1L);
        Article article2 = new Article();
        article2.setId(article1.getId());
        assertThat(article1).isEqualTo(article2);
        article2.setId(2L);
        assertThat(article1).isNotEqualTo(article2);
        article1.setId(null);
        assertThat(article1).isNotEqualTo(article2);
    }
}
