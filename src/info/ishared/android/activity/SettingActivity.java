package info.ishared.android.activity;

/**
 * Created by IntelliJ IDEA.
 * User: Lee
 * Date: 13-3-28
 * Time: 下午9:03
 */

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;
import info.ishared.android.MainActivity;
import info.ishared.android.R;
import info.ishared.android.util.PageJumpUtils;

public class SettingActivity  extends TabActivity {
    /** Called when the activity is first created. */
    private TabHost tabHost;
    private TextView main_tab_new_message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting_firewall);

        main_tab_new_message=(TextView) findViewById(R.id.main_tab_new_message);
//        main_tab_new_message.setVisibility(View.VISIBLE);
        main_tab_new_message.setText("10");

        tabHost=this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent=new Intent().setClass(this, ContactsListActivity.class);
        spec=tabHost.newTabSpec("通讯录").setIndicator("通讯录").setContent(intent);
        tabHost.addTab(spec);

        intent=new Intent().setClass(this,BlackContactsListActivity.class);
        spec=tabHost.newTabSpec("黑名单").setIndicator("黑名单").setContent(intent);
        tabHost.addTab(spec);

        intent=new Intent().setClass(this, WhiteContactListActivity.class);
        spec=tabHost.newTabSpec("白名单").setIndicator("白名单").setContent(intent);
        tabHost.addTab(spec);


        intent=new Intent().setClass(this, RuleActivity.class);
        spec=tabHost.newTabSpec("设置").setIndicator("设置").setContent(intent);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);

        RadioGroup radioGroup=(RadioGroup) this.findViewById(R.id.main_tab_group);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.main_tab_addExam://添加考试
                        tabHost.setCurrentTabByTag("通讯录");
                        break;
                    case R.id.main_tab_myExam://我的考试
                        tabHost.setCurrentTabByTag("黑名单");
                        break;
                    case R.id.main_tab_message://我的通知
                        tabHost.setCurrentTabByTag("白名单");
                        break;
                    case R.id.main_tab_settings://设置
                        tabHost.setCurrentTabByTag("设置");
                        break;
                    default:
                        //tabHost.setCurrentTabByTag("我的考试");
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        PageJumpUtils.jump(this,MainActivity.class);
        this.finish();
    }
}
