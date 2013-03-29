package info.ishared.android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import info.ishared.android.bean.ContactsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 12-12-20
 * Time: PM4:45
 */
public class ContactsInfoDBOperator {
    private DBHelper mDBHelper;

    public ContactsInfoDBOperator(Context context) {
        this.mDBHelper = DBHelper.getInstance(context);
    }

    public List<ContactsInfo> listAllContactsInfoList() {
        return queryContactsInfo(" 1=1 ");
    }
    public ContactsInfo getContactsInfoByPhoneNumber(String phoneNumber){
        String whereClause=DBConfig.ContactsInfo.PHONE_NUMBER+" = '"+phoneNumber+"'";
        List<ContactsInfo> contactsInfoList= this.queryContactsInfo(whereClause);
        if(contactsInfoList.isEmpty()){
            return null;
        }
        return contactsInfoList.get(0);
    }

    public List<ContactsInfo> queryContactInfoByNumberType(String numberType){
        String whereClause=DBConfig.ContactsInfo.NUMBER_TYPE+" = '"+numberType+"'";
        List<ContactsInfo> contactsInfoList= this.queryContactsInfo(whereClause);
        return  contactsInfoList;
    }

    private List<ContactsInfo> queryContactsInfo(String whereClause) {
        this.mDBHelper.open();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.appendWhere(whereClause);
        builder.setTables(DBConfig.ContactsInfo.TABLE_NAME);
        String arrColumn[] = {
                DBConfig.ContactsInfo.ID, DBConfig.ContactsInfo.PHONE_NUMBER, DBConfig.ContactsInfo.CONTACT_NAME, DBConfig.ContactsInfo.NUMBER_TYPE
        };
        Cursor c = builder.query(this.mDBHelper.getMDB(), arrColumn, null, null, null, null, null);
        c.moveToFirst();
        List<ContactsInfo> data = new ArrayList<ContactsInfo>();
        while (!c.isAfterLast()) {
            ContactsInfo item = new ContactsInfo();
            item.setId(c.getLong(c.getColumnIndex(arrColumn[0])));
            item.setPhoneNumber(c.getString(c.getColumnIndex(arrColumn[1])));
            item.setContactName(c.getString(c.getColumnIndex(arrColumn[2])));
            item.setNumberType(c.getString(c.getColumnIndex(arrColumn[3])));
            data.add(item);
            c.moveToNext();
        }
        c.close();
        this.mDBHelper.close();
        return data;

    }


    public void createContactsInfo(ContactsInfo contactsInfo){
        this.mDBHelper.open();
        ContentValues values = new ContentValues();
        values.put(DBConfig.ContactsInfo.PHONE_NUMBER, contactsInfo.getPhoneNumber());
        values.put(DBConfig.ContactsInfo.CONTACT_NAME, contactsInfo.getContactName());
        values.put(DBConfig.ContactsInfo.NUMBER_TYPE, contactsInfo.getNumberType());
        this.mDBHelper.getMDB().insertOrThrow(DBConfig.ContactsInfo.TABLE_NAME, null, values);
        this.mDBHelper.close();
    }


    public void updateContactsInfoType(Long id,String numberType){
        this.mDBHelper.open();
        ContentValues values = new ContentValues();
        values.put(DBConfig.ContactsInfo.NUMBER_TYPE, numberType);
        StringBuilder sb = new StringBuilder();
        sb.append(DBConfig.ContactsInfo.ID + "=" + id + "");
        this.mDBHelper.getMDB().update(DBConfig.ContactsInfo.TABLE_NAME, values, sb.toString(), null);
        this.mDBHelper.close();
    }

    public void deleteContactInfoByNumber(String phoneNumber){
        this.mDBHelper.open();
        String whereClause=DBConfig.ContactsInfo.PHONE_NUMBER +" = '"+phoneNumber+"'";
        this.mDBHelper.getMDB().delete(DBConfig.ContactsInfo.TABLE_NAME,whereClause,null);
        this.mDBHelper.close();
    }

}
