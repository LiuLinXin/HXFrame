package com.csd.activitybase.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sober_philer on 2017/3/8 13:50
 */

public class Event {
    public String httpResult;//一般用来放返回的json数据
    private int eventCode;//对应的标识时间的code
    private boolean isSuccess;//是否成功了
    private Exception exception;//捕获到了异常
    private String exceptionString;//自定义的错误标识
    private Object params[];//请求前添加的参数标识
    private int messageCode;//自定义的信息标识
    private boolean isSingleRequest;//是否能重复请求
    private List<Object> returnParams = new ArrayList<>();//返回的参数列表
    private int showDialog;//加载过程中是否显示正在加载的提示信息 0不显示 1显示且可以手动退出 2显示且不能退出

    public boolean isSingleRequest() {
        return isSingleRequest;
    }

    public void setSingleRequest(boolean singleRequest) {
        isSingleRequest = singleRequest;
    }

    public Event(int eventCode, Object... params) {
        if (params.length != 0)
            this.params = params;
        this.eventCode = eventCode;
    }

    public String getExceptionString() {
        return exceptionString;
    }

    public void setExceptionString(String exceptionString) {
        this.exceptionString = exceptionString;
    }

    public void addReturnParam(Object param) {
        returnParams.add(param);
    }

    public <T> T findParam(Class<T> c) {
        if (params.length != 0) {
            for (Object param : params) {
                if (c.isInstance(param))
                    return (T) param;
            }
        }
        return null;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }

    public <T> T findReturnParam(Class<T> c) {
        if (returnParams != null) {
            for (Object obj : returnParams) {
                if (c.isInstance(obj)) {
                    return (T) obj;
                }
            }
        }
        return null;
    }

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public List<Object> getReturnParams() {
        return returnParams;
    }

    public void setReturnParams(List<Object> returnParams) {
        this.returnParams = returnParams;
    }

    public int getShowDialog() {
        return showDialog;
    }

    public void setShowDialog(int showDialog) {
        this.showDialog = showDialog;
    }
}

