package info.ishared.android.util;

/**
 * Created by IntelliJ IDEA.
 * User: Lee
 * Date: 11-12-8
 * Time: 下午1:25
 */
public class SystemUtils {
    public static void killSelf(){
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
