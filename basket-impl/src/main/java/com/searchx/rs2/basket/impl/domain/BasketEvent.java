package com.searchx.rs2.basket.impl.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Value;

import java.time.Instant;

public interface BasketEvent extends Jsonable, AggregateEvent<BasketEvent> {

    AggregateEventShards<BasketEvent> TAG = AggregateEventTag.sharded(BasketEvent.class,10);


    @Value
    @JsonDeserialize
    final class ProductAdded implements BasketEvent {
        private final String basketId;
        private final String productId;
        private final Instant eventTime;

        @JsonCreator
        public ProductAdded(final String basketId,final String productId,final Instant eventTime) {
            this.basketId = Preconditions.checkNotNull(basketId, "basketId is null");
            this.productId = Preconditions.checkNotNull(productId, "productId is null");;
            this.eventTime = eventTime;
        }

    }

    @Value
    @JsonDeserialize
    final class ProductRemoved implements BasketEvent {
        private final String basketId;
        private final String productId;
        private final Instant eventTime;

        @JsonCreator
        public ProductRemoved(final String basketId, final String productId, final Instant eventTime) {
            this.basketId = Preconditions.checkNotNull(basketId, "basketId is null");
            this.productId = Preconditions.checkNotNull(productId, "productId is null");
            ;
            this.eventTime = eventTime;
        }
    }

    @Override
    default AggregateEventTagger<BasketEvent> aggregateTag() {
        return TAG;
    }
}
