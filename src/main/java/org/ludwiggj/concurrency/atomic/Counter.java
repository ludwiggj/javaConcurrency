package org.ludwiggj.concurrency.atomic;

import java.util.concurrent.atomic.AtomicLong;

public class Counter {

  private final AtomicLong sequenceNumber = new AtomicLong(0);

  public long nextId() {
    return sequenceNumber.getAndIncrement();
  }

  public static void main(String[] args) {
    Counter c = new Counter();
    System.out.println(c.nextId());
    System.out.println(c.nextId());
  }
}
