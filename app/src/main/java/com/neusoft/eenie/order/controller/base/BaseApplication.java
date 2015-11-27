package com.neusoft.eenie.order.controller.base;

import android.content.Context;

import com.neusoft.eenie.bmoblibrary.bmob.BombAPI;
import com.neusoft.eenie.bmoblibrary.base.BmobApplication;
import com.neusoft.eenie.bmoblibrary.bmob.bean.FoodBean;
import com.neusoft.eenie.order.controller.bean.OrderBean;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by Eenie on 2015/11/18.
 *
 */
public class BaseApplication extends BmobApplication {

    public static BombAPI bombAPI;
    public static Context context;
    public static BmobUser userBean;
    public static List<FoodBean> orderBeanListData;

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化bmob
        context = getApplicationContext();
        bombAPI = new BombAPI(context);
        userBean = getCurrentUser();

        //初始化xUtils
        x.Ext.init(this);
        x.Ext.setDebug(true);
        orderBeanListData = new ArrayList<>();

    }


    //获取用户
    public static BmobUser getCurrentUser() {
        userBean = BmobUser.getCurrentUser(context);
        return userBean;
    }

    //用户注销
    public static void userLogout() {
        BmobUser.logOut(context);
    }

    //获取接口
    public static BombAPI getBomAPI() {
        return bombAPI;
    }





}
