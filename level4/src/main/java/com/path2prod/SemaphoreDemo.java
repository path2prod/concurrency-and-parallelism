package com.path2prod;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SemaphoreDemo {

    public void run(){
        var total_users = 10;
        var max_users = 3; 
        Semaphore semaphore = new Semaphore(max_users);


        for (var i = 0; i < total_users; i++){
            var userId=i;
            Runnable userTask = () -> {
                log.info("User {} waiting resource", userId);
                try {
                    while(!semaphore.tryAcquire(1000,TimeUnit.SECONDS))
                        log.info("no resources available for user {}",userId);

                    log.info("User {} resouce acquired", userId);
                    Thread.sleep(ThreadLocalRandom.current().nextInt(500,1000));
                    semaphore.release();
                    log.info("User {} released resource", userId);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            };
            new Thread(userTask).start();
        }
    }
}
