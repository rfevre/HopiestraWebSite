package com.hopiestra.site.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hopiestra.site.domain.Commentary;
import com.hopiestra.site.service.CommentaryService;
import com.hopiestra.site.web.rest.errors.BadRequestAlertException;
import com.hopiestra.site.web.rest.util.HeaderUtil;
import com.hopiestra.site.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Commentary.
 */
@RestController
@RequestMapping("/api")
public class CommentaryResource {

    private final Logger log = LoggerFactory.getLogger(CommentaryResource.class);

    private static final String ENTITY_NAME = "commentary";

    private final CommentaryService commentaryService;

    public CommentaryResource(CommentaryService commentaryService) {
        this.commentaryService = commentaryService;
    }

    /**
     * POST  /commentaries : Create a new commentary.
     *
     * @param commentary the commentary to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commentary, or with status 400 (Bad Request) if the commentary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commentaries")
    @Timed
    public ResponseEntity<Commentary> createCommentary(@Valid @RequestBody Commentary commentary) throws URISyntaxException {
        log.debug("REST request to save Commentary : {}", commentary);
        if (commentary.getId() != null) {
            throw new BadRequestAlertException("A new commentary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Commentary result = commentaryService.save(commentary);
        return ResponseEntity.created(new URI("/api/commentaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commentaries : Updates an existing commentary.
     *
     * @param commentary the commentary to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commentary,
     * or with status 400 (Bad Request) if the commentary is not valid,
     * or with status 500 (Internal Server Error) if the commentary couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commentaries")
    @Timed
    public ResponseEntity<Commentary> updateCommentary(@Valid @RequestBody Commentary commentary) throws URISyntaxException {
        log.debug("REST request to update Commentary : {}", commentary);
        if (commentary.getId() == null) {
            return createCommentary(commentary);
        }
        Commentary result = commentaryService.save(commentary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commentary.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commentaries : get all the commentaries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of commentaries in body
     */
    @GetMapping("/commentaries")
    @Timed
    public ResponseEntity<List<Commentary>> getAllCommentaries(Pageable pageable) {
        log.debug("REST request to get a page of Commentaries");
        Page<Commentary> page = commentaryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commentaries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /commentaries/:id : get the "id" commentary.
     *
     * @param id the id of the commentary to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commentary, or with status 404 (Not Found)
     */
    @GetMapping("/commentaries/{id}")
    @Timed
    public ResponseEntity<Commentary> getCommentary(@PathVariable Long id) {
        log.debug("REST request to get Commentary : {}", id);
        Commentary commentary = commentaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commentary));
    }

    /**
     * DELETE  /commentaries/:id : delete the "id" commentary.
     *
     * @param id the id of the commentary to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commentaries/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommentary(@PathVariable Long id) {
        log.debug("REST request to delete Commentary : {}", id);
        commentaryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
