package com.path2prod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args){
        exercise1();
        exercise2();
        exercise3();
        exercise4();
        exercise5();
    }

    public static void exercise1(){
        CompletableFutureDemo demo = new CompletableFutureDemo();
        demo.run_ThenCombineDemo();
    }

    public static void exercise2(){
        CompletableFutureDemo demo = new CompletableFutureDemo();
        demo.run_ChainedDemo();
    }

    public static void exercise3(){
        CompletableFutureDemo demo = new CompletableFutureDemo();
        demo.run_allOfDemo();
    }

    public static void exercise4(){
        CompletableFutureDemo demo = new CompletableFutureDemo();
        demo.run_AnyOfDemo();
    }

    public static void exercise5(){
        RetryAPIDemo demo = new RetryAPIDemo();
        demo.start();
    }
}
