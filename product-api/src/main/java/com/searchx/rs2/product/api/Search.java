package com.searchx.rs2.product.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Value;

@Value
@JsonDeserialize
public class Search {
    final private String name;
    final private String type;

    @JsonCreator
    public Search(final String name,final String type) {
        this.name = name;
        this.type = type;
    }
}
