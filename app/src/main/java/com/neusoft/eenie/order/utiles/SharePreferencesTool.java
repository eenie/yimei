package com.neusoft.eenie.order.utiles;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Eenie on 2015/11/3.
 * SharePreferences的工具类
 *
 */
public class SharePreferencesTool {

    Context context;
    SharedPreferences sp;


    public final String KEY_FIRST = "first";
    public final String KEY_OPEN = "open";


   public SharePreferencesTool(Context context) {
        this.context = context;
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }




public boolean getBooleanValue(String key) {


    return sp.getBoolean(key, true);
}
public void setBooleanValue(String key,boolean value) {

//    SharedPreferences.Editor editor = sp.edit();
//    editor.putBoolean(key, value);
//    editor.commit();
}




}
