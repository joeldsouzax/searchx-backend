package com.searchx.rs2.product.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.internal.javadsl.persistence.jdbc.JavadslJdbcOffsetStore;
import com.lightbend.lagom.internal.javadsl.persistence.jdbc.SlickProvider;
import com.lightbend.lagom.internal.persistence.jdbc.SlickOffsetStore;
import com.lightbend.lagom.javadsl.persistence.jdbc.GuiceSlickProvider;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import com.searchx.rs2.product.api.ProductService;
import com.searchx.rs2.product.impl.domain.ProductsRepository;

public class ProductModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindService(ProductService.class, ProductServiceImpl.class);
        bind(ProductsRepository.class).asEagerSingleton();
        bind(SlickProvider.class).toProvider(GuiceSlickProvider.class);
        bind(SlickOffsetStore.class).to(JavadslJdbcOffsetStore.class);
    }
}
