package info.ishared.android.firewall.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import info.ishared.android.firewall.bean.BlockLog;
import info.ishared.android.firewall.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 12-12-20
 * Time: PM4:45
 */
public class BlockLogDBOperator {
    private DBHelper mDBHelper;

    public BlockLogDBOperator(Context context) {
        this.mDBHelper = DBHelper.getInstance(context);
    }


    public List<BlockLog> listAllBlockLogs() {
        this.mDBHelper.open();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(DBConfig.BlockLog.TABLE_NAME);
        String arrColumn[] = {
                DBConfig.BlockLog.ID, DBConfig.BlockLog.BLOCK_DATE, DBConfig.BlockLog.PHONE_NUMBER, DBConfig.BlockLog.CONTACT_NAME
        };
        Cursor c = builder.query(this.mDBHelper.getMDB(), arrColumn, null, null, null, null, "id DESC");
        c.moveToFirst();
        List<BlockLog> data = new ArrayList<BlockLog>();
        while (!c.isAfterLast()) {
            BlockLog item = new BlockLog();
            item.setId(c.getLong(c.getColumnIndex(arrColumn[0])));
            item.setBlockDate(c.getString(c.getColumnIndex(arrColumn[1])));
            item.setPhoneNumber(c.getString(c.getColumnIndex(arrColumn[2])));
            item.setContactsName(c.getString(c.getColumnIndex(arrColumn[3])));
            data.add(item);
            c.moveToNext();
        }
        c.close();
        this.mDBHelper.close();
        return data;

    }

    public void insertBlockLog(BlockLog blockLog){
        this.mDBHelper.open();
        ContentValues values = new ContentValues();
        values.put(DBConfig.BlockLog.BLOCK_DATE, DateUtils.formatDate(new Date()));
        values.put(DBConfig.BlockLog.PHONE_NUMBER, blockLog.getPhoneNumber());
        values.put(DBConfig.BlockLog.CONTACT_NAME, blockLog.getContactsName());

        this.mDBHelper.getMDB().insertOrThrow(DBConfig.BlockLog.TABLE_NAME, null, values);
        this.mDBHelper.close();
    }

    public void cleanBlockLog(){
        this.mDBHelper.open();
        this.mDBHelper.getMDB().delete(DBConfig.BlockLog.TABLE_NAME,null,null);
        this.mDBHelper.close();
    }
}
