package com.searchx.rs2.basket.impl.domain;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.time.Instant;
import java.util.Optional;

public class BasketEntity extends PersistentEntity<BasketCommand, BasketEvent,BasketState> {

    @Override
    public Behavior initialBehavior(final Optional<BasketState> snapshotState) {
        BasketState state = snapshotState.orElse(BasketState.EMPTY);
        BehaviorBuilder builder = newBehaviorBuilder(state);
        return useBasket(builder);
    }

    private Behavior useBasket(BehaviorBuilder builder) {
        builder.setCommandHandler(BasketCommand.AddProduct.class, (command, context) -> {
            if (state().getProducts().contains(command.getProductId())) {
                context.commandFailed(new BasketException("cannot add product that is already in basket"));
                return context.done();
            } else {
                return context.thenPersist(new BasketEvent.ProductAdded(entityId(),command.getProductId(), Instant.now()),
                        event -> context.reply(Done.getInstance()));
            }
        });


        builder.setCommandHandler(BasketCommand.RemoveProduct.class, (command, context) -> {
            if(!state().getProducts().contains(command.getProductId())) {
                context.commandFailed(new BasketException("cannot remove products that are not in the basket"));
                return context.done();
            } else {
                return context.thenPersist(new BasketEvent.ProductRemoved(entityId(), command.getProductId(), Instant.now()),
                        event -> context.reply(Done.getInstance()));
            }
        });


        builder.setReadOnlyCommandHandler(BasketCommand.Get.class, (command, context) -> context.reply(state()));
        builder.setEventHandler(BasketEvent.ProductAdded.class, productAdded -> state().add(productAdded.getProductId()));
        builder.setEventHandler(BasketEvent.ProductRemoved.class, productRemoved -> state().remove(productRemoved.getProductId()));

        return  builder.build();
    }

}
