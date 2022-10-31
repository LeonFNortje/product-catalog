package network.rain.product.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class PaymentMethodSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("href", table, columnPrefix + "_href"));
        columns.add(Column.aliased("authorization_code", table, columnPrefix + "_authorization_code"));
        columns.add(Column.aliased("description", table, columnPrefix + "_description"));
        columns.add(Column.aliased("is_preferred", table, columnPrefix + "_is_preferred"));
        columns.add(Column.aliased("name", table, columnPrefix + "_name"));
        columns.add(Column.aliased("status", table, columnPrefix + "_status"));
        columns.add(Column.aliased("status_date", table, columnPrefix + "_status_date"));
        columns.add(Column.aliased("status_reason", table, columnPrefix + "_status_reason"));
        columns.add(Column.aliased("valid_for_from", table, columnPrefix + "_valid_for_from"));
        columns.add(Column.aliased("valid_for_to", table, columnPrefix + "_valid_for_to"));
        columns.add(Column.aliased("schema_location", table, columnPrefix + "_schema_location"));
        columns.add(Column.aliased("type", table, columnPrefix + "_type"));

        columns.add(Column.aliased("related_place_id", table, columnPrefix + "_related_place_id"));
        columns.add(Column.aliased("related_party_id", table, columnPrefix + "_related_party_id"));
        return columns;
    }
}
