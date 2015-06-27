package com.wayne.asynch;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

/**
 * DESCRIPT: 连接asynch-http-client 的中间件 ， 单例模式
 * Author:Wayne
 * EMAIL:chudian@gmail.com
 * CREATE:2015/6/27
 * VERSION:1.0
 */
public class CoreService<T> {

    private static CoreService instance;
    private String host;
    private int mTimeout;
    private Handler mHandler;
    private static AsyncHttpClient  mAsyncHttpClient;
    private AsyncHttpResponseHandler mResponseHandler;

    static {

    }

    /**
     * 获取一个CoreService实例
     * @return
     */
    public static final CoreService getInstance(){
        if(instance == null) {
            throw new ExceptionInInitializerError("must initialize CoreService in application");
        }
        return instance;
    }

    /**
     * 初始化网络请求，必须在Application中执行这个操作
     * @param applicationContext
     * @param host
     * @param timeout 超时时间单位秒
     */
    public void initialize(Context applicationContext,String host,int timeout) {
        instance = new CoreService(applicationContext,host);
        mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setConnectTimeout(timeout*1000);
    }

    public CoreService(Context context,String host) {
        //网络请求的地址中的相同部分 base
        this.host = host;
        //用于执行主线程中的任务
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 发起请求
     * @param requestId 请求时的标示
     * @param requestType 请求类型:{@link GET}、{@link POST}
     * @param uri
     * @param params
     * @param parser
     * @param callback
     */
    public void request(Context context,final int requestId,RequestType requestType,String uri, final RequestParams params, final AbstractParser parser, final IRequestCallback callback) {
//        if(callback != null) {
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    callback.onRequestStart(requestId);
//                }
//            });
//        }
        mResponseHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String data = new String(responseBody);
                Result<T> result = parser.parser(data);
                if(result != null) {
                    callback.onRequestSuccess(requestId,statusCode,result);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(callback != null) {
                    callback.onRequestError(requestId,error);
                }
            }

            @Override
            public void onStart() {
                if(callback != null) {
                    callback.onRequestStart(requestId);
                }
            }
        };

        switch (requestType) {
            case GET:
                mAsyncHttpClient.get(context,host+uri,params,mResponseHandler);
                break;
            case POST:
                mAsyncHttpClient.post(context, host + uri, params, mResponseHandler);
                break;
        }

    }
}
