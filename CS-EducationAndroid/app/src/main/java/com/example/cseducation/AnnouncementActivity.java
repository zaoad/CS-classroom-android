package com.example.cseducation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.example.cseducation.Class.StaticGlobal.SERVER;
import static com.example.cseducation.Class.StaticGlobal.Tanvir;

public class AnnouncementActivity extends DrawerActivity {

    EditText editText;
    ImageButton home_btn, forum_btn, group_btn, sbmt_btn;
    static String courseId, userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_announcement);
        super.setContent(R.layout.activity_announcement,getApplicationContext());

        final Intent intent = getIntent();
        courseId = intent.getStringExtra("courseId");
        System.out.println("CourseId: "+ courseId + " Printing at announcement main activity");

        SharedPreferences pref = getApplicationContext().getSharedPreferences("USERINFO", MODE_PRIVATE);
        this.userName = pref.getString("username", null);


        editText = (EditText) findViewById(R.id.edit_txt_announcement_activity);
        home_btn = (ImageButton) findViewById(R.id.home_btn_classroom_announcement_activity);
        forum_btn = (ImageButton) findViewById(R.id.forum_btn_classroom_announcement_activity);
        group_btn = (ImageButton) findViewById(R.id.people_btn_classroom_announcement_activity);
        sbmt_btn = (ImageButton) findViewById(R.id.send_btn_announcement_activity);

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ClassroomActivity.class);
                startActivity(intent1);
            }
        });

        forum_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest sr = new StringRequest(Request.Method.POST,SERVER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("Respone: "+response);
                        Intent intent1 = new Intent(getApplicationContext(), ClassroomFeedActivity.class);
                        intent1.putExtra("courseId", courseId);
                        intent1.putExtra("response", response);
                        startActivity(intent1);

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
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("USERINFO", MODE_PRIVATE);
                        String userName = pref.getString("username", null);
                        params.put("request","classroom_feed");
//                                    System.out.println("CourseId: "+ courseId + " Printing at enroll onclicked activity inside volley function");
                        params.put("course_id", courseId);
                        params.put("username", userName);
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

        group_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest sr = new StringRequest(Request.Method.POST,SERVER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("Respone: "+response);
                        Intent intent1 = new Intent(getApplicationContext(), ClassroomUsersActivity.class);
                        intent1.putExtra("courseId", courseId);
                        intent1.putExtra("response", response);
                        startActivity(intent1);

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
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("USERINFO", MODE_PRIVATE);
                        String userName = pref.getString("username", null);
                        params.put("request","classroom_user");
//                                    System.out.println("CourseId: "+ courseId + " Printing at enroll onclicked activity inside volley function");
                        params.put("course_id", courseId);
                        params.put("username", userName);
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

        sbmt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String postText = editText.getText().toString().trim();


                Log.d(Tanvir," Announcement being posted");

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest sr = new StringRequest(Request.Method.POST,SERVER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("OK"))
                        {
                            response = "The announcement has been successfully posted in the Classroom";
                            System.out.println(response);
                            Toast.makeText(getBaseContext(), response, Toast.LENGTH_LONG).show();


                            Toast.makeText(getBaseContext(), "Here it has clicked", Toast.LENGTH_LONG).show();
                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            StringRequest sr = new StringRequest(Request.Method.POST,SERVER, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    System.out.println("Respone: "+response);
                                    Intent intent1 = new Intent(getApplicationContext(), ClassroomFeedActivity.class);
                                    intent1.putExtra("courseId", courseId);
                                    intent1.putExtra("response", response);
                                    startActivity(intent1);

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
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("USERINFO", MODE_PRIVATE);
                                    String userName = pref.getString("username", null);
                                    params.put("request","classroom_feed");
//                                    System.out.println("CourseId: "+ courseId + " Printing at enroll onclicked activity inside volley function");
                                    params.put("course_id", courseId);
                                    params.put("username", userName);
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
                        else
                        {
                            response = "Something happened WRONG";
                            Toast.makeText(getBaseContext(), response, Toast.LENGTH_LONG).show();
                        }

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
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("USERINFO", MODE_PRIVATE);
                        String userName = pref.getString("username", null);
                        params.put("request","classroom_announcement");
                        params.put("username", userName.toString());
                        params.put("announcement_text", postText);
                        System.out.println("CourseId: "+ courseId + " Printing at announcement main activity inside volley function");
                        params.put("course_id", courseId);
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
}
