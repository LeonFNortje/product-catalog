package network.rain.product.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class AttachmentRefOrValueSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("attachment_type", table, columnPrefix + "_attachment_type"));
        columns.add(Column.aliased("content", table, columnPrefix + "_content"));
        columns.add(Column.aliased("description", table, columnPrefix + "_description"));
        columns.add(Column.aliased("href", table, columnPrefix + "_href"));
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("mime_type", table, columnPrefix + "_mime_type"));
        columns.add(Column.aliased("name", table, columnPrefix + "_name"));
        columns.add(Column.aliased("size_of_bytes", table, columnPrefix + "_size_of_bytes"));
        columns.add(Column.aliased("url", table, columnPrefix + "_url"));
        columns.add(Column.aliased("valid_for_from", table, columnPrefix + "_valid_for_from"));
        columns.add(Column.aliased("valid_for_to", table, columnPrefix + "_valid_for_to"));
        columns.add(Column.aliased("value_type", table, columnPrefix + "_value_type"));
        columns.add(Column.aliased("schema_location", table, columnPrefix + "_schema_location"));
        columns.add(Column.aliased("type", table, columnPrefix + "_type"));

        return columns;
    }
}
