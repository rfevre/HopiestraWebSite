package com.hopiestra.site.service;

import com.hopiestra.site.domain.Theme;
import com.hopiestra.site.repository.ThemeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Theme.
 */
@Service
@Transactional
public class ThemeService {

    private final Logger log = LoggerFactory.getLogger(ThemeService.class);

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    /**
     * Save a theme.
     *
     * @param theme the entity to save
     * @return the persisted entity
     */
    public Theme save(Theme theme) {
        log.debug("Request to save Theme : {}", theme);
        return themeRepository.save(theme);
    }

    /**
     * Get all the themes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Theme> findAll(Pageable pageable) {
        log.debug("Request to get all Themes");
        return themeRepository.findAll(pageable);
    }

    /**
     * Get one theme by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Theme findOne(Long id) {
        log.debug("Request to get Theme : {}", id);
        return themeRepository.findOne(id);
    }

    /**
     * Delete the theme by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Theme : {}", id);
        themeRepository.delete(id);
    }
}
