package network.rain.product.repository;

import network.rain.product.domain.ProductSpecificationCharacteristic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductSpecificationCharacteristic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSpecificationCharacteristicRepository extends JpaRepository<ProductSpecificationCharacteristic, String> {}
