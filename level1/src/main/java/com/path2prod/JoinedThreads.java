package com.path2prod;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JoinedThreads {

    private class Thread1 extends Thread{

        @Override
        public void run() {
            try {
                Thread.sleep(new Random().nextInt(10, 25));
                log.info("thread 1 - ID {}", Thread.currentThread().threadId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

        private class Thread2 extends Thread{

        @Override
        public void run(){
            try {
                Thread.sleep(new Random().nextInt(2, 5));
                log.info("thread 2 - ID {}", Thread.currentThread().threadId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

        private class Thread3 extends Thread{

        @Override
        public void run(){
             try {
                Thread.sleep(new Random().nextInt(5, 15));
                log.info("thread 3 - ID {}", Thread.currentThread().threadId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void waitTill3ThreadsEnd() throws InterruptedException {
        log.info("starting threads");
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        Thread3 thread3 = new Thread3();
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        log.info("threads done");
    }

}
