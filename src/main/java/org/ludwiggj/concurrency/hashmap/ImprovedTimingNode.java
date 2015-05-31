package org.ludwiggj.concurrency.hashmap;

import org.ludwiggj.concurrency.fullySynchronized.Propagator;
import org.ludwiggj.concurrency.fullySynchronized.Reader;
import org.ludwiggj.concurrency.model.blog.Author;
import org.ludwiggj.concurrency.model.blog.EnhancedSimpleMicroBlogNode;
import org.ludwiggj.concurrency.model.blog.Update;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ImprovedTimingNode implements EnhancedSimpleMicroBlogNode {

  private final String identifier;

  private final ConcurrentHashMap<Update, Long> arrivalTime = new ConcurrentHashMap<>();

  public ImprovedTimingNode(String identifier_) {
    identifier = identifier_;
  }

  @Override
  public void propagateUpdate(Update upd_, EnhancedSimpleMicroBlogNode backup_) {
    long currentTime = System.currentTimeMillis();
    arrivalTime.put(upd_, currentTime);
  }

  @Override
  public boolean confirmUpdate(
      EnhancedSimpleMicroBlogNode other_, Update update_, String readerId) {
    Long timeRecvd = arrivalTime.get(update_);
    if (timeRecvd != null) {
      System.out.println("Id: " + readerId + " Recvd confirm: " + update_.getUpdateText() + " from "
          + other_.getIdent() + " at " + timeRecvd);
    }
    return (timeRecvd != null);
  }

  @Override
  public String getIdent() {
    return identifier;
  }

  public static void main(String[] args) throws InterruptedException {
    ImprovedTimingNode etn = new ImprovedTimingNode("etn");
    ImprovedTimingNode smbn = new ImprovedTimingNode("smbn");

    Author a1 = new Author("Author 1");

    List<Update> updates = buildUpdatesForPropagator(new Update.Builder().author(a1), "Page %d completed", 123456);

    new Thread(new Reader("1", etn, smbn, updates.subList(0, 10))).start();
    new Thread(new Reader("2", etn, smbn, updates.subList(11, 20))).start();
    new Thread(new Reader("3", etn, smbn, updates.subList(21, 30))).start();
    new Thread(new Reader("4", etn, smbn, updates.subList(31, 40))).start();
    new Thread(new Reader("5", etn, smbn, updates.subList(41, 50))).start();

    new Thread(new Propagator(etn, smbn, updates)).start();
  }

  private static List<Update> buildUpdatesForPropagator(Update.Builder updateBuilder, String msg, int startTime) {
    List<Update> updates = new ArrayList<>();
    int createTime = startTime;

    for (int i = 1; i <= 50; i++) {
      updates.add(updateBuilder.updateText(String.format(msg, i)).createTime(createTime).build());
      createTime += i;
    }
    return updates;
  }
}