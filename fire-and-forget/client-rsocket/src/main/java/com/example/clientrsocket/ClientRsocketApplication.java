package com.example.clientrsocket;

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

		socket
				.fireAndForget(DefaultPayload.create("Client-service is available now"))
				.subscribe();

		Thread.sleep(3000);
		socket.dispose();

	}

}
