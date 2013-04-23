package info.ishared.android.firewall.activity;

import info.ishared.android.firewall.bean.BlockRule;
import info.ishared.android.firewall.bean.BlockRuleType;
import info.ishared.android.firewall.bean.TransferNumber;
import info.ishared.android.firewall.db.BlockRuleDBOperator;
import info.ishared.android.firewall.db.TransferNumberDBOperator;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-4-1
 * Time: PM1:53
 */
public class RuleController {

    private RuleActivity ruleActivity;
    private BlockRuleDBOperator blockRuleDBOperator;
    private TransferNumberDBOperator transferNumberDBOperator;

    public RuleController(RuleActivity ruleActivity) {
        this.ruleActivity = ruleActivity;
        this.blockRuleDBOperator = new BlockRuleDBOperator(ruleActivity);
        this.transferNumberDBOperator = new TransferNumberDBOperator(ruleActivity);
    }

    public void createOrUpdateBlockRule(String ruleType){
        List<BlockRule> blockRules = this.blockRuleDBOperator.getBlockRule();
        if(blockRules.isEmpty()){
            this.blockRuleDBOperator.insertBlockRule(ruleType);
        }else{
            this.blockRuleDBOperator.updateBlockRuleType(ruleType);
        }
    }

    public BlockRuleType getCurrentBlockRule(){
        List<BlockRule> blockRules = this.blockRuleDBOperator.getBlockRule();
        if(blockRules.isEmpty()){
            return BlockRuleType.BLOCK_BLACK_LIST;
        }else{
            return BlockRuleType.valueOf(blockRules.get(0).getRuleType());
        }
    }

    public TransferNumber getDefaultTransferNumber(){
        return this.transferNumberDBOperator.queryDefaultTransferNumber();
    }

    public TransferNumber queryTransferNumberByOperatorsAndVoiceTye(String operators,String voiceType){
        return this.transferNumberDBOperator.queryTransferNumberByOperatorsAndVoiceTye(operators,voiceType);
    }

    public void createOrUpdateTransferNumber(TransferNumber transferNumber){
        this.transferNumberDBOperator.updateTransferNumber(transferNumber);
    }
}
