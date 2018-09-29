package com.hopiestra.site.repository;

import java.util.List;

import com.hopiestra.site.domain.Theme;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Theme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    List<Theme> findByParentThemeIsNullOrderByOrder();

}
