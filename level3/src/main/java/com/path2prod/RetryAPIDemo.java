package com.path2prod;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetryAPIDemo {


    public void start(){
        retryOnError(() -> fetchData(), 1, this::isRetryableException, 1000)
        .thenAccept(data -> log.info("data: {}",data))
        .exceptionally(ex -> { 
            log.error("FAILED after all retries: {}", ex.getMessage());
            return null;}
        ).join();
    }

    private CompletableFuture<String> fetchData(){
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100 + ThreadLocalRandom.current().nextInt(200));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if ( !ThreadLocalRandom.current().nextBoolean()){
                throw new RuntimeException("error");
            }

            return "success";
        });
    }


    private boolean isRetryableException(Throwable ex) {
        return ex instanceof RuntimeException || 
               (ex.getMessage() != null && ex.getMessage().contains("503"));
    }

    private <T> CompletableFuture<T> retryOnError(Supplier<CompletableFuture<T>> dataSourceSupplier,int currentAttempts,Predicate<Throwable> isRetriable, long initialDelayMs){

        return dataSourceSupplier.get().handle( (result, ex) -> {
            if (Objects.isNull(ex)){
                return CompletableFuture.completedFuture(result);
            }

            if (currentAttempts >= 3) {
                    log.info("  Max attempts reached. Giving up.");
                    return CompletableFuture.<T>failedFuture(ex);
            }

            if (!isRetriable.test(ex.getCause())){
                    log.info("  Not retriable");
                    return CompletableFuture.<T>failedFuture(ex);
            }

            long delay = (long) (initialDelayMs * Math.pow(2, currentAttempts - 1));
            log.info("  Attempt {} failed. Retrying in {} ms...", currentAttempts,delay);
            
        return CompletableFuture
            .supplyAsync(() -> null, 
                CompletableFuture.delayedExecutor(delay, TimeUnit.MILLISECONDS))
            .thenCompose(v -> retryOnError(dataSourceSupplier, 
                currentAttempts + 1, isRetriable, initialDelayMs));

        }).thenCompose( Function.identity());
    }

}
