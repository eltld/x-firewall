package info.ishared.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import info.ishared.android.bean.ContactsInfo;
import info.ishared.android.bean.NumberType;
import info.ishared.android.util.ContactsUtils;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lee
 * Date: 13-3-29
 * Time: 下午9:41
 */
public class WhiteContactListActivity extends ContactsListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List<ContactsInfo> initData(){
        return mController.queryContractInfoByNumberType(NumberType.WHITE);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
