package com.searchx.rs2.basket.impl;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.BadRequest;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.searchx.rs2.basket.api.Basket;
import com.searchx.rs2.basket.api.BasketService;
import com.searchx.rs2.basket.api.Product;
import com.searchx.rs2.basket.impl.domain.BasketCommand;
import com.searchx.rs2.basket.impl.domain.BasketEntity;
import com.searchx.rs2.basket.impl.domain.BasketException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class BasketServiceImpl implements BasketService {

    private final PersistentEntityRegistry registry;

    @Inject
    public BasketServiceImpl(final PersistentEntityRegistry registry) {
        this.registry = registry;
        registry.register(BasketEntity.class);
    }

    @Override
    public ServiceCall<Product, Done> addProduct(String basketId) {
        return product -> convertErrors(registry.refFor(BasketEntity.class, basketId).ask(new BasketCommand.AddProduct(product.getProductId())));
    }

    @Override
    public ServiceCall<Product, Done> removeProduct(String basketId) {
        return product -> convertErrors(registry.refFor(BasketEntity.class, basketId).ask(new BasketCommand.RemoveProduct(product.getProductId())));
    }

    @Override
    public ServiceCall<NotUsed, Basket> get(String baskedId) {
        return request -> {
            PersistentEntityRef<BasketCommand> entity = registry.refFor(BasketEntity.class, baskedId);
            return entity.ask(BasketCommand.Get.INSTANCE).thenApply(basket -> {
                List<Product> products = new ArrayList<>();
                for (String productId:
                     basket.getProducts()) {
                    products.add(new Product(productId));
                }

                return new Basket(baskedId, products);
            });
        };
    }


    private <T>CompletionStage<T> convertErrors(CompletionStage<T> future) {
        return future.exceptionally(ex -> {
            if (ex instanceof BasketException) {
                throw new BadRequest(ex.getMessage());
            } else {
                throw new BadRequest("Error updating basket");
            }
        });
    }
}
