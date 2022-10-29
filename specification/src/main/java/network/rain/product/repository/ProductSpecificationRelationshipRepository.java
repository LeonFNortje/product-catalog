package network.rain.product.repository;

import network.rain.product.domain.ProductSpecificationRelationship;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductSpecificationRelationship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSpecificationRelationshipRepository extends JpaRepository<ProductSpecificationRelationship, String> {}
