package com.example.service1;

public class User {
  private String name;
  private String password;
  private Integer age;

  public User(String name, String password, Integer age) {
    this.name = name;
    this.password = password;
    this.age = age;
  }
  public User(){}
  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }
}

