package com.fitness.aiservice.service;

import com.fitness.aiservice.pojo.Activity;
import org.springframework.stereotype.Service;

@Service
public interface ActivityMessageListener {
    void processActivity(Activity activity);
}
