package com.hopiestra.site.service;

import com.hopiestra.site.domain.InternationalTheme;
import com.hopiestra.site.repository.InternationalThemeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing InternationalTheme.
 */
@Service
@Transactional
public class InternationalThemeService {

    private final Logger log = LoggerFactory.getLogger(InternationalThemeService.class);

    private final InternationalThemeRepository internationalThemeRepository;

    public InternationalThemeService(InternationalThemeRepository internationalThemeRepository) {
        this.internationalThemeRepository = internationalThemeRepository;
    }

    /**
     * Save a internationalTheme.
     *
     * @param internationalTheme the entity to save
     * @return the persisted entity
     */
    public InternationalTheme save(InternationalTheme internationalTheme) {
        log.debug("Request to save InternationalTheme : {}", internationalTheme);
        return internationalThemeRepository.save(internationalTheme);
    }

    /**
     * Get all the internationalThemes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InternationalTheme> findAll(Pageable pageable) {
        log.debug("Request to get all InternationalThemes");
        return internationalThemeRepository.findAll(pageable);
    }

    /**
     * Get one internationalTheme by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public InternationalTheme findOne(Long id) {
        log.debug("Request to get InternationalTheme : {}", id);
        return internationalThemeRepository.findOne(id);
    }

    /**
     * Delete the internationalTheme by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InternationalTheme : {}", id);
        internationalThemeRepository.delete(id);
    }
}
