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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ServerRsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerRsocketApplication.class, args);

		RSocket rSocketImpl = new AbstractRSocket() {
			@Override
			public Flux<Payload> requestStream(Payload payload) {
				System.out.println(payload.getDataUtf8());
				return Flux.range(1, 5)
						.map(i -> DefaultPayload.create("onNext-" + i));
			}
		};

		Disposable server = RSocketFactory.receive()
				.acceptor((setupPayload, reactiveSocket) -> Mono.just(rSocketImpl))
				.transport(TcpServerTransport.create("localhost", 7001))
				.start()
				.subscribe();

		server.dispose();

	}

}