package com.neusoft.eenie.bmoblibrary.bmob.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Eenie on 2015/11/16.
 *
 */
public class UserBean  extends BmobUser{


    //头像
    private BmobFile headPic;

    //用户类型   0为普通用户  1为服务员
    private int userType = 0;

    //是否为会员
    private Boolean isVip = false;

    //用户积分
    private int integral = 0;

    //用户昵称
    private String nickName = "";

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }






    public Boolean getIsVip() {
        return isVip;
    }

    public void setIsVip(Boolean isVip) {
        this.isVip = isVip;
    }

    public BmobFile getHeadPic() {
        return headPic;
    }

    public void setHeadPic(BmobFile headPic) {
        this.headPic = headPic;
    }

    public int getUserType() {

        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

}
