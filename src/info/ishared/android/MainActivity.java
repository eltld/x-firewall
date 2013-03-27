package info.ishared.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import info.ishared.android.util.ToastUtils;
import info.ishared.android.util.ViewUtils;
import roboguice.inject.InjectView;

public class MainActivity extends RoboSherlockActivity {
    @InjectView(R.id.run_btn) private Button mRunButton;
    @InjectView(R.id.stop_btn) private Button mStopButton;
    @InjectView(R.id.setting_btn) private Button mSettingButton;
    @InjectView(R.id.pb_loading) private ProgressBar mLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mRunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage("run click");
            }
        });
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage("stop click");
                ViewUtils.setGone(mLoading, true);
            }
        });
        mSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage("setting click");
                ViewUtils.setGone(mLoading, false);
            }
        });
    }

    private void showMessage(String msg){
        ToastUtils.showMessage(this,msg);
    }
}
