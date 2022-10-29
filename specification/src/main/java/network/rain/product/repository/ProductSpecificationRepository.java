package network.rain.product.repository;

import network.rain.product.domain.ProductSpecification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductSpecification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification, String> {}
