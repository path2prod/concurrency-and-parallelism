package com.path2prod;

import lombok.Getter;

@Getter
public class Order{
    private String orderId;
    private String status;
    private int quantity;
    private String productId;

    public Order(String orderId, String status, int quantity, String productId){
        this.orderId = orderId;
        this.status = status;
        this.quantity = quantity;
        this.productId = productId;
    }

    public Order setConfirmStatus(){ 
        this.status = "Confirmed";
        return this;
    }

    public static Order createOrder(String orderId){
        return new Order(orderId, "Pending", 3, "product-1");
    }

}

