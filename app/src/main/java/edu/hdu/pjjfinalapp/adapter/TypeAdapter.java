package edu.hdu.pjjfinalapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.hdu.pjjfinalapp.db.TypeItem;
import edu.hdu.pjjfinalapp.R;

public class TypeAdapter extends BaseAdapter {
    Context context;
    List<TypeItem> mDatas;
    public int selectPos = 0;

    public TypeAdapter(Context context, List<TypeItem> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
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
        convertView = LayoutInflater.from(context).inflate(R.layout.item_addaccount_gv,parent,false);

        ImageView bgiv = convertView.findViewById(R.id.item_addaccount_gv_iv_bg);
        ImageView iciv = convertView.findViewById(R.id.item_addaccount_gv_iv_ic);
        TextView tv = convertView.findViewById(R.id.item_addaccount_gv_tv);
        TypeItem typeItem = mDatas.get(position);
        tv.setText(typeItem.getTypename());

        iciv.setImageResource(typeItem.getImageId());

        if (selectPos == position) {
            bgiv.setImageResource(R.drawable.shape_ic_background_select);
        }else{
            bgiv.setImageResource(R.drawable.shape_ic_background);
        }
        return convertView;
    }
}
