package com.mobile.quiz.api;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.mobile.quiz.activity.MyApplication;
import com.mobile.quiz.appInterface.APIResponseInterface;

import org.json.JSONObject;

public class APIRequestResponse {

    private Activity mContext;
    private String mUrl;
    private APIResponseInterface apiResponseInterface;

    public APIRequestResponse(Activity context, String url) {
        mContext = context;
        mUrl = url;
        apiResponseInterface = (APIResponseInterface) context;
    }


    public void generateConfirmOTPpostRequest() {
        try {
            BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, mUrl, null, new Response.Listener<Boolean>() {
                @Override
                public void onResponse(Boolean response) {
                    apiResponseInterface.apiResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    apiResponseInterface.apiError(error);
                }
            });
            MyApplication.getInstance().addToRequestQueue(booleanRequest, "postRequest");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setUserPINPostRequest() {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, mUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            apiResponseInterface.apiResponse(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    apiResponseInterface.apiError(error);
                }
            }) {

            };
            MyApplication.getInstance().addToRequestQueue(stringRequest, "postRequest");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRRQGETRequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(mUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            apiResponseInterface.apiResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                apiResponseInterface.apiError(error);
            }
        });
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest, "GetRequest");
    }


}
