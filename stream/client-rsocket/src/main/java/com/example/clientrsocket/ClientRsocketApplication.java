package com.example.clientrsocket;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.Duration;

@SpringBootApplication
public class ClientRsocketApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ClientRsocketApplication.class, args);

		RSocket socket =
				RSocketFactory.connect()
						.transport(TcpClientTransport.create("localhost", 7001))
						.start()
						.block();

		socket.requestStream(DefaultPayload.create("request-stream example!"))
				.delayElements(Duration.ofMillis(1000))
				.subscribe(
						payload -> System.out.println(payload.getDataUtf8()),
						e -> System.out.println("error" + e.toString()),
						() -> System.out.println("completed")
				);

		Thread.sleep(3000);
		socket.dispose();

	}

}



