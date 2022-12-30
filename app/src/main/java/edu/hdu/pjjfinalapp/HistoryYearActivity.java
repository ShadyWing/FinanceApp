package edu.hdu.pjjfinalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.hdu.pjjfinalapp.adapter.MonthAdapter;
import edu.hdu.pjjfinalapp.db.DBManager;
import edu.hdu.pjjfinalapp.db.MonthItem;
import edu.hdu.pjjfinalapp.utils.HistoryYearChooseDialog;

// 年份账单Activity
public class HistoryYearActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    LinearLayout chooseyear_ll;
    ImageView back_iv;
    TextView year_tv, total_tv, in_tv, out_tv;
    ListView month_lv;
    View headerView;

    List<MonthItem> lvDatas;
    MonthAdapter adapter;
    int year,month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_year);

        context = this;

        InitView();
        InitTime();

        AddHeaderView();
        lvDatas = new ArrayList<>();
        adapter = new MonthAdapter(this, lvDatas);
        month_lv.setAdapter(adapter);
    }

    private void InitView(){
        chooseyear_ll = findViewById(R.id.history_year_ll_chooseyear);
        back_iv = findViewById(R.id.history_year_iv_back);
        year_tv = findViewById(R.id.history_year_tv_year);

        month_lv = findViewById(R.id.history_year_lv);

        chooseyear_ll.setOnClickListener(this);
        back_iv.setOnClickListener(this);

        SetOnItemClickListener();
    }

    private void InitTime(){
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;

        year_tv.setText(year + "年");
    }

    private void AddHeaderView(){
        headerView = getLayoutInflater().inflate(R.layout.item_history_yearlv_top, null);
        month_lv.addHeaderView(headerView,null,false);

        total_tv = headerView.findViewById(R.id.history_year_tv_total);
        in_tv = headerView.findViewById(R.id.history_year_tv_in);
        out_tv = headerView.findViewById(R.id.history_year_tv_out);
    }

    private void SetOnItemClickListener(){
        month_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MonthItem clickItem = lvDatas.get(position-1);
                Intent it = new Intent(context,HistoryMonthActivity.class);
                Bundle b = new Bundle();
                b.putInt("year",year);
                b.putInt("month",clickItem.getMonth());
                it.putExtras(b);
                startActivity(it);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        LoadMonthData();
        SetHeaderTv();
    }

    private void LoadMonthData(){
        List<MonthItem> list = new ArrayList<>();
        for(int i = 12; i>0; i--){
            float inMoneyOneMonth = DBManager.getSumMoneyOneMonth(year, i, 1);
            float outMoneyOneMonth = DBManager.getSumMoneyOneMonth(year, i, 0);
            float totalMoneyOneMonth = inMoneyOneMonth - outMoneyOneMonth;
            MonthItem item = new MonthItem(i,inMoneyOneMonth,outMoneyOneMonth,totalMoneyOneMonth);
            list.add(item);
        }
        lvDatas.clear();
        lvDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void SetHeaderTv()
    {
        float inThisYear = DBManager.getSumMoneyOneYear(year,1);
        float outThisYear = DBManager.getSumMoneyOneYear(year,0);
        float totalThisYear = inThisYear - outThisYear;
        in_tv.setText(String.valueOf(inThisYear));
        out_tv.setText(String.valueOf(outThisYear));
        total_tv.setText(String.valueOf(totalThisYear));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.history_year_ll_chooseyear:
            case R.id.history_year_iv_calendar:
                showYearChooseDialog();
                break;
            case R.id.history_year_iv_back:
                finish();
                break;
        }
    }

    private void showYearChooseDialog(){
        HistoryYearChooseDialog dialog = new HistoryYearChooseDialog(context, year);
        dialog.show();
        dialog.setOnEnsureListener(new HistoryYearChooseDialog.OnEnsureListener() {
            @Override
            public void onEnsure(int y) {
                year = y;
                year_tv.setText(y + "年");
                LoadMonthData();
                SetHeaderTv();
            }
        });
    }
}
