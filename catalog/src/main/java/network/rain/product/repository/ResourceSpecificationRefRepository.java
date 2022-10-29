package network.rain.product.repository;

import network.rain.product.domain.ResourceSpecificationRef;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the ResourceSpecificationRef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResourceSpecificationRefRepository
    extends ReactiveCrudRepository<ResourceSpecificationRef, String>, ResourceSpecificationRefRepositoryInternal {
    Flux<ResourceSpecificationRef> findAllBy(Pageable pageable);

    @Override
    <S extends ResourceSpecificationRef> Mono<S> save(S entity);

    @Override
    Flux<ResourceSpecificationRef> findAll();

    @Override
    Mono<ResourceSpecificationRef> findById(String id);

    @Override
    Mono<Void> deleteById(String id);
}

interface ResourceSpecificationRefRepositoryInternal {
    <S extends ResourceSpecificationRef> Mono<S> save(S entity);

    Flux<ResourceSpecificationRef> findAllBy(Pageable pageable);

    Flux<ResourceSpecificationRef> findAll();

    Mono<ResourceSpecificationRef> findById(String id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<ResourceSpecificationRef> findAllBy(Pageable pageable, Criteria criteria);

}
