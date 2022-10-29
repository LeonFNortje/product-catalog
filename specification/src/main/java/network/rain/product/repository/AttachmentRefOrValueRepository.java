package network.rain.product.repository;

import network.rain.product.domain.AttachmentRefOrValue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AttachmentRefOrValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachmentRefOrValueRepository extends JpaRepository<AttachmentRefOrValue, String> {}
