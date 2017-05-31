/**
 *
 */
package com.csd.activitybase.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.csd.activitybase.activity.HApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * 获取手机信息
 */

public class AppUtil {
    static {
        System.loadLibrary("native-lib");
    }

    public static native String getProductNo();

    public static String getAgentNo(Context context) {
        String resultData = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.get("CSD_MD").toString();
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return resultData;
    }

    /**
     * 是否安装了微信与支付宝
     *
     * @return 1表示安装了支付宝 2 安装了微信 3 都安装了
     */
    private static int isWXAppInstalledAndSupported(Context context) {
        int result = 0;

        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    result += 2;
                } else if (pn.equals("com.alipay.android.app") || pn.equals("com.eg.android.alipayGphone")) {
                    result += 1;
                }
            }
        }
        return result;
    }

    /* 获取当前系统的android版本号 */
    public static int CURRENTAPIVERSION = Build.VERSION.SDK_INT;
    //andriod版本5.1
    public static String RELEASE = Build.VERSION.RELEASE;
    // 获取手机名称
    public static String MOBIL_NAME = Build.MANUFACTURER;
    // 手机型号
    public static String MOBIL_TYPE = Build.MODEL;
    // 手机品牌
    public static String MOBIL_BRAND = Build.BRAND;

    /**
     * 获取屏幕分辨率
     * isZh
     *
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        int result[] =
                {width, height};
        return result;
    }

    /**
     * 获取IMEI
     *
     * @return
     */
    public static String getIMEI(Context context) {
        String IMEI = "";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        if (imei != null && imei.trim().length() > 0) {
            IMEI = imei;
        } else {
//            if (SharePrefrenceHelper.getInstance().getIMEI().equals("")) {
//                IMEI = UUIDGenerator.getUUID();
//                SharePrefrenceHelper.getInstance().setIMEI(IMEI);
//            } else {
//                IMEI = SharePrefrenceHelper.getInstance().getIMEI();
//            }

        }
        return IMEI;
    }

    /**
     * 获取手机mac地址
     *
     * @param context
     * @return
     */
    public static String getPhoneMac(Context context) {

        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifi.getConnectionInfo();

        return info.getMacAddress();

    }

    /**
     * 获取手机ID
     *
     * @return
     */
    public static String getAndroidId(Context context) {
        String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        return androidId;
    }

    public static boolean isWifiActive(Context icontext) {
        Context context = icontext.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info;
        if (connectivity != null) {
            info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取语言版本
     *
     * @return
     */
    public static boolean isZh(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;

    }

    /**
     * 检查是否有中文
     *
     * @param str
     * @return
     */
    public static boolean checkString(String str) {
        boolean res = false;
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                // 只要字符串中有中文则为中文
                if (!checkChar(str.charAt(i))) {
                    res = true;
                    break;
                } else {
                    res = false;
                }
            }
        }
        return res;
    }

    // 英文占1byte，非英文（可认为是中文）占2byte，根据这个特性来判断字符
    public static boolean checkChar(char ch) {
        if ((ch + "").getBytes().length == 1) {
            return true;// 英文
        } else {
            return false;// 中文
        }
    }

    /**
     * app版本号
     *
     * @param context
     * @return versionName
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo != null) {
                return packageInfo.versionName;
            }
        } catch (Exception e) {

        }
        return "1.0.0";
    }

    /**
     * app版本号
     *
     * @param context
     * @return versionCode
     */
    public static int getAppVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo != null) {
                return packageInfo.versionCode;
            }
        } catch (Exception e) {

        }
        return 1;
    }

    public static boolean isScreenChange() {

        Configuration mConfiguration = HApplication.getInstance().getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向

        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {

//横屏
            return true;
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {

//竖屏
            return false;
        }
        return false;
    }

    public static final String SYS_EMUI = "sys_emui";//华为
    public static final String SYS_MIUI = "sys_miui";//小米
    public static final String SYS_FLYME = "sys_flyme";//魅族
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_VERSION = "ro.build.version.emui";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    private static String SYS;

    public static String getSystem() {
        if (!TextUtils.isEmpty(SYS))
            return SYS;
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            if (prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null) {
                SYS = SYS_MIUI;//小米
            } else if (prop.getProperty(KEY_EMUI_API_LEVEL, null) != null
                    || prop.getProperty(KEY_EMUI_VERSION, null) != null
                    || prop.getProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION, null) != null) {
                SYS = SYS_EMUI;//华为
            } else if (getMeizuFlymeOSFlag().toLowerCase().contains("flyme")) {
                SYS = SYS_FLYME;//魅族
            } else {
                SYS = "other";
            }
        } catch (IOException e) {
            e.printStackTrace();
            SYS = "other";
            return SYS;
        }
        return SYS;
    }

    public static String getMeizuFlymeOSFlag() {
        return getSystemProperty("ro.build.display.id", "");
    }

    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
        }
        return defaultValue;
    }


    /**
     * 根据构造函数获得当前手机的手机高度
     */
    public static int getDensity_Height(Context context) {
        int height;
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        height = dm.heightPixels;
        return height;
    }

    /**
     * 根据构造函数获得当前手机的手机宽度
     */
    public static int getDensity_Width(Context context) {
        // 获取当前屏幕
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        return width;
    }

}
