package com.wayne.asynch;

/**
 * DESCRIPT: 网络请求的回调
 * Author:Wayne
 * EMAIL:chudian@gmail.com
 * CREATE:2015/6/27
 * VERSION:1.0
 */
public interface IRequestCallback {
    /**
     * 网络请求开始
     * @param requestId 网络请求标示 ，用来区分在一个页面中的请求
     */
    void onRequestStart(int requestId);

    /**
     * 网络请求成功回调
     * @param requestId 网络请求标示 ，用来区分在一个页面中的请求
     * @param statusCode 网络状态标识
     * @param result 网络请求结果，封装已经解析出来的结果
     */
    void onRequestSuccess(int requestId, int statusCode, Result<?> result);

    /**
     * 网络请求失败
     * @param requestId 网络请求标示 ，用来区分在一个页面中的请求
     * @param throwable 异常
     */
    void onRequestError(int requestId, Throwable throwable);

}
