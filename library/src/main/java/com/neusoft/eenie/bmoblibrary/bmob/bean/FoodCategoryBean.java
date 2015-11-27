package com.neusoft.eenie.bmoblibrary.bmob.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Eenie on 2015/11/16.
 *
 */
public class FoodCategoryBean extends BmobObject{
    private static final String TABLE_NAME = "table_food_category";


    public FoodCategoryBean() {
//        this.setTableName(TABLE_NAME);
    }

    public void setTable() {
        this.setTableName(TABLE_NAME);
    }


    //菜品类型名
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }



}
