package com.neusoft.eenie.bmoblibrary.base;

import android.app.Application;

import cn.bmob.v3.Bmob;


/**
 * Created by Eenie on 2015/11/14.
 *
 */
public class BmobApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "a2a838a45b6831cb05fce562d21f3d50");
    }
}
