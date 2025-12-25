package com.path2prod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataProcessingPipelineDemo {

    SynchronousQueue<Integer> queue = new SynchronousQueue<>();
    List<Integer> results = new CopyOnWriteArrayList<>();


    public void run(){
        ExecutorService pool = Executors.newFixedThreadPool(2);

        Runnable producer = () -> {
                IntStream.range(0, 5)
                         .forEach( i -> { 
                            int random = ThreadLocalRandom.current().nextInt(10);
                            log.info("producing {}",random);
                            try {
                                queue.put(random);
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } });
                            
        };

        Runnable consumer = () -> {
            IntStream.range(0, 5).forEach( v -> { 
                    CompletableFuture.<Integer>supplyAsync( () -> {
                        try {
                            int consumed = queue.take();
                            log.info("consumed {}",consumed);
                            return consumed;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .thenApply( i -> i > 8 ? i : null)
                    .thenAccept( i -> { if (i != null){ results.add(i); } });
            });
        };
    
        pool.execute(producer);
        pool.execute(consumer);
        pool.shutdown();

        try {
            pool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        log.info("total {}",results.size());

    }

}
