package com.example.poznajpowiedzenia.quote;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.poznajpowiedzenia.controller.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class QuoteData {
    ArrayList<Quote> queues = new ArrayList<>();

    public void getQuotes() {
        //path to json on github
        String url = null;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject quoteObject = response.getJSONObject(i);
                        Quote quote = new Quote(quoteObject.getString("title"), quoteObject.getString("meaning"));
                        queues.add(quote);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }
}
