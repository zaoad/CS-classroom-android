package com.example.cseducation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cseducation.Adapters.EnrolledClassRecycleviewAdapter;
import com.example.cseducation.Adapters.OnItemClickListenerEnrollClass;
import com.example.cseducation.Class.EnrollClass;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.example.cseducation.Class.StaticGlobal.SERVER;
import static com.example.cseducation.Class.StaticGlobal.Tanvir;
import org.json.simple.JSONArray;
import org.json.simple.parser.*;

public class EnrolledClassActivity extends DrawerActivity {
    public List<EnrollClass> enrollClassList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_enrolled_class);
        super.setContent(R.layout.activity_enrolled_class,getApplicationContext());

        final Intent intent = getIntent();
        String response = intent.getStringExtra("response");
        setUp(response);
        EnrolledClassRecycleviewAdapter enrolledClassRecycleviewAdapter = new EnrolledClassRecycleviewAdapter(this.enrollClassList, new OnItemClickListenerEnrollClass() {
            @Override
            public void onItemClick(final EnrollClass enrollClass) {

                final String courseId = enrollClass.getCourseObjectId();
                Toast.makeText(getBaseContext(), "Here it has clicked", Toast.LENGTH_LONG).show();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest sr = new StringRequest(Request.Method.POST,SERVER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("Respone: "+response);
                        Intent intent1 = new Intent(getApplicationContext(), ClassroomFeedActivity.class);
                        intent1.putExtra("courseId", enrollClass.getCourseObjectId());
                        intent1.putExtra("response", response);
                        System.out.println("CourseId: "+ enrollClass.getCourseObjectId() + " Printing at announcement main activity");
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
                        System.out.println("CourseId: "+ courseId + " Printing at enroll onclicked activity inside volley function");
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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleview_enrolled_class_activity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(enrolledClassRecycleviewAdapter);

    }
    void setUp(String response)
    {
        System.out.println(response);
        Object object = new Object();
        try {
            object = new JSONParser().parse(response);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
//                JSONObject jsonObject = (JSONObject) object;
        JSONArray jsonArray = (JSONArray) object;
        Iterator iterator = jsonArray.iterator();
        while (iterator.hasNext())
        {
            Iterator<Map.Entry> iterator1 = ((Map) iterator.next()).entrySet().iterator();
            EnrollClass enrollClass = new EnrollClass();
            while (iterator1.hasNext())
            {
                Map.Entry pair = iterator1.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());

                if(pair.getKey().equals("course_code"))
                {
                    enrollClass.setCourseCode(pair.getValue().toString());
                }
                else if(pair.getKey().equals("course_name"))
                {
                    enrollClass.setCourseName(pair.getValue().toString());
                }
                else if(pair.getKey().equals("count"))
                {
                    enrollClass.setTotalUsers(Integer.parseInt(pair.getValue().toString()));
                }
                else if(pair.getKey().equals("_id"))
                {
                    System.out.println("YES. Found courseId,. Printing at enrolledClassActivity");
                    enrollClass.setCourseObjectId(pair.getValue().toString());
                }
                else if(pair.getKey().toString().trim().equals("instructor"))
                {
                    System.out.println(pair.getValue().toString()+" printing at parsing");
                    enrollClass.setInstructorName(pair.getValue().toString());
                }
            }
            enrollClassList.add(enrollClass);
        }
    }

    }
