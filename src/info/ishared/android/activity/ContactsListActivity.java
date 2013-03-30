package info.ishared.android.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import info.ishared.android.R;
import info.ishared.android.bean.BlockLog;
import info.ishared.android.bean.ContactsInfo;
import info.ishared.android.bean.NumberType;
import info.ishared.android.util.AlertDialogUtils;
import info.ishared.android.util.ContactsUtils;
import info.ishared.android.util.ToastUtils;
import info.ishared.android.util.ViewUtils;
import roboguice.inject.InjectView;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-3-29
 * Time: PM3:55
 */
public class ContactsListActivity extends RoboSherlockActivity {

    @InjectView(R.id.contacts_list)
    protected ListView mListView;
    @InjectView(R.id.contacts_loading)
    private ProgressBar mLoading;

    protected SimpleAdapter adapter;
    protected List<Map<String, String>> contactsData = new ArrayList<Map<String, String>>();

    protected ContactsListController mController;

    private Handler mHandler;

    private static final int ADD_TO_WHITE = 0;
    private static final int ADD_TO_BLACK = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_list_view);
        mController = new ContactsListController(this);
        mHandler = new Handler();
        ViewUtils.showView(mLoading);
        initListViewData();
        initListViewGUI();
    }

    private void initListViewGUI() {
        adapter = new SimpleAdapter(this, contactsData, R.layout.contacts_list_item, new String[]{"name", "number"}, new int[]{R.id.contacts_name, R.id.contacts_number}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if (position % 2 != 0)
                    view.setBackgroundResource(R.drawable.table_background_selector);
                else
                    view.setBackgroundResource(R.drawable.table_background_alternate_selector);
                return view;
            }
        };
        mListView.setAdapter(adapter);
        mListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, ADD_TO_WHITE, 0, "添加到白名单");
                menu.add(0, ADD_TO_BLACK, 0, "添加到黑名单");

//                MenuInflater mInflater = getMenuInflater();
//                mInflater.inflate(R.menu.contacts_context_menu, menu);
//                super.onCreateContextMenu(menu, v, menuInfo);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String phoneNumber = contactsData.get(menuInfo.position).get("number");
        String contactName = contactsData.get(menuInfo.position).get("name");
        switch (item.getItemId()) {
            case ADD_TO_WHITE:
                mController.sendContactToBlockList(phoneNumber, contactName, NumberType.WHITE);
                break;
            case ADD_TO_BLACK:
                mController.sendContactToBlockList(phoneNumber, contactName, NumberType.BLACK);
                break;
            default:
                break;
        }

        return false;
    }

    private void initListViewData() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                contactsData.clear();
                List<ContactsInfo> contactsInfoList =initData();
                Collections.sort(contactsInfoList, new Comparator<ContactsInfo>() {
                    @Override
                    public int compare(ContactsInfo lhs, ContactsInfo rhs) {
                        return lhs.getContactName().compareTo(rhs.getContactName());
                    }
                });
                for (ContactsInfo contactsInfo : contactsInfoList) {
                    Map<String, String> map = new HashMap<String, String>(2);
                    map.put("name", contactsInfo.getContactName());
                    map.put("number", contactsInfo.getPhoneNumber());
                    contactsData.add(map);
                }
                adapter.notifyDataSetChanged();
                ViewUtils.hideView(mLoading);
            }
        });

    }

    protected List<ContactsInfo> initData(){
       return ContactsUtils.getPhoneContacts(ContactsListActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initListViewData();
    }
}
