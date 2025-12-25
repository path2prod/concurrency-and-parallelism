package com.path2prod;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PriorityBlockingQueueDemo {

    public void run(){
        PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>(10, Comparator.comparingInt(Task::priority).reversed());       
         ExecutorService pool = Executors.newFixedThreadPool(4);

        IntStream.range(0, 4).forEach(i -> {
            pool.submit(() -> {
                IntStream.range(0, 10).forEach(j -> {
                    int priority = ThreadLocalRandom.current().nextInt(5);
                    String name = Thread.currentThread().getName().concat(String.format(" - %d", j));
                    log.info("offering {}",name);
                    queue.offer(new Task(priority,name));
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(100,500));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });    
             });
        });

        pool.shutdown();

        try {
            pool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("Now printing tasks in priority order:");
        Task task;
        while ((task = queue.poll()) != null) {
            log.info("polling task {} with priority {}", task.name(), task.priority());
        }
    }

    private record Task(int priority, String name){}

}
