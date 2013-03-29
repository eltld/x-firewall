package info.ishared.android.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-3-29
 * Time: AM11:10
 */
public class DateUtils {
    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatDate(Date date){
        return simpleDateFormat.format(date);
    }
}
