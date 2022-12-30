package edu.hdu.pjjfinalapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import edu.hdu.pjjfinalapp.db.DBManager;

// 设置主页Activity
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back_iv;
    RelativeLayout usersetting_rl, cleardata_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        InitView();
    }

    private void InitView(){
        back_iv = findViewById(R.id.setting_home_iv_back);
        usersetting_rl = findViewById(R.id.setting_home_rl_usersetting);
        cleardata_rl = findViewById(R.id.setting_home_rl_cleardata);

        back_iv.setOnClickListener(this);
        usersetting_rl.setOnClickListener(this);
        cleardata_rl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_home_iv_back:
                finish();
                break;
            case R.id.setting_home_rl_usersetting:
                Intent it = new Intent(this, SettingUserActivity.class);
                startActivity(it);
                break;
            case R.id.setting_home_rl_cleardata:
                showDeleteDialog();
                break;
        }
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除提示")
                .setMessage("您确定要删除所有记录么？\n注意：删除后无法恢复，请慎重选择！")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBManager.deleteAllAccount();
                        Toast.makeText(SettingActivity.this,"删除成功！",Toast.LENGTH_SHORT).show();
                    }
                });
        builder.create().show();
    }
}
