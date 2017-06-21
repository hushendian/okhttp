package com.xl.test;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by hushendian on 2017/6/21.
 */

public class OkHttpClientManager {
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private Gson mGson;

    private static final String TAG = "OkHttpClientManager";

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy
                .ACCEPT_ORIGINAL_SERVER));
        mHandler = new Handler(Looper.getMainLooper());
        mGson = new Gson();

    }

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }

            }
        }
        return mInstance;
    }

    /**
     * 同步的get请求
     *
     * @param url
     * @return Response
     * @throws IOException
     */
    private Response _getAsyn(String url) throws IOException {
        final Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        Response response = call.execute();
        return response;
    }

    /**
     * 同步的get请求
     *
     * @param url
     * @return String
     * @throws IOException
     */
    private String _getAsString(String url) throws IOException {

        Response response = _getAsyn(url);

        return response.body().string();
    }

    private void _getAsyn(String url, final ResultCallback callback) {


    }

    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {

        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type supperclass = subclass.getGenericSuperclass();
            if (supperclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterizedType = (ParameterizedType) supperclass;
            return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
        }
    }

}