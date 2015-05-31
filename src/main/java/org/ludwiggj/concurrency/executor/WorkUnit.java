package org.ludwiggj.concurrency.executor;

public class WorkUnit<T> {
  private final T workUnit;

  public T getWork() {
    return workUnit;
  }

  public WorkUnit(T workUnit_) {
    workUnit = workUnit_;
  }
}