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

// 账目选择年份的Dialog
public class HistoryYearChooseDialog extends Dialog implements View.OnClickListener {

    TextView confirmBtn,cancelBtn;
    NumberPicker year_np;
    int year;
    OnEnsureListener onEnsureListener;

    public interface OnEnsureListener{
        void onEnsure(int year);
    }

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public HistoryYearChooseDialog(@NonNull Context context, int y) {
        super(context);
        year = y;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_history_yearchoose);

        setDialogSize();

        InitView();
        InitTime();
    }

    private void InitView()
    {
        confirmBtn = findViewById(R.id.dialog_history_yearchoose_tv_confirm);
        cancelBtn = findViewById(R.id.dialog_history_yearchoose_tv_cancel);
        year_np = findViewById(R.id.dialog_history_yearchoose_np_year);

        confirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    private void InitTime() {
        year_np.setMinValue(1900);
        year_np.setMaxValue(2100);
        year_np.setWrapSelectorWheel(false);
        year_np.setValue(year);
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
            case R.id.dialog_history_yearchoose_tv_confirm:
                int y = year_np.getValue();
                if (onEnsureListener!=null) {
                    onEnsureListener.onEnsure(y);
                }
                cancel();
                break;
            case R.id.dialog_history_yearchoose_tv_cancel:
                cancel();
                break;
        }
    }
}
