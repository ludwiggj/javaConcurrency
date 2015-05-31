package org.ludwiggj.concurrency.blockingQueue.pets;

import org.ludwiggj.concurrency.model.pets.*;

import java.util.concurrent.BlockingQueue;

public class Receptionist extends Thread {
  protected final BlockingQueue<Appointment<Pet>> appts;
  protected final int arrivalTime;
  private boolean shutdown = false;
  private Integer petCount = 0;

  public Receptionist(BlockingQueue<Appointment<Pet>> lbq, int pause) {
    appts = lbq;
    arrivalTime = pause;
  }

  public synchronized void shutdown() {
    shutdown = true;
  }

  @Override
  public void run() {
    while (!shutdown) {
      addPatient();
      try {
        Thread.sleep(arrivalTime);
      } catch (InterruptedException e) {
        shutdown();
      }
    }
    System.out.println("The receptionist is leaving the building!");
  }

  public void addPatient() {
    Pet pet;
    if (petCount == Surgery.LAST_PET_NUMBER) {
      pet = new PoisonPet();
      shutdown();
    } else {
      pet = (petCount % 2 == 0) ? new Cat(petCount.toString()) : new Dog(petCount.toString());
      petCount += 1;
    }
    try {
      appts.put(new Appointment<>(pet));
      System.out.println(String.format("Pet %s booked in", pet.getName()));
    } catch (InterruptedException e) {
      shutdown = true;
    }
  }
}