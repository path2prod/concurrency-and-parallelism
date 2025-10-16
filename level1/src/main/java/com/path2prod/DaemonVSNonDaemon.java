package com.path2prod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DaemonVSNonDaemon {

    Thread daemon = new Thread(() -> {
      log.info("monitoring.....");
      try {
        Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }  
    });

    Thread nonDaemon = new Thread(() -> {
        log.info("working.....");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });

    public void runThreads() throws InterruptedException {
        log.info("starting threads");
        daemon.setDaemon(true);
        daemon.start();
        nonDaemon.start();
        log.info("Main thread is exiting now but JVM continue till non-daemon ends");
    }

}
