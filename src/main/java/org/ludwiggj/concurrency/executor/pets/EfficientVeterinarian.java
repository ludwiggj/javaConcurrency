package org.ludwiggj.concurrency.executor.pets;

import org.ludwiggj.concurrency.model.pets.Appointment;
import org.ludwiggj.concurrency.model.pets.Pet;
import org.ludwiggj.concurrency.model.pets.PoisonPet;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

public class EfficientVeterinarian extends Thread {
  protected final BlockingQueue<Appointment<Pet>> appts;
  private final ScheduledExecutorService service;

  public EfficientVeterinarian(BlockingQueue<Appointment<Pet>> lbq, ScheduledExecutorService service) {
    this.appts = lbq;
    this.service = service;
  }

  @Override
  public void run() {
      seePatient();
  }

  public void seePatient() {
    try {
      System.out.print("Thread: " + this + " ");
      Appointment<Pet> ap = appts.take();
      Pet patient = ap.getPatient();

      if (PoisonPet.POISON_NAME.equals(patient.getName())) {
        shutdown();
      } else {
        patient.examine();
      }
    } catch (InterruptedException e) {
      shutdown();
    }
  }

  private void shutdown() {
    System.out.println("The veterinarian is leaving the building!");
    service.shutdownNow();
  }
}