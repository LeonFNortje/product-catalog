package network.rain.product.repository;

import network.rain.product.domain.RelatedParty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the RelatedParty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelatedPartyRepository extends ReactiveCrudRepository<RelatedParty, String>, RelatedPartyRepositoryInternal {
    Flux<RelatedParty> findAllBy(Pageable pageable);

    @Override
    <S extends RelatedParty> Mono<S> save(S entity);

    @Override
    Flux<RelatedParty> findAll();

    @Override
    Mono<RelatedParty> findById(String id);

    @Override
    Mono<Void> deleteById(String id);
}

interface RelatedPartyRepositoryInternal {
    <S extends RelatedParty> Mono<S> save(S entity);

    Flux<RelatedParty> findAllBy(Pageable pageable);

    Flux<RelatedParty> findAll();

    Mono<RelatedParty> findById(String id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<RelatedParty> findAllBy(Pageable pageable, Criteria criteria);

}
