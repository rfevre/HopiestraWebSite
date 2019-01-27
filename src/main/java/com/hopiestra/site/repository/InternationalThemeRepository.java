package com.hopiestra.site.repository;

import com.hopiestra.site.domain.InternationalTheme;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the InternationalTheme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternationalThemeRepository extends JpaRepository<InternationalTheme, Long> {

    @Query("select internationalTheme from InternationalTheme internationalTheme where internationalTheme.theme.id =:themeId and internationalTheme.language.code =:langCode")
    InternationalTheme findByThemeAndLangCode(@Param("themeId") Long themeId, @Param("langCode") String langCode);
}
