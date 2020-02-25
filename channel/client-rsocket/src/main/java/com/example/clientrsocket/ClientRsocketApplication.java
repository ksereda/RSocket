package com.example.clientrsocket;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import java.time.Duration;

@SpringBootApplication
public class ClientRsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientRsocketApplication.class, args);

		RSocket socket =
				RSocketFactory.connect()
						.transport(TcpClientTransport.create("localhost", 7000))
						.start()
						.block();

		socket.requestChannel(Flux.just("one", "two")
				.map(DefaultPayload::create))
				.delayElements(Duration.ofMillis(1000))
				.map(Payload::getDataUtf8)
				.doOnNext(System.out::println)
				.blockLast();

	}

}