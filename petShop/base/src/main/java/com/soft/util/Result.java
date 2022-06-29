package com.soft.util;

import java.io.Serializable;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/22 10:35
 **/
public class Result<T> implements Serializable {
    private String statusCode = ResultType.SUCCESS.getCode();
    private String message = ResultType.SUCCESS.getName();
    private T data = null;
    private T etc = null;
    private Boolean isSuccess = true;
    /*
        获取状态编码
     */
    public String getStatusCode() {
        return statusCode;
    }
    /*
        设置状态编码
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    /*
        获取提示信息
     */
    public String getMessage() {
        return message;
    }
    /*
        设置提示信息
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /*
        获取返回结果
     */
    public T getData() {
        return data;
    }
    /*
        设置返回结果
     */
    public void setData(T data) {
        this.data = data;
    }

    public T getEtc(){
        return etc;
    }

    public void setEtc(T etc) {
        this.etc = etc;
    }

    /*
            请求是否成功
         */
    public Boolean getIsSuccess(){
        return isSuccess;
    }
    /*
        成功请求
     */
    public void success(String message){
        this.message = message;
        this.isSuccess = true;
    }
    /*
        错误请求
     */
    public void error(String message) {
        this.isSuccess = false;
        this.message = message;
        this.statusCode = ResultType.INTERNAL_SERVER_ERROR.getCode();
        if(this.message == null || "".equals(this.message)){
            this.message = ResultType.INTERNAL_SERVER_ERROR.getName();
        }
    }
    /*
        失败请求
     */
    public void fail(String message) {
        this.isSuccess = false;
        this.message = message;
        this.statusCode = ResultType.FAIL.getCode();
        if(this.message == null || "".equals(this.message)){
            this.message = ResultType.FAIL.getName();
        }
    }
    /*
        权限不足
     */
    public void unauthorized(String message) {
        this.isSuccess = false;
        this.message = message;
        this.statusCode = ResultType.UNAUTHORIZED.getCode();

        if(this.message == null || "".equals(this.message)){
            this.message = ResultType.UNAUTHORIZED.getName();
        }
    }
    /*
        未找到
     */
    public void notFound(String message) {
        this.isSuccess = false;
        this.message = message;
        this.statusCode = ResultType.NOT_FOUND.getCode();
        if(this.message == null || "".equals(this.message)){
            this.message = ResultType.NOT_FOUND.getName();
        }
    }
    /*
        重新登录
     */
    public void againLogin(String message) {
        this.isSuccess = false;
        this.message = message;
        this.statusCode = ResultType.AGAIN_LOGIN.getCode();
        if(this.message == null || "".equals(this.message)){
            this.message = ResultType.AGAIN_LOGIN.getName();
        }
    }
}
