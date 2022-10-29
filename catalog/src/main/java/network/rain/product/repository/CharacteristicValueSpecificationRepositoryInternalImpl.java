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
import network.rain.product.domain.CharacteristicValueSpecification;
import network.rain.product.repository.rowmapper.CharacteristicValueSpecificationRowMapper;
import network.rain.product.repository.rowmapper.ProductSpecificationCharacteristicRelationshipRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the CharacteristicValueSpecification entity.
 */
@SuppressWarnings("unused")
class CharacteristicValueSpecificationRepositoryInternalImpl
    extends SimpleR2dbcRepository<CharacteristicValueSpecification, Long>
    implements CharacteristicValueSpecificationRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final ProductSpecificationCharacteristicRelationshipRowMapper productspecificationcharacteristicrelationshipMapper;
    private final CharacteristicValueSpecificationRowMapper characteristicvaluespecificationMapper;

    private static final Table entityTable = Table.aliased("characteristic_value_specification", EntityManager.ENTITY_ALIAS);
    private static final Table productSpecificationCharacteristicRelationshipTable = Table.aliased(
        "product_specification_characteristic_relationship",
        "productSpecificationCharacteristicRelationship"
    );

    public CharacteristicValueSpecificationRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        ProductSpecificationCharacteristicRelationshipRowMapper productspecificationcharacteristicrelationshipMapper,
        CharacteristicValueSpecificationRowMapper characteristicvaluespecificationMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(
                converter.getMappingContext().getRequiredPersistentEntity(CharacteristicValueSpecification.class)
            ),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.productspecificationcharacteristicrelationshipMapper = productspecificationcharacteristicrelationshipMapper;
        this.characteristicvaluespecificationMapper = characteristicvaluespecificationMapper;
    }

    @Override
    public Flux<CharacteristicValueSpecification> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<CharacteristicValueSpecification> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = CharacteristicValueSpecificationSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
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
        String select = entityManager.createSelect(selectFrom, CharacteristicValueSpecification.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<CharacteristicValueSpecification> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<CharacteristicValueSpecification> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private CharacteristicValueSpecification process(Row row, RowMetadata metadata) {
        CharacteristicValueSpecification entity = characteristicvaluespecificationMapper.apply(row, "e");
        entity.setProductSpecificationCharacteristicRelationship(
            productspecificationcharacteristicrelationshipMapper.apply(row, "productSpecificationCharacteristicRelationship")
        );
        return entity;
    }

    @Override
    public <S extends CharacteristicValueSpecification> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
