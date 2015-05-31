package org.ludwiggj.concurrency.deadlocks;

import org.ludwiggj.concurrency.model.blog.Author;
import org.ludwiggj.concurrency.model.blog.SimpleMicroBlogNode;
import org.ludwiggj.concurrency.model.blog.Update;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockWithLockMicroBlogNode implements SimpleMicroBlogNode {

  private static Update getUpdate(String s) {
    Update.Builder b = new Update.Builder();
    b.updateText(s).author(new Author("Ben"));

    return b.build();
  }

  private final String ident;

  private final Lock lock = new ReentrantLock();

  public DeadlockWithLockMicroBlogNode(String ident_) {
    ident = ident_;
  }

  public String getIdent() {
    return ident;
  }

  @Override
  public void propagateUpdate(Update upd_, SimpleMicroBlogNode backup_) {
    lock.lock();
    try {
      System.out.println(ident + ": recvd: " + upd_.getUpdateText()
          + " ; backup: " + backup_.getIdent());
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("ident: " + ident + " to call confirmUpdate on " + backup_.getIdent());
      backup_.confirmUpdate(this, upd_);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public void confirmUpdate(SimpleMicroBlogNode other_, Update upd_) {
    lock.lock();
    try {
      System.out.println(ident + ": recvd confirm: " + upd_.getUpdateText()
          + " from " + other_.getIdent());
    } finally {
      lock.unlock();
    }
  }

  public static void main(String[] a) {
    final DeadlockWithLockMicroBlogNode local = new DeadlockWithLockMicroBlogNode(
        "localhost:1111");
    final DeadlockWithLockMicroBlogNode other = new DeadlockWithLockMicroBlogNode(
        "localhost:9999");
    final Update first = getUpdate("1");
    final Update second = getUpdate("2");

    new Thread(new Runnable() {
      public void run() {
        local.propagateUpdate(first, other);
      }
    }).start();

    new Thread(new Runnable() {
      public void run() {
        other.propagateUpdate(second, local);
      }
    }).start();
  }

}