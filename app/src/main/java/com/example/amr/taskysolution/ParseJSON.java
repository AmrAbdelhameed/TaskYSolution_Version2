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
    DBHelper mydb,mydb2;
    Activity _activity;
    String table_name;

    private JSONArray users = null;
    private String json;

    public ParseJSON(Activity _activity, String json , String table_name) {
        this.json = json;
        this._activity = _activity;
        this.table_name = table_name;
    }

    protected void parseJSON() {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray("results");

            data_array = new ArrayList<>();

            mydb2 = new DBHelper(_activity);
            if (mydb2.getAllData(table_name).size() > 0) {
                mydb2.deleteAll(table_name);
            }

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);

                s = new Sample();

                JSONArray imgs = jo.getJSONArray("multimedia");
                JSONObject imgobj = imgs.getJSONObject(imgs.length() / 2);

                s.setTitle(jo.getString("title"));
                s.setPublished_date(jo.getString("published_date").substring(0, 10));
                s.setUrl(imgobj.getString("url"));

                mydb = new DBHelper(_activity);
                mydb.insertData(jo.getString("title"), jo.getString("published_date").substring(0, 10), imgobj.getString("url"), table_name);

                data_array.add(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}