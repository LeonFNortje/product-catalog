package network.rain.product.domain;

import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback;
import org.springframework.data.r2dbc.mapping.event.AfterSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ProductSpecificationCharacteristicRelationshipCallback
    implements
        AfterSaveCallback<ProductSpecificationCharacteristicRelationship>,
        AfterConvertCallback<ProductSpecificationCharacteristicRelationship> {

    @Override
    public Publisher<ProductSpecificationCharacteristicRelationship> onAfterConvert(
        ProductSpecificationCharacteristicRelationship entity,
        SqlIdentifier table
    ) {
        return Mono.just(entity.setIsPersisted());
    }

    @Override
    public Publisher<ProductSpecificationCharacteristicRelationship> onAfterSave(
        ProductSpecificationCharacteristicRelationship entity,
        OutboundRow outboundRow,
        SqlIdentifier table
    ) {
        return Mono.just(entity.setIsPersisted());
    }
}
