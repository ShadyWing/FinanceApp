package edu.hdu.pjjfinalapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import edu.hdu.pjjfinalapp.R;
import edu.hdu.pjjfinalapp.db.AccountItem;

public class SearchAdapter extends BaseAdapter {
    Context context;
    List<AccountItem> mDatas;
    LayoutInflater inflater;
    int year,month,day;

    public SearchAdapter(Context context, List<AccountItem> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
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
            convertView = inflater.inflate(R.layout.item_search_lv,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        // 设置此Item在Activity中的各种View显示
        AccountItem item = mDatas.get(position);

        holder.typeIv.setImageResource(item.getsImageId());
        holder.typeTv.setText(item.getTypename());
        holder.noteTv.setText(item.getNote());

        if(item.getKind() == 0)
            holder.moneyTv.setText("-"+item.getMoney());
        else
            holder.moneyTv.setText(String.valueOf(item.getMoney()));

        if (item.getYear()==year&&item.getMonth()==month&&item.getDay()==day+1) {
            String time = item.getTime().split(" ")[1];
            holder.timeTv.setText("今天 "+time);
        }
        else {
            holder.timeTv.setText(item.getTime());
        }
        return convertView;
    }

    // 一个Item内的所有View
    class ViewHolder{
        ImageView typeIv;
        TextView typeTv,noteTv,timeTv,moneyTv;

        public ViewHolder(View view){
            typeIv = view.findViewById(R.id.item_search_lv_iv);
            typeTv = view.findViewById(R.id.item_search_lv_tv_title);
            timeTv = view.findViewById(R.id.item_search_lv_tv_time);
            noteTv = view.findViewById(R.id.item_search_lv_tv_note);
            moneyTv = view.findViewById(R.id.item_search_lv_tv_money);
        }
    }
}