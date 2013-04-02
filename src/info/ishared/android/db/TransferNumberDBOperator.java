package info.ishared.android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import info.ishared.android.bean.ContactsInfo;
import info.ishared.android.bean.TransferNumber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-4-2
 * Time: PM3:23
 */
public class TransferNumberDBOperator {
    private DBHelper mDBHelper;

    public TransferNumberDBOperator(Context context) {
        this.mDBHelper = DBHelper.getInstance(context);
    }

    public void insertTransferNumber(TransferNumber transferNumber) {
        this.mDBHelper.open();
        ContentValues values = new ContentValues();
        values.put(DBConfig.TransferNumber.OPERATORS, transferNumber.getOperators());
        values.put(DBConfig.TransferNumber.VOICE_TYPE, transferNumber.getVoiceType());
        values.put(DBConfig.TransferNumber.CALL_NUMBER, transferNumber.getCallNumber());
        values.put(DBConfig.TransferNumber.SELECTED, transferNumber.isSelected() ? 1 : 0);

        this.mDBHelper.getMDB().insertOrThrow(DBConfig.TransferNumber.TABLE_NAME, null, values);
        this.mDBHelper.close();
    }


    private List<TransferNumber> queryTransferNumber(String whereClause) {
        this.mDBHelper.open();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.appendWhere(whereClause);
        builder.setTables(DBConfig.TransferNumber.TABLE_NAME);
        String arrColumn[] = {
                DBConfig.TransferNumber.OPERATORS, DBConfig.TransferNumber.VOICE_TYPE, DBConfig.TransferNumber.CALL_NUMBER, DBConfig.TransferNumber.SELECTED,
        };
        Cursor c = builder.query(this.mDBHelper.getMDB(), arrColumn, null, null, null, null, null);
        c.moveToFirst();
        List<TransferNumber> data = new ArrayList<TransferNumber>();
        while (!c.isAfterLast()) {
            TransferNumber item = new TransferNumber();
            item.setOperators(c.getString(c.getColumnIndex(arrColumn[0])));
            item.setVoiceType(c.getString(c.getColumnIndex(arrColumn[1])));
            item.setCallNumber(c.getString(c.getColumnIndex(arrColumn[2])));
            item.setSelected(c.getInt(c.getColumnIndex(arrColumn[3])) == 1 ? true : false);
            data.add(item);
            c.moveToNext();
        }
        c.close();
        this.mDBHelper.close();
        return data;

    }

    public List<TransferNumber> queryTransferNumberByOperators(String operators) {
        String whereClause = DBConfig.TransferNumber.OPERATORS + " = '" + operators + "'";
        List<TransferNumber> transferNumberList = this.queryTransferNumber(whereClause);
        return transferNumberList;
    }

    public TransferNumber queryTransferNumberByOperatorsAndVoiceTye(String operators, String voiceType) {
        String whereClause = DBConfig.TransferNumber.OPERATORS + " = '" + operators + "'";
        whereClause += " AND ";
        whereClause += DBConfig.TransferNumber.VOICE_TYPE + " = '" + voiceType + "'";
        List<TransferNumber> transferNumberList = this.queryTransferNumber(whereClause);
        if (transferNumberList.isEmpty()) return null;
        return transferNumberList.get(0);
    }

    public TransferNumber queryDefaultTransferNumber(){
        String whereClause = DBConfig.TransferNumber.SELECTED + " = 1 ";
        List<TransferNumber> transferNumberList = this.queryTransferNumber(whereClause);
        if(transferNumberList.isEmpty()){
            return null;
        }
        return transferNumberList.get(0);
    }

    public void updateTransferNumber(TransferNumber transferNumber) {
        updateAllTransferNumberToNotSelected();
        TransferNumber existTransferNumber = this.queryTransferNumberByOperatorsAndVoiceTye(transferNumber.getOperators(), transferNumber.getVoiceType());
        if (existTransferNumber == null) {
            this.insertTransferNumber(transferNumber);
        } else {
            this.mDBHelper.open();
            ContentValues values = new ContentValues();
            values.put(DBConfig.TransferNumber.CALL_NUMBER, transferNumber.getCallNumber());
            values.put(DBConfig.TransferNumber.SELECTED, transferNumber.isSelected() ? 1 : 0);
            StringBuilder sb = new StringBuilder();
            sb.append(DBConfig.TransferNumber.OPERATORS + " = '" + transferNumber.getOperators() + "'");
            sb.append(" AND ");
            sb.append(DBConfig.TransferNumber.VOICE_TYPE + " = '" + transferNumber.getVoiceType() + "'");
            this.mDBHelper.getMDB().update(DBConfig.TransferNumber.TABLE_NAME, values, sb.toString(), null);
            this.mDBHelper.close();
        }
    }


    private void updateAllTransferNumberToNotSelected() {
        this.mDBHelper.open();
        ContentValues values = new ContentValues();
        values.put(DBConfig.TransferNumber.SELECTED, 0);
        StringBuilder sb = new StringBuilder();
        sb.append(" 1 = 1 ");
        this.mDBHelper.getMDB().update(DBConfig.TransferNumber.TABLE_NAME, values, sb.toString(), null);
        this.mDBHelper.close();
    }


}
