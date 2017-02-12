package com.vincent.sourvide.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


/**
 * description 锛�
 * project name锛歋ourVide
 * author : Vincent
 * creation date: 2017/2/11 18:29
 *
 * @version 1.0
 */
public class MyApplication extends Application {
	
	private static MyApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        
    }
    
    public static MyApplication getContext(){
    	return app;
    }
 
}
