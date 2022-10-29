package network.rain.product.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class ProductSpecificationCharacteristicSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("configurable", table, columnPrefix + "_configurable"));
        columns.add(Column.aliased("description", table, columnPrefix + "_description"));
        columns.add(Column.aliased("extensible", table, columnPrefix + "_extensible"));
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("is_unique", table, columnPrefix + "_is_unique"));
        columns.add(Column.aliased("max_cardinality", table, columnPrefix + "_max_cardinality"));
        columns.add(Column.aliased("min_cardinality", table, columnPrefix + "_min_cardinality"));
        columns.add(Column.aliased("name", table, columnPrefix + "_name"));
        columns.add(Column.aliased("regex", table, columnPrefix + "_regex"));
        columns.add(Column.aliased("valid_for_from", table, columnPrefix + "_valid_for_from"));
        columns.add(Column.aliased("valid_for_to", table, columnPrefix + "_valid_for_to"));
        columns.add(Column.aliased("value_type", table, columnPrefix + "_value_type"));
        columns.add(Column.aliased("schema_location", table, columnPrefix + "_schema_location"));
        columns.add(Column.aliased("type", table, columnPrefix + "_type"));
        columns.add(Column.aliased("value_schema_location", table, columnPrefix + "_value_schema_location"));

        columns.add(
            Column.aliased(
                "product_specification_characteristic_relationship_id",
                table,
                columnPrefix + "_product_specification_characteristic_relationship_id"
            )
        );
        return columns;
    }
}
