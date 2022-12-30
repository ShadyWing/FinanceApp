package edu.hdu.pjjfinalapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import edu.hdu.pjjfinalapp.R;

// 新建账目备注的Dialog
public class NoteDialog extends Dialog implements View.OnKeyListener {

    EditText et;
    String setinstr;
    TextView money_tv;
    OnEnsureListener onEnsureListener;
    InputMethodManager inputMethodManager;

    public interface OnEnsureListener{
        void onEnsure();
    }

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public NoteDialog(@NonNull Context context, String str) {
        super(context);
        setinstr = str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_note);

        setDialogSize();
        InitView();

    }

    private void InitView(){
        et = findViewById(R.id.dialog_note_et_note);
        money_tv = findViewById(R.id.dialog_note_tv_money);

        et.setText(setinstr);
        et.setSelection(et.getText().length());

        et.setOnKeyListener(this);

        et.requestFocus();
    }

    public String getEditText(){
        return et.getText().toString().trim();
    }

    // 设置Dialog位置
    public void setDialogSize(){

        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int)(d.getWidth());
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
        handler.sendEmptyMessageDelayed(1,100);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            if (onEnsureListener!=null) {
                onEnsureListener.onEnsure();
            }
            return true;
        }
        return false;
    }
}
