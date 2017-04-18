package com.example.fengdeyu.xuanyue_reader.other;

import com.example.fengdeyu.xuanyue_reader.bean.UserInfoBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fengdeyu on 2017/3/13.
 */

public class GetUserInfo {
    private static GetUserInfo getUserInfo=null;
    public static GetUserInfo getInstance(){
        if(getUserInfo==null){
            getUserInfo=new GetUserInfo();
        }
        return getUserInfo;
    }

    public boolean sign=false;              //是否登录
    public String userName="请登录";          //用户名

    public UserInfoBean userInfo=new UserInfoBean();

    public void getInfoByJSON(JSONObject obj) throws JSONException {
        userInfo.uid=obj.get("uid").toString();
        userInfo.nickname=obj.get("nickname").toString();
        userInfo.sex=obj.get("sex").toString();
        userInfo.birthday=obj.get("birthday").toString();
        userInfo.head_portrait=obj.get("head_portrait").toString();
        userInfo.signature=obj.get("signature").toString();
    }

    public void toLogout(){
        sign=false;
        userInfo.uid="0";
    }
}
