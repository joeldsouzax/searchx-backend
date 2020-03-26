package com.searchx.rs2.basket.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import com.searchx.rs2.basket.api.BasketService;

public class BasketModule extends AbstractModule implements ServiceGuiceSupport {

    @Override
    protected void configure() {
        bindService(BasketService.class, BasketServiceImpl.class);
    }
}
