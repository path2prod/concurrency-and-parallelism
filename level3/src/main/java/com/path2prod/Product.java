package com.path2prod;

public record Product(String productId,String name, double price) {
    static Product createProduct(String productId){
        return new Product(productId, "random-product",2.0);
    }
}
