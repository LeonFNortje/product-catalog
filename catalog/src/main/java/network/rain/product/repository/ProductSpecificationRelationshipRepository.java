package network.rain.product.repository;

import network.rain.product.domain.ProductSpecificationRelationship;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the ProductSpecificationRelationship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSpecificationRelationshipRepository
    extends ReactiveCrudRepository<ProductSpecificationRelationship, String>, ProductSpecificationRelationshipRepositoryInternal {
    Flux<ProductSpecificationRelationship> findAllBy(Pageable pageable);

    @Override
    <S extends ProductSpecificationRelationship> Mono<S> save(S entity);

    @Override
    Flux<ProductSpecificationRelationship> findAll();

    @Override
    Mono<ProductSpecificationRelationship> findById(String id);

    @Override
    Mono<Void> deleteById(String id);
}

interface ProductSpecificationRelationshipRepositoryInternal {
    <S extends ProductSpecificationRelationship> Mono<S> save(S entity);

    Flux<ProductSpecificationRelationship> findAllBy(Pageable pageable);

    Flux<ProductSpecificationRelationship> findAll();

    Mono<ProductSpecificationRelationship> findById(String id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<ProductSpecificationRelationship> findAllBy(Pageable pageable, Criteria criteria);

}
