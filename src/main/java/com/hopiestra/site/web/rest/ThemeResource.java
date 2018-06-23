package com.hopiestra.site.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hopiestra.site.domain.Theme;
import com.hopiestra.site.security.AuthoritiesConstants;
import com.hopiestra.site.service.ThemeService;
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
 * REST controller for managing Theme.
 */
@RestController
@RequestMapping("/api")
public class ThemeResource {

    private final Logger log = LoggerFactory.getLogger(ThemeResource.class);

    private static final String ENTITY_NAME = "theme";

    private final ThemeService themeService;

    public ThemeResource(ThemeService themeService) {
        this.themeService = themeService;
    }

    /**
     * POST  /themes : Create a new theme.
     *
     * @param theme the theme to create
     * @return the ResponseEntity with status 201 (Created) and with body the new theme, or with status 400 (Bad Request) if the theme has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/themes")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Theme> createTheme(@Valid @RequestBody Theme theme) throws URISyntaxException {
        log.debug("REST request to save Theme : {}", theme);
        if (theme.getId() != null) {
            throw new BadRequestAlertException("A new theme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Theme result = themeService.save(theme);
        return ResponseEntity.created(new URI("/api/themes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /themes : Updates an existing theme.
     *
     * @param theme the theme to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated theme,
     * or with status 400 (Bad Request) if the theme is not valid,
     * or with status 500 (Internal Server Error) if the theme couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/themes")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Theme> updateTheme(@Valid @RequestBody Theme theme) throws URISyntaxException {
        log.debug("REST request to update Theme : {}", theme);
        if (theme.getId() == null) {
            return createTheme(theme);
        }
        Theme result = themeService.save(theme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, theme.getId().toString()))
            .body(result);
    }

    /**
     * GET  /themes : get all the themes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of themes in body
     */
    @GetMapping("/themes")
    @Timed
    public ResponseEntity<List<Theme>> getAllThemes(Pageable pageable) {
        log.debug("REST request to get a page of Themes");
        Page<Theme> page = themeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/themes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /themes/:id : get the "id" theme.
     *
     * @param id the id of the theme to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the theme, or with status 404 (Not Found)
     */
    @GetMapping("/themes/{id}")
    @Timed
    public ResponseEntity<Theme> getTheme(@PathVariable Long id) {
        log.debug("REST request to get Theme : {}", id);
        Theme theme = themeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(theme));
    }

    /**
     * DELETE  /themes/:id : delete the "id" theme.
     *
     * @param id the id of the theme to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/themes/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        log.debug("REST request to delete Theme : {}", id);
        themeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
