package network.rain.product.repository;

import network.rain.product.domain.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Event entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventRepository extends ReactiveCrudRepository<Event, String>, EventRepositoryInternal {
    Flux<Event> findAllBy(Pageable pageable);

    @Override
    <S extends Event> Mono<S> save(S entity);

    @Override
    Flux<Event> findAll();

    @Override
    Mono<Event> findById(String id);

    @Override
    Mono<Void> deleteById(String id);
}

interface EventRepositoryInternal {
    <S extends Event> Mono<S> save(S entity);

    Flux<Event> findAllBy(Pageable pageable);

    Flux<Event> findAll();

    Mono<Event> findById(String id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Event> findAllBy(Pageable pageable, Criteria criteria);

}
