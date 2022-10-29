package network.rain.product.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class CharacteristicValueSpecificationSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("is_default", table, columnPrefix + "_is_default"));
        columns.add(Column.aliased("range_interval", table, columnPrefix + "_range_interval"));
        columns.add(Column.aliased("regex", table, columnPrefix + "_regex"));
        columns.add(Column.aliased("unit_of_measure", table, columnPrefix + "_unit_of_measure"));
        columns.add(Column.aliased("valid_for_from", table, columnPrefix + "_valid_for_from"));
        columns.add(Column.aliased("valid_for_to", table, columnPrefix + "_valid_for_to"));
        columns.add(Column.aliased("value_type", table, columnPrefix + "_value_type"));
        columns.add(Column.aliased("schema_location", table, columnPrefix + "_schema_location"));
        columns.add(Column.aliased("type", table, columnPrefix + "_type"));

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
