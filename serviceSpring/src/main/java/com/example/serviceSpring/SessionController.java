package com.example.serviceSpring;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class SessionController {
  @GetMapping("/sessionId")
  public ResponseEntity<String> getSessionIdFromServiceVertx() throws URISyntaxException, IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
      .uri(new URI("http://localhost:8889/sessionId"))
      .version(HttpClient.Version.HTTP_2)
      .GET()
      .build();
    HttpResponse<String> response = HttpClient.newHttpClient()
      .send(request, HttpResponse.BodyHandlers.ofString());
    return ResponseEntity.ok(response.body());
  }
}
