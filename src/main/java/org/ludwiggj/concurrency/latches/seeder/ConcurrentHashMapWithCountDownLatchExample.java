package org.ludwiggj.concurrency.latches.seeder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentHashMapWithCountDownLatchExample {

  public static final int NUMBER_OF_WORKER_THREADS = 50;
  public static final int NUMBER_OF_SEEDER_THREADS = 1;
  public static final int TOTAL_NUMBER_OF_THREADS = NUMBER_OF_WORKER_THREADS + NUMBER_OF_SEEDER_THREADS;

  public static final int MAX_RECORD_NUMBER = 1000;
  public static final int BLOCK_SIZE = MAX_RECORD_NUMBER / NUMBER_OF_WORKER_THREADS;

  private static CountDownLatch latch;
  private static Map<String, Integer> dataMap;
  private static ExecutorService executor;

  public static void main(String[] args) {
    dataMap = new ConcurrentHashMap<>(16, 0.9f, 1);
    latch = new CountDownLatch(TOTAL_NUMBER_OF_THREADS);
    executor = Executors.newFixedThreadPool(TOTAL_NUMBER_OF_THREADS);

    long timeBefore = System.currentTimeMillis();

    startWorkerThreads();
    startSeederThreads();

    // This will make the executor accept no new threads and finish all existing threads in the queue
    executor.shutdown();

    waitForAllThreadsToComplete();

    System.out.println("All threads completed in " + getProcessingTime(timeBefore) + " seconds");
  }

  private static void waitForAllThreadsToComplete() {
    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static void startSeederThreads() {
    for (int seederNumber = 1; seederNumber <= NUMBER_OF_SEEDER_THREADS; seederNumber++) {
      executor.execute(new SeederThread(dataMap, latch, MAX_RECORD_NUMBER));
    }
  }

  private static void startWorkerThreads() {
    for (int workerNumber = 1; workerNumber <= NUMBER_OF_WORKER_THREADS; workerNumber++) {
      executor.execute(new WorkerThread(dataMap, latch, workerNumber, BLOCK_SIZE));
    }
  }

  private static Float getProcessingTime(long timeBefore) {
    long timeAfter = System.currentTimeMillis();
    return new Float((float) (timeAfter - timeBefore) / (float) 1000);
  }
}
