package com.hopiestra.site.repository;

import com.hopiestra.site.domain.Article;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Article entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("select article from Article article where article.author.login = ?#{principal.username}")
    List<Article> findByAuthorIsCurrentUser();

    @Query("select distinct article from Article article left join fetch article.tags")
    List<Article> findAllWithEagerRelationships();

    @Query("select article from Article article left join fetch article.tags where article.id =:id")
    Article findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select distinct article from Article article where article.theme.id IN :themesId")
    Page<Article> findAllByThemes(Pageable pageable, @Param("themesId") List<Long> themesId);

}
