package com.fitness.aiservice.service.impl;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.pojo.Activity;
import com.fitness.aiservice.repositories.RecommendationRepository;
import com.fitness.aiservice.service.ActivityAIService;
import com.fitness.aiservice.service.ActivityMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityMessageListenerImpl implements ActivityMessageListener {

    private final RecommendationRepository  recommendationRepository;
    private final ActivityAIService activityAIService;
    @Override
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void processActivity(Activity activity) {
        log.info("Receive activity data for processing: {}", activity.getId());
        Recommendation recommendation = activityAIService.generateRecommendation(activity);
        log.info("Recommendation going to save in db {}", recommendation);
        Recommendation savedRecommendation = recommendationRepository.save(recommendation);
        log.info("Recommendation saved: {}", savedRecommendation);
    }
}
