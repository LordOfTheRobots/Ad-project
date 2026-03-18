package com.ad.entity;

import lombok.Data;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class APIKey {
    private String key;
    private AtomicInteger tokens;

    public APIKey(String key, int initialTokens) {
        this.key = key;
        this.tokens = new AtomicInteger(initialTokens);
    }

    public boolean consumeTokens(int amount) {
        while (true) {
            int current = tokens.get();
            if (current < amount) return false;
            if (tokens.compareAndSet(current, current - amount)) return true;
        }
    }

    public int getTokens() {
        return tokens.get();
    }

    public void resetTokens(int amount) {
        this.tokens.set(amount);
    }
}
