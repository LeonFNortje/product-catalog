package network.rain.product.domain;

import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback;
import org.springframework.data.r2dbc.mapping.event.AfterSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PaymentMethodCallback implements AfterSaveCallback<PaymentMethod>, AfterConvertCallback<PaymentMethod> {

    @Override
    public Publisher<PaymentMethod> onAfterConvert(PaymentMethod entity, SqlIdentifier table) {
        return Mono.just(entity.setIsPersisted());
    }

    @Override
    public Publisher<PaymentMethod> onAfterSave(PaymentMethod entity, OutboundRow outboundRow, SqlIdentifier table) {
        return Mono.just(entity.setIsPersisted());
    }
}
