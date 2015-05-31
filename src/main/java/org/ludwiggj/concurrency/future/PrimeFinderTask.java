package org.ludwiggj.concurrency.future;

import java.util.concurrent.*;

public class PrimeFinderTask {
  public static void main(String[] args) throws Exception {
    boolean firstTry = true;
    Future<Long> fut = getNthPrime(1_000_000_000);
    Long result = null;
    while (result == null) {
      try {
        result = fut.get(2, TimeUnit.SECONDS);
      } catch (TimeoutException tox) {
        System.out.println("Timed out");
      }
      if (firstTry) {
        firstTry = false;
      } else {
        System.out.println("Still not found the billionth prime!");
      }
    }
    System.out.println("Found it: " + result.longValue());
  }

  private static Future<Long> getNthPrime(int i) throws InterruptedException {
    Callable<Long> callable = new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        Thread.sleep(5000);
        return 5L;
      }
    };
    FutureTask<Long> futureTask = new FutureTask<>(callable);
    new Thread(futureTask).start();
    return futureTask;
  }
}