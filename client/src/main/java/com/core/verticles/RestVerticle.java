package com.core.verticles;

import com.core.services.GameService;
import com.google.common.io.Resources;
import com.restfb.types.webhook.messaging.MessagingItem;
import com.restfb.DefaultJsonMapper;
import com.restfb.types.webhook.WebhookObject;

import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.JksOptions;
import io.vertx.rxjava.core.MultiMap;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.http.HttpServerResponse;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.StaticHandler;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by volodymyr on 02.08.17.
 */
public class RestVerticle extends AbstractVerticle {

    private static final int PORT = 7171;
    private final static Logger logger = LoggerFactory.getLogger(RestVerticle.class);

    private final static DefaultJsonMapper jsonMapper = new DefaultJsonMapper();

    private GameService gameService = GameService.getInstance();

    @Override
    public void start(Future<Void> fut) throws Exception {
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        router.post("/get/scissors/webhook").handler(this::webhookPost);

        router.get("/get/scissors/webhook").handler(this::webhookGet);

        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/html").end("<h1>Rock, Paper, Scissors</h1>");
        });

        router.route("/public/*").handler(StaticHandler.create("public"));

        vertx.createHttpServer(getHttpServerOptions()).requestHandler(router::accept).listen(
                result -> {
                    if (result.succeeded()) {
                        logger.info("Facebook GameBot started at port:[{}]", PORT);
                        fut.complete();
                    } else {
                        fut.fail(result.cause());
                    }
                });
    }


    /**
     * This method used for subscribing
     *
     * @param routingContext
     */
    private void webhookGet(RoutingContext routingContext) {
        final String bodyJson = routingContext.getBodyAsString();
        logger.info("GET BODY Received [{}]", bodyJson);

        MultiMap params = routingContext.request().params();
        for (String param : params.names()) {
            logger.info("Param key={}; value={}", param, params.get(param));
        }

        routingContext.response().setStatusCode(200).end(params.get("hub.challenge"));
    }

    /**
     * This is for handling events
     *
     * @param routingContext
     */
    private void webhookPost(RoutingContext routingContext) {
        final String bodyJson = routingContext.getBodyAsString();

        logger.info("Recieve FB POST Event : {}", bodyJson);

        routingContext.response().setStatusCode(200).end("{\n" +
                "  \"success\": true\n" +
                "}");
        //async process response here!


        WebhookObject webhookObject = jsonMapper.toJavaObject(bodyJson, WebhookObject.class);
        List<MessagingItem> messagesFromUser = lookupMessageEvent(webhookObject);
        gameService.playFullGame(messagesFromUser);

    }

    private List<MessagingItem> lookupMessageEvent(final WebhookObject webhookObject) {

        return webhookObject.getEntryList().stream()
                .flatMap(e -> e.getMessaging().stream())
                .filter(e -> e.getMessage() != null)
                .collect(Collectors.toList());

    }

    private static HttpServerOptions getHttpServerOptions() throws IOException {
        // Create the HTTP server and pass the "accept" method to the request handler.

        byte[] jksBytes = IOUtils.toByteArray(Resources.getResource("keys.jks").openStream());

        Buffer store = Buffer.buffer(jksBytes);

        JksOptions jksOptions = new JksOptions();
        jksOptions.setPassword("password");
        jksOptions.setValue(store);

        HttpServerOptions options = new HttpServerOptions();
        options.setSsl(true);
        options.setKeyStoreOptions(jksOptions);
        options.setPort(PORT);

        return options;
    }


}