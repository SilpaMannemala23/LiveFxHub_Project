package com.livefxhub.service;

import org.springframework.stereotype.Service;

@Service
public class BroadcastService {

    public void broadcast(String payload) {
        System.out.println(payload);
    }
}