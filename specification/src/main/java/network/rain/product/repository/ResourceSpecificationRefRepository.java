package network.rain.product.repository;

import network.rain.product.domain.ResourceSpecificationRef;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ResourceSpecificationRef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResourceSpecificationRefRepository extends JpaRepository<ResourceSpecificationRef, String> {}
