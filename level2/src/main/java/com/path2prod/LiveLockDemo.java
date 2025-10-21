package com.path2prod;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LiveLockDemo {
    ReentrantLock firstLock = new ReentrantLock();
    ReentrantLock secondLock = new ReentrantLock();


    Worker worker1 = new Worker("worker-1",firstLock,secondLock);
    Worker worker2 = new Worker("worker-2",secondLock,firstLock);

    public void start(){
        Thread t1 = new Thread(worker1::work);
        Thread t2 = new Thread(worker2::work);
        
        t1.start();
        t2.start();
    }


    private class Worker{
        private String name;
        ReentrantLock firstLock;
        ReentrantLock secondLock;

        public Worker(String name, ReentrantLock firstLock, ReentrantLock secondLock) {
            this.name = name;
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }

        public void work(){
            int attemps=0;
            while (true) {
                attemps++;

                if (firstLock.tryLock()){
                    log.info("{} acquired first lock: {} on attemp {}",name,firstLock.isHeldByCurrentThread(),attemps);

                    try{
                        if (secondLock.tryLock()){
                            log.info("{} acquired second lock: {} on attemp {}",name,secondLock.isHeldByCurrentThread(),attemps);
                            log.info("{} has complete its job", name);
                            secondLock.unlock();
                            break;
                        }
                        else{
                            log.info("{} has not acquired second lock: {} on attemp {}",name,secondLock.isHeldByCurrentThread(),attemps);
                        }
                    }finally{
                        firstLock.unlock();
                    }
                }else{
                    log.info("{} has not acquired first lock: {} on attemp {}",name,firstLock.isHeldByCurrentThread(),attemps);
                }

                                
                int sleepTime = 100 + new Random().nextInt(200);
                log.info("{} backing off {} ms, attemp {}",name,sleepTime,attemps);
                
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
