package info.ishared.android.activity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import info.ishared.android.bean.ContactsInfo;
import info.ishared.android.bean.NumberType;
import info.ishared.android.util.ToastUtils;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lee
 * Date: 13-3-29
 * Time: 下午7:53
 */
public class BlackContactsListActivity  extends ContactsListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 0, 0, "删除联系人");

            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String phoneNumber = contactsData.get(menuInfo.position).get("number");
        mController.deleteContactInfoByPhoneNumber(phoneNumber);
        contactsData.remove(menuInfo.position);
        adapter.notifyDataSetChanged();
        return false;
    }

    @Override
    protected List<ContactsInfo> initData() {
        return mController.queryContactInfoByNumberType(NumberType.BLACK);
    }
}
