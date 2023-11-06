package com.example.serviceSpring;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
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

  public Integer getAge() {
    return age;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setAge(Integer age) {
    this.age = age;
  }
}
