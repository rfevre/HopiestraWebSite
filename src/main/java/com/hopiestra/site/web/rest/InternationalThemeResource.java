package com.hopiestra.site.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hopiestra.site.domain.InternationalTheme;
import com.hopiestra.site.service.InternationalThemeService;
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
 * REST controller for managing InternationalTheme.
 */
@RestController
@RequestMapping("/api")
public class InternationalThemeResource {

    private final Logger log = LoggerFactory.getLogger(InternationalThemeResource.class);

    private static final String ENTITY_NAME = "internationalTheme";

    private final InternationalThemeService internationalThemeService;

    public InternationalThemeResource(InternationalThemeService internationalThemeService) {
        this.internationalThemeService = internationalThemeService;
    }

    /**
     * POST  /international-themes : Create a new internationalTheme.
     *
     * @param internationalTheme the internationalTheme to create
     * @return the ResponseEntity with status 201 (Created) and with body the new internationalTheme, or with status 400 (Bad Request) if the internationalTheme has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/international-themes")
    @Timed
    public ResponseEntity<InternationalTheme> createInternationalTheme(@Valid @RequestBody InternationalTheme internationalTheme) throws URISyntaxException {
        log.debug("REST request to save InternationalTheme : {}", internationalTheme);
        if (internationalTheme.getId() != null) {
            throw new BadRequestAlertException("A new internationalTheme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InternationalTheme result = internationalThemeService.save(internationalTheme);
        return ResponseEntity.created(new URI("/api/international-themes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /international-themes : Updates an existing internationalTheme.
     *
     * @param internationalTheme the internationalTheme to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated internationalTheme,
     * or with status 400 (Bad Request) if the internationalTheme is not valid,
     * or with status 500 (Internal Server Error) if the internationalTheme couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/international-themes")
    @Timed
    public ResponseEntity<InternationalTheme> updateInternationalTheme(@Valid @RequestBody InternationalTheme internationalTheme) throws URISyntaxException {
        log.debug("REST request to update InternationalTheme : {}", internationalTheme);
        if (internationalTheme.getId() == null) {
            return createInternationalTheme(internationalTheme);
        }
        InternationalTheme result = internationalThemeService.save(internationalTheme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, internationalTheme.getId().toString()))
            .body(result);
    }

    /**
     * GET  /international-themes : get all the internationalThemes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of internationalThemes in body
     */
    @GetMapping("/international-themes")
    @Timed
    public ResponseEntity<List<InternationalTheme>> getAllInternationalThemes(Pageable pageable) {
        log.debug("REST request to get a page of InternationalThemes");
        Page<InternationalTheme> page = internationalThemeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/international-themes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /international-themes/:id : get the "id" internationalTheme.
     *
     * @param id the id of the internationalTheme to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the internationalTheme, or with status 404 (Not Found)
     */
    @GetMapping("/international-themes/{id}")
    @Timed
    public ResponseEntity<InternationalTheme> getInternationalTheme(@PathVariable Long id) {
        log.debug("REST request to get InternationalTheme : {}", id);
        InternationalTheme internationalTheme = internationalThemeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(internationalTheme));
    }

    /**
     * DELETE  /international-themes/:id : delete the "id" internationalTheme.
     *
     * @param id the id of the internationalTheme to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/international-themes/{id}")
    @Timed
    public ResponseEntity<Void> deleteInternationalTheme(@PathVariable Long id) {
        log.debug("REST request to delete InternationalTheme : {}", id);
        internationalThemeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

        /**
     * GET  /international-themes/theme/:themeId/lang-code/:langCode : get the "id" InternationalTheme.
     *
     * @param id the id of the InternationalTheme to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the InternationalTheme, or with status 404 (Not Found)
     */
    @GetMapping("/international-themes/theme/{themeId}/lang-code/{langCode}")
    @Timed
    public ResponseEntity<InternationalTheme> getInternationalThemeByArticleIdAndLangCode(@PathVariable Long themeId, @PathVariable String langCode) {
        log.debug("REST request to get InternationalTheme by theme id and language code : {}", themeId, langCode);
        InternationalTheme internationalTheme = internationalThemeService.findByThemeAndLangCode(themeId, langCode);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(internationalTheme));
    }
}
