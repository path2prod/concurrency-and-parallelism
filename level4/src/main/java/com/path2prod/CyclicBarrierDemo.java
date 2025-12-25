package com.path2prod;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CyclicBarrierDemo {
    ExecutorService pool = Executors.newFixedThreadPool(3);

    public void run(){
        CompletableFuture<Void> pipeline = runStage("wiring cables")
        .thenCompose(v -> runStage("plastering walls"))
        .thenCompose( v-> runStage("painting walls"))
        .thenRun(() -> System.out.println("\n🏠 All stages completed successfully!"));


        pipeline.join();
        pool.shutdown();
        log.info("job finished");
    }

    public CompletableFuture<Void> runStage(String taskName){

        CompletableFuture<Void> stageDone = new CompletableFuture<>();
        CyclicBarrier barrier = new CyclicBarrier(3, () -> { 
            log.info("Stage {} has finished",taskName);
            stageDone.complete(null);
        });

        CompletableFuture.runAsync(() -> {
            log.info("Stage {} has started",taskName);
            for (int i=0; i<3; i++){
                pool.submit( () -> {
                    try {
                        log.info("{} has started",Thread.currentThread().getName());
                        Thread.sleep(ThreadLocalRandom.current().nextInt(100,500)*10);
                        log.info("{} has finished",Thread.currentThread().getName());
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    
                });
            }
        });

        return stageDone;
    }
}
