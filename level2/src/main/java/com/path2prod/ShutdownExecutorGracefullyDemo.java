package com.path2prod;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShutdownExecutorGracefullyDemo {

    public void start() throws InterruptedException{
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 4; i++) {
            executorService.submit(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        log.info("{} doing a job", Thread.currentThread().getName());
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        log.error("{} thread interrupted", Thread.currentThread().getName());
                        break;
                    }            
                }
            });            
        }


        IntStream.range(1, 5).forEach( i -> {
            String threadName = "Main Thread ".concat(String.valueOf(i));
            Thread t = new Thread(() -> {
                log.info("{} doing a job",threadName);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }, threadName);

            t.start();
        });

        executorService.shutdown();
        
        if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
            log.warn("Tasks were forced to end");
            executorService.shutdownNow();
        }

        log.info("Main has finished");
    }

}
