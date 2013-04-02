package info.ishared.android.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import info.ishared.android.MainActivity;
import info.ishared.android.R;
import info.ishared.android.bean.BlockRuleType;
import info.ishared.android.bean.OperatorsType;
import info.ishared.android.bean.TransferNumber;
import info.ishared.android.bean.VoicePromptsType;
import info.ishared.android.util.PageJumpUtils;
import info.ishared.android.util.ToastUtils;
import roboguice.inject.InjectView;

import java.util.ArrayList;
import java.util.List;

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

    @InjectView(R.id.china_mobile_btn)
    protected RadioButton mChinaMobileBtn;
    @InjectView(R.id.china_unicom_btn)
    protected RadioButton mChinaUnicomBtn;
    @InjectView(R.id.china_telecom_btn)
    protected RadioButton mChinaTelecomBtn;
    @InjectView(R.id.other_operators_btn)
    protected RadioButton mOtherOperators;

    @InjectView(R.id.ring_user_busy_btn)
    protected RadioButton mUserBusyBtn;
    @InjectView(R.id.ring_fee_arrears_btn)
    protected RadioButton mFeeArrearsBtn;
    @InjectView(R.id.ring_null_number_btn)
    protected RadioButton mNullNumberBtn;
    @InjectView(R.id.ring_shut_down_btn)
    protected RadioButton mShutDownBtn;

    @InjectView(R.id.input_number)
    protected EditText mInputNumber;

    @InjectView(R.id.rule_setting_btn)
    protected Button mSettingBtn;

    private RuleController mController;

    private Handler mHandler;

    private String operators = OperatorsType.CHINA_MOBILE.name();
    private String voiceType = VoicePromptsType.BUSY.name();

    private List<String> notAccessNumber = new ArrayList<String>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rule_layout);

        notAccessNumber.add("110");
        notAccessNumber.add("120");
        notAccessNumber.add("119");


        mController = new RuleController(this);
        mHandler = new Handler();

        mBlockBlackBtn.setOnClickListener(this);
        mAllowWhiteBtn.setOnClickListener(this);
        mBlockAllBtn.setOnClickListener(this);

        mChinaMobileBtn.setOnClickListener(this);
        mChinaUnicomBtn.setOnClickListener(this);
        mChinaTelecomBtn.setOnClickListener(this);
        mOtherOperators.setOnClickListener(this);

        mSettingBtn.setOnClickListener(this);

        mUserBusyBtn.setOnClickListener(this);
        mFeeArrearsBtn.setOnClickListener(this);
        mNullNumberBtn.setOnClickListener(this);
        mShutDownBtn.setOnClickListener(this);


        mHandler.post(new Runnable() {
            @Override
            public void run() {
                initBlockRuleButtonsStatus();
                initTransferNumber();
            }
        });
    }

    private void initTransferNumber() {
        TransferNumber defaultTransferNumber = this.mController.getDefaultTransferNumber();
        if (defaultTransferNumber == null) {
            mInputNumber.setText("##67#");
        } else {
            setRadioStatusByTransferNumber(defaultTransferNumber);
        }
    }

    private void radioButtonOnChangeEvent() {
        TransferNumber transferNumber = this.mController.queryTransferNumberByOperatorsAndVoiceTye(operators, voiceType);
        if (transferNumber == null) {
            if (OperatorsType.CHINA_MOBILE.name().equals(operators) && VoicePromptsType.NULL_NUMBER.name().equals(voiceType)) {
                mInputNumber.setText("**67*13800000000#");
            } else {
                mInputNumber.setText("");
            }
        } else {
            setRadioStatusByTransferNumber(transferNumber);
        }
    }

    private void setRadioStatusByTransferNumber(TransferNumber transferNumber) {
        operators = transferNumber.getOperators();
        voiceType = transferNumber.getVoiceType();
        switch (OperatorsType.valueOf(transferNumber.getOperators())) {
            case CHINA_MOBILE:
                mChinaMobileBtn.setChecked(true);
                break;
            case CHINA_UNICOM:
                mChinaUnicomBtn.setChecked(true);
                break;
            case CHINA_TELECOM:
                mChinaTelecomBtn.setChecked(true);
                break;
            case OTHER:
                mOtherOperators.setChecked(true);
                break;
        }
        switch (VoicePromptsType.valueOf(transferNumber.getVoiceType())) {
            case BUSY:
                mUserBusyBtn.setChecked(true);
                break;
            case NULL_NUMBER:
                mNullNumberBtn.setChecked(true);
                break;
            case ARREARS:
                mFeeArrearsBtn.setChecked(true);
                break;
            case SHUTDOWN:
                mShutDownBtn.setChecked(true);
                break;
        }
        mInputNumber.setText(transferNumber.getCallNumber());
    }

    public void initBlockRuleButtonsStatus() {
        BlockRuleType blockRule = this.mController.getCurrentBlockRule();
        switch (blockRule) {
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
        switch (v.getId()) {
            case R.id.block_black_btn:
                this.mController.createOrUpdateBlockRule(BlockRuleType.BLOCK_BLACK_LIST.name());
                break;
            case R.id.allow_white_btn:
                this.mController.createOrUpdateBlockRule(BlockRuleType.ALLOW_WHITE_LIST.name());
                break;
            case R.id.block_all_btn:
                this.mController.createOrUpdateBlockRule(BlockRuleType.BLOCK_ALL.name());
                break;

            case R.id.china_mobile_btn:
                operators = OperatorsType.CHINA_MOBILE.name();
                radioButtonOnChangeEvent();
                break;
            case R.id.china_unicom_btn:
                operators = OperatorsType.CHINA_UNICOM.name();
                radioButtonOnChangeEvent();
                break;
            case R.id.china_telecom_btn:
                operators = OperatorsType.CHINA_TELECOM.name();
                radioButtonOnChangeEvent();
                break;
            case R.id.other_operators_btn:
                operators = OperatorsType.OTHER.name();
                radioButtonOnChangeEvent();
                break;

            case R.id.ring_user_busy_btn:
                voiceType = VoicePromptsType.BUSY.name();
                radioButtonOnChangeEvent();
                break;
            case R.id.ring_fee_arrears_btn:
                voiceType = VoicePromptsType.ARREARS.name();
                radioButtonOnChangeEvent();
                break;
            case R.id.ring_null_number_btn:
                voiceType = VoicePromptsType.NULL_NUMBER.name();
                radioButtonOnChangeEvent();
                break;
            case R.id.ring_shut_down_btn:
                voiceType = VoicePromptsType.SHUTDOWN.name();
                radioButtonOnChangeEvent();
                break;

            case R.id.rule_setting_btn:
                callTransferNumber();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        PageJumpUtils.jump(this, MainActivity.class);
        this.finish();
    }


    private void callTransferNumber() {
        String phoneNumber = mInputNumber.getText().toString();
        if (phoneNumber.trim().equals("") || notAccessNumber.contains(phoneNumber.trim())) {
            ToastUtils.showMessage(this, "错误,设置号码为空或非法号码..");
            return;
        }
        TransferNumber transferNumber = new TransferNumber();
        transferNumber.setOperators(operators);
        transferNumber.setVoiceType(voiceType);
        transferNumber.setCallNumber(phoneNumber);
        transferNumber.setSelected(true);
        mController.createOrUpdateTransferNumber(transferNumber);
        callPhone(phoneNumber);
        ToastUtils.showMessage(this, "设置成功..");
    }

    private void callPhone(String phoneNumber) {

        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.intent.action.CALL");
//        Uri uri = Uri.parse("tel:" + "**67*13800000000%23");
        Uri uri = Uri.parse("tel:" + phoneNumber.replaceAll("#", "%23"));
//        Uri uri = Uri.parse("tel:" + "**67*13810538911%23");
//        Uri uri = Uri.parse("tel:" + "**67*13701110216%23");
        localIntent.setData(uri);
        this.startActivity(localIntent);
    }
}
