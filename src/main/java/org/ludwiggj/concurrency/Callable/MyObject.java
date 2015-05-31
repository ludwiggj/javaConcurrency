package org.ludwiggj.concurrency.Callable;

import java.util.concurrent.Callable;

public class MyObject {
  @Override
  public String toString() {
    return "MyObject";
  }

  public static void main(String[] args) throws Exception {
    Callable<String> cb = new Callable<String>() {
      @Override
      public String call() throws Exception {
        return new MyObject().toString();
      }
    };

    System.out.println(cb.call());
  }
}
