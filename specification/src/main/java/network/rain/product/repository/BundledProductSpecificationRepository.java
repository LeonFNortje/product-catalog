package network.rain.product.repository;

import network.rain.product.domain.BundledProductSpecification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BundledProductSpecification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BundledProductSpecificationRepository extends JpaRepository<BundledProductSpecification, String> {}
