package com.fitness.aiservice.service.impl;

import com.fitness.aiservice.service.GeminiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@Slf4j
public class GeminiServiceImpl implements GeminiService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public GeminiServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }


    @Override
    public String getAnswer(String question) {
        Map<String, Object> requestContent = Map.of(
                "contents", new Object[]{
                        Map.of(
                                "parts", new Object[]{
                                        Map.of("text", question)
                                }
                        )
                }
        );
        log.info("Request sending to ai to generate recommendation");
        String response = webClient.post()
                .uri(geminiApiUrl + geminiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestContent)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        erResponse -> erResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("Gemini Error: {}", errorBody);
                                    return Mono.error(
                                            new RuntimeException(errorBody)
                                    );
                                })
                )
                .bodyToMono(String.class)
                .block();
        log.info("Response getting from ai: {}", response);
        return response;
    }
}
