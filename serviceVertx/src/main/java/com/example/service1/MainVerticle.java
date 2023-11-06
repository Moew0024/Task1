package com.example.service1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;

import java.net.http.HttpRequest;

public class MainVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    SessionStore store = LocalSessionStore.create(vertx);
    SessionHandler sessionHandler = SessionHandler.create(store);
    vertx.deployVerticle(new RedisVerticle());
    vertx.deployVerticle(new SessionVerticle());
    //vertx.deployVerticle(new MySqlVerticle());
    Router router = Router.router(vertx);
    router.route().handler(sessionHandler);
    router.post("/user/login").handler(this::saveUser);
    vertx.createHttpServer().requestHandler(router).listen(8888, handler -> {
      System.out.println("Server listen on port 8888");
    });
  }

  public void saveUser(RoutingContext ctx) {
    final EventBus eventBus = vertx.eventBus();

    eventBus.registerDefaultCodec(User.class, new GenericCodec<User>(User.class));
    HttpServerRequest request = ctx.request();
    Session session = ctx.session();
    request.body(handler -> {
      if (handler.succeeded()) {
        JsonObject reqBody = handler.result().toJsonObject();
        User user = new User();
        user.setName(reqBody.getString("name"));
        user.setPassword(reqBody.getString("password"));
        user.setAge(reqBody.getInteger("age"));
        eventBus.publish("incoming.user.address", user);
        eventBus.publish("incoming.sessionId.address", session.id());
      } else {
        System.out.println("Failed to login: " + handler.cause().getMessage());
      }
    });
  }
}
