package com.searchx.rs2.product.impl;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.lightbend.lagom.javadsl.persistence.ReadSide;
import com.searchx.rs2.product.api.Product;
import com.searchx.rs2.product.api.ProductService;
import com.searchx.rs2.product.api.Search;
import com.searchx.rs2.product.impl.domain.InventoryCommand;
import com.searchx.rs2.product.impl.domain.InventoryEntity;
import com.searchx.rs2.product.impl.domain.ProductsRepository;
import com.searchx.rs2.product.impl.read.InventoryProcessor;
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
    public ProductServiceImpl(final ProductsRepository repository, final PersistentEntityRegistry registry, ReadSide readSide) {
        this.repository = repository;
        this.registry = registry;
        registry.register(InventoryEntity.class);
        readSide.register(InventoryProcessor.class);
    }

    @Override
    public ServiceCall<Search, PSequence<Product>> search() {
        return request -> repository.search(request.getName(),request.getType());
    }

    @Override
    public ServiceCall<Product, Done> addProduct(String inventoryId) {
        return request -> registry.refFor(InventoryEntity.class, inventoryId).ask(new InventoryCommand.CreateProduct(request.getProductId(),request.getProductName(),request.getProductType(),request.getProductUserId()));
    }


}
