package com.searchx.rs2.product.impl.domain;

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

public interface InventoryEvent extends Jsonable, AggregateEvent<InventoryEvent> {

    AggregateEventShards<InventoryEvent> TAG = AggregateEventTag.sharded(InventoryEvent.class,10);

    @Override
    default AggregateEventTagger<InventoryEvent> aggregateTag() {
        return TAG;
    }


    @Value
    @JsonDeserialize
     final class ProductCreated implements InventoryEvent {
         private final String productId;
         private final String name;
         private final String type;
         private final String userId;
         private final Instant eventTime;

         @JsonCreator
         public ProductCreated(final String productId, final String name, final String type , final String userId, final Instant eventTime) {
             this.productId = Preconditions.checkNotNull(productId, "productId is null");
             this.name = Preconditions.checkNotNull(name, "name");
             this.type = Preconditions.checkNotNull(type, "type");
             this.userId = Preconditions.checkNotNull(userId, "user id");
             this.eventTime = eventTime;
         }


    }
}
