package com.searchx.rs2.basket.api;


import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceAcl;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

public interface BasketService extends Service {

    ServiceCall<Product,Done> addProduct(String basketId);
    ServiceCall<Product, Done> removeProduct(String basketId);
    ServiceCall<NotUsed, Basket> get(String baskedId);


    @Override
    default Descriptor descriptor() {
     return Service.named("basket").withCalls(
             Service.restCall(Method.POST,"/basket/:productId", this::addProduct),
             Service.restCall(Method.DELETE, "/basket/:productId", this::removeProduct),
             Service.restCall(Method.GET, "/basket/:basketId",this::get)
     ).withAutoAcl(true).withServiceAcls(
             ServiceAcl.methodAndPath(Method.OPTIONS, "/basket/*"));
    }
}
