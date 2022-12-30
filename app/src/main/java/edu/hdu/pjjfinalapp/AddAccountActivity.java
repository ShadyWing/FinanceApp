package edu.hdu.pjjfinalapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import edu.hdu.pjjfinalapp.adapter.AddAccountPagerAdapter;
import edu.hdu.pjjfinalapp.frag.IncomeFragment;
import edu.hdu.pjjfinalapp.frag.OutcomeFragment;

// 添加账目Activity
public class AddAccountActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaccount);

        tabLayout = findViewById(R.id.addaccount_tabs);
        viewPager = findViewById(R.id.addaccount_vp);

        initPager();
    }

    private void initPager()
    {
        List<Fragment> fragmentList = new ArrayList<>();

        OutcomeFragment outFrag = new OutcomeFragment();
        IncomeFragment inFrag = new IncomeFragment();
        fragmentList.add(outFrag);
        fragmentList.add(inFrag);

        AddAccountPagerAdapter pagerAdapter = new AddAccountPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addaccount_iv_back:
                finish();
                break;
        }
    }
}
