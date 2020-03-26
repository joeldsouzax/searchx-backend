package com.searchx.rs2.basket.impl.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
@JsonDeserialize
public class BasketException extends RuntimeException implements Jsonable {
    public final String message;

    @JsonCreator
    public BasketException(String message) {
        super(message);
        this.message = message;
    }
}
