package com.example.serverrsocket;

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import org.reactivestreams.Publisher;
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
			public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
				return Flux.from(payloads).flatMap(payload ->
						Flux.fromStream(
								payload.getDataUtf8().codePoints()
										.mapToObj(c -> String.valueOf((char) c))
										.map(i -> DefaultPayload.create("channel: " + i))))
						.doOnNext(System.out::println);
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