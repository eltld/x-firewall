package info.ishared.android.activity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import info.ishared.android.bean.ContactsInfo;
import info.ishared.android.bean.NumberType;

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
        mListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 0, 0, "删除联系人");

            }
        });
    }

    @Override
    protected List<ContactsInfo> initData(){
        return mController.queryContactInfoByNumberType(NumberType.WHITE);
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
}
