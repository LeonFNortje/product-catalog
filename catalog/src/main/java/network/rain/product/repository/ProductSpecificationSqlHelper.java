package network.rain.product.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class ProductSpecificationSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("brand", table, columnPrefix + "_brand"));
        columns.add(Column.aliased("description", table, columnPrefix + "_description"));
        columns.add(Column.aliased("href", table, columnPrefix + "_href"));
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("name", table, columnPrefix + "_name"));
        columns.add(Column.aliased("is_bundle", table, columnPrefix + "_is_bundle"));
        columns.add(Column.aliased("last_update", table, columnPrefix + "_last_update"));
        columns.add(Column.aliased("lifecycle_status", table, columnPrefix + "_lifecycle_status"));
        columns.add(Column.aliased("product_number", table, columnPrefix + "_product_number"));
        columns.add(Column.aliased("valid_for_from", table, columnPrefix + "_valid_for_from"));
        columns.add(Column.aliased("valid_for_to", table, columnPrefix + "_valid_for_to"));
        columns.add(Column.aliased("version", table, columnPrefix + "_version"));
        columns.add(Column.aliased("schema_location", table, columnPrefix + "_schema_location"));
        columns.add(Column.aliased("type", table, columnPrefix + "_type"));

        columns.add(Column.aliased("target_product_schema_id", table, columnPrefix + "_target_product_schema_id"));
        columns.add(Column.aliased("resource_specification_ref_id", table, columnPrefix + "_resource_specification_ref_id"));
        columns.add(Column.aliased("attachment_ref_or_value_id", table, columnPrefix + "_attachment_ref_or_value_id"));
        columns.add(Column.aliased("related_party_id", table, columnPrefix + "_related_party_id"));
        columns.add(Column.aliased("service_specification_ref_id", table, columnPrefix + "_service_specification_ref_id"));
        columns.add(
            Column.aliased("product_specification_relationship_id", table, columnPrefix + "_product_specification_relationship_id")
        );
        columns.add(Column.aliased("bundled_product_specification_id", table, columnPrefix + "_bundled_product_specification_id"));
        columns.add(
            Column.aliased("product_specification_characteristic_id", table, columnPrefix + "_product_specification_characteristic_id")
        );
        return columns;
    }
}
