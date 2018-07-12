package com.hopiestra.site.repository;

import com.hopiestra.site.domain.InternationalTag;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InternationalTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternationalTagRepository extends JpaRepository<InternationalTag, Long> {

}
