package com.hopiestra.site.service;

import com.hopiestra.site.domain.ThemeSubscription;
import com.hopiestra.site.repository.ThemeSubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ThemeSubscription.
 */
@Service
@Transactional
public class ThemeSubscriptionService {

    private final Logger log = LoggerFactory.getLogger(ThemeSubscriptionService.class);

    private final ThemeSubscriptionRepository themeSubscriptionRepository;

    public ThemeSubscriptionService(ThemeSubscriptionRepository themeSubscriptionRepository) {
        this.themeSubscriptionRepository = themeSubscriptionRepository;
    }

    /**
     * Save a themeSubscription.
     *
     * @param themeSubscription the entity to save
     * @return the persisted entity
     */
    public ThemeSubscription save(ThemeSubscription themeSubscription) {
        log.debug("Request to save ThemeSubscription : {}", themeSubscription);
        return themeSubscriptionRepository.save(themeSubscription);
    }

    /**
     * Get all the themeSubscriptions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ThemeSubscription> findAll(Pageable pageable) {
        log.debug("Request to get all ThemeSubscriptions");
        return themeSubscriptionRepository.findAll(pageable);
    }

    /**
     * Get one themeSubscription by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ThemeSubscription findOne(Long id) {
        log.debug("Request to get ThemeSubscription : {}", id);
        return themeSubscriptionRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the themeSubscription by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ThemeSubscription : {}", id);
        themeSubscriptionRepository.delete(id);
    }
}
