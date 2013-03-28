package info.ishared.android;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import info.ishared.android.service.FirewallService;
import info.ishared.android.ui.CustomerProgressDialog;
import info.ishared.android.util.SystemUtils;
import info.ishared.android.util.ToastUtils;
import info.ishared.android.util.ViewUtils;
import roboguice.inject.InjectView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends RoboSherlockActivity implements View.OnClickListener {

    @InjectView(R.id.run_btn)
    private Button mRunButton;
    @InjectView(R.id.stop_btn)
    private Button mStopButton;
    @InjectView(R.id.setting_btn)
    private Button mSettingButton;
    @InjectView(R.id.view_log_btn)
    private Button mViewLogBtn;
    @InjectView(R.id.pb_loading)
    private ProgressBar mLoading;
    @InjectView(R.id.service_status_txt)
    private TextView mTextView;


    private CustomerProgressDialog mDialog;

    private FirewallService mService;

    private boolean isServiceWorked=false;

    private Handler mHandler;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = ((FirewallService.MyBinder)service).getService();
        }

        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mHandler=new Handler();
        mRunButton.setOnClickListener(this);
        mStopButton.setOnClickListener(this);
        mSettingButton.setOnClickListener(this);
        mViewLogBtn.setOnClickListener(this);

        isServiceWorked =  SystemUtils.isServiceWorked(this, "info.ishared.android.service.FirewallService");
        refreshButtonAndTextView(isServiceWorked);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.run_btn:
                startServiceAndRefreshUI();
                break;
            case R.id.stop_btn:
                stopServiceAndRefreshUI();
                break;
            case R.id.setting_btn:
                boolean isWork= SystemUtils.isServiceWorked(this, "info.ishared.android.service.FirewallService");
                ToastUtils.showMessage(this,isWork+"");
                break;
            case R.id.view_log_btn:
                break;
            default:
                break;
        }
    }

    private void startServiceAndRefreshUI(){
        Intent intent=new Intent(this,FirewallService.class);
        this.startService(intent);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                while (!SystemUtils.isServiceWorked(MainActivity.this, "info.ishared.android.service.FirewallService")){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                refreshButtonAndTextView(true);
            }
        });
    }
    private void stopServiceAndRefreshUI(){
        Intent intent=new Intent(this,FirewallService.class);
        this.stopService(intent);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                while (SystemUtils.isServiceWorked(MainActivity.this, "info.ishared.android.service.FirewallService")){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                refreshButtonAndTextView(false);
            }
        });
    }

    private void refreshButtonAndTextView(boolean isServiceWorked){
        if(isServiceWorked){
            mTextView.setText("来电防火墙正在运行中....");
            ViewUtils.hideView(mRunButton);
            ViewUtils.showView(mStopButton);
            ViewUtils.showView(mLoading);
        }else{
            mTextView.setText("来电防火墙已停止拦截....");
            ViewUtils.hideView(mStopButton);
            ViewUtils.showView(mRunButton);
            ViewUtils.hideView(mLoading);
        }
    }
}
