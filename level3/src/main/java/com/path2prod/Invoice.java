package com.path2prod;

public record Invoice(String invoiceId, Order order, double totalCost) {
    public static Invoice createInvoice(Order order, Product product){
        return new Invoice("invoice-1", order, order.getQuantity() * product.price());
    }
}
