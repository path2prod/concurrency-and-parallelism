package com.path2prod;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadsJoinedSequentially {

    Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(new Random().nextInt(10, 25));
                log.info("thread 1 - ID {}", Thread.currentThread().threadId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
    });

    Thread thread2 = new Thread(() -> {
            try {
                thread1.join();
                Thread.sleep(new Random().nextInt(2, 5));
                log.info("thread 2 - ID {}", Thread.currentThread().threadId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
    });
    Thread thread3 = new Thread(() -> {
            try {
                thread2.join();
                Thread.sleep(new Random().nextInt(5, 15));
                log.info("thread 3 - ID {}", Thread.currentThread().threadId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
    });  

    public void runThreadsSequentially() throws InterruptedException {
        log.info("starting threads");
        thread3.start();
        thread2.start();
        thread1.start();
        thread1.join();
        thread2.join();
        thread3.join();
        log.info("threads done");
    }
    
    
}
