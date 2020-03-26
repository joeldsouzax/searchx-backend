package com.searchx.rs2.product.impl.domain;

import com.lightbend.lagom.javadsl.persistence.jpa.JpaSession;
import com.searchx.rs2.product.api.Product;
import org.pcollections.PSequence;
import org.pcollections.TreePVector;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletionStage;

@Singleton
public class ProductsRepository {
    private final JpaSession jpaSession;

    @Inject
    public ProductsRepository(JpaSession jpaSession) {
        this.jpaSession = jpaSession;
    }

    public CompletionStage<PSequence<Product>> search(final String productName, final String productType) {
        return jpaSession
                .withTransaction(
                        em -> em.createQuery( "SELECT NEW com.searchx.rs2.product.api.Product(p.id, p.name, p.type, p.userId)" +
                " FROM Product p" + " WHERE p.name = :name  AND p.type = :type",Product.class).setParameter("name",productName).setParameter("type",productType)
                                .getResultList()
        ).thenApply(TreePVector::from);
    }

}
