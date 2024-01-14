package com.example.cinema_backend.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenService {

    private static TokenService instance;

    private final Map<Long, String> userTokens;

    private TokenService() {
        userTokens = new ConcurrentHashMap<>();
    }

    public static synchronized TokenService getInstance() {
        if (instance == null) {
            instance = new TokenService();
        }
        return instance;
    }

    /**
     * Stores the token for a specific user.
     * @param userId The ID of the user.
     * @param token The token to store.
     */
    public void storeToken(Long userId, String token) {
        userTokens.put(userId, token);
        System.out.println(userTokens);
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

    public Long getUserId(String token) {
        for (Long userId : userTokens.keySet()) {
            if (userTokens.get(userId).equals(token)) {
                return userId;
            }
        }
        return null;
    }

    public boolean isTokenValid(String token) {
        return userTokens.containsValue(token);
    }

//    public boolean isTokenValid(String token) {
//        System.out.println(userTokens);
//        Iterator it = userTokens.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pair = (Map.Entry)it.next();
//            if (pair.getValue().equals(token)) {
//                return true;
//            }
//        }
//        return false;
//    }

    public void removeToken(String token) {
        for (Long userId : userTokens.keySet()) {
            if (userTokens.get(userId).equals(token)) {
                userTokens.remove(userId);
            }
        }
    }
}
