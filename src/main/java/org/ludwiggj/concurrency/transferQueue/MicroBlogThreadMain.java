package org.ludwiggj.concurrency.transferQueue;

import org.ludwiggj.concurrency.model.blog.Author;
import org.ludwiggj.concurrency.model.blog.Update;

import java.util.concurrent.*;

public class MicroBlogThreadMain {

  public static void main(String[] a) {
    final Update.Builder ub = new Update.Builder();
    final TransferQueue<Update> lbq = new LinkedTransferQueue<>();

    MicroBlogThread t1 = new MicroBlogThread(lbq, 10) {
      public void doAction() {
        text = text + "X";
        Update u = ub.author(new Author("Tallulah")).updateText(text).build();
        boolean handed = false;
        try {
          handed = updates.tryTransfer(u, 100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
        }
        if (!handed)
          System.out
              .println("Unable to handoff Update to Queue due to timeout");
      }
    };

    MicroBlogThread t2 = new MicroBlogThread(lbq, 1000) {
      public void doAction() {
        Update u = null;
        try {
          u = updates.take();
          System.out.println(u);
        } catch (InterruptedException e) {
          return;
        }
      }
    };
    t1.start();
    t2.start();
  }
}