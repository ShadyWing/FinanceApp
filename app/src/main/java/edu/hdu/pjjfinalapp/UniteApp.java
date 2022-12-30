package edu.hdu.pjjfinalapp;

import android.app.Application;

import edu.hdu.pjjfinalapp.db.DBManager;

// 首次启动Activity
public class UniteApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DBManager.initDB(getApplicationContext());
    }
}
