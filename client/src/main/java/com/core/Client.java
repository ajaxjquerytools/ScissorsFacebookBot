package com.core;

import com.core.codec.DefaultCodec;
import com.core.verticles.RestVerticle;
import io.vertx.rxjava.core.Vertx;

import java.util.ArrayList;

public class Client {

	public static void main(String[] args) {
		Vertx rxVertx = Vertx.vertx();


		io.vertx.core.Vertx vertx = (io.vertx.core.Vertx) rxVertx.getDelegate();

		vertx.eventBus().registerDefaultCodec(ArrayList.class, new DefaultCodec<>(ArrayList.class.getName()));
		vertx.eventBus().registerDefaultCodec(String.class, new DefaultCodec<>(String.class.getName()));
		vertx.eventBus().registerDefaultCodec(Long.class, new DefaultCodec<>(Long.class.getName()));

		vertx.deployVerticle(new RestVerticle());

	}
}
