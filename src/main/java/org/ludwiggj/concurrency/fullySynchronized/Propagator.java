package org.ludwiggj.concurrency.fullySynchronized;

import org.ludwiggj.concurrency.model.blog.EnhancedSimpleMicroBlogNode;
import org.ludwiggj.concurrency.model.blog.Update;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Propagator implements Runnable {

  private EnhancedSimpleMicroBlogNode timingNode;
  private EnhancedSimpleMicroBlogNode enhancedSimpleMicroBlogNode;
  private List<Update> updates;

  public Propagator(EnhancedSimpleMicroBlogNode timingNode,
                    EnhancedSimpleMicroBlogNode enhancedSimpleMicroBlogNode, List<Update> updates) {
    this.timingNode = timingNode;
    this.enhancedSimpleMicroBlogNode = enhancedSimpleMicroBlogNode;
    this.updates = updates;
  }

  @Override
  public void run() {
    List<Integer> indexList = range(0, 49);
    Collections.shuffle(indexList);
    for (Integer index : indexList) {
      Update update = updates.get(index);
      try {
        Thread.sleep(500);
        timingNode.propagateUpdate(update, enhancedSimpleMicroBlogNode);
        System.out.println(String.format("Propagated: %s", update));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private static List<Integer> range(int start, int end) {
    List<Integer> result = new ArrayList<>();
    for (int i = start; i <= end; i++) {
      result.add(i);
    }
    return result;
  }
}
