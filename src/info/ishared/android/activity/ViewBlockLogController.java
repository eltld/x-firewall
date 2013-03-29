package info.ishared.android.activity;

import android.content.Context;
import info.ishared.android.bean.BlockLog;
import info.ishared.android.db.BlockLogDBOperator;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-3-29
 * Time: AM11:14
 */
public class ViewBlockLogController {
    private ViewBlockLogActivity viewBlockLogActivity;
    private BlockLogDBOperator blockLogDBOperator;

    public ViewBlockLogController(Context content){
        this.blockLogDBOperator = new BlockLogDBOperator(content);
    }

    public ViewBlockLogController(ViewBlockLogActivity viewBlockLogActivity) {
        this.viewBlockLogActivity = viewBlockLogActivity;
        this.blockLogDBOperator = new BlockLogDBOperator(viewBlockLogActivity);
    }

    public List<BlockLog> queryBlockLogs(){
        List<BlockLog> blockLogs = this.blockLogDBOperator.listAllBlockLogs();
        return blockLogs;
    }

    public void cleanBlockLogs(){
        this.blockLogDBOperator.cleanBlockLog();
    }

    public void insertTestDate(){
        for(int i=0;i<15;i++){
            BlockLog blockLog=new BlockLog();
            blockLog.setPhoneNumber("136781995"+i);
            blockLog.setContactsName("张三"+i);
            this.blockLogDBOperator.insertBlockLog(blockLog);
        }
    }
}
