package info.ishared.android.firewall.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import info.ishared.android.firewall.MainActivity;
import info.ishared.android.firewall.R;
import info.ishared.android.firewall.bean.ContactsInfo;
import info.ishared.android.firewall.bean.NumberType;
import info.ishared.android.firewall.util.ContactsUtils;
import info.ishared.android.firewall.util.PageJumpUtils;
import info.ishared.android.firewall.util.ViewUtils;
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

    @InjectView(R.id.contacts_list_back_btn)
    private Button mBackBtn;

    @InjectView(R.id.contacts_list_add_btn)
    private Button mAddBtn;


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
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageJumpUtils.jump(ContactsListActivity.this,MainActivity.class);
                ContactsListActivity.this.finish();
            }
        });
        ViewUtils.hideView(mAddBtn);
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


    protected void refreshViewListData(){
        this.initListViewData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initListViewData();
    }

    @Override
    public void onBackPressed() {
        PageJumpUtils.jump(this,MainActivity.class);
        this.finish();
    }
}
