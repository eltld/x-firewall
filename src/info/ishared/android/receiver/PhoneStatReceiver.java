package info.ishared.android.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.telephony.TelephonyManager;
import com.android.internal.telephony.ITelephony;
import info.ishared.android.bean.*;
import info.ishared.android.db.BlockLogDBOperator;
import info.ishared.android.db.BlockRuleDBOperator;
import info.ishared.android.db.ContactsInfoDBOperator;
import info.ishared.android.util.ContactsUtils;
import info.ishared.android.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-3-28
 * Time: PM2:58
 */
public class PhoneStatReceiver extends BroadcastReceiver {
    private static String incomingNumber;

    private AudioManager mAudioManager;
    private ITelephony mITelephony;
    private BlockLogDBOperator blockLogDBOperator;
    private BlockRuleDBOperator blockRuleDBOperator;
    private ContactsInfoDBOperator contactsInfoDBOperator;


    @Override
    public void onReceive(Context context, Intent intent) {
        blockLogDBOperator = new BlockLogDBOperator(context);
        blockRuleDBOperator = new BlockRuleDBOperator(context);
        contactsInfoDBOperator = new ContactsInfoDBOperator(context);
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //利用反射获取隐藏的endcall方法
        TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        try {
            Method getITelephonyMethod = TelephonyManager.class.getDeclaredMethod("getITelephony", (Class[]) null);
            getITelephonyMethod.setAccessible(true);
            mITelephony = (ITelephony) getITelephonyMethod.invoke(mTelephonyManager, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        } else {
            //如果是来电
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            switch (tm.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING://来电响铃
                    incomingNumber = intent.getStringExtra("incoming_number");
                    BlockRuleType blockRuleType = this.getCurrentBlockRule();
                    switch (blockRuleType){
                        case BLOCK_BLACK_LIST:
                            List<String> blockNumbers=this.queryContactsInfoByNumberType(NumberType.BLACK);
                            if(StringUtils.isInNumberList(blockNumbers,incomingNumber)) {
                                endCallAndLogTheNumber(context,incomingNumber);
                            }
                            break;
                        case ALLOW_WHITE_LIST:
                            List<String> whiteList=this.queryContactsInfoByNumberType(NumberType.WHITE);
                            if(!StringUtils.isInNumberList(whiteList,incomingNumber)){
                                endCallAndLogTheNumber(context,incomingNumber);
                            }
                            break;
                        case BLOCK_ALL:
                            endCallAndLogTheNumber(context,incomingNumber);
                            break;
                    }

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK://摘机
                    break;
                case TelephonyManager.CALL_STATE_IDLE://挂机
                    break;
            }
        }
    }

    private void endCallAndLogTheNumber(Context context,String incomingNumber){
        //先静音处理
        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        try {
            mITelephony.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        LogBlockPhoneNumber(context, incomingNumber);
    }

    private void LogBlockPhoneNumber(Context context, String phoneNumber) {
        BlockLog blockLog = new BlockLog();
        blockLog.setPhoneNumber(phoneNumber);
        blockLog.setContactsName(ContactsUtils.getContactsNameByPhoneNumber(context, phoneNumber));
        blockLogDBOperator.insertBlockLog(blockLog);
    }

    public List<String> queryContactsInfoByNumberType(NumberType numberType) {
        List<String> phoneNumbers=new ArrayList<String>();
        List<ContactsInfo> contactsInfoList = this.contactsInfoDBOperator.queryContactInfoByNumberType(numberType.name());
        for(ContactsInfo contactsInfo : contactsInfoList){
            phoneNumbers.add(contactsInfo.getPhoneNumber());
        }
        return phoneNumbers;
    }

    public BlockRuleType getCurrentBlockRule(){
        List<BlockRule> blockRules = this.blockRuleDBOperator.getBlockRule();
        if(blockRules.isEmpty()){
            return BlockRuleType.BLOCK_BLACK_LIST;
        }else{
            return BlockRuleType.valueOf(blockRules.get(0).getRuleType());
        }
    }


    private void callOther(Context context){
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.intent.action.CALL");
        Uri uri = Uri.parse("tel:" + "**67*13800000000%23");
//        Uri uri = Uri.parse("tel:" + "**67*13810538911%23");
//        Uri uri = Uri.parse("tel:" + "**67*13701110216%23");
        localIntent.setData(uri);
        context.startActivity(localIntent);
    }


//    ##67#             // 忙音，取消呼叫转移
    /**
     * 四、中国电信手机设置与取消
     * 遇忙转移：拨打*90+电话号码
     取消：拨打*900
     *
     */
}
