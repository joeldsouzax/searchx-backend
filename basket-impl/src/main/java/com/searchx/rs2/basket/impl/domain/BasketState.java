package com.searchx.rs2.basket.impl.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.serialization.CompressedJsonable;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@JsonDeserialize
public final class BasketState implements CompressedJsonable {

    private final List<String> products;

    public static final BasketState EMPTY = new BasketState(new ArrayList<String>());

    @JsonCreator
    public BasketState(List<String> products) {
        this.products = Preconditions.checkNotNull(products, "products");
    }

    public BasketState add(String productId) {
        List<String> newProducts = new ArrayList<>(products);;
        newProducts.add(productId);
        return new BasketState(newProducts);
    }

    public BasketState remove(String productId) {
        products.remove(productId);
        List<String> newProducts = new ArrayList<>(products);;
        newProducts.add(productId);
        return new BasketState(newProducts);
    }

    public List<String> getProducts() {
        return products;
    }
}
