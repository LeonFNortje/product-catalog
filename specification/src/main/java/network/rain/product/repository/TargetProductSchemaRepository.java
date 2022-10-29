package network.rain.product.repository;

import network.rain.product.domain.TargetProductSchema;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TargetProductSchema entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TargetProductSchemaRepository extends JpaRepository<TargetProductSchema, Long> {}
