package com.neusoft.eenie.bmoblibrary.bmob.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Eenie on 2015/11/16.
 */
public class FoodBean extends BmobObject {
    private static final String TABLE_NAME = "table_food";

    //菜名
    private String foodName;
    //菜品类型
    private FoodCategoryBean foodCategory;
    //菜品价格
    private int foodPrice;
    //菜品图片
    private BmobFile foodPicture;
    //菜品销量
    private int foodSales;
    //菜品介绍


    private int count = 0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public FoodCategoryBean getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(FoodCategoryBean foodCategory) {
        this.foodCategory = foodCategory;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }

    public BmobFile getFoodPicture() {
        return foodPicture;
    }

    public void setFoodPicture(BmobFile foodPicture) {
        this.foodPicture = foodPicture;
    }

    public int getFoodSales() {
        return foodSales;
    }

    public void setFoodSales(int foodSales) {
        this.foodSales = foodSales;
    }

    private String remark;



    public void addCount() {
        count++;
    }
    public void subCount() {
        count--;
    }


    public FoodBean() {
//        this.setTableName(TABLE_NAME);
    }


}
