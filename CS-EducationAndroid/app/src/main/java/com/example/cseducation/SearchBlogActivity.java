package com.example.cseducation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.Vector;

import static com.example.cseducation.Class.StaticGlobal.SERVER;
import static com.example.cseducation.Class.StaticGlobal.Tanvir;

public class SearchBlogActivity extends DrawerActivity {
    Button submit;
    EditText key;
    TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContent(R.layout.activity_search_blog,getApplicationContext());

        key=(EditText)findViewById(R.id.search_blog_key);
        submit=(Button)findViewById(R.id.search_blog_submit);
        msg=(TextView)findViewById(R.id.search_blog_msg);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Vector<String> m= new Vector<>();
                final Vector<String> articles= new Vector<>();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest sr = new StringRequest(Request.Method.POST,SERVER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.length()==0){
                            msg.setText("No Article found");
                            return;
                        }
                        msg.setText("");
                        StringTokenizer stringTokenizer = new StringTokenizer(response,"#");
                        while ((stringTokenizer.hasMoreElements())){
                            String token = stringTokenizer.nextToken();
                            articles.add(token);
                            try {
                                JSONObject jsonObject=new JSONObject(token);
                                m.add(jsonObject.getString("title")+"\nby "+jsonObject.getString("author"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        setupList(m,articles);
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
                        params.put("request","search-articles");
                        params.put("key",key.getText().toString());
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
        });



    }

    private void setupList(Vector<String> m, final Vector<String> articles) {
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, m);


        ListView listView = (ListView) findViewById(R.id.search_blog_article_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),ShowArticleActivity.class);
                intent.putExtra("article",articles.get(position));
                startActivity(intent);
            }
        });
    }
}
