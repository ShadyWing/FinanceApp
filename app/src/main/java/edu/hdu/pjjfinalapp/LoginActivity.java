package edu.hdu.pjjfinalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// 登陆Activity
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button login_btn, changepwd_btn;
    EditText account_et, pwd_et;
    String correct_account;
    String correct_pwd;

    String account, pwd;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        editor = preferences.edit();

        correct_account = "123";
        correct_pwd = preferences.getString("pwd","456");

        if(preferences.getBoolean("savedlogin",false)){
            Intent it =new Intent(getApplicationContext(),MainActivity.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(it);
        }

        account_et =findViewById(R.id.login_et_account);
        pwd_et =findViewById(R.id.login_et_pwd);

        login_btn = findViewById(R.id.login_btn_login);
        changepwd_btn = findViewById(R.id.login_btn_changepwd);

        login_btn.setOnClickListener(this);
        changepwd_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn_login:
                account = account_et.getText().toString();
                pwd = pwd_et.getText().toString();
                if(account.equals("123")&&pwd.equals(correct_pwd)) {
                    editor.putBoolean("savedlogin",true);
                    editor.putString("pwd",pwd);
                    editor.commit();

                    Intent it =new Intent(getApplicationContext(),MainActivity.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(it);
                    Toast.makeText(getApplicationContext(), "登录成功！", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_btn_changepwd:
                Intent it = new Intent(this, ChangePwdActivity.class);
                startActivity(it);
                break;
        }
    }
}
