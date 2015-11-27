package com.neusoft.eenie.order.controller.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.neusoft.eenie.bmoblibrary.bmob.BombAPI;
import com.neusoft.eenie.bmoblibrary.bmob.bean.UserBean;
import com.neusoft.eenie.order.R;
import com.neusoft.eenie.order.controller.main.fragments.MeFragment;
import com.neusoft.eenie.order.utiles.mUtiles;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    public final String TAG = "LoginActivity";
    public static final String KEY_USER = "userBean";

    public static final int REGISTER_REQUESTCODE = 100;
    public static final int REGISTER_RESULTCODE = 101;
    public static final int LOGIN_REQUESTCODE = 100;
    public static final int LOGIN_RESULTCODE = 101;

    EditText editAccountName, editAccountPwd;

    Button btnLogin;

    Toolbar menuToobar;


    BombAPI bombAPI;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        findView();
        initToolbar();
        initOBJ();


        btnLogin.setOnClickListener(this);
    }


    private void initOBJ() {
        bombAPI = new BombAPI(context);


    }

    private void findView() {

        menuToobar = (Toolbar) findViewById(R.id.menuToobar);
        editAccountName = (EditText) findViewById(R.id.editAccountName);
        editAccountPwd = (EditText) findViewById(R.id.editAccountPwd);
        btnLogin = (Button) findViewById(R.id.btnLogin);

    }

    private void initToolbar() {


        menuToobar.setTitle("登录");
        menuToobar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        setSupportActionBar(menuToobar);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menuReg:
                Intent regIntent = new Intent(LoginActivity.this, RegsiterActivity.class);
                startActivityForResult(regIntent, REGISTER_REQUESTCODE);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnLogin:

                mUtiles.showDialog(context, "正在登录,请稍后...").show();
                String name = editAccountName.getText().toString();
                String password = editAccountPwd.getText().toString();
                userLogin(name, password);
                break;
        }
    }


    private void userLogin(String name, String password) {
        bombAPI.userLogin(name, password, new LogInListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                mUtiles.dismissProgressDialog();
                if (userBean != null) {

                    Log.e("userLogin", userBean.getUsername());
                    setResult(MeFragment.USERINFO_REQUESTCODE_CHANGE);
                    finish();

                } else {
                    Log.e("userLogin", "fail");
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //判断请求码、响应码处理事件
        if (requestCode == REGISTER_REQUESTCODE && resultCode == REGISTER_RESULTCODE) {
            UserBean user = (UserBean) data.getSerializableExtra(KEY_USER);
            editAccountName.setText(user.getUsername());

            if (data != null) {

            }

        }
    }
}
