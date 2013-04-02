package info.ishared.android.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import info.ishared.android.MainActivity;
import info.ishared.android.R;
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

    @InjectView(R.id.ring_user_busy_btn)
    protected RadioButton mUserBusyBtn;
    @InjectView(R.id.ring_fee_arrears_btn)
    protected RadioButton mFeeArrearsBtn;
    @InjectView(R.id.ring_null_number_btn)
    protected RadioButton mNullNumberBtn;
    @InjectView(R.id.ring_shut_down_btn)
    protected RadioButton mShutDownBtn;

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

        mUserBusyBtn.setOnClickListener(this);
        mFeeArrearsBtn.setOnClickListener(this);
        mNullNumberBtn.setOnClickListener(this);
        mShutDownBtn.setOnClickListener(this);

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
            case R.id.ring_user_busy_btn:
                callPhone("%23%2367%23");
                break;
            case R.id.ring_fee_arrears_btn:
                break;
            case R.id.ring_null_number_btn:
                callPhone("**67*13800000000%23");
                break;
            case R.id.ring_shut_down_btn:
                break;
        }
        ToastUtils.showMessage(this,"设置成功");
    }

    @Override
    public void onBackPressed() {
        PageJumpUtils.jump(this, MainActivity.class);
        this.finish();
    }


    private void callTransferNumberByUser(String mobilePhoneOperators,String type){

    }

    private void callPhone(String phoneNumber){
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.intent.action.CALL");
//        Uri uri = Uri.parse("tel:" + "**67*13800000000%23");
        Uri uri = Uri.parse("tel:" + phoneNumber);
//        Uri uri = Uri.parse("tel:" + "**67*13810538911%23");
//        Uri uri = Uri.parse("tel:" + "**67*13701110216%23");
        localIntent.setData(uri);
        this.startActivity(localIntent);
    }
}
