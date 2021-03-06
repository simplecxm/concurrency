package com.simple.concurrency.example.syncContainer;


import com.google.common.collect.Lists;
import com.simple.concurrency.annotation.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Create by S I M P L E on 2018/04/24 15:13:51
 */

@Slf4j
@ThreadSafe
public class CollectionsExample1 {

    private static List<Integer> integers = Collections.synchronizedList(Lists.newArrayList());

    public static void main(String[] args) throws Exception {
        //请求总和
        int clientTotal = 5000;
        //同时并发执行的线程数
        int threadTotal = 200;
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            final int count = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update(count);
                    semaphore.release();
                } catch (Exception e) {
                    log.error("Exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("size:{}",integers.size());
    }

    private static void update(int i) {
        integers.add(i);
    }
}
