package com.hopiestra.site.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hopiestra.site.domain.InternationalArticle;
import com.hopiestra.site.security.AuthoritiesConstants;
import com.hopiestra.site.service.InternationalArticleService;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InternationalArticle.
 */
@RestController
@RequestMapping("/api")
public class InternationalArticleResource {

    private final Logger log = LoggerFactory.getLogger(InternationalArticleResource.class);

    private static final String ENTITY_NAME = "internationalArticle";

    private final InternationalArticleService internationalArticleService;

    public InternationalArticleResource(InternationalArticleService internationalArticleService) {
        this.internationalArticleService = internationalArticleService;
    }

    /**
     * POST  /international-articles : Create a new internationalArticle.
     *
     * @param internationalArticle the internationalArticle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new internationalArticle, or with status 400 (Bad Request) if the internationalArticle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/international-articles")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<InternationalArticle> createInternationalArticle(@Valid @RequestBody InternationalArticle internationalArticle) throws URISyntaxException {
        log.debug("REST request to save InternationalArticle : {}", internationalArticle);
        if (internationalArticle.getId() != null) {
            throw new BadRequestAlertException("A new internationalArticle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InternationalArticle result = internationalArticleService.save(internationalArticle);
        return ResponseEntity.created(new URI("/api/international-articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /international-articles : Updates an existing internationalArticle.
     *
     * @param internationalArticle the internationalArticle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated internationalArticle,
     * or with status 400 (Bad Request) if the internationalArticle is not valid,
     * or with status 500 (Internal Server Error) if the internationalArticle couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/international-articles")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<InternationalArticle> updateInternationalArticle(@Valid @RequestBody InternationalArticle internationalArticle) throws URISyntaxException {
        log.debug("REST request to update InternationalArticle : {}", internationalArticle);
        if (internationalArticle.getId() == null) {
            return createInternationalArticle(internationalArticle);
        }
        InternationalArticle result = internationalArticleService.save(internationalArticle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, internationalArticle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /international-articles : get all the internationalArticles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of internationalArticles in body
     */
    @GetMapping("/international-articles")
    @Timed
    public ResponseEntity<List<InternationalArticle>> getAllInternationalArticles(Pageable pageable) {
        log.debug("REST request to get a page of InternationalArticles");
        Page<InternationalArticle> page = internationalArticleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/international-articles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /international-articles/:id : get the "id" internationalArticle.
     *
     * @param id the id of the internationalArticle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the internationalArticle, or with status 404 (Not Found)
     */
    @GetMapping("/international-articles/{id}")
    @Timed
    public ResponseEntity<InternationalArticle> getInternationalArticle(@PathVariable Long id) {
        log.debug("REST request to get InternationalArticle : {}", id);
        InternationalArticle internationalArticle = internationalArticleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(internationalArticle));
    }

    /**
     * DELETE  /international-articles/:id : delete the "id" internationalArticle.
     *
     * @param id the id of the internationalArticle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/international-articles/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteInternationalArticle(@PathVariable Long id) {
        log.debug("REST request to delete InternationalArticle : {}", id);
        internationalArticleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
