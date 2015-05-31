package org.ludwiggj.concurrency.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PrimeFinder {
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
    // Following is a naive attempt; see PrimeFinderTask for correct implementation
    return new Future<Long>() {
      @Override
      public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
      }

      @Override
      public boolean isCancelled() {
        return false;
      }

      @Override
      public boolean isDone() {
        return false;
      }

      @Override
      public Long get() throws InterruptedException, ExecutionException {
        return 5L;
      }

      @Override
      public Long get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        Thread.sleep(3000);
        return 5L;
      }
    };
  }
}