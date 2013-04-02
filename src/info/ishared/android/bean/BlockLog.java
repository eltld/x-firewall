package info.ishared.android.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-3-29
 * Time: AM11:01
 */
public class BlockLog implements Serializable {

    private Long id;
    private String blockDate;
    private String phoneNumber;
    private String contactsName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBlockDate() {
        return blockDate;
    }

    public void setBlockDate(String blockDate) {
        this.blockDate = blockDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    @Override
    public String toString() {
        return "BlockLog{" +
                "id=" + id +
                ", blockDate=" + blockDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", contactsName='" + contactsName + '\'' +
                '}';
    }
}
