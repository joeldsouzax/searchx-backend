package com.searchx.rs2.product.impl.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.searchx.rs2.product.api.Product;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@JsonDeserialize
public final class InventoryState implements CompressedJsonable {


    private final List<Product> products;

    public static final InventoryState EMPTY = new InventoryState(new ArrayList<>());

    public InventoryState(List<Product> products) {
        this.products = Preconditions.checkNotNull(products, "products");
    }


    public InventoryState add(Product product) {
        List<Product> newProducts = new ArrayList<>(products);;
        newProducts.add(product);
        return new InventoryState(newProducts);
    }
}
