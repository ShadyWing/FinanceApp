package edu.hdu.pjjfinalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.hdu.pjjfinalapp.adapter.SearchAdapter;
import edu.hdu.pjjfinalapp.db.AccountItem;
import edu.hdu.pjjfinalapp.db.DBManager;

// 搜索Activity
public class SearchActivity extends AppCompatActivity implements View.OnKeyListener {

    ListView search_lv;
    EditText search_et;
    TextView empty_tv;
    List<AccountItem> mDatas;
    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        mDatas = new ArrayList<>();
        adapter = new SearchAdapter(this,mDatas);
        
        search_lv.setAdapter(adapter);
        search_lv.setEmptyView(empty_tv);

        if(search_et.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void initView() {
        search_et = findViewById(R.id.search_et);
        search_lv = findViewById(R.id.search_lv);
        empty_tv = findViewById(R.id.search_tv_empty);

        search_et.setOnKeyListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_iv_back:
                finish();
                break;
            case R.id.search_iv_sh:
                String msg = search_et.getText().toString().trim();
                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(this,"输入内容不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }

                List<AccountItem> list = DBManager.getAccountListByRemarkFromAccounttb(msg);
                mDatas.clear();
                mDatas.addAll(list);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            String msg = search_et.getText().toString().trim();

            if (TextUtils.isEmpty(msg)) {
                return true;
            }
            List<AccountItem> list = DBManager.getAccountListByRemarkFromAccounttb(msg);
            mDatas.clear();
            mDatas.addAll(list);
            adapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }
}
