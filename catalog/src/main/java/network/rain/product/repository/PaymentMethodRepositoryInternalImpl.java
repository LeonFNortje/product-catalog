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
import network.rain.product.domain.PaymentMethod;
import network.rain.product.repository.rowmapper.PaymentMethodRowMapper;
import network.rain.product.repository.rowmapper.RelatedPartyRowMapper;
import network.rain.product.repository.rowmapper.RelatedPlaceRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the PaymentMethod entity.
 */
@SuppressWarnings("unused")
class PaymentMethodRepositoryInternalImpl extends SimpleR2dbcRepository<PaymentMethod, String> implements PaymentMethodRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final RelatedPlaceRowMapper relatedplaceMapper;
    private final RelatedPartyRowMapper relatedpartyMapper;
    private final PaymentMethodRowMapper paymentmethodMapper;

    private static final Table entityTable = Table.aliased("payment_method", EntityManager.ENTITY_ALIAS);
    private static final Table relatedPlaceTable = Table.aliased("related_place", "relatedPlace");
    private static final Table relatedPartyTable = Table.aliased("related_party", "relatedParty");

    public PaymentMethodRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        RelatedPlaceRowMapper relatedplaceMapper,
        RelatedPartyRowMapper relatedpartyMapper,
        PaymentMethodRowMapper paymentmethodMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(PaymentMethod.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.relatedplaceMapper = relatedplaceMapper;
        this.relatedpartyMapper = relatedpartyMapper;
        this.paymentmethodMapper = paymentmethodMapper;
    }

    @Override
    public Flux<PaymentMethod> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<PaymentMethod> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = PaymentMethodSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(RelatedPlaceSqlHelper.getColumns(relatedPlaceTable, "relatedPlace"));
        columns.addAll(RelatedPartySqlHelper.getColumns(relatedPartyTable, "relatedParty"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(relatedPlaceTable)
            .on(Column.create("related_place_id", entityTable))
            .equals(Column.create("id", relatedPlaceTable))
            .leftOuterJoin(relatedPartyTable)
            .on(Column.create("related_party_id", entityTable))
            .equals(Column.create("id", relatedPartyTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, PaymentMethod.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<PaymentMethod> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<PaymentMethod> findById(String id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(StringUtils.wrap(id.toString(), "'")));
        return createQuery(null, whereClause).one();
    }

    private PaymentMethod process(Row row, RowMetadata metadata) {
        PaymentMethod entity = paymentmethodMapper.apply(row, "e");
        entity.setRelatedPlace(relatedplaceMapper.apply(row, "relatedPlace"));
        entity.setRelatedParty(relatedpartyMapper.apply(row, "relatedParty"));
        return entity;
    }

    @Override
    public <S extends PaymentMethod> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
