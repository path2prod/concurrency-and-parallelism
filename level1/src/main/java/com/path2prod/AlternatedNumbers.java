package com.path2prod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlternatedNumbers {
    private static volatile int currentNumber=0;
    private static volatile boolean isSwitched=false;
    private static int MAX = 10;

    private class EvenPrinter extends Thread {

        @Override
        public void run(){
            while(currentNumber <= MAX){
                if (!isSwitched && currentNumber % 2 == 0){
                    log.info("Even ".concat(String.valueOf(currentNumber)));
                    currentNumber++;
                    isSwitched=true;
                }
            }
        }


    }

    private class OddPrinter extends Thread {

        @Override
        public void run(){
            while(currentNumber <= MAX){
                if (isSwitched && currentNumber % 2 != 0){
                    log.info("Odd ".concat(String.valueOf(currentNumber)));
                    currentNumber++;
                    isSwitched=false;
                }
            }

        }

    }

    public void printNumbersConcurrently() {   
        log.info("starting threads");
        new EvenPrinter().start();
        new OddPrinter().start();
        log.info("main thread ends before counting");
   }



}
