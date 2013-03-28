package info.ishared.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import info.ishared.android.util.LogUtils;

import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-3-28
 * Time: PM1:24
 */
public class FirewallService extends Service {

    private MyBinder binder = new MyBinder();
    private Executor executor= Executors.newFixedThreadPool(2);

    private boolean threadFlag = true ;
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.log("onBind....");
        return binder;
    }

    @Override
    public void onCreate() {
        LogUtils.log("onCreate....");
        super.onCreate();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (threadFlag){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LogUtils.log(Calendar.getInstance().getTime()+"");
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        LogUtils.log("onDestroy....");
       threadFlag=false;
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.log("onUnbind....");
        return super.onUnbind(intent);
    }



    public class MyBinder extends Binder {
        public FirewallService getService() {
            return FirewallService.this;
        }
    }
}
