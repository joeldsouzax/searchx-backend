package com.searchx.rs2.product.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import com.searchx.rs2.product.api.ProductService;
import com.searchx.rs2.product.impl.domain.ProductsRepository;

public class ProductModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindService(ProductService.class, ProductServiceImpl.class);
        bind(ProductsRepository.class).asEagerSingleton();
    }
}
