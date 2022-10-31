package network.rain.account.repository;

import network.rain.account.domain.RelatedPlace;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RelatedPlace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelatedPlaceRepository extends JpaRepository<RelatedPlace, String> {}
