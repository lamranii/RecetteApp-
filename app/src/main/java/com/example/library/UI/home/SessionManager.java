package com.example.library.UI.home;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static SessionManager instance;
    private SharedPreferences sharedPreferences;

    private static final String USER_ID_KEY = "user_id";

    SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE);
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    public void saveUserId(int userId) {
        sharedPreferences.edit().putInt(USER_ID_KEY, userId).apply();
    }

    public int getUserId() {
        return sharedPreferences.getInt(USER_ID_KEY, -1);
    }

    public boolean isLoggedIn() {
        return getUserId() != -1;
    }

    public void logout() {
        sharedPreferences.edit().clear().apply();
    }
}
