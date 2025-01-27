package com.example.demo;

import com.example.demo.proto.HelloRequest;
import com.example.demo.proto.SimpleGrpc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("it")
class IntegrationTests {

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Lazy
        public SimpleGrpc.SimpleBlockingStub blockingStub(GrpcChannelFactory channelFactory) {
            return SimpleGrpc.newBlockingStub(channelFactory.createChannel("local"));
        }
    }

    @Autowired
    SimpleGrpc.SimpleBlockingStub blockingStub;

    @Test
    void testSayHello() {
        var helloResponse = blockingStub
                .sayHello(
                        HelloRequest.newBuilder()
                                .setName("World")
                                .build()
                );
        assertThat(helloResponse.getMessage()).isEqualTo("Hello ==> World");
    }

}
