package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.pojo.Activity;
import org.springframework.stereotype.Service;

@Service
public interface ActivityAIService {

    Recommendation generateRecommendation(Activity activity);
}
