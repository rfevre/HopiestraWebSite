package com.hopiestra.site.service;

import com.hopiestra.site.domain.Commentary;
import com.hopiestra.site.repository.CommentaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Commentary.
 */
@Service
@Transactional
public class CommentaryService {

    private final Logger log = LoggerFactory.getLogger(CommentaryService.class);

    private final CommentaryRepository commentaryRepository;

    public CommentaryService(CommentaryRepository commentaryRepository) {
        this.commentaryRepository = commentaryRepository;
    }

    /**
     * Save a commentary.
     *
     * @param commentary the entity to save
     * @return the persisted entity
     */
    public Commentary save(Commentary commentary) {
        log.debug("Request to save Commentary : {}", commentary);
        return commentaryRepository.save(commentary);
    }

    /**
     * Get all the commentaries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Commentary> findAll(Pageable pageable) {
        log.debug("Request to get all Commentaries");
        return commentaryRepository.findAll(pageable);
    }

    /**
     * Get one commentary by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Commentary findOne(Long id) {
        log.debug("Request to get Commentary : {}", id);
        return commentaryRepository.findOne(id);
    }

    /**
     * Delete the commentary by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Commentary : {}", id);
        commentaryRepository.delete(id);
    }
}
