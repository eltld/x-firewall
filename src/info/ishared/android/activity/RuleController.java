package info.ishared.android.activity;

import info.ishared.android.bean.BlockRule;
import info.ishared.android.bean.BlockRuleType;
import info.ishared.android.db.BlockRuleDBOperator;

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

    public RuleController(RuleActivity ruleActivity) {
        this.ruleActivity = ruleActivity;
        this.blockRuleDBOperator = new BlockRuleDBOperator(ruleActivity);
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
}
