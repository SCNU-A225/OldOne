package com.campus.oldone.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.campus.oldone.model.User;

public class PreferencesUtil {
    private PreferencesUtil(){}

    private static final String USER_INFO = "user_info";

    public static void saveUserInfo(Context context, User user){
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id",user.getId())
                .putString("account",user.getAccount())
                .putString("name",user.getName())
                .putString("campus",user.getCampus())
                .putString("phone",user.getPhone())
                .putString("email",user.getEmail())
                .apply();
    }

    public static User getUserInfo(Context context){
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO,context.MODE_PRIVATE);
        User user = new User();
        user.setId(preferences.getInt("id",-1));
        user.setAccount(preferences.getString("account",null));
        user.setCampus(preferences.getString("campus",null));
        user.setPhone(preferences.getString("phone",null));
        user.setEmail(preferences.getString("email",null));
        return user;
    }

    public static void clearUserInfo(Context context){
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO,context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }
}
