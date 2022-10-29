package network.rain.product.repository;

import network.rain.product.domain.ProductSpecificationCharacteristic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the ProductSpecificationCharacteristic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSpecificationCharacteristicRepository
    extends ReactiveCrudRepository<ProductSpecificationCharacteristic, String>, ProductSpecificationCharacteristicRepositoryInternal {
    Flux<ProductSpecificationCharacteristic> findAllBy(Pageable pageable);

    @Query(
        "SELECT * FROM product_specification_characteristic entity WHERE entity.product_specification_characteristic_relationship_id = :id"
    )
    Flux<ProductSpecificationCharacteristic> findByProductSpecificationCharacteristicRelationship(String id);

    @Query(
        "SELECT * FROM product_specification_characteristic entity WHERE entity.product_specification_characteristic_relationship_id IS NULL"
    )
    Flux<ProductSpecificationCharacteristic> findAllWhereProductSpecificationCharacteristicRelationshipIsNull();

    @Override
    <S extends ProductSpecificationCharacteristic> Mono<S> save(S entity);

    @Override
    Flux<ProductSpecificationCharacteristic> findAll();

    @Override
    Mono<ProductSpecificationCharacteristic> findById(String id);

    @Override
    Mono<Void> deleteById(String id);
}

interface ProductSpecificationCharacteristicRepositoryInternal {
    <S extends ProductSpecificationCharacteristic> Mono<S> save(S entity);

    Flux<ProductSpecificationCharacteristic> findAllBy(Pageable pageable);

    Flux<ProductSpecificationCharacteristic> findAll();

    Mono<ProductSpecificationCharacteristic> findById(String id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<ProductSpecificationCharacteristic> findAllBy(Pageable pageable, Criteria criteria);

}
