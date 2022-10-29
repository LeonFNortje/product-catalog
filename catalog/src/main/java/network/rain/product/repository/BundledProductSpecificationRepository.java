package network.rain.product.repository;

import network.rain.product.domain.BundledProductSpecification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the BundledProductSpecification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BundledProductSpecificationRepository
    extends ReactiveCrudRepository<BundledProductSpecification, String>, BundledProductSpecificationRepositoryInternal {
    Flux<BundledProductSpecification> findAllBy(Pageable pageable);

    @Override
    <S extends BundledProductSpecification> Mono<S> save(S entity);

    @Override
    Flux<BundledProductSpecification> findAll();

    @Override
    Mono<BundledProductSpecification> findById(String id);

    @Override
    Mono<Void> deleteById(String id);
}

interface BundledProductSpecificationRepositoryInternal {
    <S extends BundledProductSpecification> Mono<S> save(S entity);

    Flux<BundledProductSpecification> findAllBy(Pageable pageable);

    Flux<BundledProductSpecification> findAll();

    Mono<BundledProductSpecification> findById(String id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<BundledProductSpecification> findAllBy(Pageable pageable, Criteria criteria);

}
