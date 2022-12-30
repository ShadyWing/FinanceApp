package edu.hdu.pjjfinalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.hdu.pjjfinalapp.adapter.DayRecordAdapter;
import edu.hdu.pjjfinalapp.db.AccountItem;
import edu.hdu.pjjfinalapp.db.DBManager;
import edu.hdu.pjjfinalapp.db.DayRecordItem;

// 主页Activity
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton add_btn;
    ImageView search_iv, logo_iv;

    TextView out_tv, in_tv, year_tv, month_tv;
    ListView days_lv;
    LinearLayout more_ll;

    List<DayRecordItem> lvDatas;
    DayRecordAdapter adapter;

    int year,month,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitView();
        InitTime();

        lvDatas = new ArrayList<>();
        adapter = new DayRecordAdapter(this, lvDatas);
        days_lv.setAdapter(adapter);
    }

    private void InitView() {
        out_tv = findViewById(R.id.main_tv_top_out);
        in_tv = findViewById(R.id.main_tv_top_in);
        year_tv = findViewById(R.id.main_tv_top_year);
        month_tv = findViewById(R.id.main_tv_top_month);

        days_lv = findViewById(R.id.main_days_lv);

        add_btn = findViewById(R.id.main_btn_add);
        search_iv = findViewById(R.id.main_btn_search);
        logo_iv = findViewById(R.id.main_iv_logo);
        more_ll = findViewById(R.id.main_ll_more);

        add_btn.setOnClickListener(this);
        search_iv.setOnClickListener(this);
        logo_iv.setOnClickListener(this);
        more_ll.setOnClickListener(this);
    }

    private void InitTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);

        year_tv.setText(year + " 年");
        month_tv.setText(String.valueOf(month));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDBData();
        setTopTv();
    }

    public void loadDBData() {
        lvDatas.clear();
        for(int i = 31; i>0; i--){
            List<AccountItem> l;

            l = DBManager.getAccountListOneDayFromAccounttb(year, month, i);
            float in = DBManager.getSumMoneyOneDay(year, month, i, 1);
            float out = DBManager.getSumMoneyOneDay(year, month, i, 0);

            if(!l.isEmpty()) {
                DayRecordItem item = new DayRecordItem(year, month, i, in, out);
                lvDatas.add(item);
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void setTopTv() {
        float incomeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 1);
        float outcomeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 0);
        in_tv.setText(String.valueOf(incomeOneMonth));
        out_tv.setText(String.valueOf(outcomeOneMonth));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.main_btn_add:
                Intent it = new Intent(this, AddAccountActivity.class);
                startActivity(it);
                break;
            case R.id.main_btn_search:
                Intent it1 = new Intent(this, SearchActivity.class);
                startActivity(it1);
                break;
            case R.id.main_ll_more:
                Intent it2 = new Intent(this, HistoryYearActivity.class);
                startActivity(it2);
                break;
            case R.id.main_iv_logo:
                Intent it3 = new Intent(this, SettingActivity.class);
                startActivity(it3);
                break;
        }
    }
}
