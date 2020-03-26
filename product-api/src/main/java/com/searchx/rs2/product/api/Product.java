package com.searchx.rs2.product.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import lombok.Value;


@Value
@JsonDeserialize
public class Product {

    private final String productId;

    private final String productName;

    private final String productType;

    private final String productUserId;


    @JsonCreator
    public Product(final String productId,final String productName,final String productType,final String productUserId) {
        this.productId = Preconditions.checkNotNull(productId,"product id");
        this.productName = Preconditions.checkNotNull(productName,"product name");
        this.productType = Preconditions.checkNotNull(productType, "product Type");
        this.productUserId = Preconditions.checkNotNull(productUserId, "product User id");
    }
}
