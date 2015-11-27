package com.neusoft.eenie.order.controller.main.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neusoft.eenie.bmoblibrary.bmob.BombAPI;
import com.neusoft.eenie.bmoblibrary.bmob.bean.UserBean;
import com.neusoft.eenie.order.R;
import com.neusoft.eenie.order.controller.base.BaseApplication;
import com.neusoft.eenie.order.controller.base.BaseV4Fragment;
import com.neusoft.eenie.order.controller.login.LoginActivity;
import com.neusoft.eenie.order.controller.main.UserInfoActivity;
import com.neusoft.eenie.order.view.AddFoodCategoryActivity;
import com.neusoft.eenie.order.view.addFoodActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseV4Fragment implements View.OnClickListener {


    View contentView;
    Button btnLogin;
    Button btnLogout;
    TextView textName;

    ImageView imageHead;

    UserBean user;

    BombAPI bombAPI;

    RelativeLayout relativeAdd;
    RelativeLayout relativeFood;


    public static final int USERINFO_REQUESTCODE = 100;
    public static final int USERINFO_REQUESTCODE_CHANGE = 101;



    public MeFragment() {


        bombAPI = BaseApplication.getBomAPI();

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_me, container, false);

        findView(contentView);



        btnLogout.setOnClickListener(this);
        imageHead.setOnClickListener(this);
        relativeAdd.setOnClickListener(this);
        relativeFood.setOnClickListener(this);


        loadUserInfo();
        return contentView;
    }


    public void findView(View rootView) {
        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);
        textName = (TextView) rootView.findViewById(R.id.textName);
        imageHead = (ImageView) rootView.findViewById(R.id.imageHead);
        relativeAdd = (RelativeLayout) rootView.findViewById(R.id.relativeAdd);
        relativeFood = (RelativeLayout) rootView.findViewById(R.id.relativeFood);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relativeAdd:
//                startActivity(new Intent(getContext(), addFoodActivity.class));

                break;
            case R.id.relativeFood:
//                startActivity(new Intent(getContext(), AddFoodCategoryActivity.class));

                break;
            case R.id.imageHead:

                if (user != null) {
                    Intent userInfoIntent = new Intent(getContext(), UserInfoActivity.class);
                    userInfoIntent.putExtra("userinfo", user);
                    startActivityForResult(userInfoIntent, USERINFO_REQUESTCODE);
                } else {
                    Intent userLoginIntent = new Intent(getContext(), LoginActivity.class);
                    userLoginIntent.putExtra("login", user);
                    startActivityForResult(userLoginIntent, USERINFO_REQUESTCODE);
                }

                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == LoginActivity.LOGIN_REQUESTCODE && resultCode == LoginActivity.LOGIN_RESULTCODE) {

            Log.e("onActivityResult", "onActivityResult");
            loadUserInfo();
        }else if (requestCode == USERINFO_REQUESTCODE && resultCode == USERINFO_REQUESTCODE_CHANGE) {
            //注销用户事件处理
            loadUserInfo();

        }

    }


    public void loadUserInfo() {

        user = bombAPI.getCurrentUser();
        textName.setText("");
        if (user == null) {
            return;
        }
        textName.setText(user.getUsername());
        Log.e("Login Successful", "Successful  " + user.getUsername());
    }


}
