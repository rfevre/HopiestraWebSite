package com.hopiestra.site.repository;

import com.hopiestra.site.domain.InternationalArticle;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InternationalArticle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternationalArticleRepository extends JpaRepository<InternationalArticle, Long> {

}
