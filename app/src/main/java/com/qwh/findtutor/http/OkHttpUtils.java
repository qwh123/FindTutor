package com.qwh.findtutor.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.internal.$Gson$Types;
import com.qwh.findtutor.bean.Param;
import com.qwh.findtutor.utils.JsonUtils;
import com.qwh.findtutor.utils.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * com.qwh.findtutor.utils
 * 开发者 qwh
 * 时间: 14:57
 * 邮箱:2529509180@qq.com
 * 类作用：
 */

public class OkHttpUtils {
    private static final String TAG = "OkHttpUtils";

    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;

    private OkHttpUtils() {
        /**
         * 构建OkHttpClient
         */
        mOkHttpClient = new OkHttpClient();
        /**
         * 设置连接的超时时间
         */
        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        /**
         * 设置响应的超时时间
         */
        mOkHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        /**
         * 请求的超时时间
         */
        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        /**
         * 允许使用Cookie
         */
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        /**
         * 获取主线程的handler
         */
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 通过单例模式构造对象
     *
     * @return OkHttpUtils
     */
    private synchronized static OkHttpUtils getInstance() {
        if (mInstance == null) {
            mInstance = new OkHttpUtils();
        }
        return mInstance;
    }

    /**
     * 构造Get请求
     *
     * @param url      请求的url
     * @param callback 结果回调的方法
     */
    private void getRequest(String url, final ResultCallback callback) {
        final Request request = new Request.Builder().url(url).build();
        deliveryResult(callback, request);
    }

    /**
     * 构造post 请求
     *
     * @param url      请求的url
     * @param callback 结果回调的方法
     * @param params   请求参数
     */
    private void postRequest(String url, final ResultCallback callback, List<Param> params) {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    /**
     * 处理请求结果的回调
     *
     * @param callback
     * @param request
     */
    private void deliveryResult(final ResultCallback callback, Request request) {

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                sendFailCallback(callback, e);

                Log.i(TAG, "onFailure: "+request.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.i(TAG, "onResponse: "+response.message());
                try {
                    String str = response.body().string();
                    if (callback.mType == String.class) {
                        sendSuccessCallBack(callback, str);
                    } else {
                        Object object = JsonUtils.deserialize(str, callback.mType);
                        sendSuccessCallBack(callback, object);
                    }
                } catch (final Exception e) {
                    Log.e(TAG, "convert json failure", e);
                    sendFailCallback(callback, e);
                }

            }
        });
    }

    /**
     * 发送失败的回调
     *
     * @param callback
     * @param e
     */
    private void sendFailCallback(final ResultCallback callback, final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFailure(e);
                }
            }
        });
    }

    /**
     * 发送成功的调
     *
     * @param callback
     * @param obj
     */
    private void sendSuccessCallBack(final ResultCallback callback, final Object obj) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onSuccess(obj);
                }
            }
        });
    }

    /**
     * 构造post请求
     *
     * @param url    请求url
     * @param params 请求的参数
     * @return 返回 Request
     */
    private Request buildPostRequest(String url, List<Param> params) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

    /**********************对外接口************************/

    /**
     * get请求
     *
     * @param url      请求url
     * @param callback 请求回调
     */
    public static void get(String url, ResultCallback callback) {
        getInstance().getRequest(url, callback);
    }

    /**
     * post请求
     *
     * @param url      请求url
     * @param callback 请求回调
     * @param params   请求参数
     */
    public static void post(String url, final ResultCallback callback, List<Param> params) {
        getInstance().postRequest(url, callback, params);
    }

    /**
     * http请求回调类,回调方法在UI线程中执行
     *
     * @param <T>
     */
    public static abstract class ResultCallback<T> {

        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();//返回父类的类型
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        /**
         * 请求成功回调
         *
         * @param response
         */
        public abstract void onSuccess(T response);

        /**
         * 请求失败回调
         *
         * @param e
         */
        public abstract void onFailure(Exception e);
    }


    public String put(MediaType mediaType, String uploadUrl, String localPath) throws IOException {
        File file = new File(localPath);
        RequestBody body = RequestBody.create(mediaType, file);
        Request request = new Request.Builder()
                .url(uploadUrl)
                .put(body)
                .build();

//        Response response = mOkHttpClient.newCall(request).execute();
        Response response = mOkHttpClient.newCall(request).execute();
        return response.code() + ":" + response.body().string();
    }

    //上传JPG图片
    public static String putImg(String uploadUrl, String localPath) throws IOException {
        MediaType Image = MediaType.parse("image/jpeg; charset=utf-8");
        return getInstance().put(Image, uploadUrl, localPath);
    }

}
