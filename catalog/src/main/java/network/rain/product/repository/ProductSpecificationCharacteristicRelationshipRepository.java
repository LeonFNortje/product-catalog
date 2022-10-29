package network.rain.product.repository;

import network.rain.product.domain.ProductSpecificationCharacteristicRelationship;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the ProductSpecificationCharacteristicRelationship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSpecificationCharacteristicRelationshipRepository
    extends
        ReactiveCrudRepository<ProductSpecificationCharacteristicRelationship, String>,
        ProductSpecificationCharacteristicRelationshipRepositoryInternal {
    Flux<ProductSpecificationCharacteristicRelationship> findAllBy(Pageable pageable);

    @Override
    <S extends ProductSpecificationCharacteristicRelationship> Mono<S> save(S entity);

    @Override
    Flux<ProductSpecificationCharacteristicRelationship> findAll();

    @Override
    Mono<ProductSpecificationCharacteristicRelationship> findById(String id);

    @Override
    Mono<Void> deleteById(String id);
}

interface ProductSpecificationCharacteristicRelationshipRepositoryInternal {
    <S extends ProductSpecificationCharacteristicRelationship> Mono<S> save(S entity);

    Flux<ProductSpecificationCharacteristicRelationship> findAllBy(Pageable pageable);

    Flux<ProductSpecificationCharacteristicRelationship> findAll();

    Mono<ProductSpecificationCharacteristicRelationship> findById(String id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<ProductSpecificationCharacteristicRelationship> findAllBy(Pageable pageable, Criteria criteria);

}
