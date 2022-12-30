package edu.hdu.pjjfinalapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;

import edu.hdu.pjjfinalapp.R;

// 新建账目选择时间的Dialog
public class SelectTimeDialog extends Dialog implements View.OnClickListener{

    TextView confirmBtn,cancelBtn;
    NumberPicker year_np, month_np, day_np;
    int year, month, day;
    OnEnsureListener onEnsureListener;

    public interface OnEnsureListener{
        void onEnsure(String time, int year, int month, int day);
    }

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public SelectTimeDialog(@NonNull Context context, int y, int m, int d) {
        super(context);
        year = y;
        month = m;
        day = d;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time);

        setDialogSize();

        InitView();
        InitTime();
    }

    private void InitView()
    {
        confirmBtn = findViewById(R.id.dialog_time_tv_confirm);
        cancelBtn = findViewById(R.id.dialog_time_tv_cancel);
        year_np = findViewById(R.id.dialog_time_np_year);
        month_np =findViewById(R.id.dialog_time_np_month);
        day_np = findViewById(R.id.dialog_time_np_day);

        month_np.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                UpdateTimeRange();
            }
        });
        confirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    private void InitTime() {

        year_np.setMinValue(1900);
        year_np.setMaxValue(2100);
        year_np.setWrapSelectorWheel(false);
        year_np.setValue(year);

        month_np.setMinValue(1);
        month_np.setMaxValue(12);
        month_np.setValue(month);

        day_np.setMinValue(1);
        switch(month_np.getValue()){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day_np.setMaxValue(31);
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                day_np.setMaxValue(30);
                break;
            case 2:
                day_np.setMaxValue(29);
                break;
        }
        day_np.setValue(day);
    }

    private void UpdateTimeRange()
    {
        switch(month_np.getValue()){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day_np.setMaxValue(31);
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                day_np.setMaxValue(30);
                break;
            case 2:
                day_np.setMaxValue(29);
                break;
        }
    }

    // 设置Dialog位置
    public void setDialogSize() {

        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int)(d.getWidth());
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_time_tv_confirm:
                int y = year_np.getValue();
                int m = month_np.getValue();
                int d = day_np.getValue();

                String monthStr = String.valueOf(m);
                if (month<10){
                    monthStr = "0"+m;
                }
                String dayStr = String.valueOf(d);
                if (d<10){
                    dayStr="0"+d;
                }

                String timeFormat = y+"/"+monthStr+"/"+dayStr;
                if (onEnsureListener!=null) {
                    onEnsureListener.onEnsure(timeFormat,y,m,d);
                }
                cancel();
                break;
            case R.id.dialog_time_tv_cancel:
                cancel();
                break;
        }
    }
}
