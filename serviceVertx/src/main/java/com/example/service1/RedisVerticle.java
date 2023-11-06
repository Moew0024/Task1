package com.example.service1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.client.*;
import io.vertx.redis.client.impl.RedisClient;
import com.fasterxml.jackson.core.*;
import java.util.Map;

public class RedisVerticle extends AbstractVerticle {
    @Override
    public void start() {
      vertx.eventBus().consumer("incoming.user.address", message -> {
        User user = (User) message.body();
        JsonObject object = mapUserToJsonObject(user);
        Redis.createClient(vertx, "redis://localhost:6379")
          .connect()
          .onSuccess(conn -> {
             conn.send(Request.cmd(Command.HSET).arg("user24").arg(object.toBuffer().toJsonObject()));
             conn.close();
          });
      });
      vertx.eventBus().consumer("incoming.sessionId.address", message -> {
        Redis.createClient(vertx,
            new RedisOptions()
              .setConnectionString("redis://localhost:6379")
              .setHashSlotCacheTTL(3))
          .connect()
          .onSuccess(conn -> {
            conn.send(Request.cmd(Command.SET).arg("sessionId").arg((String )message.body()));
            conn.close();
          });
      });
    }
    public static JsonObject mapUserToJsonObject(User user) {
      JsonObject object = new JsonObject();
      object.put("name", user.getName());
      object.put("password", user.getPassword());
      object.put("age", user.getAge());
      return object;
    }
}
