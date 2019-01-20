package com.hopiestra.site.repository;

import com.hopiestra.site.domain.InternationalArticle;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the InternationalArticle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternationalArticleRepository extends JpaRepository<InternationalArticle, Long> {

    @Query("select internationalArticle from InternationalArticle internationalArticle where internationalArticle.article.id =:articleId and internationalArticle.language.code =:langCode")
    InternationalArticle findByArticleAndLangCode(@Param("articleId") Long articleId, @Param("langCode") String langCode);

}
