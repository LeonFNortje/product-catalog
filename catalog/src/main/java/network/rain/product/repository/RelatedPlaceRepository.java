package network.rain.product.repository;

import network.rain.product.domain.RelatedPlace;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the RelatedPlace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelatedPlaceRepository extends ReactiveCrudRepository<RelatedPlace, String>, RelatedPlaceRepositoryInternal {
    Flux<RelatedPlace> findAllBy(Pageable pageable);

    @Override
    <S extends RelatedPlace> Mono<S> save(S entity);

    @Override
    Flux<RelatedPlace> findAll();

    @Override
    Mono<RelatedPlace> findById(String id);

    @Override
    Mono<Void> deleteById(String id);
}

interface RelatedPlaceRepositoryInternal {
    <S extends RelatedPlace> Mono<S> save(S entity);

    Flux<RelatedPlace> findAllBy(Pageable pageable);

    Flux<RelatedPlace> findAll();

    Mono<RelatedPlace> findById(String id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<RelatedPlace> findAllBy(Pageable pageable, Criteria criteria);

}
