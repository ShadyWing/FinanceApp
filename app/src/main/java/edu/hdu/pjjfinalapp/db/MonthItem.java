package edu.hdu.pjjfinalapp.db;

// 单月记录的数据
public class MonthItem {
    int month;
    float in;
    float out;
    float total;

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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public MonthItem(int m, float i, float o, float t){
        this.month = m;
        this.in = i;
        this.out = o;
        this.total = t;
    }
}
