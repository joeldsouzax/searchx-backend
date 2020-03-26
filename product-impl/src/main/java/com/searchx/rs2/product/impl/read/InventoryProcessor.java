package com.searchx.rs2.product.impl.read;

import com.google.common.collect.ImmutableMap;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.ReadSideProcessor;
import com.lightbend.lagom.javadsl.persistence.jpa.JpaReadSide;
import com.searchx.rs2.product.impl.domain.InventoryEvent;
import com.searchx.rs2.product.impl.domain.ProductJpaEntity;
import org.pcollections.PSequence;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class InventoryProcessor extends ReadSideProcessor<InventoryEvent> {


    private final JpaReadSide readSide;

    @Inject
    public InventoryProcessor(JpaReadSide readSide) {
        this.readSide = readSide;
    }

    @Override
    public ReadSideHandler<InventoryEvent> buildHandler() {

        JpaReadSide.ReadSideHandlerBuilder<InventoryEvent> builder = readSide.builder("inventorySummaryOffset");
        builder.setGlobalPrepare(this::createSchema);
        builder.setEventHandler(InventoryEvent.ProductCreated.class,this::createProduct).build();

        return builder.build();

    }

    @Override
    public PSequence<AggregateEventTag<InventoryEvent>> aggregateTags() {
        return InventoryEvent.TAG.allTags();
    }

    private void createSchema(@SuppressWarnings("unused") EntityManager ignored) {
        Persistence.generateSchema("default", ImmutableMap.of("hibernate.hbm2ddl.auto", "update"));
    }


    private void createProduct(EntityManager em, InventoryEvent.ProductCreated event) {

        if(checkProduct(em,event.getProductId()) == null) {
            ProductJpaEntity productJpaEntity = new ProductJpaEntity();
            productJpaEntity.setId(event.getProductId());
            productJpaEntity.setName(event.getName());
            productJpaEntity.setType(event.getType());
            productJpaEntity.setUserId(event.getUserId());
            em.persist(productJpaEntity);
        }
    }

    private ProductJpaEntity checkProduct(EntityManager em, String productId) {
        return em.find(ProductJpaEntity.class, productId);
    }
}
