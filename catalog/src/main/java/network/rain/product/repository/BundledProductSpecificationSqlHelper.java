package network.rain.product.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class BundledProductSpecificationSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("href", table, columnPrefix + "_href"));
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("name", table, columnPrefix + "_name"));
        columns.add(Column.aliased("lifecycle_status", table, columnPrefix + "_lifecycle_status"));
        columns.add(Column.aliased("schema_location", table, columnPrefix + "_schema_location"));
        columns.add(Column.aliased("type", table, columnPrefix + "_type"));

        return columns;
    }
}