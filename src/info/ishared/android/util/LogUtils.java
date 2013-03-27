package info.ishared.android.util;

import android.util.Log;
import info.ishared.android.AppConfig;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-2-22
 * Time: PM3:19
 */
public class LogUtils {
    public static void log(String msg){
        Log.d(AppConfig.TAG,msg);
    }
}
