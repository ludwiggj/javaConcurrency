package org.ludwiggj.concurrency.executor.pets;

import org.ludwiggj.concurrency.blockingQueue.pets.Receptionist;
import org.ludwiggj.concurrency.model.pets.Appointment;
import org.ludwiggj.concurrency.model.pets.Pet;

import java.util.concurrent.*;

public class EfficientSurgery {

  public static void main(String[] args) {
    ScheduledExecutorService stpe = Executors.newScheduledThreadPool(3);

    BlockingQueue<Appointment<Pet>> appointments = new ArrayBlockingQueue<>(10);

    Receptionist receptionist = new Receptionist(appointments, 200);

    receptionist.start();

    for (int i = 0; i < 3; i++) {
      stpe.scheduleAtFixedRate(new EfficientVeterinarian(appointments, stpe), 0, 1000, TimeUnit.MILLISECONDS);
    }
  }
}