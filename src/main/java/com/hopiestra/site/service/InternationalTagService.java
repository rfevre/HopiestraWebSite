package com.hopiestra.site.service;

import com.hopiestra.site.domain.InternationalTag;
import com.hopiestra.site.repository.InternationalTagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing InternationalTag.
 */
@Service
@Transactional
public class InternationalTagService {

    private final Logger log = LoggerFactory.getLogger(InternationalTagService.class);

    private final InternationalTagRepository internationalTagRepository;

    public InternationalTagService(InternationalTagRepository internationalTagRepository) {
        this.internationalTagRepository = internationalTagRepository;
    }

    /**
     * Save a internationalTag.
     *
     * @param internationalTag the entity to save
     * @return the persisted entity
     */
    public InternationalTag save(InternationalTag internationalTag) {
        log.debug("Request to save InternationalTag : {}", internationalTag);
        return internationalTagRepository.save(internationalTag);
    }

    /**
     * Get all the internationalTags.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InternationalTag> findAll(Pageable pageable) {
        log.debug("Request to get all InternationalTags");
        return internationalTagRepository.findAll(pageable);
    }

    /**
     * Get one internationalTag by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public InternationalTag findOne(Long id) {
        log.debug("Request to get InternationalTag : {}", id);
        return internationalTagRepository.findOne(id);
    }

    /**
     * Delete the internationalTag by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InternationalTag : {}", id);
        internationalTagRepository.delete(id);
    }
}
