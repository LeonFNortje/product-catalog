package network.rain.product.repository;

import network.rain.product.domain.TargetProductSchema;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the TargetProductSchema entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TargetProductSchemaRepository
    extends ReactiveCrudRepository<TargetProductSchema, Long>, TargetProductSchemaRepositoryInternal {
    Flux<TargetProductSchema> findAllBy(Pageable pageable);

    @Override
    <S extends TargetProductSchema> Mono<S> save(S entity);

    @Override
    Flux<TargetProductSchema> findAll();

    @Override
    Mono<TargetProductSchema> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface TargetProductSchemaRepositoryInternal {
    <S extends TargetProductSchema> Mono<S> save(S entity);

    Flux<TargetProductSchema> findAllBy(Pageable pageable);

    Flux<TargetProductSchema> findAll();

    Mono<TargetProductSchema> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<TargetProductSchema> findAllBy(Pageable pageable, Criteria criteria);

}
