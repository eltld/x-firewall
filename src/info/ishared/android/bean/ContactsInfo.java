package info.ishared.android.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-3-28
 * Time: PM4:17
 */
public class ContactsInfo implements Serializable {
    private Long id;
    private String phoneNumber;
    private String contactName;
    private String numberType;

    public ContactsInfo(){

    }

    public ContactsInfo(String phoneNumber, String contactName, String numberType) {
        this.phoneNumber = phoneNumber;
        this.contactName = contactName;
        this.numberType = numberType;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ContactsInfo{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", contactName='" + contactName + '\'' +
                ", numberType='" + numberType + '\'' +
                '}';
    }

}
