package com.liskovsoft.youtubeapi.app;

import java.util.List;

public class AppService {
    private static AppService sInstance;

    private AppService() {
        
    }

    public static AppService instance() {
        if (sInstance == null) {
            sInstance = new AppService();
        }

        return sInstance;
    }

    /**
     * Decipher strings using js code
     */
    public List<String> decipher(List<String> ciphered) {
        return ciphered;
    }
}