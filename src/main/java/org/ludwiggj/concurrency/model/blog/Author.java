package org.ludwiggj.concurrency.model.blog;

public class Author {

  private final String name;

  public String getName() {
    return name;
  }

  public Author(String name_) {
    name = name_;
  }

  @Override
  public String toString() {
    return "Author [name=" + name + "]";
  }

}
