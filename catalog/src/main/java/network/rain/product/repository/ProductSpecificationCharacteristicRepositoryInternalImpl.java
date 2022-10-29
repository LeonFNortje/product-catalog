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
import network.rain.product.domain.ProductSpecificationCharacteristic;
import network.rain.product.repository.rowmapper.ProductSpecificationCharacteristicRelationshipRowMapper;
import network.rain.product.repository.rowmapper.ProductSpecificationCharacteristicRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the ProductSpecificationCharacteristic entity.
 */
@SuppressWarnings("unused")
class ProductSpecificationCharacteristicRepositoryInternalImpl
    extends SimpleR2dbcRepository<ProductSpecificationCharacteristic, String>
    implements ProductSpecificationCharacteristicRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final ProductSpecificationCharacteristicRelationshipRowMapper productspecificationcharacteristicrelationshipMapper;
    private final ProductSpecificationCharacteristicRowMapper productspecificationcharacteristicMapper;

    private static final Table entityTable = Table.aliased("product_specification_characteristic", EntityManager.ENTITY_ALIAS);
    private static final Table productSpecificationCharacteristicRelationshipTable = Table.aliased(
        "product_specification_characteristic_relationship",
        "productSpecificationCharacteristicRelationship"
    );

    public ProductSpecificationCharacteristicRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        ProductSpecificationCharacteristicRelationshipRowMapper productspecificationcharacteristicrelationshipMapper,
        ProductSpecificationCharacteristicRowMapper productspecificationcharacteristicMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(
                converter.getMappingContext().getRequiredPersistentEntity(ProductSpecificationCharacteristic.class)
            ),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.productspecificationcharacteristicrelationshipMapper = productspecificationcharacteristicrelationshipMapper;
        this.productspecificationcharacteristicMapper = productspecificationcharacteristicMapper;
    }

    @Override
    public Flux<ProductSpecificationCharacteristic> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<ProductSpecificationCharacteristic> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = ProductSpecificationCharacteristicSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(
            ProductSpecificationCharacteristicRelationshipSqlHelper.getColumns(
                productSpecificationCharacteristicRelationshipTable,
                "productSpecificationCharacteristicRelationship"
            )
        );
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(productSpecificationCharacteristicRelationshipTable)
            .on(Column.create("product_specification_characteristic_relationship_id", entityTable))
            .equals(Column.create("id", productSpecificationCharacteristicRelationshipTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, ProductSpecificationCharacteristic.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<ProductSpecificationCharacteristic> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<ProductSpecificationCharacteristic> findById(String id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(StringUtils.wrap(id.toString(), "'")));
        return createQuery(null, whereClause).one();
    }

    private ProductSpecificationCharacteristic process(Row row, RowMetadata metadata) {
        ProductSpecificationCharacteristic entity = productspecificationcharacteristicMapper.apply(row, "e");
        entity.setProductSpecificationCharacteristicRelationship(
            productspecificationcharacteristicrelationshipMapper.apply(row, "productSpecificationCharacteristicRelationship")
        );
        return entity;
    }

    @Override
    public <S extends ProductSpecificationCharacteristic> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
