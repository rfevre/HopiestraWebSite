package com.hopiestra.site.service;

import com.hopiestra.site.domain.InternationalArticle;
import com.hopiestra.site.repository.InternationalArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing InternationalArticle.
 */
@Service
@Transactional
public class InternationalArticleService {

    private final Logger log = LoggerFactory.getLogger(InternationalArticleService.class);

    private final InternationalArticleRepository internationalArticleRepository;

    public InternationalArticleService(InternationalArticleRepository internationalArticleRepository) {
        this.internationalArticleRepository = internationalArticleRepository;
    }

    /**
     * Save a internationalArticle.
     *
     * @param internationalArticle the entity to save
     * @return the persisted entity
     */
    public InternationalArticle save(InternationalArticle internationalArticle) {
        log.debug("Request to save InternationalArticle : {}", internationalArticle);
        return internationalArticleRepository.save(internationalArticle);
    }

    /**
     * Get all the internationalArticles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InternationalArticle> findAll(Pageable pageable) {
        log.debug("Request to get all InternationalArticles");
        return internationalArticleRepository.findAll(pageable);
    }

    /**
     * Get one internationalArticle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public InternationalArticle findOne(Long id) {
        log.debug("Request to get InternationalArticle : {}", id);
        return internationalArticleRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the internationalArticle by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InternationalArticle : {}", id);
        internationalArticleRepository.delete(id);
    }
}
