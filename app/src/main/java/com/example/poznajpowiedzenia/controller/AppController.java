package com.example.poznajpowiedzenia.controller;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.poznajpowiedzenia.data.wiki.Proverb;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();
    private static  AppController mInstance;
    private RequestQueue mRequestQueue;
    public static List<Proverb> proverbs = new ArrayList<>();

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static synchronized List<Proverb> getInstance2() {
        return proverbs;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Powiedzenia")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            proverbs.add(new Proverb(document.getString("title"), document.getString("meaning")));
                        }
                    } else {
                        System.out.println(("Error getting documents." + task.getException()));
                    }
                });
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
