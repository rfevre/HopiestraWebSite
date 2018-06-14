package com.hopiestra.site.repository;

import com.hopiestra.site.domain.ThemeSubscription;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the ThemeSubscription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThemeSubscriptionRepository extends JpaRepository<ThemeSubscription, Long> {
    @Query("select distinct theme_subscription from ThemeSubscription theme_subscription left join fetch theme_subscription.themes")
    List<ThemeSubscription> findAllWithEagerRelationships();

    @Query("select theme_subscription from ThemeSubscription theme_subscription left join fetch theme_subscription.themes where theme_subscription.id =:id")
    ThemeSubscription findOneWithEagerRelationships(@Param("id") Long id);

}
