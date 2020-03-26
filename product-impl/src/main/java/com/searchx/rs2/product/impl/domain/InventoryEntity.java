package com.searchx.rs2.product.impl.domain;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.searchx.rs2.product.api.Product;

import java.time.Instant;
import java.util.Optional;

public class InventoryEntity extends PersistentEntity<InventoryCommand, InventoryEvent,InventoryState> {

    @Override
    public Behavior initialBehavior(final Optional<InventoryState> snapshotState) {
        InventoryState state = snapshotState.orElse(InventoryState.EMPTY);
        BehaviorBuilder builder = newBehaviorBuilder(state);
        return useBasket(builder);
    }

    private Behavior useBasket(BehaviorBuilder builder) {
        builder.setCommandHandler(InventoryCommand.CreateProduct.class, (command, context) -> {
            return context.thenPersist(new InventoryEvent.ProductCreated(command.getProductId(),command.getName(), command.getType(),command.getUserId(), Instant.now()),
                    event -> context.reply(Done.getInstance()));
        });


        builder.setEventHandler(InventoryEvent.ProductCreated.class, productCreated -> state().add(new Product(productCreated.getProductId(),productCreated.getName(),productCreated.getType(), productCreated.getUserId())));

        return  builder.build();
    }

}
