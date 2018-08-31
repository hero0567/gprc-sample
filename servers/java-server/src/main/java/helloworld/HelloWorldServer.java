package helloworld;

import helloworld.services.HelloWorldService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * User: tokgozmusa Date: 25.08.2018 Time: 00:23
 **/
public class HelloWorldServer {

  public static final Logger logger = Logger.getLogger(HelloWorldServer.class.getName());

  private int port = 42420;
  private Server server;

  public static void main(String[] args) throws Exception {
    logger.info("Server startup. Args = " + Arrays.toString(args));
    final HelloWorldServer helloWorldServer = new HelloWorldServer();
    helloWorldServer.start();
    helloWorldServer.blockUntilShutdown();
  }

  private void start() throws Exception {
    logger.info("Starting the grpc server");

    server = ServerBuilder.forPort(port)
        .addService(new HelloWorldService())
        .build()
        .start();

    logger.info("Server started. Listening on port " + port);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.err.println("*** JVM is shutting down. Turning off grpc server as well ***");
      HelloWorldServer.this.stop();
      System.err.println("*** shutdown complete ***");
    }));
  }

  private void stop() {
    if (server != null) {
      server.shutdown();
    }
  }

  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }
}

