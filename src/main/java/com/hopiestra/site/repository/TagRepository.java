package com.hopiestra.site.repository;

import com.hopiestra.site.domain.Tag;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Tag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("select distinct tag from Tag tag left join fetch tag.internationalTags")
    List<Tag> findAllWithEagerRelationships();

    @Query("select tag from Tag tag left join fetch tag.internationalTags where tag.id =:id")
    Tag findOneWithEagerRelationships(@Param("id") Long id);

}
