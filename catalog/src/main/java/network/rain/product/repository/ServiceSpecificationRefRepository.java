package network.rain.product.repository;

import network.rain.product.domain.ServiceSpecificationRef;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the ServiceSpecificationRef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceSpecificationRefRepository
    extends ReactiveCrudRepository<ServiceSpecificationRef, String>, ServiceSpecificationRefRepositoryInternal {
    Flux<ServiceSpecificationRef> findAllBy(Pageable pageable);

    @Override
    <S extends ServiceSpecificationRef> Mono<S> save(S entity);

    @Override
    Flux<ServiceSpecificationRef> findAll();

    @Override
    Mono<ServiceSpecificationRef> findById(String id);

    @Override
    Mono<Void> deleteById(String id);
}

interface ServiceSpecificationRefRepositoryInternal {
    <S extends ServiceSpecificationRef> Mono<S> save(S entity);

    Flux<ServiceSpecificationRef> findAllBy(Pageable pageable);

    Flux<ServiceSpecificationRef> findAll();

    Mono<ServiceSpecificationRef> findById(String id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<ServiceSpecificationRef> findAllBy(Pageable pageable, Criteria criteria);

}
