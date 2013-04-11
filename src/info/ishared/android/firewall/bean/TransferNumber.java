package info.ishared.android.firewall.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-4-2
 * Time: PM3:09
 */
public class TransferNumber implements Serializable{
    private String operators;
    private String voiceType;
    private String callNumber;
    private boolean selected;

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators;
    }

    public String getVoiceType() {
        return voiceType;
    }

    public void setVoiceType(String voiceType) {
        this.voiceType = voiceType;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
