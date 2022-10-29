package network.rain.product.repository;

import network.rain.product.domain.ServiceSpecificationRef;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServiceSpecificationRef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceSpecificationRefRepository extends JpaRepository<ServiceSpecificationRef, String> {}
