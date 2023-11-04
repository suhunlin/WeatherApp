package com.suhun.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private String tag = MainActivity.class.getSimpleName();
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
    }

    public void fetchOpenDataFun(View view){
        String url = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001?Authorization=CWA-9A3E2D46-542D-443B-8EAD-7CE121556980";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(tag, "-----response data" + response.toString());
                        parseJSON(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(tag, "-----VolleyError"+ error.toString());

            }
        });
        requestQueue.add(stringRequest);
    }
    private void parseJSON(String json){
        try {
            JSONObject root_jsonObj = new JSONObject(json);
            //依照得到的JSON格式物件，先判斷第一組資料是否成功，如果成功繼續往下解析，如果失敗停止解析
            if(root_jsonObj.getBoolean("success")){//從名稱為root_jsonObj的JSON裡面取得key為"success"的value判斷是否為true
                Log.d(tag, "-----1.parseJSON 後端溝通成功");
                JSONObject records_jsonObj = root_jsonObj.getJSONObject("records");
                Log.d(tag, "-----2.parseJSON records"+ records_jsonObj);
                JSONArray location_arrayObj = records_jsonObj.getJSONArray("location");
                Log.d(tag, "-----3.parseJSON location_arrayObj"+ location_arrayObj);
                for(int i=0; i<location_arrayObj.length();i++){
                    JSONObject area_jsonObj = location_arrayObj.getJSONObject(i);
                    String locationName = area_jsonObj.getString("locationName");
                    Log.d(tag, "-----4.parseJSON locationName"+ locationName);
                    JSONArray weatherElement_arrayObj = area_jsonObj.getJSONArray("weatherElement");
                    Log.d(tag, "-----5.parseJSON weatherElement_arrayObj"+ weatherElement_arrayObj);
                    //只取weatherElement[0]的資料
                    JSONObject weatherElement_jsonObj = weatherElement_arrayObj.getJSONObject(0);
                    JSONArray time_arrayObj = weatherElement_jsonObj.getJSONArray("time");
                    //只取time[0]的資料
                    JSONObject time_jsonObj = time_arrayObj.getJSONObject(0);
                    String endTime = time_jsonObj.getString("endTime");
                    Log.d(tag, "-----6.parseJSON endTime"+ endTime);
                    JSONObject parameter_jsonObj = time_jsonObj.getJSONObject("parameter");
                    String weatherSituation = parameter_jsonObj.getString("parameterName");
                    Log.d(tag, "-----7.parseJSON weatherSituation"+ weatherSituation);
                }

            }else{
                Log.d(tag, "-----parseJSON 後端溝通失敗");

            }
        }catch (Exception e){
            Log.d(tag, "parseJSON error in " + e.toString());
        }
    }
}