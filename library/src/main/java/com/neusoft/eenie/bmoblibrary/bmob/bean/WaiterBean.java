package com.neusoft.eenie.bmoblibrary.bmob.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by Eenie on 2015/11/16.
 * <p/>
 * JavaBean不需要对objectId、createdAt、updatedAt、ACL四个属性进行定义。
 */
public class WaiterBean extends BmobUser {



    private String waiterIcon;

    public WaiterBean() {
        this.setTableName("table_waiter");
    }




    public String getWaiterIcon() {
        return waiterIcon;
    }

    public void setWaiterIcon(String waiterIcon) {
        this.waiterIcon = waiterIcon;
    }
}
