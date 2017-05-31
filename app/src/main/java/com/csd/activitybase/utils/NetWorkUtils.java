package com.csd.activitybase.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sober_philer on 2017/3/3 09:09
 * 网络链接状态的类
 */

public class NetWorkUtils {

    private static NetWorkUtils netWorkUtils;
    private NetWorkUtils(){}
    public static NetWorkUtils getInstance(){
        if(netWorkUtils == null)
            netWorkUtils = new NetWorkUtils();
        return netWorkUtils;
    }

    /**
     * @param context
     * @return -1：没有网络  1：WIFI网络2：wap网络3：net网络
     */
    public static int getNetype(Context context)
    {
        int netType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo==null)
        {
            return netType;
        }
        int nType = networkInfo.getType();
        if(nType== ConnectivityManager.TYPE_MOBILE)
        {
            if(networkInfo.getExtraInfo().toLowerCase().equals("cmnet"))
            {
                netType = 3;
            }
            else
            {
                netType = 2;
            }
        }
        else if(nType== ConnectivityManager.TYPE_WIFI)
        {
            netType = 1;
        }
        return netType;
    }

    public static class NetWorkStateChageCallBack{
        public void onNetDisConnect(){}
        public void onNetConnect(){}
        public void onWifiConnect(){}
        public void onWifiDisConnect(){}
    }

    private static List<NetWorkStateChageCallBack> changeCallBackList = new ArrayList<>();

    /**
     * 注册网络变化的监听，不再使用或退出界面请一定要调用unregistCallBack方法，否则会发送严重内存泄漏
     * @param netWorkStateChageCallBack
     */
    public void registCallBack(NetWorkStateChageCallBack netWorkStateChageCallBack){
        changeCallBackList.add(netWorkStateChageCallBack);
    }
    public void unRegistCallBack(NetWorkStateChageCallBack netWorkStateChageCallBack){
        changeCallBackList.remove(netWorkStateChageCallBack);
    }
    public void clearAllCallBack(){
        changeCallBackList.clear();
    }
    public void netWorkChange(boolean netWorkConnected, boolean wifiConnected){
        if (wifiConnected){
            for (NetWorkStateChageCallBack temp:changeCallBackList){
                temp.onWifiConnect();
                temp.onNetConnect();
            }
            return;
        }
        if(!wifiConnected){
            for (NetWorkStateChageCallBack temp:changeCallBackList){
                temp.onWifiDisConnect();
            }
        }
        if(netWorkConnected){
            for (NetWorkStateChageCallBack temp:changeCallBackList){
                temp.onNetConnect();
            }
        }else {
            for (NetWorkStateChageCallBack temp:changeCallBackList){
                temp.onNetDisConnect();
            }
        }
    }
}
