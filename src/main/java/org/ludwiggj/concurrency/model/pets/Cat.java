package org.ludwiggj.concurrency.model.pets;

public class Cat extends Pet {
  public Cat(String name) {
    super(name);
  }

  public void examine() {
    System.out.println(String.format("Cat %s says Meow!", name));
  }
}