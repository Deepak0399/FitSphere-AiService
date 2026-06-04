package com.fitness.aiservice.service;

import org.springframework.stereotype.Service;

@Service
public interface GeminiService {

    String getAnswer(String question);
}
