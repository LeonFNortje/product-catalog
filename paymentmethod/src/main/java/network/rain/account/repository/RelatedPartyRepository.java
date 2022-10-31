package network.rain.account.repository;

import network.rain.account.domain.RelatedParty;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RelatedParty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelatedPartyRepository extends JpaRepository<RelatedParty, String> {}
