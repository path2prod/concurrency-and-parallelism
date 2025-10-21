package com.path2prod;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProducerConsumerLinkedBlockingQueuePattern {
    BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);

    public void start(){
        Thread producer = new Thread(new Producer(), "producer");
        Thread consumer = new Thread(new Consumer(), "consumer");

        producer.start();
        consumer.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        producer.interrupt();
        consumer.interrupt();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        log.info("Producer-Consumer completed.");
    }

    private class Producer implements Runnable {
        @Override
        public void run() { 
            while (!Thread.currentThread().isInterrupted()) {
                int nextInt = new Random().nextInt(10);
                try {
                    queue.put(nextInt);
                    log.info("Producer successfully put: {}",nextInt);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } 
            }
        }
    }

    private class Consumer implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    log.info("Consumer successfully take: {}",queue.take());
                     Thread.sleep(500);
                } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                }
            }
         }
    }

}
