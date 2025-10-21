package com.path2prod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FixedThreadPool {

    public static void executeTasks() throws InterruptedException{
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
          IntStream.rangeClosed(0, 10).forEach( i -> {
            executor.submit(() -> {
                log.info("Thread {} running Task-{}", Thread.currentThread().threadId(),i);
                try {
                      Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            });
          });    
        

        executor.shutdown();
        executor.awaitTermination(1,TimeUnit.MINUTES);

        log.info("All tasks submitted");
    }

    public static void executeComputingTasks() throws InterruptedException, ExecutionException{
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<Integer>> futures = new ArrayList<>();
        
        IntStream.rangeClosed(0,10).forEach( (i) -> {
            Callable<Integer> task = () -> {
                int randomNumber = new Random().nextInt(11);
                log.info("TaskId-{}: Calculating squares till {} ",i,randomNumber);
                return IntStream.rangeClosed(1, randomNumber).map(n -> n*n).sum();
            };

            futures.add(executor.submit(task));
        });

        executor.shutdown();

        executor.awaitTermination(1,TimeUnit.MINUTES);

        int total = 0;
        for (Future<Integer> future : futures){
            total+=future.get();
        }

        log.info("All computed tasks executed, total = {}",total);
        
    }
}
