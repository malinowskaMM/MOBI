package com.example.poznajpowiedzenia.controller;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();
    private static  AppController mInstance;
    private RequestQueue mRequestQueue;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public RequestQueue getmRequestQueue() {
        if(mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request <T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getmRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request <T> request) {
        request.setTag(TAG);
        getmRequestQueue().add(request);
    }

    public void  cancelPendingRequest(Object tag) {
    if(mRequestQueue != null) {
        mRequestQueue.cancelAll(tag);
        }
    }

}
