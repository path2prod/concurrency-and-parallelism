package com.path2prod;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RaceCondition {
    private static int counter = 0;
    private int fixedCounter = 0;
    private AtomicInteger atomicCounter = new AtomicInteger(0);
    private static final int NUM_THREADS = 10;
    private static final int INCREMENTS_PER_THREAD = 100_000;



    public void createRaceCondition() throws InterruptedException{

        Thread[] threads = new Thread[NUM_THREADS];

        Runnable incrementTask = () -> {
            for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
                counter++; 
            }
        };

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(incrementTask);
            threads[i].start();
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i].join();
        }

        int expected = NUM_THREADS * INCREMENTS_PER_THREAD;
        log.info("Expected counter: {}", expected);
        log.info("Actual counter:   {}", counter);
    }


    public void fixRaceConditionUsingSyncronized() throws InterruptedException {
    
        Thread[] threads = new Thread[NUM_THREADS];

        Runnable incrementTask = () -> {
            for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
                incrementSharedCounter();
            }
        };

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(incrementTask);
            threads[i].start();
        }

         for (int i = 0; i < NUM_THREADS; i++) {
            threads[i].join();
        }

        int expected = NUM_THREADS * INCREMENTS_PER_THREAD;
        log.info("Expected counter: {}", expected);
        log.info("Actual counter:  {} ", fixedCounter);
    }

    private synchronized void incrementSharedCounter(){
        fixedCounter++; 
    }

    public void fixRaceConditionUsingAtomicInteger() throws InterruptedException {
    
        Thread[] threads = new Thread[NUM_THREADS];

        Runnable incrementTask = () -> {
            for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
                atomicCounter.incrementAndGet();
            }
        };

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(incrementTask);
            threads[i].start();
        }

         for (int i = 0; i < NUM_THREADS; i++) {
            threads[i].join();
        }

        int expected = NUM_THREADS * INCREMENTS_PER_THREAD;
        log.info("Expected counter: {}", expected);
        log.info("Actual counter:  {} ",atomicCounter.get());
    }

        

}
