package com.example.service1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class SessionVerticle extends AbstractVerticle {
  @Override
  public void start() {
    vertx.eventBus().consumer("incoming.sessionId.address", message -> {
      Router router = Router.router(vertx);
      router.get("/sessionId").handler((RoutingContext ctx) -> {
        ctx.response().send((String)message.body());
      });
      vertx.createHttpServer().requestHandler(router).listen(8889);
    });
  }

}
