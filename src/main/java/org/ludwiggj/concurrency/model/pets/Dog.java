package org.ludwiggj.concurrency.model.pets;

public class Dog extends Pet {
  public Dog(String name) {
    super(name);
  }

  @Override
  public void examine() {
    System.out.println(String.format("Dog %s says Woof!", name));
  }
}
