package com.neusoft.eenie.order.controller.bean;

import com.neusoft.eenie.bmoblibrary.bmob.bean.FoodBean;

/**
 * Created by Eenie on 2015/11/25.
 * 订单对象
 */
public class OrderBean {

    FoodBean foodBean;
    int count = 1;

    public FoodBean getFoodBean() {
        return foodBean;
    }

    public void setFoodBean(FoodBean foodBean) {
        this.foodBean = foodBean;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addCount() {
        count++;
    }


    public void subCount() {
        count--;
    }

}
