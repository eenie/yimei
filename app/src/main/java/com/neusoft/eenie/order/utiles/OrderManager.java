package com.neusoft.eenie.order.utiles;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.Toast;

import com.neusoft.eenie.bmoblibrary.bmob.bean.FoodBean;
import com.neusoft.eenie.order.controller.base.BaseApplication;
import com.neusoft.eenie.order.controller.bean.OrderBean;

import java.util.List;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Eenie on 2015/11/25.
 *
 *
 */
public class OrderManager {

    /**
     * 添加订单方法
     *
     * @param foodBean 菜品
     */
    public int addOrderBean(List<FoodBean> orderBeanListData, FoodBean foodBean) {
        //判断订单中是否有菜品
        if (orderBeanListData.contains(foodBean)) {
            Log.e("OrderBean", foodBean.getFoodName() + "存在" + orderBeanListData.get(orderBeanListData.indexOf(foodBean)).getCount());
            orderBeanListData.get(orderBeanListData.indexOf(foodBean)).addCount();

            return orderBeanListData.get(orderBeanListData.indexOf(foodBean)).getCount();
        } else {

            Log.e("OrderBean", foodBean.getFoodName() + "不存在");
//            OrderBean orderBean = new OrderBean();
            foodBean.addCount();
//            orderBean.setFoodBean(foodBean);
            orderBeanListData.add(foodBean);
            return orderBeanListData.get(orderBeanListData.indexOf(foodBean)).getCount();
        }
    }


    /**
     * 从订单中删除菜品
     */
    public int subOrderBean(List<FoodBean> orderBeanListData, FoodBean foodBean) {
        if (orderBeanListData.contains(foodBean)) {
            Log.e("OrderBean", foodBean.getFoodName() + "存在" + orderBeanListData.get(orderBeanListData.indexOf(foodBean)).getCount());
            orderBeanListData.get(orderBeanListData.indexOf(foodBean)).subCount();
            if (orderBeanListData.get(orderBeanListData.indexOf(foodBean)).getCount() == 0) {
                orderBeanListData.remove(foodBean);
                return 0;
            }
            return orderBeanListData.get(orderBeanListData.indexOf(foodBean)).getCount();
        } else {
            return 0;
        }
    }

    /**
     * 计算总价
     * @param orderBeanListData
     * @return
     */
    public int sumOrderBean(List<FoodBean> orderBeanListData) {

        return 0;
    }

    public void commitOrderBean(Context context,SaveListener saveListener) {




        com.neusoft.eenie.bmoblibrary.bmob.bean.OrderBean orderBean = new com.neusoft.eenie.bmoblibrary.bmob.bean.OrderBean();

        if (BaseApplication.orderBeanListData.size() > 0) {
            orderBean.setFoodBeanList(BaseApplication.orderBeanListData);
            orderBean.setUser(BaseApplication.bombAPI.getCurrentUser());
            mUtiles.showDialog(context, "正在提交订单").show();
            BaseApplication.bombAPI.addOrder(orderBean, saveListener);
        } else {
            Toast.makeText(context, "购物车为空", Toast.LENGTH_SHORT).show();
        }



    }

    public void clearOrderBean(List<FoodBean> orderBeanList) {
        orderBeanList.clear();
    }

}
