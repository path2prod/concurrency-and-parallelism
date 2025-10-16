package com.path2prod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {
        exercise1();
        exercise2();
        exercise3();
        exercise4();
    }

    private static void exercise1() {
        AlternatedNumbers alternatedNumbers = new AlternatedNumbers();
        alternatedNumbers.printNumbersConcurrently();
    }

    private static void exercise2() throws InterruptedException{
        JoinedThreads joinedThreads = new JoinedThreads();
        joinedThreads.waitTill3ThreadsEnd();
    }

    private static void exercise3() throws InterruptedException{
        ThreadsJoinedSequentially threadsJoinedSequentially = new ThreadsJoinedSequentially();
        threadsJoinedSequentially.runThreadsSequentially();
    }

    private static void exercise4() throws InterruptedException{
        DaemonVSNonDaemon daemonVSNonDaemon = new DaemonVSNonDaemon();
        daemonVSNonDaemon.runThreads();
    }

}
