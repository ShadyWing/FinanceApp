package edu.hdu.pjjfinalapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

// 账户设置Activity
public class SettingUserActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back_iv;
    RelativeLayout changepwd_rl, logout_rl;
    TextView account_tv, pwd_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_user);

        InitView();
    }

    private void InitView(){
        account_tv = findViewById(R.id.setting_user_tv_account);
        pwd_tv = findViewById(R.id.setting_user_tv_pwd);

        back_iv = findViewById(R.id.setting_user_iv_back);
        changepwd_rl = findViewById(R.id.setting_user_rl_changepwd);
        logout_rl = findViewById(R.id.setting_user_rl_logout);

        back_iv.setOnClickListener(this);
        changepwd_rl.setOnClickListener(this);
        logout_rl.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateUserinfo();
    }

    private void UpdateUserinfo(){
        SharedPreferences preferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        pwd_tv.setText(preferences.getString("pwd",null));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_user_iv_back:
                finish();
                break;
            case R.id.setting_user_rl_changepwd:
                Intent it = new Intent(this, ChangePwdActivity.class);
                startActivity(it);
                break;
            case R.id.setting_user_rl_logout:
                showLogoutDialog();
                break;
        }
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("退出后不会删除任何历史数据，下次登录依然可以使用本账号")
            .setNegativeButton("取消", null)
            .setPositiveButton("退出登录",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences preferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("savedlogin", false);
                    editor.commit();

                    Intent it = new Intent(getApplicationContext(), LoginActivity.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(it);
                }
            });
        builder.create().show();
    }
}
