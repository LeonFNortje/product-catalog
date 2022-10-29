package network.rain.product.domain;

import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback;
import org.springframework.data.r2dbc.mapping.event.AfterSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ServiceSpecificationRefCallback
    implements AfterSaveCallback<ServiceSpecificationRef>, AfterConvertCallback<ServiceSpecificationRef> {

    @Override
    public Publisher<ServiceSpecificationRef> onAfterConvert(ServiceSpecificationRef entity, SqlIdentifier table) {
        return Mono.just(entity.setIsPersisted());
    }

    @Override
    public Publisher<ServiceSpecificationRef> onAfterSave(ServiceSpecificationRef entity, OutboundRow outboundRow, SqlIdentifier table) {
        return Mono.just(entity.setIsPersisted());
    }
}
