package edu.hdu.pjjfinalapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static SQLiteDatabase db;

    public static void initDB(Context context){
        DBOpenHelper helper = new DBOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    // 取得所有的收支type
    public static List<TypeItem>getTypeList(int kind){
        List<TypeItem>list = new ArrayList<>();
        String sql = "select * from typetb where kind = "+kind;
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind1 = cursor.getInt(cursor.getColumnIndex("kind"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            TypeItem typeItem = new TypeItem(id, typename, imageId, sImageId, kind);
            list.add(typeItem);
        }
        return list;
    }

    // 在accounttb里插入一条数据
    public static void insertItemToAccounttb(AccountItem item){
        ContentValues values = new ContentValues();
        values.put("typename",item.getTypename());
        values.put("sImageId",item.getsImageId());
        values.put("note",item.getNote());
        values.put("money",item.getMoney());
        values.put("time",item.getTime());
        values.put("year",item.getYear());
        values.put("month",item.getMonth());
        values.put("day",item.getDay());
        values.put("kind",item.getKind());
        db.insert("accounttb",null,values);
    }

    // 从accounttb中获取 day 这一天的所有账目
    public static List<AccountItem>getAccountListOneDayFromAccounttb(int year,int month,int day){
        List<AccountItem>list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=? and day=? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + ""});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String note = cursor.getString(cursor.getColumnIndex("note"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            AccountItem accountBean = new AccountItem(id, typename, sImageId, note, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }

    // 从accounttb中获取 month 这一月的所有账目
    public static List<AccountItem>getAccountListOneMonthFromAccounttb(int year,int month){
        List<AccountItem>list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + ""});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String note = cursor.getString(cursor.getColumnIndex("note"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            AccountItem accountItem = new AccountItem(id, typename, sImageId, note, money, time, year, month, day, kind);
            list.add(accountItem);
        }
        return list;
    }

    // 从accounttb中获取 kind类型收支 在 day 这一天的总和
    public static float getSumMoneyOneDay(int year,int month,int day,int kind){
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and day=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + "", kind + ""});
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }

    // 从accounttb中获取 kind类型收支 在 month 这一月的总和
    public static float getSumMoneyOneMonth(int year,int month,int kind){
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }

    // 从accounttb中获取 kind类型收支 在 year 这一年的总和
    public static float getSumMoneyOneYear(int year,int kind){
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", kind + ""});
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }

    // 从accounttb删除一条账目
    public static int deleteItemFromAccounttbById(int id){
        int i = db.delete("accounttb", "id=?", new String[]{id + ""});
        return i;
    }

    // 在accounttb中搜索note属性，获取所有账目的list
    public static List<AccountItem>getAccountListByRemarkFromAccounttb(String note){
        List<AccountItem>list = new ArrayList<>();
        String sql = "select * from accounttb where note like '%"+note+"%'";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String note1 = cursor.getString(cursor.getColumnIndex("note"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            AccountItem accountItem = new AccountItem(id, typename, sImageId, note1, money, time, year, month, day, kind);
            list.add(accountItem);
        }
        return list;
    }

    // 清空accounttb
    public static void deleteAllAccount(){
        String sql = "delete from accounttb";
        db.execSQL(sql);
    }
}
