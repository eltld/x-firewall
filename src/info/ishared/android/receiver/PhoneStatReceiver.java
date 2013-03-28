package info.ishared.android.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-3-28
 * Time: PM2:58
 */
public class PhoneStatReceiver extends BroadcastReceiver {
    private static String incoming_number;

    private AudioManager mAudioManager;
    private ITelephony mITelephony;


    @Override
    public void onReceive(Context context, Intent intent) {
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //利用反射获取隐藏的endcall方法
        TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        try {
            Method getITelephonyMethod = TelephonyManager.class.getDeclaredMethod("getITelephony", (Class[]) null);
            getITelephonyMethod.setAccessible(true);
            mITelephony = (ITelephony) getITelephonyMethod.invoke(mTelephonyManager, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        } else {
            //如果是来电
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            switch (tm.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING://来电响铃
                    incoming_number = intent.getStringExtra("incoming_number");
//                    if(number.equals(BLOCKED_NUMBER)){//拦截指定的电话号码
                    //先静音处理
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    try {
                        mITelephony.endCall();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK://摘机
//                        Log.i(TAG, "incoming ACCEPT :"+ incoming_number);
                    break;

                case TelephonyManager.CALL_STATE_IDLE://挂机
//                        Log.i(TAG, "incoming IDLE");
                    break;
            }
        }
    }
}