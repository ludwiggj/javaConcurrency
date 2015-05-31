package org.ludwiggj.concurrency.blockingQueue.pets;

import org.ludwiggj.concurrency.model.pets.Appointment;
import org.ludwiggj.concurrency.model.pets.Pet;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Surgery {
  public static final int LAST_PET_NUMBER = 100;

  public static void main(String[] args) {
    BlockingQueue<Appointment<Pet>> appointments = new ArrayBlockingQueue<>(10);

    Veterinarian veterinarian = new Veterinarian(appointments, 1000);
    Receptionist receptionist = new Receptionist(appointments, 200);

    veterinarian.start();
    receptionist.start();
  }
}
