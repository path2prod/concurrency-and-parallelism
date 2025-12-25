package com.path2prod;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcurrentHashMapDemo {
    private final int totalThread = 4;
    private ConcurrentHashMap<String,Integer> wordsCounter = new ConcurrentHashMap<>();

    public void run(){
        ExecutorService pool = Executors.newFixedThreadPool(totalThread);
        List<String> words = Arrays.asList(
                "apple", "banana", "apple", "orange", "banana", "apple",
                "pear", "banana", "orange", "apple", "pear", "banana",
                "apple", "banana", "apple", "orange", "banana", "apple",
                "pear", "banana", "orange", "apple", "pear", "banana",
                "apple", "banana", "apple", "orange", "banana", "apple",
                "pear", "banana", "orange", "apple", "pear", "banana",
                "apple", "banana", "apple", "orange", "banana", "apple",
                "pear", "banana", "orange", "apple", "pear", "banana",
                "apple", "banana", "apple", "orange", "banana", "apple",
                "pear", "banana", "orange", "apple", "pear", "banana",
                "apple", "banana", "apple", "orange", "banana", "apple",
                "pear", "banana", "orange", "apple", "pear", "banana"
        );

        IntStream.range(0, 4).forEach(i-> {
            pool.submit(() -> {
                List<String> subList = subListRangeByIndex(words, i, totalThread);
                subList.forEach(word -> {
                    log.info("{} thread inserting: {}",Thread.currentThread().getName(),word);
                    wordsCounter.merge(word, 1, Integer::sum);
                });

            });
        });

        pool.shutdown();
        while(!pool.isTerminated()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        wordsCounter.entrySet().forEach( e -> log.info("{} word is {} present",e.getKey(),e.getValue()));
    }

    private List<String> subListRangeByIndex(List<String> originalList, int sublistIndex, int totalSublists){

        int size = originalList.size();
        int baseSize = size / totalSublists;
        int remainder = size % totalSublists;
        
        int start = sublistIndex * baseSize + Math.min(sublistIndex, remainder);
        int end = start + baseSize + (sublistIndex < remainder ? 1 : 0);
        

        
        return originalList.subList(start, end);
    }


}
