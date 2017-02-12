package com.vincent.sourvide.utils;

import android.content.Context;


import java.util.Timer;
import java.util.TimerTask;

/**
 * description �?
 * project name：SourVide
 * author : Vincent
 * creation date: 2017/2/11 23:43
 *
 * @version 1.0
 */
public class ExitUtil {
    private static boolean isQuit=false;
    /**
     * 在onBackPressed()方法中调�?,使用此函数时不能调用onBackPressed()父类方法，应该去掉，
     @Override
     public void onBackPressed() {
     ExitUtils.isQuit(this,"再按�?次�??出app");
     }
      *true �?�?
     *@author Vincent QQ1032006226
     *created at 2016/9/27 11:03
     */
    public static boolean isQuit(Context context, String exitMsg){
        if (isQuit == false) {
            isQuit = true;
            ToastUtils.defaultToast(exitMsg);
            TimerTask task = null;
            task = new TimerTask() {
                @Override
                public void run() {
                    isQuit = false;
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 2000);
//            MyLog.d("�?出提�?","用户正在�?�?");
            return true;
        } else {
//            MyLog.d("app is exit", "app is exit");
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
            return false;
        }
    }


}
