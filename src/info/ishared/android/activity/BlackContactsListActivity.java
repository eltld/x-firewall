package info.ishared.android.activity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import info.ishared.android.R;
import info.ishared.android.bean.ContactsInfo;
import info.ishared.android.bean.NumberType;
import info.ishared.android.util.AlertDialogUtils;
import info.ishared.android.util.ViewUtils;
import roboguice.inject.InjectView;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lee
 * Date: 13-3-29
 * Time: 下午7:53
 */
public class BlackContactsListActivity  extends ContactsListActivity {
    @InjectView(R.id.contacts_list_add_btn)
    private Button mAddBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.showView(mAddBtn);
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogUtils.showInputDialog(BlackContactsListActivity.this,"",new AlertDialogUtils.ReturnExecutor() {
                    @Override
                    public void execute(Object... objects) {
//                        ToastUtils.showMessage(BlackContactsListActivity.this,objects[0]+","+objects[1]);
                        mController.sendContactToBlockList(objects[1].toString(),objects[0].toString(),NumberType.BLACK);
                        refreshViewListData();
                    }
                });
            }
        });
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
