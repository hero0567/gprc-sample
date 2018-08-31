package helloworld;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: tokgozmusa Date: 26.08.2018 Time: 02:43
 **/
public class HelloWorldClient {

  private static final Logger logger = Logger.getLogger(HelloWorldClient.class.getName());

  private final ManagedChannel channel;
  private HelloWorldGrpc.HelloWorldBlockingStub blockingStub;

  private static String hostName = "localhost";
  private static int port = 42420;

  public static void main(String[] args) throws Exception {
    HelloWorldClient client = new HelloWorldClient(hostName, port);
    String name = args.length > 0 ? args[0] : "hello levy";

    try {
      client.requestToSayHello(name);
    } finally {
      client.shutdown();
    }
  }

  public HelloWorldClient(String hostname, int port) {
    channel = ManagedChannelBuilder.forAddress(hostname, port)
        .usePlaintext(true)
        .build();
    blockingStub = HelloWorldGrpc.newBlockingStub(channel);
  }

  private void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  private void requestToSayHello(String name) {
    logger.info("Trying to make a request to sayHello " + name);
    try {
      HelloRequest request = HelloRequest.newBuilder().setName(name).build();
      HelloResponse response = blockingStub.sayHello(request);
      logger.info("Response: " + response.getMessage());
    } catch (RuntimeException e) {
      logger.log(Level.WARNING, "Request to grpc server failed", e);
    }
  }
}
