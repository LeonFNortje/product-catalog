package network.rain.product.repository;

import network.rain.product.domain.CharacteristicValueSpecification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the CharacteristicValueSpecification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CharacteristicValueSpecificationRepository
    extends ReactiveCrudRepository<CharacteristicValueSpecification, Long>, CharacteristicValueSpecificationRepositoryInternal {
    Flux<CharacteristicValueSpecification> findAllBy(Pageable pageable);

    @Query(
        "SELECT * FROM characteristic_value_specification entity WHERE entity.product_specification_characteristic_relationship_id = :id"
    )
    Flux<CharacteristicValueSpecification> findByProductSpecificationCharacteristicRelationship(Long id);

    @Query(
        "SELECT * FROM characteristic_value_specification entity WHERE entity.product_specification_characteristic_relationship_id IS NULL"
    )
    Flux<CharacteristicValueSpecification> findAllWhereProductSpecificationCharacteristicRelationshipIsNull();

    @Override
    <S extends CharacteristicValueSpecification> Mono<S> save(S entity);

    @Override
    Flux<CharacteristicValueSpecification> findAll();

    @Override
    Mono<CharacteristicValueSpecification> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface CharacteristicValueSpecificationRepositoryInternal {
    <S extends CharacteristicValueSpecification> Mono<S> save(S entity);

    Flux<CharacteristicValueSpecification> findAllBy(Pageable pageable);

    Flux<CharacteristicValueSpecification> findAll();

    Mono<CharacteristicValueSpecification> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<CharacteristicValueSpecification> findAllBy(Pageable pageable, Criteria criteria);

}
