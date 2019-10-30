package playgrounds;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {
    vertx.createHttpServer()
      .requestHandler(this::handleRequest)
      .listen(8080, ar -> {
        if (ar.succeeded()) {
          System.out.println("HTTP server running");
          startPromise.complete();
        } else {
          System.out.println("Woops");
          startPromise.fail(ar.cause());
        }
      });
  }

  private void handleRequest(HttpServerRequest request) {
    System.out.println(request.path() + " from " + request.remoteAddress().host());
    request.response()
      .putHeader("Content-Type", "plain/text")
      .end("Hello!");
  }

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new MainVerticle());
  }
}
