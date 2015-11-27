package com.neusoft.eenie.order.controller.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.neusoft.eenie.bmoblibrary.bmob.BombAPI;
import com.neusoft.eenie.bmoblibrary.bmob.bean.UserBean;
import com.neusoft.eenie.order.R;
import com.neusoft.eenie.order.controller.base.BaseApplication;
import com.neusoft.eenie.order.utiles.mUtiles;

import cn.bmob.v3.listener.SaveListener;

public class RegsiterActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar menuToobar;
    EditText editAccountName, editAccountPwd;
    Button btnReg;
    BombAPI bombAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsiter);

        bombAPI = ((BaseApplication) getApplication()).bombAPI;

        findView();
        initToolbar();
        btnReg.setOnClickListener(this);

    }

    private void findView() {

        menuToobar = (Toolbar) findViewById(R.id.menuToobar);
        editAccountName = (EditText) findViewById(R.id.editAccountName);
        editAccountPwd = (EditText) findViewById(R.id.editAccountPwd);
        btnReg = (Button) findViewById(R.id.btnReg);
    }


    private void initToolbar() {
        menuToobar.setTitle("注册");
        menuToobar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        setSupportActionBar(menuToobar);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnReg:
                mUtiles.showDialog(RegsiterActivity.this, "正在注册,请稍后...").show();
                String name = editAccountName.getText().toString();
                String password = editAccountPwd.getText().toString();
                userReg(name, password);
                break;
        }
    }


    private void userReg(String name, String pwd) {
        final UserBean user = new UserBean();
        user.setUsername(name);
        user.setPassword(pwd);
        bombAPI.userRegister(user, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Log.e("SaveListener", "onSuccess");
                        mUtiles.dismissProgressDialog();
                        Intent resultIntent = getIntent();
                        resultIntent.putExtra(LoginActivity.KEY_USER, user);
                        RegsiterActivity.this.setResult(LoginActivity.REGISTER_RESULTCODE, resultIntent);
                        Toast.makeText(RegsiterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.e("SaveListener", "onSuccess");
                        mUtiles.dismissProgressDialog();
                        Toast.makeText(RegsiterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }
                }
        );


    }


}
