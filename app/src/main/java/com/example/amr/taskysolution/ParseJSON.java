package com.example.amr.taskysolution;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParseJSON {

    public static ArrayList<Sample> data_array;
    public static Sample s;
//    DBHelper mydb;
    Activity _activity;

    private JSONArray users = null;
    private String json;

    public ParseJSON(Activity _activity, String json) {
        this.json = json;
        this._activity = _activity;
    }

    protected void parseJSON() {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray("results");

            data_array = new ArrayList<>();

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);

                s = new Sample();

//                SharedPreferences sharedPreferences = _activity.getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
//                String choose = sharedPreferences.getString("choose", "home");
//                mydb = new DBHelper(_activity, choose);

                JSONArray imgs = jo.getJSONArray("multimedia");
                JSONObject imgobj = imgs.getJSONObject(imgs.length() / 2);

                s.setTitle(jo.getString("title"));
                s.setPublished_date(jo.getString("published_date").substring(0, 10));
                s.setUrl(imgobj.getString("url"));

//                if (mydb.insertData(jo.getString("title"), jo.getString("published_date").substring(0, 10), imgobj.getString("url"))) {
//                    Toast.makeText(_activity, "Added Successfully " + + i + " " + choose, Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(_activity, "Not Added ...", Toast.LENGTH_SHORT).show();
//                }

                data_array.add(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}