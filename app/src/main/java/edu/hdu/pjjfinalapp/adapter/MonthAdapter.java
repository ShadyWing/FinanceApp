package edu.hdu.pjjfinalapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edu.hdu.pjjfinalapp.R;
import edu.hdu.pjjfinalapp.db.MonthItem;

public class MonthAdapter extends BaseAdapter {
    Context context;
    List<MonthItem> mDatas;
    LayoutInflater inflater;

    public MonthAdapter(Context context, List<MonthItem> mDatas){
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
            convertView = inflater.inflate(R.layout.item_history_yearlv_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        // 设置此Item在Activity中的各种View显示
        MonthItem item = mDatas.get(position);

        holder.monthTv.setText(item.getMonth() + "月");
        holder.inTv.setText(String.valueOf(item.getIn()));
        holder.outTv.setText(String.valueOf(item.getOut()));
        holder.totalTv.setText(String.valueOf(item.getTotal()));

        return convertView;
    }

    // 一个Item内的所有View
    class ViewHolder{
        TextView monthTv,inTv,outTv,totalTv;

        public ViewHolder(View view){
            monthTv = view.findViewById(R.id.item_history_yearlv_item_tv_month);
            inTv = view.findViewById(R.id.item_history_yearlv_item_tv_in);
            outTv = view.findViewById(R.id.item_history_yearlv_item_tv_out);
            totalTv = view.findViewById(R.id.item_history_yearlv_item_tv_total);
        }
    }


}
