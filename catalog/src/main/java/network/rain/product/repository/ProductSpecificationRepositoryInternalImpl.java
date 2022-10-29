package network.rain.product.repository;

import static org.springframework.data.relational.core.query.Criteria.where;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;
import network.rain.product.domain.ProductSpecification;
import network.rain.product.repository.rowmapper.AttachmentRefOrValueRowMapper;
import network.rain.product.repository.rowmapper.BundledProductSpecificationRowMapper;
import network.rain.product.repository.rowmapper.ProductSpecificationCharacteristicRowMapper;
import network.rain.product.repository.rowmapper.ProductSpecificationRelationshipRowMapper;
import network.rain.product.repository.rowmapper.ProductSpecificationRowMapper;
import network.rain.product.repository.rowmapper.RelatedPartyRowMapper;
import network.rain.product.repository.rowmapper.ResourceSpecificationRefRowMapper;
import network.rain.product.repository.rowmapper.ServiceSpecificationRefRowMapper;
import network.rain.product.repository.rowmapper.TargetProductSchemaRowMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC custom repository implementation for the ProductSpecification entity.
 */
@SuppressWarnings("unused")
class ProductSpecificationRepositoryInternalImpl
    extends SimpleR2dbcRepository<ProductSpecification, String>
    implements ProductSpecificationRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final TargetProductSchemaRowMapper targetproductschemaMapper;
    private final ResourceSpecificationRefRowMapper resourcespecificationrefMapper;
    private final AttachmentRefOrValueRowMapper attachmentreforvalueMapper;
    private final RelatedPartyRowMapper relatedpartyMapper;
    private final ServiceSpecificationRefRowMapper servicespecificationrefMapper;
    private final ProductSpecificationRelationshipRowMapper productspecificationrelationshipMapper;
    private final BundledProductSpecificationRowMapper bundledproductspecificationMapper;
    private final ProductSpecificationCharacteristicRowMapper productspecificationcharacteristicMapper;
    private final ProductSpecificationRowMapper productspecificationMapper;

    private static final Table entityTable = Table.aliased("product_specification", EntityManager.ENTITY_ALIAS);
    private static final Table targetProductSchemaTable = Table.aliased("target_product_schema", "targetProductSchema");
    private static final Table resourceSpecificationRefTable = Table.aliased("resource_specification_ref", "resourceSpecificationRef");
    private static final Table attachmentRefOrValueTable = Table.aliased("attachment_ref_or_value", "attachmentRefOrValue");
    private static final Table relatedPartyTable = Table.aliased("related_party", "relatedParty");
    private static final Table serviceSpecificationRefTable = Table.aliased("service_specification_ref", "serviceSpecificationRef");
    private static final Table productSpecificationRelationshipTable = Table.aliased(
        "product_specification_relationship",
        "productSpecificationRelationship"
    );
    private static final Table bundledProductSpecificationTable = Table.aliased(
        "bundled_product_specification",
        "bundledProductSpecification"
    );
    private static final Table productSpecificationCharacteristicTable = Table.aliased(
        "product_specification_characteristic",
        "productSpecificationCharacteristic"
    );

    public ProductSpecificationRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        TargetProductSchemaRowMapper targetproductschemaMapper,
        ResourceSpecificationRefRowMapper resourcespecificationrefMapper,
        AttachmentRefOrValueRowMapper attachmentreforvalueMapper,
        RelatedPartyRowMapper relatedpartyMapper,
        ServiceSpecificationRefRowMapper servicespecificationrefMapper,
        ProductSpecificationRelationshipRowMapper productspecificationrelationshipMapper,
        BundledProductSpecificationRowMapper bundledproductspecificationMapper,
        ProductSpecificationCharacteristicRowMapper productspecificationcharacteristicMapper,
        ProductSpecificationRowMapper productspecificationMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(ProductSpecification.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.targetproductschemaMapper = targetproductschemaMapper;
        this.resourcespecificationrefMapper = resourcespecificationrefMapper;
        this.attachmentreforvalueMapper = attachmentreforvalueMapper;
        this.relatedpartyMapper = relatedpartyMapper;
        this.servicespecificationrefMapper = servicespecificationrefMapper;
        this.productspecificationrelationshipMapper = productspecificationrelationshipMapper;
        this.bundledproductspecificationMapper = bundledproductspecificationMapper;
        this.productspecificationcharacteristicMapper = productspecificationcharacteristicMapper;
        this.productspecificationMapper = productspecificationMapper;
    }

    @Override
    public Flux<ProductSpecification> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<ProductSpecification> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = ProductSpecificationSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(TargetProductSchemaSqlHelper.getColumns(targetProductSchemaTable, "targetProductSchema"));
        columns.addAll(ResourceSpecificationRefSqlHelper.getColumns(resourceSpecificationRefTable, "resourceSpecificationRef"));
        columns.addAll(AttachmentRefOrValueSqlHelper.getColumns(attachmentRefOrValueTable, "attachmentRefOrValue"));
        columns.addAll(RelatedPartySqlHelper.getColumns(relatedPartyTable, "relatedParty"));
        columns.addAll(ServiceSpecificationRefSqlHelper.getColumns(serviceSpecificationRefTable, "serviceSpecificationRef"));
        columns.addAll(
            ProductSpecificationRelationshipSqlHelper.getColumns(productSpecificationRelationshipTable, "productSpecificationRelationship")
        );
        columns.addAll(BundledProductSpecificationSqlHelper.getColumns(bundledProductSpecificationTable, "bundledProductSpecification"));
        columns.addAll(
            ProductSpecificationCharacteristicSqlHelper.getColumns(
                productSpecificationCharacteristicTable,
                "productSpecificationCharacteristic"
            )
        );
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(targetProductSchemaTable)
            .on(Column.create("target_product_schema_id", entityTable))
            .equals(Column.create("id", targetProductSchemaTable))
            .leftOuterJoin(resourceSpecificationRefTable)
            .on(Column.create("resource_specification_ref_id", entityTable))
            .equals(Column.create("id", resourceSpecificationRefTable))
            .leftOuterJoin(attachmentRefOrValueTable)
            .on(Column.create("attachment_ref_or_value_id", entityTable))
            .equals(Column.create("id", attachmentRefOrValueTable))
            .leftOuterJoin(relatedPartyTable)
            .on(Column.create("related_party_id", entityTable))
            .equals(Column.create("id", relatedPartyTable))
            .leftOuterJoin(serviceSpecificationRefTable)
            .on(Column.create("service_specification_ref_id", entityTable))
            .equals(Column.create("id", serviceSpecificationRefTable))
            .leftOuterJoin(productSpecificationRelationshipTable)
            .on(Column.create("product_specification_relationship_id", entityTable))
            .equals(Column.create("id", productSpecificationRelationshipTable))
            .leftOuterJoin(bundledProductSpecificationTable)
            .on(Column.create("bundled_product_specification_id", entityTable))
            .equals(Column.create("id", bundledProductSpecificationTable))
            .leftOuterJoin(productSpecificationCharacteristicTable)
            .on(Column.create("product_specification_characteristic_id", entityTable))
            .equals(Column.create("id", productSpecificationCharacteristicTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, ProductSpecification.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<ProductSpecification> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<ProductSpecification> findById(String id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(StringUtils.wrap(id.toString(), "'")));
        return createQuery(null, whereClause).one();
    }

    private ProductSpecification process(Row row, RowMetadata metadata) {
        ProductSpecification entity = productspecificationMapper.apply(row, "e");
        entity.setTargetProductSchema(targetproductschemaMapper.apply(row, "targetProductSchema"));
        entity.setResourceSpecificationRef(resourcespecificationrefMapper.apply(row, "resourceSpecificationRef"));
        entity.setAttachmentRefOrValue(attachmentreforvalueMapper.apply(row, "attachmentRefOrValue"));
        entity.setRelatedParty(relatedpartyMapper.apply(row, "relatedParty"));
        entity.setServiceSpecificationRef(servicespecificationrefMapper.apply(row, "serviceSpecificationRef"));
        entity.setProductSpecificationRelationship(productspecificationrelationshipMapper.apply(row, "productSpecificationRelationship"));
        entity.setBundledProductSpecification(bundledproductspecificationMapper.apply(row, "bundledProductSpecification"));
        entity.setProductSpecificationCharacteristic(
            productspecificationcharacteristicMapper.apply(row, "productSpecificationCharacteristic")
        );
        return entity;
    }

    @Override
    public <S extends ProductSpecification> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
