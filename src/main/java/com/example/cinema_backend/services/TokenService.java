package com.example.cinema_backend.services;

import java.util.concurrent.ConcurrentHashMap;

public class TokenService {

    private ConcurrentHashMap<Long, String> userTokens = new ConcurrentHashMap<>();

    /**
     * Stores the token for a specific user.
     * @param userId The ID of the user.
     * @param token The token to store.
     */
    public void storeToken(Long userId, String token) {
        userTokens.put(userId, token);
    }

    /**
     * Retrieves the token for a specific user.
     * @param userId The ID of the user.
     * @return The token, or null if no token is stored for the user.
     */
    public String getToken(Long userId) {
        return userTokens.get(userId);
    }

    /**
     * Removes the token for a specific user.
     * @param userId The ID of the user.
     */
    public void removeToken(Long userId) {
        userTokens.remove(userId);
    }

    /**
     * Checks if a token is stored for a specific user.
     * @param userId The ID of the user.
     * @return True if a token is stored for the user, false otherwise.
     */
    public boolean hasToken(Long userId) {
        return userTokens.containsKey(userId);
    }
}
