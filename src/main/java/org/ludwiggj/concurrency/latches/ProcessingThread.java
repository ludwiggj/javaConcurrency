package org.ludwiggj.concurrency.latches;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class ProcessingThread extends Thread {
  private static int MAX_THREADS = 20;
  private final String ident;
  private final CountDownLatch latch;

  public ProcessingThread(String ident_, CountDownLatch cdl_) {
    ident = ident_;
    latch = cdl_;
  }

  public String getIdentifier() {
    return ident;
  }

  public void initialize() {
    try {
      Thread.sleep((int) (Math.random() * 1000));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    latch.countDown();
    System.out.println(String.format("%s initialised, count is %d", ident, latch.getCount()));
  }

  public void run() {
    initialize();
  }

  public static void main(String[] a) {
    final int quorum = 1 + (MAX_THREADS / 2);
    final CountDownLatch cdl = new CountDownLatch(quorum);

    final Set<ProcessingThread> nodes = new HashSet<>();
    try {
      for (int i = 0; i < MAX_THREADS; i++) {
        ProcessingThread local = new ProcessingThread(
            "localhost:" + (9000 + i), cdl);
        nodes.add(local);
        local.start();
      }
      cdl.await();
      System.out.println("Let's go!");
    } catch (InterruptedException e) {

    } finally {
    }
  }
}