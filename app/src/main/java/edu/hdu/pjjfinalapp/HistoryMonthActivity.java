package edu.hdu.pjjfinalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.hdu.pjjfinalapp.adapter.SearchAdapter;
import edu.hdu.pjjfinalapp.db.AccountItem;
import edu.hdu.pjjfinalapp.db.DBManager;

// 月份账单Activity
public class HistoryMonthActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back_iv;
    TextView total_tv,out_tv,in_tv,title_tv;
    ListView item_lv;

    int year,month;

    List<AccountItem> lvDatas;
    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_month);

        InitView();
        InitTime();

        lvDatas = new ArrayList<>();
        adapter = new SearchAdapter(this, lvDatas);
        item_lv.setAdapter(adapter);

        LoadDB();
        SetHeaderTv();
    }

    private void InitView(){
        back_iv = findViewById(R.id.history_month_iv_back);
        total_tv = findViewById(R.id.history_month_tv_total);
        out_tv = findViewById(R.id.history_month_tv_out);
        in_tv = findViewById(R.id.history_month_tv_in);
        item_lv = findViewById(R.id.history_lv);
        title_tv = findViewById(R.id.history_month_tv_title);

        back_iv.setOnClickListener(this);
    }

    private void InitTime(){
        Bundle b = getIntent().getExtras();
        if(b != null)
        {
            year = b.getInt("year");
            month = b.getInt("month");
        }
        title_tv.setText(year + "年" + month + "月账单");
    }

    private void LoadDB(){
        List<AccountItem> list = DBManager.getAccountListOneMonthFromAccounttb(year, month);
        lvDatas.clear();
        lvDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void SetHeaderTv(){
        float in = DBManager.getSumMoneyOneMonth(year,month,1);
        float out = DBManager.getSumMoneyOneMonth(year,month,0);
        float total = in - out;
        in_tv.setText(String.valueOf(in));
        out_tv.setText(String.valueOf(out));
        total_tv.setText(String.valueOf(total));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.history_month_iv_back:
                finish();
                break;
        }
    }
}
