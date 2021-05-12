package com.example.hospitalhelper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;

public class Utility {

    public static boolean restorePrefData(Context context) {
        SharedPreferences pref = context.getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;
    }

    public static void setIsLogin(Context activity,String value){
        SharedPreferences pref = activity.getSharedPreferences("IsLogin",0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("IsLogin",value);
        editor.apply();
    }

    public static String getIsLogin(Context activity){
        SharedPreferences pref = activity.getSharedPreferences("IsLogin",0);
        return pref.getString("IsLogin","");
    }


    public static void setEnablebnt(final Activity activity, View view) {
        view.setEnabled(false);
        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                });
            }
        },1000);
    }



}
