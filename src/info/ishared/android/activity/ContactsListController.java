package info.ishared.android.activity;

import info.ishared.android.bean.ContactsInfo;
import info.ishared.android.bean.NumberType;
import info.ishared.android.db.ContactsInfoDBOperator;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lee
 * Date: 13-3-29
 * Time: 下午7:17
 */
public class ContactsListController {
    private ContactsListActivity contactsListActivity;
    private BlackContactsListActivity blackContactsListActivity;
    private ContactsInfoDBOperator contactsInfoDBOperator;

    public ContactsListController(BlackContactsListActivity blackContactsListActivity) {
        this.blackContactsListActivity = blackContactsListActivity;
        this.contactsInfoDBOperator = new ContactsInfoDBOperator(blackContactsListActivity);
    }

    public ContactsListController(ContactsListActivity contactsListActivity) {
        this.contactsListActivity = contactsListActivity;
        this.contactsInfoDBOperator = new ContactsInfoDBOperator(contactsListActivity);
    }

    public void sendContactToBlockList(String phoneNumber, String contactName, NumberType numberType) {
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setPhoneNumber(phoneNumber);
        contactsInfo.setContactName(contactName);
        contactsInfo.setNumberType(numberType.name());
        ContactsInfo existContactInfo=this.contactsInfoDBOperator.getContactsInfoByPhoneNumber(phoneNumber);
        if(existContactInfo ==null){
            this.contactsInfoDBOperator.createContactsInfo(contactsInfo);
        }else{
            if(!existContactInfo.getNumberType().equals(numberType.name())){
                this.contactsInfoDBOperator.updateContactsInfoType(existContactInfo.getId(),numberType.name());
            }
        }
    }

    public List<ContactsInfo> queryContractInfoByNumberType(NumberType numberType){
        List<ContactsInfo> contactsInfoList = this.contactsInfoDBOperator.queryContactInfoByNumberType(numberType.name());
        return contactsInfoList;
    }



}
