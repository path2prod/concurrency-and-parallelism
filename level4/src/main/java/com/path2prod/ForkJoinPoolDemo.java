package com.path2prod;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ForkJoinPoolDemo {

    public void run(){
        ForkJoinPool fpool = new ForkJoinPool();
        int[] numbers = IntStream.rangeClosed(1,10_000_00).toArray();
         
        long start = System.currentTimeMillis();
        long total = fpool.invoke(new ParallelSumTask(numbers));
        long end = System.currentTimeMillis();
        log.info("total {}",total);
        log.info("total ms {}", start - end);
    }

    private class ParallelSumTask extends RecursiveTask<Long>{
        int[] numbers;


        public ParallelSumTask(int[] numbers){
            this.numbers = numbers;
        }


        @Override
        protected Long compute() {
                int sequentialThreshold = 100;

                if (numbers.length <= sequentialThreshold) {
                        return Long.valueOf(IntStream.of(numbers).sum());
                }else{
                    ParallelSumTask left = new ParallelSumTask(IntStream.of(numbers).limit(numbers.length/2).toArray());
                    ParallelSumTask right = new ParallelSumTask(IntStream.of(numbers).skip(numbers.length/2).toArray());
                    return left.fork().join() + right.fork().join();
                }
        }

    }
}
