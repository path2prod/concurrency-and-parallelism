package com.path2prod;

import java.util.concurrent.ExecutionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException{
        // exercise1();
        // exercise2();
        // exercise3();
        // exercise4();
        // exercise5();
        // exercise6();
        // exercise7();
        //exercise8();
        //exercise9();
        exercise10();
    }

    private static void exercise1() throws InterruptedException{
        RaceCondition rCondition = new RaceCondition();
        rCondition.createRaceCondition();
    }

    private static void exercise2() throws InterruptedException {
        RaceCondition rCondition = new RaceCondition();
        rCondition.fixRaceConditionUsingSyncronized();
    }

    private static void exercise3() throws InterruptedException {
        RaceCondition rCondition = new RaceCondition();
        rCondition.fixRaceConditionUsingAtomicInteger();
    }

    private static void exercise4() throws InterruptedException {
        FixedThreadPool.executeTasks();
    }

    private static void exercise5() throws InterruptedException, ExecutionException {
        FixedThreadPool.executeComputingTasks();
    }

    private static void exercise6() throws InterruptedException{
        ProducerConsumerPattern.start();
    }

    private static void exercise7() throws InterruptedException{
        ProducerConsumerLinkedBlockingQueuePattern producerConsumer = new ProducerConsumerLinkedBlockingQueuePattern();
        producerConsumer.start();
    }

    private static void exercise8() throws InterruptedException{
        LiveLockDemo liveLockDemo = new LiveLockDemo();
        liveLockDemo.start();
    }

    private static void exercise9() throws InterruptedException{
        ShutdownExecutorGracefullyDemo shutdownExecutorGracefullyDemo = new ShutdownExecutorGracefullyDemo();
        shutdownExecutorGracefullyDemo.start();
    }

    private static void exercise10() throws InterruptedException{
        AtomicReferenceDemo atomicReferenceDemo = new AtomicReferenceDemo();
        atomicReferenceDemo.start();
    }


}
