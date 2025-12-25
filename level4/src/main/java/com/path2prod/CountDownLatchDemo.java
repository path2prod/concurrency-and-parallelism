package com.path2prod;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountDownLatchDemo {

    public void run(){
        CountDownLatch latch = new CountDownLatch(10);

        IntStream.range(0, 10).forEach(i -> {
            new Thread(() -> {
                log.info("Starting {}", Thread.currentThread().getName());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    latch.countDown();
                }
                
                log.info("Completing {}", Thread.currentThread().getName());
            }).start();
        });

        log.info("Waiting for threads");
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("All tasks done");



    }

}
