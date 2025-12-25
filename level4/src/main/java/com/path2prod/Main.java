package com.path2prod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args){
       //execirse1();
       //exercise2();
       //exercise3();
       //exercise4();
       //exercise5();
       //exercise6();
       exercise7();
    }

    public static void execirse1(){
        PriorityBlockingQueueDemo demo = new PriorityBlockingQueueDemo();
        demo.run();
    }

    public static void exercise2(){
        ConcurrentHashMapDemo demo = new ConcurrentHashMapDemo();
        demo.run();
    }

    public static void exercise3(){
        DataProcessingPipelineDemo demo = new DataProcessingPipelineDemo();
        demo.run();
    }

    public static void exercise4(){
        CountDownLatchDemo demo = new CountDownLatchDemo();
        demo.run();
    }

    public static void exercise5(){
        CyclicBarrierDemo demo = new CyclicBarrierDemo();
        demo.run();
    }

    public static void exercise6(){
        ForkJoinPoolDemo demo = new ForkJoinPoolDemo();
        demo.run();
    }

    public static void exercise7(){
        SemaphoreDemo demo = new SemaphoreDemo();
        demo.run();
    }
}
