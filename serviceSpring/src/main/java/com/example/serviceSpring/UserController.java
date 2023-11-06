package com.example.serviceSpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;
  @GetMapping("/{name}")
  public User findByName(@PathVariable("name") String name) {
    System.out.println(name);
    return this.userService.findByName(name);
  }
}
