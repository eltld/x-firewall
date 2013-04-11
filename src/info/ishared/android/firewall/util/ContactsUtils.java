package info.ishared.android.firewall.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import info.ishared.android.firewall.bean.ContactsInfo;
import info.ishared.android.firewall.bean.NumberType;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-3-29
 * Time: PM3:23
 */
public class ContactsUtils {
    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Photo.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

    /**
     * 联系人显示名称*
     */
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;

    /**
     * 电话号码*
     */
    private static final int PHONES_NUMBER_INDEX = 1;

    /**
     * 头像ID*
     */
    private static final int PHONES_PHOTO_ID_INDEX = 2;

    /**
     * 联系人的ID*
     */
    private static final int PHONES_CONTACT_ID_INDEX = 3;

    /**
     * 联系人名称*
     */


    public static String getContactsNameByPhoneNumber(Context context,String phoneNumber){
        ContentResolver resolver = context.getContentResolver();
// 获取手机联系人
        String whereClause=ContactsContract.CommonDataKinds.Phone.NUMBER+ " = '"+phoneNumber+"'";
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, whereClause, null, null);
        if (phoneCursor != null) {
            if (phoneCursor.moveToNext()) {
                return phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
            }
        }
        return phoneNumber;
    }

    public static List<ContactsInfo> getPhoneContacts(Context context) {
        ArrayList<ContactsInfo> contactsInfoArrayList = new ArrayList<ContactsInfo>();
        ContentResolver resolver = context.getContentResolver();

// 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);


        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {

                //得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;

                //得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

                //得到联系人ID
                Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);

                //得到联系人头像ID
                Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);

                //得到联系人头像Bitamp
                Bitmap contactPhoto = null;

                //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
                if (photoid > 0) {
                    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
                    contactPhoto = BitmapFactory.decodeStream(input);
                } else {
//                    contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.contact_photo);
                }

                contactsInfoArrayList.add(new ContactsInfo(phoneNumber, contactName, NumberType.CONTRACT.name()));
            }

            phoneCursor.close();
        }
        return contactsInfoArrayList;
    }

    public static List<ContactsInfo> getSIMContacts(Context context) {
        ArrayList<ContactsInfo> contactsInfoArrayList = new ArrayList<ContactsInfo>();
        ContentResolver resolver = context.getContentResolver();
        // 获取Sims卡联系人
        Uri uri = Uri.parse("content://icc/adn");
        Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,
                null);

        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {

                // 得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                // 当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                // 得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

                //Sim卡中没有联系人头像
                contactsInfoArrayList.add(new ContactsInfo(phoneNumber, contactName, NumberType.SIM.name()));
            }

            phoneCursor.close();
        }
        return contactsInfoArrayList;
    }
}
