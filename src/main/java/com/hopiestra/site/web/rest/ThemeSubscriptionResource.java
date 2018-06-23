package com.hopiestra.site.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hopiestra.site.domain.ThemeSubscription;
import com.hopiestra.site.security.AuthoritiesConstants;
import com.hopiestra.site.service.ThemeSubscriptionService;
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
 * REST controller for managing ThemeSubscription.
 */
@RestController
@RequestMapping("/api")
public class ThemeSubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(ThemeSubscriptionResource.class);

    private static final String ENTITY_NAME = "themeSubscription";

    private final ThemeSubscriptionService themeSubscriptionService;

    public ThemeSubscriptionResource(ThemeSubscriptionService themeSubscriptionService) {
        this.themeSubscriptionService = themeSubscriptionService;
    }

    /**
     * POST  /theme-subscriptions : Create a new themeSubscription.
     *
     * @param themeSubscription the themeSubscription to create
     * @return the ResponseEntity with status 201 (Created) and with body the new themeSubscription, or with status 400 (Bad Request) if the themeSubscription has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/theme-subscriptions")
    @Timed
    public ResponseEntity<ThemeSubscription> createThemeSubscription(@Valid @RequestBody ThemeSubscription themeSubscription) throws URISyntaxException {
        log.debug("REST request to save ThemeSubscription : {}", themeSubscription);
        if (themeSubscription.getId() != null) {
            throw new BadRequestAlertException("A new themeSubscription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThemeSubscription result = themeSubscriptionService.save(themeSubscription);
        return ResponseEntity.created(new URI("/api/theme-subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /theme-subscriptions : Updates an existing themeSubscription.
     *
     * @param themeSubscription the themeSubscription to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated themeSubscription,
     * or with status 400 (Bad Request) if the themeSubscription is not valid,
     * or with status 500 (Internal Server Error) if the themeSubscription couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/theme-subscriptions")
    @Timed
    public ResponseEntity<ThemeSubscription> updateThemeSubscription(@Valid @RequestBody ThemeSubscription themeSubscription) throws URISyntaxException {
        log.debug("REST request to update ThemeSubscription : {}", themeSubscription);
        if (themeSubscription.getId() == null) {
            return createThemeSubscription(themeSubscription);
        }
        ThemeSubscription result = themeSubscriptionService.save(themeSubscription);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, themeSubscription.getId().toString()))
            .body(result);
    }

    /**
     * GET  /theme-subscriptions : get all the themeSubscriptions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of themeSubscriptions in body
     */
    @GetMapping("/theme-subscriptions")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<ThemeSubscription>> getAllThemeSubscriptions(Pageable pageable) {
        log.debug("REST request to get a page of ThemeSubscriptions");
        Page<ThemeSubscription> page = themeSubscriptionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/theme-subscriptions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /theme-subscriptions/:id : get the "id" themeSubscription.
     *
     * @param id the id of the themeSubscription to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the themeSubscription, or with status 404 (Not Found)
     */
    @GetMapping("/theme-subscriptions/{id}")
    @Timed
    public ResponseEntity<ThemeSubscription> getThemeSubscription(@PathVariable Long id) {
        log.debug("REST request to get ThemeSubscription : {}", id);
        ThemeSubscription themeSubscription = themeSubscriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(themeSubscription));
    }

    /**
     * DELETE  /theme-subscriptions/:id : delete the "id" themeSubscription.
     *
     * @param id the id of the themeSubscription to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/theme-subscriptions/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteThemeSubscription(@PathVariable Long id) {
        log.debug("REST request to delete ThemeSubscription : {}", id);
        themeSubscriptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
