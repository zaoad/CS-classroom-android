package com.example.cseducation;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static com.example.cseducation.Class.StaticGlobal.SERVER;
import static com.example.cseducation.Class.StaticGlobal.Tanvir;

public class MyArticleActivity extends DrawerActivity{
    EditText title,body;
    TextView date,msg;
    Button edit,delete;
    JSONObject article = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContent(R.layout.activity_my_article,getApplicationContext());

        title=(EditText) findViewById(R.id.my_article_title);
        date=(TextView)findViewById(R.id.my_article_date);
        body=(EditText) findViewById(R.id.my_article_body);
        msg=(TextView)findViewById(R.id.my_article_msg);

        try {
            article = new JSONObject(getIntent().getExtras().getString("article"));
            title.setText(article.getString("title"));
            date.setText(article.getString("date"));
            body.setText(article.getString("body"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        edit = (Button)findViewById(R.id.my_article_edit);
        delete=(Button)findViewById(R.id.my_article_delete);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editArticle();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteArticle();
            }
        });



    }

    private void deleteArticle() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(getApplicationContext(), MyBlogActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(Tanvir,error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("request","delete-article");
                try {
                    params.put("id",article.getString("_id"));
                } catch (JSONException e) {
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    private void editArticle() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                msg.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(Tanvir,error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("request","edit-article");
                try {
                    params.put("id",article.getString("_id"));
                    params.put("title",title.getText().toString());
                    params.put("body",body.getText().toString());
                } catch (JSONException e) {
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MyBlogActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
