package com.searchx.rs2.basket.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import lombok.Value;

import java.util.List;

@Value
@JsonDeserialize
public class Basket {

    private final String id;

    private final List<Product> products;

    @JsonCreator
    public Basket(String id, List<Product> products) {
        this.id = Preconditions.checkNotNull(id, "id is null");
        this.products = Preconditions.checkNotNull(products, "products is null");
    }
}
