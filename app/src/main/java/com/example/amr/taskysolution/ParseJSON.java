package com.example.amr.taskysolution;

import android.app.Activity;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParseJSON {

    ArrayList<Sample> data_array;
    Sample s;
    DBHelper mydb;
    Context context;
    String table_name;

    JSONArray users = null;
    String json;

    public ParseJSON(Context context, String json, String table_name) {
        this.json = json;
        this.context = context;
        this.table_name = table_name;
    }

    public ArrayList<Sample> parseJSON() {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray("results");

            data_array = new ArrayList<>();

            mydb = new DBHelper(context);
            if (mydb.getAllData(table_name).size() > 0) {
                mydb.deleteAll(table_name);
            }

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);

                s = new Sample();

                JSONArray imgs = jo.getJSONArray("multimedia");
                JSONObject imgobj = imgs.getJSONObject(imgs.length() / 2);

                s.setTitle(jo.getString("title"));
                s.setPublished_date(jo.getString("published_date").substring(0, 10));
                s.setUrl(imgobj.getString("url"));

                mydb = new DBHelper(context);
                mydb.insertData(jo.getString("title"), jo.getString("published_date").substring(0, 10), imgobj.getString("url"), table_name);

                data_array.add(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data_array;
    }
}