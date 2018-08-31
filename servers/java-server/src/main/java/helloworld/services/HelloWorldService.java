package helloworld.services;

import helloworld.HelloRequest;
import helloworld.HelloResponse;
import helloworld.HelloWorldGrpc;
import helloworld.HelloWorldServer;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;

/**
 * User: tokgozmusa Date: 26.08.2018 Time: 01:42
 **/
public class HelloWorldService extends HelloWorldGrpc.HelloWorldImplBase {

  @Override
  public void sayHello(HelloRequest helloRequest, StreamObserver<HelloResponse> responseObserver) {
    HelloWorldServer.logger.info("Request received:" + LocalDateTime.now());

    HelloResponse helloResponse = HelloResponse.newBuilder().setMessage(sayHelloImpl(helloRequest.getName())).build();
    responseObserver.onNext(helloResponse);
    responseObserver.onCompleted();
  }

  private String sayHelloImpl(String name) {
    return "Hello " + name;
  }
}
