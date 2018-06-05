package com.hopiestra.site.repository;

import com.hopiestra.site.domain.InternationalArticle;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the InternationalArticle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternationalArticleRepository extends JpaRepository<InternationalArticle, Long> {
    @Query("select distinct international_article from InternationalArticle international_article left join fetch international_article.tags")
    List<InternationalArticle> findAllWithEagerRelationships();

    @Query("select international_article from InternationalArticle international_article left join fetch international_article.tags where international_article.id =:id")
    InternationalArticle findOneWithEagerRelationships(@Param("id") Long id);

}
