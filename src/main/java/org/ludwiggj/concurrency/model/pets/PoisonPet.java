package org.ludwiggj.concurrency.model.pets;

public class PoisonPet extends Pet {
  public static final String POISON_NAME = "Go home!";

  public PoisonPet() {
    super(POISON_NAME);
  }

  public void examine() {
    System.out.println(POISON_NAME);
  }
}