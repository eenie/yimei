package com.neusoft.eenie.bmoblibrary.bmob.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Eenie on 2015/11/16.
 *
 */
public class OrderBean extends BmobObject {




    //流水号
    private long ordernum = 0;
    //菜单列表
    private List<FoodBean> foodBeanList;
    //用户
    private UserBean user;
    //订单状态：0未付款、1已经付款、2商家接单、3完成交易、4取消订单
    private int state = 0;

    //该订单产生的积分
    private int score = 0;

    //该订单的总价
    private int totalPrice;


    //各种订单处理时间
    private long timeMakeOrder;
    private long timePay;
    private long timeTakeOrder;
    private long timeCompareOrder;
    private long timeCancleOrder;

    public OrderBean() {
        setOrdernum(System.currentTimeMillis());
    }


    public long getTimeMakeOrder() {
        return timeMakeOrder;
    }

    public long getTimePay() {
        return timePay;
    }

    public long getTimeTakeOrder() {
        return timeTakeOrder;
    }

    public long getTimeCompareOrder() {
        return timeCompareOrder;
    }

    public long getTimeCancleOrder() {
        return timeCancleOrder;
    }

    public int getScore() {
        return score;
    }

    private void setScore(int score) {
        this.score = score;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    private void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;

        setScore(totalPrice * 10);
    }

    public long getOrdernum() {
        return ordernum;
    }

    private void setOrdernum(long ordernum) {
        this.ordernum = ordernum;
    }

    public List<FoodBean> getFoodBeanList() {
        return foodBeanList;
    }

    public void setFoodBeanList(List<FoodBean> foodBeanList) {
        this.foodBeanList = foodBeanList;
        setTotalPrice(sumPrice(foodBeanList));
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    private int sumPrice(List<FoodBean> mfoodBeanList) {
        int sum = 0;
        for (FoodBean f : mfoodBeanList) {
            sum = sum + f.getFoodPrice() * f.getCount();
        }
        return sum;
    }


}
