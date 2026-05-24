package com.fitness.aiservice.service.impl;

import com.fitness.aiservice.exception.ResourceNotFoundException;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repositories.RecommendationRepository;
import com.fitness.aiservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    @Override
    public List<Recommendation> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    @Override
    public Recommendation getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId).
                orElseThrow(() -> new ResourceNotFoundException("No recommendation found for this activity: " + activityId));
    }
}
