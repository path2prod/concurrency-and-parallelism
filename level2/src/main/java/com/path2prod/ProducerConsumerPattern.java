package com.path2prod;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProducerConsumerPattern {
    private static Queue<Integer> buffer = new LinkedList<>();
    private static int maxSize=5;

    public static void start() throws InterruptedException{
        Thread producer = new Thread(new Producer(), "producer");
        Thread consumer = new Thread(new Consumer(), "consumer");

        log.info("starting buffer");

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
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Producer-Consumer completed.");

    }


    private static class Consumer implements Runnable {

        @Override
        public void run(){
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    consume();
                }
            } catch (InterruptedException e) {
                log.info("Consumer interrupted, exiting...");
            }
        }

        private void consume() throws InterruptedException{
            synchronized (buffer) {
                while (buffer.isEmpty()){
                    log.info("{} waiting on empty buffer", Thread.currentThread().getName());
                    buffer.wait();
                }

                log.info("{} polled {}",Thread.currentThread().getName(),buffer.poll());
                Thread.sleep(500);
                buffer.notifyAll();
            }
        }

    }

    private static class Producer implements Runnable {

        @Override
        public void run(){
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    produce();
                }
            } catch (InterruptedException e) {
                log.info("Producer interrupted, exiting...");
            }
        }

        private void produce() throws InterruptedException{
            synchronized (buffer){
                while (buffer.size() == maxSize){
                    log.info("{} waiting on full buffer", Thread.currentThread().getName());
                    buffer.wait();
                }

                int nextInt = new Random().nextInt(10);
                buffer.offer(nextInt);
                log.info("{} offered {}",Thread.currentThread().getName(),nextInt);
                buffer.notifyAll();
            }
        }

    
    }

}
