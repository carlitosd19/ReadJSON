package com.example.carlos_m.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button start;
    TextView textView;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start=(Button)findViewById(R.id.buttonReq);
        textView=(TextView)findViewById(R.id.textView);
        requestQueue= Volley.newRequestQueue(this);

        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://pub.pixub.com/phpFiles/test4.php",
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try
                                {
                                    JSONArray jsonArray = response.getJSONArray("movies");
                                    for (int i=0;i<jsonArray.length();i++)
                                    {
                                        JSONObject movies = jsonArray.getJSONObject(i);
                                        String movie_id=movies.getString("movie_id");
                                        String title=movies.getString("title");
                                        String videoURL=movies.getString("videoUrl");
                                        textView.append(movie_id+" " + title+ " "+ videoURL +" \n ");

                                    }
                                }
                                catch (JSONException e)
                                {
                                        e.printStackTrace();

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Volley","ERROR");
                            }
                        }
                );
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}
