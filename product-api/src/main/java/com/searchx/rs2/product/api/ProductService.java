package com.searchx.rs2.product.api;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;
import org.pcollections.PSequence;

public interface ProductService extends Service {

    ServiceCall<NotUsed, PSequence<Product>> search(final String productType, final String productName);

    ServiceCall<Product, Done> addProduct(String inventoryId);

    @Override
    default Descriptor descriptor() {
        return Service.named("products")
                .withCalls(
                        Service.restCall(Method.POST,"/product/:inventoryId",this::addProduct),
                        Service.restCall(Method.GET, "/product/search/:productTypeName/product/:productName",this::search)
                ).withAutoAcl(true);
    }
}
