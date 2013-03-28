package info.ishared.android.bean;

import java.io.Serializable;
import java.security.PrivateKey;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-3-28
 * Time: PM4:17
 */
public class ContactsInfo implements Serializable {
    private String number;
    private String name;
    private String numberType;


    public ContactsInfo(String number, String name, String numberType) {
        this.number = number;
        this.name = name;
        this.numberType = numberType;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ContactsInfo{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", numberType='" + numberType + '\'' +
                '}';
    }
}
