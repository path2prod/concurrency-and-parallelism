package com.path2prod;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompletableFutureDemo {
    public void run_ThenCombineDemo(){
        new ThenCombineDemo().getReport();
    }

    public void run_ChainedDemo(){
        new MultipleChainedFutures().processOrder();
    }

    public void run_allOfDemo(){
        new AllOfDemo().processOrder();
    }

    public void run_AnyOfDemo(){
        new AnyOfDemo().printFirstFinished();
    }


    private class AnyOfDemo{
        private CompletableFuture<Integer> first(){
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(10 * 100));
                    log.info("first finished");
                    return 1;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        private CompletableFuture<Integer> second(){
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(10 * 100));
                    log.info("second finished");
                    return 2;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        private CompletableFuture<Integer> third(){
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(10 * 100));
                    log.info("third finished");
                    return 3;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        public void printFirstFinished(){
            CompletableFuture
            .anyOf(first(), second(),third())
            .thenAccept( v -> log.info("first number: {}",v.toString()))
            .join();
        }
    }

    private class AllOfDemo{
        private CompletableFuture<Double> fetchPrice(String productId){
            return CompletableFuture.supplyAsync( () -> {
                    return productId.contains("cheap") 
                        ? ThreadLocalRandom.current().nextDouble(5.0) 
                        : ThreadLocalRandom.current().nextDouble(5.0,10.0);
            });
            
        }

        private CompletableFuture<Double> fetchShippingCosts(String country){
            return CompletableFuture.supplyAsync( () -> {
                    return country.contains("UK") 
                        ? 0.0 
                        : ThreadLocalRandom.current().nextDouble(3.5,6.0);
            });
        }

        private CompletableFuture<Double> fetchDiscount(String code){
            return CompletableFuture.supplyAsync( () -> {
                    return code.contains("SUMMER10") 
                        ?  0.10
                        : 0;
            });
        }

        public void processOrder(){
            CompletableFuture<Double> priceFuture = fetchPrice("expensive1");
            CompletableFuture<Double> shippingCostsFuture = fetchShippingCosts("UK");
            CompletableFuture<Double> discountFuture = fetchDiscount("SUMMER10");

            CompletableFuture<Void> allOf = CompletableFuture.allOf(priceFuture, shippingCostsFuture, discountFuture)
            .thenRun( () -> log.info("All tasks complete"));

            double finalPrice = allOf.thenApply( v -> {
                double price = priceFuture.join();
                double shippingCosts = shippingCostsFuture.join();
                double discount = discountFuture.join();
                return price + shippingCosts - (price * discount);

            })
            .join();

            log.info("Fina price: {}",finalPrice);
        }
    }


    private class MultipleChainedFutures{

        public void processOrder(){
            fetchOrder("order1")
            .thenCompose(this::createInvoice)
            .thenApply(this::updateOrder)
            .thenAccept( (order) -> {
                log.info("Order {} is {}", order.getProductId(), order.getStatus());
            });
        }

        private CompletableFuture<Order> fetchOrder(String orderId){
            return CompletableFuture.supplyAsync(() -> Order.createOrder(orderId));
        }

        private CompletableFuture<Invoice> createInvoice(Order order){
            return CompletableFuture.supplyAsync(() -> {
                return Invoice.createInvoice(order, Product.createProduct(order.getProductId()));
            });
        }

        private Order updateOrder(Invoice invoice){
            return invoice.order().setConfirmStatus();
        }
    }


    private class ThenCombineDemo{

        private String fetchUserData(){
            return "UserId: ".concat(String.valueOf(new Random().nextInt(10)));
        }

        private String fetchOrderData(){
            return "OrderId: ".concat(String.valueOf(new Random().nextInt(10)));
        }

        public void getReport(){
            CompletableFuture<String> userDataFuture = CompletableFuture.supplyAsync( () -> fetchUserData());
            CompletableFuture<String> orderDataFuture = CompletableFuture.supplyAsync( () -> fetchOrderData());

            String report = userDataFuture
                                .thenCombine(orderDataFuture, (userData, orderData) -> String.format("%s has purchased %s",userData, orderData))
                                .join();

            log.info(report);                    
        }
    }
}
