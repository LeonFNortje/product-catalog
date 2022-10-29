package network.rain.product.repository;

import network.rain.product.domain.ProductSpecification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the ProductSpecification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSpecificationRepository
    extends ReactiveCrudRepository<ProductSpecification, String>, ProductSpecificationRepositoryInternal {
    Flux<ProductSpecification> findAllBy(Pageable pageable);

    @Query("SELECT * FROM product_specification entity WHERE entity.target_product_schema_id = :id")
    Flux<ProductSpecification> findByTargetProductSchema(String id);

    @Query("SELECT * FROM product_specification entity WHERE entity.target_product_schema_id IS NULL")
    Flux<ProductSpecification> findAllWhereTargetProductSchemaIsNull();

    @Query("SELECT * FROM product_specification entity WHERE entity.resource_specification_ref_id = :id")
    Flux<ProductSpecification> findByResourceSpecificationRef(String id);

    @Query("SELECT * FROM product_specification entity WHERE entity.resource_specification_ref_id IS NULL")
    Flux<ProductSpecification> findAllWhereResourceSpecificationRefIsNull();

    @Query("SELECT * FROM product_specification entity WHERE entity.attachment_ref_or_value_id = :id")
    Flux<ProductSpecification> findByAttachmentRefOrValue(String id);

    @Query("SELECT * FROM product_specification entity WHERE entity.attachment_ref_or_value_id IS NULL")
    Flux<ProductSpecification> findAllWhereAttachmentRefOrValueIsNull();

    @Query("SELECT * FROM product_specification entity WHERE entity.related_party_id = :id")
    Flux<ProductSpecification> findByRelatedParty(String id);

    @Query("SELECT * FROM product_specification entity WHERE entity.related_party_id IS NULL")
    Flux<ProductSpecification> findAllWhereRelatedPartyIsNull();

    @Query("SELECT * FROM product_specification entity WHERE entity.service_specification_ref_id = :id")
    Flux<ProductSpecification> findByServiceSpecificationRef(String id);

    @Query("SELECT * FROM product_specification entity WHERE entity.service_specification_ref_id IS NULL")
    Flux<ProductSpecification> findAllWhereServiceSpecificationRefIsNull();

    @Query("SELECT * FROM product_specification entity WHERE entity.product_specification_relationship_id = :id")
    Flux<ProductSpecification> findByProductSpecificationRelationship(String id);

    @Query("SELECT * FROM product_specification entity WHERE entity.product_specification_relationship_id IS NULL")
    Flux<ProductSpecification> findAllWhereProductSpecificationRelationshipIsNull();

    @Query("SELECT * FROM product_specification entity WHERE entity.bundled_product_specification_id = :id")
    Flux<ProductSpecification> findByBundledProductSpecification(String id);

    @Query("SELECT * FROM product_specification entity WHERE entity.bundled_product_specification_id IS NULL")
    Flux<ProductSpecification> findAllWhereBundledProductSpecificationIsNull();

    @Query("SELECT * FROM product_specification entity WHERE entity.product_specification_characteristic_id = :id")
    Flux<ProductSpecification> findByProductSpecificationCharacteristic(String id);

    @Query("SELECT * FROM product_specification entity WHERE entity.product_specification_characteristic_id IS NULL")
    Flux<ProductSpecification> findAllWhereProductSpecificationCharacteristicIsNull();

    @Override
    <S extends ProductSpecification> Mono<S> save(S entity);

    @Override
    Flux<ProductSpecification> findAll();

    @Override
    Mono<ProductSpecification> findById(String id);

    @Override
    Mono<Void> deleteById(String id);
}

interface ProductSpecificationRepositoryInternal {
    <S extends ProductSpecification> Mono<S> save(S entity);

    Flux<ProductSpecification> findAllBy(Pageable pageable);

    Flux<ProductSpecification> findAll();

    Mono<ProductSpecification> findById(String id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<ProductSpecification> findAllBy(Pageable pageable, Criteria criteria);

}
