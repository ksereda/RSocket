package com.example.serverrsocket;

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ServerRsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerRsocketApplication.class, args);

		RSocket rSocketImpl = new AbstractRSocket() {
			@Override
			public Mono<Payload> requestResponse(Payload payload) {
				System.out.println(payload.getDataUtf8());
				return Mono.just(DefaultPayload.create("Nice to meet you"));
			}
		};

		Disposable server = RSocketFactory.receive()
				.acceptor((setupPayload, reactiveSocket) -> Mono.just(rSocketImpl))
				.transport(TcpServerTransport.create("localhost", 7000))
				.start()
				.subscribe();

		server.dispose();

	}

}