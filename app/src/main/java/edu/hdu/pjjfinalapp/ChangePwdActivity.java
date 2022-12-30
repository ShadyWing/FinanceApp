package edu.hdu.pjjfinalapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

// 修改密码Activity
public class ChangePwdActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back_iv;
    Button changepwd_btn, setdefaultpwd_btn;
    EditText oldpwd_et, newpwd_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        InitView();
    }

    private void InitView(){
        oldpwd_et = findViewById(R.id.setting_changepwd_et_oldpwd);
        newpwd_et = findViewById(R.id.setting_changepwd_et_newpwd);

        back_iv = findViewById(R.id.setting_changepwd_iv_back);
        changepwd_btn = findViewById(R.id.setting_changepwd_btn_changepwd);
        setdefaultpwd_btn = findViewById(R.id.setting_changepwd_btn_setdefaultpwd);

        back_iv.setOnClickListener(this);
        changepwd_btn.setOnClickListener(this);
        setdefaultpwd_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_changepwd_iv_back:
                finish();
                break;
            case R.id.setting_changepwd_btn_changepwd:
                showChangePwdDialog();
                break;
            case R.id.setting_changepwd_btn_setdefaultpwd:
                setDefaultPwdDialog();
                break;
        }
    }

    private void showChangePwdDialog() {
        final SharedPreferences preferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        if(oldpwd_et.getText().toString().isEmpty() || newpwd_et.getText().toString().isEmpty()){
            return;
        }
        if(!(oldpwd_et.getText().toString().equals(preferences.getString("pwd",null)))){
            Toast.makeText(this,"原密码错误",Toast.LENGTH_LONG).show();
            return;
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("修改提示")
                .setMessage("您确定要修改密码吗？\n\n修改密码后需要重新登录")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newpwd = newpwd_et.getText().toString();
                        editor.putBoolean("savedlogin", false);
                        editor.putString("pwd", newpwd);
                        editor.commit();

                        Intent it = new Intent(getApplicationContext(), LoginActivity.class);
                        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(it);
                    }
                });
            builder.create().show();
        }
    }

    private void setDefaultPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("重置提示")
            .setMessage("您确定要重置密码为 456 吗？\n\n重置密码后需要重新登录")
            .setNegativeButton("取消", null)
            .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences preferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("savedlogin", false);
                    editor.putString("pwd", "456");
                    editor.commit();

                    Intent it = new Intent(getApplicationContext(), LoginActivity.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(it);
                }
            });
        builder.create().show();
    }
}
