package com.neusoft.eenie.bmoblibrary.bmob;

import android.content.Context;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.neusoft.eenie.bmoblibrary.bmob.bean.FoodBean;
import com.neusoft.eenie.bmoblibrary.bmob.bean.FoodCategoryBean;
import com.neusoft.eenie.bmoblibrary.bmob.bean.OrderBean;
import com.neusoft.eenie.bmoblibrary.bmob.bean.UserBean;
import com.neusoft.eenie.bmoblibrary.bmob.bean.WaiterBean;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;


/**
 * Created by Eenie on 2015/11/16.
 * BOMB封装类
 */
public class BombAPI {

    Context context;


    public BombAPI(Context context) {
        this.context = context;
    }

    /**
     * 服务员注册接口
     *
     * @param waiter       需要注册的服务员对象
     * @param saveListener 注册结果回调
     */
    public void waiterRegister(WaiterBean waiter, SaveListener saveListener) {
        waiter.signUp(context, saveListener);
    }

    /**
     * 服务员登录接口
     *
     * @param waiter       需要登录的服务员对象
     * @param saveListener 登录结果回调
     */
    public void waiterLogin(WaiterBean waiter, SaveListener saveListener) {
        waiter.login(context, saveListener);
    }


    /**
     * 用户登录接口
     *
     * @param userName      用户名
     * @param userPwd       用户密码
     * @param logInListener 登录结果回调
     */
    public void userLogin(String userName, String userPwd, LogInListener<UserBean> logInListener) {

        UserBean user = new UserBean();
        user.loginByAccount(context, userName, userPwd, logInListener);
    }


    /**
     * 用户注册接口
     *
     * @param user         需要注册的用户对象
     * @param saveListener 注册结果回调
     */
    public void userRegister(UserBean user, SaveListener saveListener) {
        user.signUp(context, saveListener);
    }


    /**
     * 查询所有菜品接口
     *
     * @param findListener 查询结果回调
     * @param limit        需要返回的条数
     */
    public void queryAllFood(int limit, FindListener<FoodBean> findListener) {
        BmobQuery<FoodBean> query = new BmobQuery<FoodBean>();
        query.setLimit(limit);
        query.findObjects(context, findListener);
    }

    /**
     * 根据菜品类型的ID查询菜品
     *
     * @param foodCategory 菜品类型
     * @param limit        需要返回的数据条数
     * @param findListener 查询结果回调
     */
    public void queryFoodByCategory(FoodCategoryBean foodCategory, int limit, FindListener<FoodBean> findListener) {
        BmobQuery<FoodBean> query = new BmobQuery<FoodBean>();
        query.setLimit(limit);

        if (!foodCategory.getCategoryName().equals("")) {
            query.addWhereEqualTo("foodCategory", foodCategory);
        }

        query.findObjects(context, findListener);
    }

    /**
     * 搜索菜品接口
     *
     * @param foodName     需要搜索的关键字
     * @param limit        需要返回的条数
     * @param findListener 查询结果回调
     */
    public void searchFoodByName(String foodName, int limit, FindListener<FoodBean> findListener) {
        BmobQuery<FoodBean> query = new BmobQuery<FoodBean>();
        query.setLimit(limit);
        query.findObjects(context, findListener);
    }


    /**
     * 获取当前用户
     *
     * @return
     */
    public UserBean getCurrentUser() {
        return BmobUser.getCurrentUser(context, UserBean.class);
    }

    /**
     * 用户注销
     */
    public void lonOut() {
        BmobUser.logOut(context);
    }


    public void upHeadImage(String imagePath, UploadListener uploadListener) {

        BTPFileResponse response = BmobProFile.getInstance(context)
                .upload(imagePath, uploadListener);
    }


    public void addFoodCategory(FoodCategoryBean foodCategoryBean, SaveListener saveListener) {
        foodCategoryBean.save(context, saveListener);
    }
    //获得食物类别
    public void getFoodCategory(int limit, FindListener<FoodCategoryBean> findListener) {
        BmobQuery<FoodCategoryBean> query = new BmobQuery<FoodCategoryBean>();
        query.setLimit(limit);
        query.findObjects(context, findListener);
    }


    //添加食物
    public void addFood(FoodBean foodBean, SaveListener saveListener) {
        foodBean.save(context, saveListener);
    }


    //添加订单
    public void addOrder(OrderBean orderBean, SaveListener saveListener) {
        orderBean.save(context, saveListener);
    }

    public void getOrder(UserBean userBean,FindListener<OrderBean> findListener) {
        BmobQuery<OrderBean> query = new BmobQuery<OrderBean>();
        query.addWhereEqualTo("user", userBean);

        query.order("-createdAt");


        query.findObjects(context,findListener);
    }





}
