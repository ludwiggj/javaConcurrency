package org.ludwiggj.concurrency.executor;

import java.util.concurrent.*;

public class PeriodicReader {
  private ScheduledExecutorService stpe;
  private ScheduledFuture<?> hndl;        // Needed for cancellation
  private BlockingQueue<WorkUnit<String>> lbq = new LinkedBlockingQueue<>();

  private void run() {
    stpe = Executors.newScheduledThreadPool(2);
    final Runnable msgReader = new Runnable() {
      @Override
      public void run() {
        WorkUnit<String> nextMsg = lbq.poll();
        if (nextMsg != null) {
          System.out.println("Msg recvd: " + nextMsg.getWork());
        } else {
          System.out.println("Nothing doing...");
        }
      }
    };
    hndl = stpe.scheduleAtFixedRate(msgReader, 10, 1000, TimeUnit.MILLISECONDS);
  }

  public void cancel() {
    final ScheduledFuture<?> myHndl = hndl;
    stpe.schedule(new Runnable() {
      public void run() {
        myHndl.cancel(true);
      }
    }, 10, TimeUnit.MILLISECONDS);
  }


  public static void main(String[] args) {
    new PeriodicReader().run();
  }
}