package network.rain.product.repository;

import network.rain.product.domain.AttachmentRefOrValue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the AttachmentRefOrValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachmentRefOrValueRepository
    extends ReactiveCrudRepository<AttachmentRefOrValue, String>, AttachmentRefOrValueRepositoryInternal {
    Flux<AttachmentRefOrValue> findAllBy(Pageable pageable);

    @Override
    <S extends AttachmentRefOrValue> Mono<S> save(S entity);

    @Override
    Flux<AttachmentRefOrValue> findAll();

    @Override
    Mono<AttachmentRefOrValue> findById(String id);

    @Override
    Mono<Void> deleteById(String id);
}

interface AttachmentRefOrValueRepositoryInternal {
    <S extends AttachmentRefOrValue> Mono<S> save(S entity);

    Flux<AttachmentRefOrValue> findAllBy(Pageable pageable);

    Flux<AttachmentRefOrValue> findAll();

    Mono<AttachmentRefOrValue> findById(String id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<AttachmentRefOrValue> findAllBy(Pageable pageable, Criteria criteria);

}
