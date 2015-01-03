package org.ludwiggj.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class SeederThread implements Runnable {
  public static final int REPORT_INTERVAL = 50;

  private final Map<String, Integer> dataMap;
  private final CountDownLatch latch;
  private final int maxRecordNumber;

  public SeederThread(Map<String, Integer> assignedMap, CountDownLatch latch, int maxRecordNumber) {
    this.dataMap = assignedMap;
    this.latch = latch;
    this.maxRecordNumber = maxRecordNumber;
  }

  @Override
  public void run() {
    System.out.println("Seeder started, dataMap size [" + dataMap.size() + "]");

    populateDataMap(getShuffledIntegerList());

    latch.countDown();

    System.out.println("Seeder complete, dataMap size [" + dataMap.size() + "], Latch count[" + latch.getCount() + "]");
  }

  private void populateDataMap(List<Integer> l) {
    for (int i: l) {
      randomWait();
      dataMap.put(String.valueOf(i), i);
      report();
    }
  }

  private void report() {
    if (dataMap.size() % REPORT_INTERVAL == 0) {
      System.out.println("Added [" + dataMap.size() + "] entries");
    }
  }

  private void randomWait() {
    try {
      Thread.sleep((long) (Math.random() * 10));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private List<Integer> getShuffledIntegerList() {
    List<Integer> l = new ArrayList<Integer>();
    for (int i = 1; i <= maxRecordNumber; i++) {
      l.add(i);
    }
    Collections.shuffle(l);
    return l;
  }
}