package info.ishared.android.firewall;

import info.ishared.android.firewall.bean.*;
import info.ishared.android.firewall.db.BlockRuleDBOperator;
import info.ishared.android.firewall.db.TransferNumberDBOperator;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-3-28
 * Time: PM4:24
 */
public class MainController {

    private BlockRuleDBOperator blockRuleDBOperator;
    private TransferNumberDBOperator transferNumberDBOperator;


    private MainActivity mainActivity;

    public MainController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.blockRuleDBOperator = new BlockRuleDBOperator(mainActivity);
        this.transferNumberDBOperator = new TransferNumberDBOperator(mainActivity);
    }

    public String getBlockRuleTextViewText() {
        String str = "";
        switch (queryBlockRuleStatus()) {
            case BLOCK_BLACK_LIST:
                str = "拦截黑名单";
                break;
            case ALLOW_WHITE_LIST:
                str = "允许白名单";
                break;
            case BLOCK_ALL:
                str = "全部拦截";
                break;
        }
        return str;
    }

    public String getTransferRuleText() {
        String str = "";
        TransferNumber transferNumber = queryDefaultTransferNumber();
        if (transferNumber == null) {
            str = "全部运营商,用户忙";
        } else {
            switch (OperatorsType.valueOf(transferNumber.getOperators())) {
                case CHINA_MOBILE:
                    str += "中国移动,";
                    break;
                case CHINA_UNICOM:
                    str += "中国联通,";
                    break;
                case CHINA_TELECOM:
                    str += "中国电信,";
                    break;
                case OTHER:
                    str += "其他运营商,";
                    break;
            }
            switch (VoicePromptsType.valueOf(transferNumber.getVoiceType())) {
                case BUSY:
                    str += "用户忙";
                    break;
                case NULL_NUMBER:
                    str += "号码是空号";
                    break;
                case ARREARS:
                    str += "用户已欠费";
                    break;
                case SHUTDOWN:
                    str += "用户已关机";
                    break;
            }
        }

        return str;
    }


    private BlockRuleType queryBlockRuleStatus() {
        List<BlockRule> blockRules = this.blockRuleDBOperator.getBlockRule();
        if (blockRules.isEmpty()) {
            return BlockRuleType.BLOCK_BLACK_LIST;
        } else {
            return BlockRuleType.valueOf(blockRules.get(0).getRuleType());
        }
    }

    private TransferNumber queryDefaultTransferNumber() {
        return this.transferNumberDBOperator.queryDefaultTransferNumber();
    }


}
