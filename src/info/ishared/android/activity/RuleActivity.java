package info.ishared.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import info.ishared.android.MainActivity;
import info.ishared.android.R;
import info.ishared.android.bean.BlockRule;
import info.ishared.android.bean.BlockRuleType;
import info.ishared.android.util.PageJumpUtils;
import info.ishared.android.util.ToastUtils;
import roboguice.inject.InjectView;

/**
 * Created by IntelliJ IDEA.
 * User: Lee
 * Date: 13-3-30
 * Time: 下午11:11
 */
public class RuleActivity extends RoboSherlockActivity implements AdapterView.OnClickListener {

    @InjectView(R.id.block_black_btn)
    protected RadioButton mBlockBlackBtn;

    @InjectView(R.id.allow_white_btn)
    protected RadioButton mAllowWhiteBtn;

    @InjectView(R.id.block_all_btn)
    protected RadioButton mBlockAllBtn;

    private RuleController mController;

    private Handler mHandler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rule_layout);
        mController = new RuleController(this);
        mHandler = new Handler();

        mBlockBlackBtn.setOnClickListener(this);
        mAllowWhiteBtn.setOnClickListener(this);
        mBlockAllBtn.setOnClickListener(this);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                initBlockRuleButtonsStatus();
            }
        });
    }

    public void initBlockRuleButtonsStatus(){
        BlockRuleType blockRule= this.mController.getCurrentBlockRule();
        switch (blockRule){
            case BLOCK_BLACK_LIST:
                mBlockBlackBtn.setChecked(true);
                break;
            case ALLOW_WHITE_LIST:
                mAllowWhiteBtn.setChecked(true);
                break;
            case BLOCK_ALL:
                mBlockAllBtn.setChecked(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.block_black_btn:
                this.mController.createOrUpdateBlockRule(BlockRuleType.BLOCK_BLACK_LIST.name());
                break;
            case R.id.allow_white_btn:
                this.mController.createOrUpdateBlockRule(BlockRuleType.ALLOW_WHITE_LIST.name());
                break;
            case R.id.block_all_btn:
                this.mController.createOrUpdateBlockRule(BlockRuleType.BLOCK_ALL.name());
                break;
        }
        ToastUtils.showMessage(this,"设置成功");
    }

    @Override
    public void onBackPressed() {
        PageJumpUtils.jump(this, MainActivity.class);
        this.finish();
    }
}
