package info.ishared.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import info.ishared.android.R;
import roboguice.inject.InjectView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-3-29
 * Time: AM11:03
 */
public class ViewBlockLogActivity extends RoboSherlockActivity {
    @InjectView(R.id.block_log_list_view)
    private ListView mListView;

    private SimpleAdapter adapter;
    private List<Map<String,String>> logsData=new ArrayList<Map<String,String>>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_block_log_list);

        initListViewData();
        initListViewGUI();
    }

    private void initListViewGUI() {
        adapter = new SimpleAdapter(this,logsData, R.layout.view_block_log_list_item, new String[]{"info","time"},new int[]{R.id.block_log_item_text1,R.id.block_log_item_text2}){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view=super.getView(position, convertView, parent);
                if (position % 2 != 0)
                    view.setBackgroundResource(R.drawable.table_background_selector);
                else
                    view.setBackgroundResource(R.drawable.table_background_alternate_selector);
                return view;
            }
        };
        mListView.setAdapter(adapter);
    }

    private void initListViewData() {
        for(int i=0;i<20;i++){
            Map<String,String> map=new HashMap<String, String>(2);
            map.put("info","张三,13678199505");
            map.put("time","拦截时间：2012-11-11 11:11:11");
            logsData.add(map);
        }
    }
}