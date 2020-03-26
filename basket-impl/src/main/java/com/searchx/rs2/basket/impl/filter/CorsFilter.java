package com.searchx.rs2.basket.impl.filter;

import play.filters.cors.CORSFilter;
import play.http.DefaultHttpFilters;

import javax.inject.Inject;

public class CorsFilter extends DefaultHttpFilters {

    @Inject
    public CorsFilter(CORSFilter filter) {
        super(filter);
    }
}
