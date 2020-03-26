package com.searchx.rs2.basket.impl.domain;

import akka.Done;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Value;

public interface BasketCommand extends Jsonable {


    @Value
    @JsonDeserialize
    final class AddProduct implements BasketCommand, CompressedJsonable, PersistentEntity.ReplyType<Done> {
        private final String productId;

        @JsonCreator
        public AddProduct(final String productId) {
            this.productId = Preconditions.checkNotNull(productId, "productId is null");
        }
    }

    @Value
    @JsonDeserialize
    final class RemoveProduct implements BasketCommand, CompressedJsonable, PersistentEntity.ReplyType<Done> {
        private final String productId;

        @JsonCreator
        public RemoveProduct(final String productId) {
            this.productId = Preconditions.checkNotNull(productId, "productId is null");
        }
    }


    enum Get implements BasketCommand, PersistentEntity.ReplyType<BasketState> {
        INSTANCE
    }
}
