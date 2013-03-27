package info.ishared.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import info.ishared.android.util.ToastUtils;
import roboguice.inject.InjectView;

public class MainActivity extends RoboSherlockActivity {
    @InjectView(R.id.run_btn) private Button mRunButton;
    @InjectView(R.id.stop_btn) private Button mStopButton;
    @InjectView(R.id.setting_btn) private Button mSettingButton;

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
            }
        });
        mSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage("setting click");
            }
        });
    }

    private void showMessage(String msg){
        ToastUtils.showMessage(this,msg);
    }
}
