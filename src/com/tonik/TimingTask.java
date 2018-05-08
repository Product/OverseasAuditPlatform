package com.tonik;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

public class TimingTask {
    
    private static Logger logger = Logger.getLogger(TimingTask.class);
    
    private static Random r = new Random();
    
    private static AtomicLong scannedNumber = new AtomicLong(292901);
    
    public static long getScannedNumber() {
        return scannedNumber.get();
    }
    
    static void updateScannedNumber() {
        scannedNumber.addAndGet(r.nextInt(2000) + 2000);
    }
}