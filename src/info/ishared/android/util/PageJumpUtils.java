package info.ishared.android.util;

import android.content.Context;
import android.content.Intent;

/**
 * Created by IntelliJ IDEA.
 * User: Lee
 * Date: 11-12-5
 * Time: 下午4:20
 */
public class PageJumpUtils {
    public static void jump(Context currentPage, Class targetPageClass) {
        Intent intent = new Intent(currentPage, targetPageClass);
        currentPage.startActivity(intent);
    }
}
