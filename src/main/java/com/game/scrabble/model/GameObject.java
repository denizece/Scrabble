package com.game.scrabble.model;

import java.time.Instant;
import java.util.UUID;

public abstract class GameObject {
	protected String generateUniqueId() {
        // Generate a unique ID using timestamp and UUID
        String uniqueId = Instant.now().toEpochMilli() + "-" + UUID.randomUUID().toString();
        return uniqueId;
    }
}
