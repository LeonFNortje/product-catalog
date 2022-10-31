package network.rain.product.repository;

import network.rain.product.domain.PaymentMethod;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the PaymentMethod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentMethodRepository extends ReactiveCrudRepository<PaymentMethod, String>, PaymentMethodRepositoryInternal {
    Flux<PaymentMethod> findAllBy(Pageable pageable);

    @Query("SELECT * FROM payment_method entity WHERE entity.related_place_id = :id")
    Flux<PaymentMethod> findByRelatedPlace(String id);

    @Query("SELECT * FROM payment_method entity WHERE entity.related_place_id IS NULL")
    Flux<PaymentMethod> findAllWhereRelatedPlaceIsNull();

    @Query("SELECT * FROM payment_method entity WHERE entity.related_party_id = :id")
    Flux<PaymentMethod> findByRelatedParty(String id);

    @Query("SELECT * FROM payment_method entity WHERE entity.related_party_id IS NULL")
    Flux<PaymentMethod> findAllWhereRelatedPartyIsNull();

    @Override
    <S extends PaymentMethod> Mono<S> save(S entity);

    @Override
    Flux<PaymentMethod> findAll();

    @Override
    Mono<PaymentMethod> findById(String id);

    @Override
    Mono<Void> deleteById(String id);
}

interface PaymentMethodRepositoryInternal {
    <S extends PaymentMethod> Mono<S> save(S entity);

    Flux<PaymentMethod> findAllBy(Pageable pageable);

    Flux<PaymentMethod> findAll();

    Mono<PaymentMethod> findById(String id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<PaymentMethod> findAllBy(Pageable pageable, Criteria criteria);

}
