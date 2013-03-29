package info.ishared.android.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import info.ishared.android.R;
import info.ishared.android.bean.ContactsInfo;
import info.ishared.android.bean.NumberType;
import info.ishared.android.util.AlertDialogUtils;
import info.ishared.android.util.ContactsUtils;
import info.ishared.android.util.ToastUtils;
import roboguice.inject.InjectView;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lee
 * Date: 13-3-29
 * Time: 下午7:53
 */
public class BlackContactsListActivity  extends RoboSherlockActivity {
    @InjectView(R.id.contacts_list)
    protected ListView mListView;
    protected SimpleAdapter adapter;
    protected List<Map<String,String>> contactsData=new ArrayList<Map<String,String>>();

    protected ProgressDialog mProgressDialog=null;
    protected Handler mHandler;

    private ContactsListController mController;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_list_view);
        mController=new ContactsListController(this);
        mHandler = new Handler();
        mProgressDialog= AlertDialogUtils.createProgressDialog(this);
        mProgressDialog.setMessage("正在加载黑名单....");
        mProgressDialog.show();
        initListViewData();
        initListViewGUI();
    }

    private void initListViewGUI() {
        adapter = new SimpleAdapter(this,contactsData, R.layout.contacts_list_item, new String[]{"name","number"},new int[]{R.id.contacts_name,R.id.contacts_number}){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view=super.getView(position, convertView, parent);
                if (position % 2 != 0)
                    view.setBackgroundResource(R.drawable.table_background_selector);
                else
                    view.setBackgroundResource(R.drawable.table_background_alternate_selector);
                return view;
            }
        };
        mListView.setAdapter(adapter);
    }



    private void initListViewData() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                contactsData.clear();
                List<ContactsInfo> contactsInfoList= mController.queryContractInfoByNumberType(NumberType.BLACK);
                Collections.sort(contactsInfoList, new Comparator<ContactsInfo>() {
                    @Override
                    public int compare(ContactsInfo lhs, ContactsInfo rhs) {
                        return lhs.getContactName().compareTo(rhs.getContactName());
                    }
                });
                for(ContactsInfo contactsInfo : contactsInfoList){
                    Map<String,String> map=new HashMap<String, String>(2);
                    map.put("name",contactsInfo.getContactName());
                    map.put("number",contactsInfo.getPhoneNumber());
                    contactsData.add(map);
                }
                adapter.notifyDataSetChanged();
                mProgressDialog.hide();
            }
        });

    }
}
