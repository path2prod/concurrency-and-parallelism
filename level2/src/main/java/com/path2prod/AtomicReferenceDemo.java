package com.path2prod;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtomicReferenceDemo {


    private class Task{
        private String taskName;
        private AtomicReference<Boolean> processed = new AtomicReference(false);


        public Task(String taskName) {
            this.taskName = taskName;
        }

        
        public void process() {
            if (processed.compareAndSet(false, true)) { 
                log.info(" {} is rocessing {}", Thread.currentThread().getName(),taskName);
                try {
                    Thread.sleep(new Random().nextInt(20, 100) * 10);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }else{
                log.info("{} has been already processed",taskName);
            }
        }
    }

    void start(){
        BlockingQueue<Task> queue = new LinkedBlockingQueue<>();
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 50; i++) {
            queue.add(new Task("TaskID-".concat(String.valueOf(i))));
        }

        Runnable worker = () -> {
            while (!queue.isEmpty()) {
                queue.poll().process();
            }
        };

        
        IntStream.range(0, 10).parallel().forEach(i -> executorService.submit(worker));
        

        executorService.shutdown();
        
        log.info("All tasks submitted");
    }

}
