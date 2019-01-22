package com.hopiestra.site.repository;

import com.hopiestra.site.domain.InternationalTheme;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InternationalTheme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternationalThemeRepository extends JpaRepository<InternationalTheme, Long> {

}
