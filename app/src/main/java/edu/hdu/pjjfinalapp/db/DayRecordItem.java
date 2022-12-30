package edu.hdu.pjjfinalapp.db;

// 单日账目列表记录的数据
public class DayRecordItem {
    int year;
    int month;
    int day;
    String date;
    float in;
    float out;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getIn() {
        return in;
    }

    public void setIn(float in) {
        this.in = in;
    }

    public float getOut() {
        return out;
    }

    public void setOut(float out) {
        this.out = out;
    }

    public DayRecordItem(){}

    public DayRecordItem(int y, int m, int d, float i, float o){
        this.year = y;
        this.month = m;
        this.day = d;
        this.date = m + "月" + d + "日";
        this.in = i;
        this.out = o;
    }
}
