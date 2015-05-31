package org.ludwiggj.concurrency.blockingQueue.pets;

import org.ludwiggj.concurrency.model.pets.Appointment;
import org.ludwiggj.concurrency.model.pets.Pet;
import org.ludwiggj.concurrency.model.pets.PoisonPet;

import java.util.concurrent.BlockingQueue;

public class Veterinarian extends Thread {
  protected final BlockingQueue<Appointment<Pet>> appts;
  protected String text = "";
  protected final int restTime;
  private boolean shutdown = false;

  public Veterinarian(BlockingQueue<Appointment<Pet>> lbq, int pause) {
    appts = lbq;
    restTime = pause;
  }

  public synchronized void shutdown() {
    shutdown = true;
  }

  @Override
  public void run() {
    while (!shutdown) {
      seePatient();
      try {
        Thread.sleep(restTime);
      } catch (InterruptedException e) {
        shutdown = true;
      }
    }
    System.out.println("The veterinarian is leaving the building!");
  }

  public void seePatient() {
    try {
      Appointment<Pet> ap = appts.take();
      Pet patient = ap.getPatient();

      if (PoisonPet.POISON_NAME.equals(patient.getName())) {
        shutdown = true;
      } else {
        patient.examine();
      }
    } catch (InterruptedException e) {
      shutdown = true;
    }
  }
}