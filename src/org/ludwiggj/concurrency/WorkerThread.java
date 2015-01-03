package org.ludwiggj.concurrency;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class WorkerThread implements Runnable {
  private final CountDownLatch latch;
  private final int workerNumber;
  private final Map<String, Integer> dataMap;
  private final int minTarget;
  private final int maxTarget;
  private List<Integer> targetList;

  public WorkerThread(Map<String, Integer> assignedMap, CountDownLatch latch, int workerNumber, int blockSize) {
    this.dataMap = assignedMap;
    this.latch = latch;
    this.workerNumber = workerNumber;

    this.minTarget = ((workerNumber - 1) * blockSize) + 1;
    this.maxTarget = workerNumber * blockSize;
  }

  @Override
  public void run() {
    createTargetList();

    do {
      checkTargetList();
    } while (targetList.size() > 0);

    latch.countDown();
    System.out.println("Worker [" + workerNumber + "] completed. Latch count[" + latch.getCount() + "]");
  }

  private void checkTargetList() {
    for (Iterator<Integer> iterator = targetList.iterator(); iterator.hasNext(); ) {
      if (targetIsPresent(iterator.next())) {
        iterator.remove();
      }
    }
  }

  private boolean targetIsPresent(Integer target) {
    return dataMap.get(String.valueOf(target)) != null;
  }

  private void createTargetList() {
    targetList = new ArrayList<Integer>();
    for (int i = minTarget; i <= maxTarget; i++) {
      targetList.add(i);
    }
    System.out.println("Worker [" + workerNumber + "] Handling [" + minTarget + " - " + maxTarget + "]");
  }
}