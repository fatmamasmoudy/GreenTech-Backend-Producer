package com.projet.fatma.configuration.client;

import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfiguration {
    @Bean
    public WebClient webclient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        1000 * 60 * 5) // Connection Timeout to 5 minutes
                .responseTimeout(Duration.ofSeconds(60 * 5L)); // Timeout response to 5 minutes

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))// Use http client settings
                .codecs(clientCodecConfigurer -> clientCodecConfigurer
                        .defaultCodecs()
                        .maxInMemorySize(1024 * 1024 * 1024)) //Max files to save in memory 200 MB
                .build();
    }
}