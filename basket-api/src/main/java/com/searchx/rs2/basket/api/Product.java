package com.searchx.rs2.basket.api;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import lombok.Value;

@Value
@JsonDeserialize
public class Product {

    private final String productId;

    @JsonCreator
    public Product(final String productId) {
        this.productId = Preconditions.checkNotNull(productId,"product id is null");
    }
}
