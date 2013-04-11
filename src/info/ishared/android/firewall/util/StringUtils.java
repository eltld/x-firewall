package info.ishared.android.firewall.util;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-4-1
 * Time: PM3:04
 */
public class StringUtils {
    public static boolean isInNumberList(List<String> phoneNumbers,String checkNumber){
        if (phoneNumbers.contains(checkNumber)) return true;
        if(phoneNumbers.contains(checkNumber.replace("+86", ""))) return true;
        if (phoneNumbers.contains("+86"+phoneNumbers)) return true;
        return false;
    }
}
