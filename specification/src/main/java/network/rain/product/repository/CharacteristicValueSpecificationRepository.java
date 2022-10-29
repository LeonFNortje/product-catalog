package network.rain.product.repository;

import network.rain.product.domain.CharacteristicValueSpecification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CharacteristicValueSpecification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CharacteristicValueSpecificationRepository extends JpaRepository<CharacteristicValueSpecification, Long> {}
