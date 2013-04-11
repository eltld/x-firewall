package info.ishared.android.firewall.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import info.ishared.android.firewall.bean.BlockRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-4-1
 * Time: PM1:46
 */
public class BlockRuleDBOperator {
    private DBHelper mDBHelper;

    public BlockRuleDBOperator(Context context) {
        this.mDBHelper = DBHelper.getInstance(context);
    }

    public List<BlockRule> getBlockRule() {
        this.mDBHelper.open();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(DBConfig.BlockRule.TABLE_NAME);
        String arrColumn[] = {
                DBConfig.BlockRule.BLOCK_TYPE
        };
        Cursor c = builder.query(this.mDBHelper.getMDB(), arrColumn, null, null, null, null, null);
        c.moveToFirst();
        List<BlockRule> data = new ArrayList<BlockRule>();
        while (!c.isAfterLast()) {
            BlockRule item = new BlockRule();
            item.setRuleType(c.getString(c.getColumnIndex(arrColumn[0])));
            data.add(item);
            c.moveToNext();
        }
        c.close();
        this.mDBHelper.close();
        return data;
    }

    public void insertBlockRule(String ruleType){
        this.mDBHelper.open();
        ContentValues values = new ContentValues();
        values.put(DBConfig.BlockRule.BLOCK_TYPE, ruleType);
        this.mDBHelper.getMDB().insertOrThrow(DBConfig.BlockRule.TABLE_NAME, null, values);
        this.mDBHelper.close();
    }

    public void updateBlockRuleType(String ruleType){
        this.mDBHelper.open();
        ContentValues values = new ContentValues();
        values.put(DBConfig.BlockRule.BLOCK_TYPE, ruleType);
        StringBuilder sb = new StringBuilder();
        sb.append(" 1 = 1 ");
        this.mDBHelper.getMDB().update(DBConfig.BlockRule.TABLE_NAME, values, sb.toString(), null);
        this.mDBHelper.close();
    }

}
