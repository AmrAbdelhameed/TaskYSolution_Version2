package com.example.amr.taskysolution;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    boolean h;
    ArrayList<String> arr;
    DBHelper mydb;
    ArrayList<Sample> s;
    String choose;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dialog = new ProgressDialog(MainActivity.this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");

        listView = (ListView) findViewById(R.id.listView);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        choose = sharedPreferences.getString("choose", "home");

        mydb = new DBHelper(MainActivity.this);

        sendRequest(choose);

        h = false;

        arr = new ArrayList<String>();

        arr.add("home");
        arr.add("arts");
        arr.add("automobiles");
        arr.add("books");
        arr.add("business");
        arr.add("fashion");
        arr.add("food");
        arr.add("health");
        arr.add("insider");
        arr.add("magazine");
        arr.add("movies");
        arr.add("national");
        arr.add("nyregion");
        arr.add("obituaries");
        arr.add("opinion");
        arr.add("politics");
        arr.add("realestate");
        arr.add("science");
        arr.add("sports");
        arr.add("sundayreview");
        arr.add("technology");
        arr.add("theater");
        arr.add("tmagazine");
        arr.add("travel");
        arr.add("upshot");
        arr.add("world");

        int c = arr.indexOf(choose);

        arr.remove(c);

        arr.add(0, choose);
    }

    private void sendRequest(String x) {

        dialog.show();

        StringRequest stringRequest = new StringRequest("http://api.nytimes.com/svc/topstories/v2/" + x + ".json?api_key=b8e44f592a524d3db24fcb3636f874e5",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (mydb.getAllData(choose).size() > 0) {
                            mydb.deleteAll(choose);
                        }

                        ParseJSON pj = new ParseJSON(MainActivity.this, response);
                        pj.parseJSON();

                        CustomList cl = new CustomList(MainActivity.this, ParseJSON.data_array);
                        listView.setAdapter(cl);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                                Toast.makeText(MainActivity.this, ParseJSON.data_array.get(position).getTitle(), Toast.LENGTH_SHORT).show();

                            }

                        });
                        dialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String message = null;
                        if (error instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (error instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                        } else if (error instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (error instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                        } else if (error instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (error instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                        }
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                        s = mydb.getAllData(choose);

                        if (s.size() > 0) {

                            CustomList c2 = new CustomList(MainActivity.this, s);
                            listView.setAdapter(c2);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                                    Toast.makeText(MainActivity.this, s.get(position).getTitle(), Toast.LENGTH_SHORT).show();

                                }
                            });
                        } else {
                            Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                }) {

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.spinner);

        Spinner staticSpinner = (Spinner) MenuItemCompat.getActionView(item);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter staticAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arr);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);

        staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                String s = ((String) parent.getItemAtPosition(position));

                if (h) {
                    SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("choose", s);
                    editor.commit();

                    mydb = new DBHelper(MainActivity.this);

                    sendRequest(s);
                }
                h = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
