package network.rain.product.repository;

import network.rain.product.domain.ProductSpecificationCharacteristicRelationship;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductSpecificationCharacteristicRelationship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSpecificationCharacteristicRelationshipRepository
    extends JpaRepository<ProductSpecificationCharacteristicRelationship, String> {}
