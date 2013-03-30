package info.ishared.android.activity;

import android.app.Activity;
import android.os.Bundle;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import info.ishared.android.R;

/**
 * Created by IntelliJ IDEA.
 * User: Lee
 * Date: 13-3-30
 * Time: 下午11:11
 */
public class RuleActivity extends RoboSherlockActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rule_layout);
    }
}
