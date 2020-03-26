package com.searchx.rs2.product.impl;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.searchx.rs2.product.api.Product;
import com.searchx.rs2.product.api.ProductService;
import com.searchx.rs2.product.impl.domain.InventoryCommand;
import com.searchx.rs2.product.impl.domain.InventoryEntity;
import com.searchx.rs2.product.impl.domain.ProductsRepository;
import org.pcollections.PSequence;

import javax.inject.Inject;


/**
 * this service has read side as well
 *
 */
public class ProductServiceImpl implements ProductService {

    private final ProductsRepository repository;
    private final PersistentEntityRegistry registry;

    @Inject
    public ProductServiceImpl(final ProductsRepository repository, final PersistentEntityRegistry registry) {
        this.repository = repository;
        this.registry = registry;
        registry.register(InventoryEntity.class);
    }

    @Override
    public ServiceCall<NotUsed, PSequence<Product>> search(final String productType, final String productName) {
        return request -> repository.search(productName,productType);
    }

    @Override
    public ServiceCall<Product, Done> addProduct(String inventoryId) {
        return request -> registry.refFor(InventoryEntity.class, inventoryId).ask(new InventoryCommand.CreateProduct(request.getProductId(),request.getProductName(),request.getProductType(),request.getProductUserId()));
    }


}
