package info.ishared.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import info.ishared.android.ui.CustomerProgressDialog;
import info.ishared.android.util.AlertDialogUtils;
import info.ishared.android.util.ToastUtils;
import info.ishared.android.util.ViewUtils;
import roboguice.inject.InjectView;

public class MainActivity extends RoboSherlockActivity {
    @InjectView(R.id.run_btn) private Button mRunButton;
    @InjectView(R.id.stop_btn) private Button mStopButton;
    @InjectView(R.id.setting_btn) private Button mSettingButton;
    @InjectView(R.id.pb_loading) private ProgressBar mLoading;
    private CustomerProgressDialog mDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mRunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog = AlertDialogUtils.createProgressDialog(MainActivity.this);
                mDialog.setMessage("正在处理，请等待......");
                mDialog.show();
            }

        });
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewUtils.setGone(mLoading, true);
                mDialog.hide();
            }
        });
        mSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewUtils.setGone(mLoading, false);
            }
        });
    }

    private void showMessage(String msg){
        ToastUtils.showMessage(this,msg);
    }
}
