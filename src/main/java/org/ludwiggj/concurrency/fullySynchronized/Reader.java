package org.ludwiggj.concurrency.fullySynchronized;

import org.ludwiggj.concurrency.model.blog.EnhancedSimpleMicroBlogNode;
import org.ludwiggj.concurrency.model.blog.Update;

import java.util.List;

public class Reader implements Runnable {

  private EnhancedSimpleMicroBlogNode timingNode;
  private EnhancedSimpleMicroBlogNode enhancedSimpleMicroBlogNode;
  private List<Update> updates;
  private String id;

  public Reader(String id, EnhancedSimpleMicroBlogNode timingNode,
                EnhancedSimpleMicroBlogNode enhancedSimpleMicroBlogNode, List<Update> updates) {
    this.id = id;
    this.timingNode = timingNode;
    this.enhancedSimpleMicroBlogNode = enhancedSimpleMicroBlogNode;
    this.updates = updates;
  }

  @Override
  public void run() {
    System.out.println(String.format("Reader %s started!", id));
    boolean allDone;
    do {
      allDone = true;
      for (Update update : updates) {
        try {
          Thread.sleep(50);
          allDone = allDone && timingNode.confirmUpdate(enhancedSimpleMicroBlogNode, update, id);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    } while (! allDone);
    System.out.println(String.format("Reader %s all done!", id));
  }
}
