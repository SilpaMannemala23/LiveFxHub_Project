package com.livefxhub.persistence;

import org.springframework.stereotype.Repository;

@Repository
public class RedisStateRepository {

    public void saveState() {
        System.out.println("Saving market state to Redis");
    }

    public void restoreState() {
        System.out.println("Restoring market state from Redis");
    }
}