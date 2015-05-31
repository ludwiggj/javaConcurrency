package org.ludwiggj.concurrency.copyOnWrite;

import org.ludwiggj.concurrency.model.blog.Update;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;

public class MicroBlogTimeline {
  private final CopyOnWriteArrayList<Update> updates;
  private final Lock lock;
  private final String name;
  private Iterator<Update> it;

  MicroBlogTimeline(String name_, CopyOnWriteArrayList<Update> l_, Lock lock_) {
    name = name_;
    updates = l_;
    lock = lock_;
  }

  public void prep() {
    it = updates.iterator();
  }

  public void printTimeline() {
    lock.lock();
    try {
      if (it != null) {
        System.out.print(name + ": ");
        while (it.hasNext()) {
          Update s = it.next();
          System.out.print(s + ", ");
        }
        System.out.println();
      }
    } finally {
      lock.unlock();
    }
  }
}