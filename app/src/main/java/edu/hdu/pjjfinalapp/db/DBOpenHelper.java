package edu.hdu.pjjfinalapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import edu.hdu.pjjfinalapp.R;

// 创建db和table的helper衍生类
public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(@Nullable Context context) {
        super(context,"bill.db" , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table typetb(id integer primary key autoincrement,typename varchar(10),imageId integer,sImageId integer,kind integer)";
        db.execSQL(sql);
        insertType(db);
        sql = "create table accounttb(id integer primary key autoincrement,typename varchar(10),sImageId integer,note varchar(80),money float," +
                "time varchar(60),year integer,month integer,day integer,kind integer)";
        db.execSQL(sql);
    }

    private void insertType(SQLiteDatabase db) {
        String sql = "insert into typetb (typename,imageId,sImageId,kind) values (?,?,?,?)";
        db.execSQL(sql,new Object[]{"其他", R.mipmap.ic_more_line,R.mipmap.ic_more_fill,0});
        db.execSQL(sql,new Object[]{"餐饮", R.mipmap.ic_knife_line,R.mipmap.ic_knife_fill,0});
        db.execSQL(sql,new Object[]{"购物", R.mipmap.ic_store_2_line,R.mipmap.ic_store_2_fill,0});
        db.execSQL(sql,new Object[]{"娱乐", R.mipmap.ic_headphone_line,R.mipmap.ic_headphone_fill,0});
        db.execSQL(sql,new Object[]{"医疗", R.mipmap.ic_first_aid_kit_line,R.mipmap.ic_first_aid_kit_fill,0});
        db.execSQL(sql,new Object[]{"日用", R.mipmap.ic_file_paper_2_line,R.mipmap.ic_file_paper_2_fill,0});
        db.execSQL(sql,new Object[]{"交通", R.mipmap.ic_bus_line,R.mipmap.ic_bus_fill,0});
        db.execSQL(sql,new Object[]{"住房", R.mipmap.ic_home_8_line,R.mipmap.ic_home_8_fill,0});
        db.execSQL(sql,new Object[]{"孩子", R.mipmap.ic_emotion_happy_line,R.mipmap.ic_emotion_happy_fill,0});
        db.execSQL(sql,new Object[]{"长辈", R.mipmap.ic_parent_line,R.mipmap.ic_parent_fill,0});
        db.execSQL(sql,new Object[]{"社交", R.mipmap.ic_chat_1_line,R.mipmap.ic_chat_1_fill,0});
        db.execSQL(sql,new Object[]{"旅行", R.mipmap.ic_earth_line,R.mipmap.ic_earth_fill,0});
        db.execSQL(sql,new Object[]{"数码", R.mipmap.ic_camera_line,R.mipmap.ic_camera_fill,0});
        db.execSQL(sql,new Object[]{"汽车", R.mipmap.ic_car_line,R.mipmap.ic_car_fill,0});
        db.execSQL(sql,new Object[]{"学习", R.mipmap.ic_book_2_line,R.mipmap.ic_book_2_fill,0});
        db.execSQL(sql,new Object[]{"办公", R.mipmap.ic_briefcase_2_line,R.mipmap.ic_briefcase_2_fill,0});
        db.execSQL(sql,new Object[]{"快递", R.mipmap.ic_truck_line,R.mipmap.ic_truck_fill,0});
        db.execSQL(sql,new Object[]{"维修", R.mipmap.ic_hammer_line,R.mipmap.ic_hammer_fill,0});

        db.execSQL(sql,new Object[]{"其他", R.mipmap.ic_more_line,R.mipmap.ic_more_fill,1});
        db.execSQL(sql,new Object[]{"工资", R.mipmap.ic_bank_card_line,R.mipmap.ic_bank_card_fill,1});
        db.execSQL(sql,new Object[]{"借入", R.mipmap.ic_money_cny_box_line,R.mipmap.ic_money_cny_box_fill,1});
        db.execSQL(sql,new Object[]{"理财", R.mipmap.ic_line_chart_line,R.mipmap.ic_line_chart_fill,1});
        db.execSQL(sql,new Object[]{"礼金", R.mipmap.ic_coin_line,R.mipmap.ic_coin_fill,1});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
