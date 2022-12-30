package edu.hdu.pjjfinalapp.frag;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.hdu.pjjfinalapp.adapter.TypeAdapter;
import edu.hdu.pjjfinalapp.db.AccountItem;
import edu.hdu.pjjfinalapp.R;
import edu.hdu.pjjfinalapp.db.TypeItem;
import edu.hdu.pjjfinalapp.utils.KeyboardUtils;
import edu.hdu.pjjfinalapp.utils.NoteDialog;
import edu.hdu.pjjfinalapp.utils.SelectTimeDialog;

// 添加账目页面的fragment
public abstract class BaseAddAccountFragment extends Fragment implements View.OnClickListener{

    KeyboardView keyboardView;
    KeyboardUtils boardUtils;
    EditText moneyEt;
    TextView noteTv,timeTv;
    GridView typeGv;
    List<TypeItem> typeList;
    TypeAdapter adapter;
    AccountItem accountItem;
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountItem = new AccountItem();
        accountItem.setTypename("其他");
        accountItem.setsImageId(R.mipmap.ic_more_fill);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_addaccount, container, false);

        initView(view);
        setInitTime();

        loadDataToGV();
        setGVListener();
        return view;
    }

    // 初始化View
    private void initView(View view) {
        keyboardView = view.findViewById(R.id.frag_addaccount_keyboard);
        moneyEt = view.findViewById(R.id.dialog_note_tv_money);
        typeGv = view.findViewById(R.id.frag_addaccount_gv);
        noteTv = view.findViewById(R.id.frag_addaccount_tv_note);
        timeTv = view.findViewById(R.id.frag_addaccount_tv_time);
        noteTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);

        boardUtils = new KeyboardUtils(keyboardView, moneyEt);
        boardUtils.showKeyboard();

        // 设置虚拟键盘监听
        boardUtils.setOnEnsureListener(new KeyboardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                String moneyStr = moneyEt.getText().toString();
                if (TextUtils.isEmpty(moneyStr)||moneyStr.equals("0")) {
                    getActivity().finish();
                    return;
                }
                float money = Float.parseFloat(moneyStr);
                accountItem.setMoney(money);
                saveAccountToDB();
                getActivity().finish();
            }
        });
    }

    // 设置fragment初始view的显示
    private void setInitTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String time = sdf.format(date);
        accountItem.setTime(time);

        sdf = new SimpleDateFormat("yyyy/MM/dd");
        time = sdf.format(date);
        timeTv.setText(time);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        accountItem.setYear(year);
        accountItem.setMonth(month);
        accountItem.setDay(day);
    }

    // 设置fragment中GridView的点击监听
    private void setGVListener() {
        typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            adapter.selectPos = position;
            adapter.notifyDataSetInvalidated();
            TypeItem typeItem = typeList.get(position);
            String typename = typeItem.getTypename();
            accountItem.setTypename(typename);
            int simageId = typeItem.getSimageId();
            accountItem.setsImageId(simageId);
            }
        });
    }

    // 加载fragment中的GridView的元素
    public void loadDataToGV() {
        typeList = new ArrayList<>();
        adapter = new TypeAdapter(getContext(), typeList);
        typeGv.setAdapter(adapter);
    }

    public abstract void saveAccountToDB();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_addaccount_tv_time:
                showTimeDialog();
                break;
            case R.id.frag_addaccount_tv_note:
                showNoteDialog();
                break;
        }
    }

    private void showTimeDialog() {
        String[] ymd = timeTv.getText().toString().split("/");
        int y = Integer.valueOf(ymd[0]);
        int m = Integer.valueOf(ymd[1]);
        int d = Integer.valueOf(ymd[2]);
        SelectTimeDialog dialog = new SelectTimeDialog(getContext(), y, m, d);
        dialog.show();
        dialog.setOnEnsureListener(new SelectTimeDialog.OnEnsureListener() {
            @Override
            public void onEnsure(String time, int year, int month, int day) {
                timeTv.setText(time);

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat(" HH:mm");
                time += sdf.format(date);
                accountItem.setTime(time);

                accountItem.setYear(year);
                accountItem.setMonth(month);
                accountItem.setDay(day);
            }
        });
    }

    public void showNoteDialog(){
        final NoteDialog dialog;
        if(noteTv.getText().toString().isEmpty())
            dialog = new NoteDialog(getContext(), "");
        else
            dialog = new NoteDialog(getContext(), noteTv.getText().toString().trim());

        dialog.show();
        dialog.setOnEnsureListener(new NoteDialog.OnEnsureListener() {
            @Override
            public void onEnsure() {
                String msg = dialog.getEditText();
                noteTv.setText(msg);
                accountItem.setNote(msg);
                dialog.cancel();
            }
        });
    }
}
