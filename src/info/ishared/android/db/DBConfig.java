package info.ishared.android.db;

/**
 * Created by IntelliJ IDEA.
 * User: Lee
 * Date: 11-12-7
 * Time: 下午4:47
 */
public class DBConfig {
    public static final String DB_NAME = "x-firewall.db";
    public static final int DB_VERSION = 0x00000001;

    public static class ContactsInfo {

        public static final String TABLE_NAME = "contacts_info";

        public static final String ID = "id";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String CONTACT_NAME = "contact_name";
        public static final String NUMBER_TYPE = "number_type";

        public static final String CREATE_CONTACTS_INFO_SQL = "create table " + TABLE_NAME
                + "("
                + ID + " integer primary key autoincrement, "
                + PHONE_NUMBER + " TEXT, "
                + CONTACT_NAME + " TEXT, "
                + NUMBER_TYPE + " TEXT "
                + ");";
    }


    public static class BlockLog {
        public static final String TABLE_NAME = "block_log";

        public static final String ID = "id";
        public static final String BLOCK_DATE = "block_date";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String CONTACT_NAME = "contact_name";

        public static final String CREATE_BLOCK_LOG_SQL = "create table " + TABLE_NAME
                + "("
                + ID + " integer primary key autoincrement, "
                + BLOCK_DATE + " TEXT, "
                + PHONE_NUMBER + " TEXT, "
                + CONTACT_NAME + " TEXT "
                + ");";
    }


}
