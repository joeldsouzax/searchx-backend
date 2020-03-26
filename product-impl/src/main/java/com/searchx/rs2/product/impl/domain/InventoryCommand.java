package com.searchx.rs2.product.impl.domain;

import akka.Done;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Value;

public interface InventoryCommand extends Jsonable {

    @Value
    @JsonDeserialize
    public final class CreateProduct implements InventoryCommand, CompressedJsonable, PersistentEntity.ReplyType<Done> {

        private final String productId;
        private final String name;
        private final String type;
        private final String userId;

        @JsonCreator
        public CreateProduct(final String productId, final String name, final String type, final String userId) {
            this.productId = Preconditions.checkNotNull(productId, "product id");
            this.name = Preconditions.checkNotNull(name, "product name");
            this.type = Preconditions.checkNotNull(type, "product type");
            this.userId = Preconditions.checkNotNull(userId, "user id");
        }
    }
}
