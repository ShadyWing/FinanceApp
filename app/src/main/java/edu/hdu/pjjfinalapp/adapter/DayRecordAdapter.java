package edu.hdu.pjjfinalapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.hdu.pjjfinalapp.MainActivity;
import edu.hdu.pjjfinalapp.db.AccountItem;
import edu.hdu.pjjfinalapp.db.DBManager;
import edu.hdu.pjjfinalapp.db.DayRecordItem;
import edu.hdu.pjjfinalapp.R;

public class DayRecordAdapter extends BaseAdapter {
    Context context;
    List<DayRecordItem> mDatas;
    LayoutInflater inflater;
    RelativeLayout rl;
    int year, month, day;

    public DayRecordAdapter(Context context, List<DayRecordItem> mDatas){
        this.context = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_dayrecord_lv,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        // 设置此Item在Activity中的各种View显示
        DayRecordItem item = mDatas.get(position);

        final List<AccountItem> lvDatas = new ArrayList<>();
        final AccountAdapter adapter = new AccountAdapter(context, lvDatas);

        holder.dateTv.setText(item.getDate());
        holder.onedayTv.setText("支出："+String.valueOf(item.getOut())+"    收入："+String.valueOf(item.getIn()));
        holder.onedayLv.setAdapter(adapter);

        year = item.getYear();
        month = item.getMonth();
        day = item.getDay();

        List<AccountItem> list = DBManager.getAccountListOneDayFromAccounttb(year,month,day);
        lvDatas.clear();
        lvDatas.addAll(list);

        tweakRlHeight(list, convertView);

        // 长按Item的Listener
        holder.onedayLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final AccountItem clickItem = lvDatas.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("删除提示").setMessage("确定要删除这条记录么？")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBManager.deleteItemFromAccounttbById(clickItem.getId());
                                lvDatas.remove(clickItem);
                                adapter.notifyDataSetChanged();

                                // 删除后刷新主页的View
                                ((MainActivity) context).loadDBData();
                                ((MainActivity) context).setTopTv();
                            }
                        });
                builder.create().show();
                return false;
            }
        });
        return convertView;
    }

    // 调整内部ListView的高度
    private void tweakRlHeight(List<AccountItem> list, View view) {
        int dp = 30+1+(50+1)*list.size()-2;
        final float scale = context.getResources().getDisplayMetrics().density;
        int pixels = (int) (dp * scale + 0.5f);

        rl = view.findViewById(R.id.item_dayrecord_lv_rl);
        RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, pixels);
        rel_btn.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rl.setLayoutParams(rel_btn);
    }

    // 一个Item内的所有View
    class ViewHolder{
        TextView dateTv,onedayTv;
        ListView onedayLv;

        public ViewHolder(View view){
            dateTv = view.findViewById(R.id.item_dayrecord_lv_header_tv_date);
            onedayTv = view.findViewById(R.id.item_dayrecord_lv_header_tv_inout);
            onedayLv = view.findViewById(R.id.item_dayrecord_lv);
        }
    }
}
