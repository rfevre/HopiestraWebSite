package com.hopiestra.site.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hopiestra.site.domain.InternationalTag;
import com.hopiestra.site.service.InternationalTagService;
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
 * REST controller for managing InternationalTag.
 */
@RestController
@RequestMapping("/api")
public class InternationalTagResource {

    private final Logger log = LoggerFactory.getLogger(InternationalTagResource.class);

    private static final String ENTITY_NAME = "internationalTag";

    private final InternationalTagService internationalTagService;

    public InternationalTagResource(InternationalTagService internationalTagService) {
        this.internationalTagService = internationalTagService;
    }

    /**
     * POST  /international-tags : Create a new internationalTag.
     *
     * @param internationalTag the internationalTag to create
     * @return the ResponseEntity with status 201 (Created) and with body the new internationalTag, or with status 400 (Bad Request) if the internationalTag has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/international-tags")
    @Timed
    public ResponseEntity<InternationalTag> createInternationalTag(@Valid @RequestBody InternationalTag internationalTag) throws URISyntaxException {
        log.debug("REST request to save InternationalTag : {}", internationalTag);
        if (internationalTag.getId() != null) {
            throw new BadRequestAlertException("A new internationalTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InternationalTag result = internationalTagService.save(internationalTag);
        return ResponseEntity.created(new URI("/api/international-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /international-tags : Updates an existing internationalTag.
     *
     * @param internationalTag the internationalTag to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated internationalTag,
     * or with status 400 (Bad Request) if the internationalTag is not valid,
     * or with status 500 (Internal Server Error) if the internationalTag couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/international-tags")
    @Timed
    public ResponseEntity<InternationalTag> updateInternationalTag(@Valid @RequestBody InternationalTag internationalTag) throws URISyntaxException {
        log.debug("REST request to update InternationalTag : {}", internationalTag);
        if (internationalTag.getId() == null) {
            return createInternationalTag(internationalTag);
        }
        InternationalTag result = internationalTagService.save(internationalTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, internationalTag.getId().toString()))
            .body(result);
    }

    /**
     * GET  /international-tags : get all the internationalTags.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of internationalTags in body
     */
    @GetMapping("/international-tags")
    @Timed
    public ResponseEntity<List<InternationalTag>> getAllInternationalTags(Pageable pageable) {
        log.debug("REST request to get a page of InternationalTags");
        Page<InternationalTag> page = internationalTagService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/international-tags");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /international-tags/:id : get the "id" internationalTag.
     *
     * @param id the id of the internationalTag to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the internationalTag, or with status 404 (Not Found)
     */
    @GetMapping("/international-tags/{id}")
    @Timed
    public ResponseEntity<InternationalTag> getInternationalTag(@PathVariable Long id) {
        log.debug("REST request to get InternationalTag : {}", id);
        InternationalTag internationalTag = internationalTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(internationalTag));
    }

    /**
     * DELETE  /international-tags/:id : delete the "id" internationalTag.
     *
     * @param id the id of the internationalTag to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/international-tags/{id}")
    @Timed
    public ResponseEntity<Void> deleteInternationalTag(@PathVariable Long id) {
        log.debug("REST request to delete InternationalTag : {}", id);
        internationalTagService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
