package com.example.clientrsocket;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientRsocketApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ClientRsocketApplication.class, args);

		RSocket socket =
				RSocketFactory.connect()
						.transport(TcpClientTransport.create("localhost", 7000))
						.start()
						.block();

		assert socket != null;

		socket.requestResponse(DefaultPayload.create("Hello!"))
				.map(Payload::getDataUtf8)
				.doOnNext(System.out::println)
				.block();

		socket.dispose();

	}

}
