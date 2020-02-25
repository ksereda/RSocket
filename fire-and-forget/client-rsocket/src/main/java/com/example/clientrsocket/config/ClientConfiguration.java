//package com.example.clientrsocket.config;
//
//import io.rsocket.RSocket;
//import io.rsocket.RSocketFactory;
//import io.rsocket.frame.decoder.PayloadDecoder;
//import io.rsocket.transport.netty.client.TcpClientTransport;
//import lombok.SneakyThrows;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.rsocket.RSocketRequester;
//import org.springframework.messaging.rsocket.RSocketStrategies;
//import org.springframework.util.MimeTypeUtils;
//import java.net.InetSocketAddress;
//
//@Configuration
//public class ClientConfiguration {
//
//// Config 1
//
////    @Bean
////    public RSocket rSocket() {
////        return RSocketFactory
////                .connect()
////                .mimeType(MimeTypeUtils.APPLICATION_JSON_VALUE, MimeTypeUtils.APPLICATION_JSON_VALUE)
////                .frameDecoder(PayloadDecoder.ZERO_COPY)
////                .transport(TcpClientTransport.create(7000))
////                .start()
////                .block();
////    }
////
////    @Bean
////    RSocketRequester rSocketRequester(RSocketStrategies rSocketStrategies) {
////        return RSocketRequester.wrap(rSocket(), MimeTypeUtils.APPLICATION_JSON, MimeTypeUtils.APPLICATION_JSON, rSocketStrategies);
////    }
//
//// Config 2
//
//    @Bean
//    @SneakyThrows
//    RSocket rSocket() {
//        return RSocketFactory.connect()
//                .dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
//                .frameDecoder(PayloadDecoder.ZERO_COPY)
//                .transport(TcpClientTransport.create(new InetSocketAddress("127.0.0.1", 7000)))
//                .start()
//                .block();
//    }
//
//    @Bean
//    RSocketRequester requester(RSocketStrategies strategies) {
//        return RSocketRequester.wrap(
//                rSocket(),
//                MimeTypeUtils.APPLICATION_JSON,
//                MimeTypeUtils.APPLICATION_JSON,
//                strategies
//        );
//    }
//}