package com.example.serviceSpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public User findByName(String name) {
    return this.userRepository.findByName(name);
  }
}
