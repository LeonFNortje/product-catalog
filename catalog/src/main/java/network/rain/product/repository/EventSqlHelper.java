package network.rain.product.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class EventSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("correlation_id", table, columnPrefix + "_correlation_id"));
        columns.add(Column.aliased("description", table, columnPrefix + "_description"));
        columns.add(Column.aliased("domain", table, columnPrefix + "_domain"));
        columns.add(Column.aliased("event_id", table, columnPrefix + "_event_id"));
        columns.add(Column.aliased("event_time", table, columnPrefix + "_event_time"));
        columns.add(Column.aliased("event_type", table, columnPrefix + "_event_type"));
        columns.add(Column.aliased("href", table, columnPrefix + "_href"));
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("priority", table, columnPrefix + "_priority"));
        columns.add(Column.aliased("time_occurred", table, columnPrefix + "_time_occurred"));
        columns.add(Column.aliased("title", table, columnPrefix + "_title"));

        return columns;
    }
}
