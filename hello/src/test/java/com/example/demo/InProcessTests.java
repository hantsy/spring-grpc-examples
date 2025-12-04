package com.example.demo;

import com.example.demo.proto.HelloRequest;
import com.example.demo.proto.SimpleGrpc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.grpc.test.autoconfigure.AutoConfigureInProcessTransport;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.client.GrpcChannelFactory;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureInProcessTransport
class InProcessTests {

    @TestConfiguration
    static class TestConfig {

        @Bean
        public SimpleGrpc.SimpleBlockingStub blockingStub(GrpcChannelFactory channelFactory) {
            return SimpleGrpc.newBlockingStub(channelFactory.createChannel("inprocess"));
        }
    }

    @Autowired
    SimpleGrpc.SimpleBlockingStub blockingStub;

    @Test
    void testSayHello() {
        var helloResponse = blockingStub.sayHello(HelloRequest.newBuilder()
                .setName("World")
                .build());
        assertThat(helloResponse.getMessage()).isEqualTo("Hello ==> World");
    }

}
