package com.example.service1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.*;

public class MySqlVerticle extends AbstractVerticle {
  @Override
  public void start() {

    vertx.eventBus().consumer("incoming.user.address", message -> {
      User user = (User) message.body();
      System.out.println(user.toString());
      MySQLConnectOptions options = new MySQLConnectOptions()
        .setHost("localhost")
        .setPort(3306)
        .setDatabase("db2")
        .setUser("root")
        .setPassword("24102003");
      PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
      MySQLPool pool = MySQLPool.pool(vertx, options, poolOptions);
      pool.getConnection().compose(conn -> {
        System.out.println("Got a connection pool");
        return conn
          .preparedQuery("INSERT INTO users (name, password, age) VALUES (?, ?, ?)")
          .execute(Tuple.of(user.getName(), user.getPassword(), user.getAge()))
          .onComplete(ar -> {
            RowSet<Row> rows = ar.result();
            System.out.println(rows.rowCount());
            conn.close();
          });
      }).onComplete(ar -> {
        System.out.println("Done");
      });
    });
  }
}
