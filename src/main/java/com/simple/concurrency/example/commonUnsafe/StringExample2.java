package com.simple.concurrency.example.commonUnsafe;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Create by S I M P L E on 2018/04/24 14:36:16
 */

@Slf4j
public class StringExample2 {

    public static StringBuffer stringBuffer = new StringBuffer();

    public static void main(String[] args) throws Exception {
        //请求总和
        int clientTotal = 5000;
        //同时并发执行的线程数
        int threadTotal = 200;
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                    log.error("Exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("stringBuffer:{}", stringBuffer.length());
    }

    private static void add() {
        stringBuffer.append(1);
    }
}
