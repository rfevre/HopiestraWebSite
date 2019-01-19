package com.hopiestra.site.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.hopiestra.site.domain.Article;
import com.hopiestra.site.domain.Theme;
import com.hopiestra.site.repository.ArticleRepository;
import com.hopiestra.site.repository.ThemeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Article.
 */
@Service
@Transactional
public class ArticleService {

    private final Logger log = LoggerFactory.getLogger(ArticleService.class);

    private final ArticleRepository articleRepository;

    private final ThemeRepository themeRepository;

    public ArticleService(ArticleRepository articleRepository, ThemeRepository themeRepository) {
        this.articleRepository = articleRepository;
        this.themeRepository = themeRepository;
    }

    /**
     * Save a article.
     *
     * @param article the entity to save
     * @return the persisted entity
     */
    public Article save(Article article) {
        log.debug("Request to save Article : {}", article);

        Instant dateNow = Instant.now(); 

        if(article.getId() == null) {
            article.creationDate(dateNow);
        }
        article.updateDate(dateNow);

        return articleRepository.save(article);
    }

    /**
     * Get all the articles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Article> findAll(Pageable pageable) {
        log.debug("Request to get all Articles");
        return articleRepository.findAll(pageable);
    }

    /**
     * Get all the articles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Article> findAllByTheme(Pageable pageable, Long themeId) {
        log.debug("Request to get all Articles by theme : {}", themeId);

        List<Long> themesId = new ArrayList<>();
        List<Theme> themes = themeRepository.findAll();

        for(Theme theme: themes) {
            Long subThemeId = theme.getId();
            while(theme.getParentTheme() != null) {
                theme = theme.getParentTheme();

                if(theme.getId().equals(themeId)) {
                    themesId.add(subThemeId);
                }
            }
        }

        themesId.add(themeId);

        return articleRepository.findAllByThemes(pageable, themesId);
    }

    /**
     * Get one article by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Article findOne(Long id) {
        log.debug("Request to get Article : {}", id);
        return articleRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the article by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Article : {}", id);
        articleRepository.delete(id);
    }
}
