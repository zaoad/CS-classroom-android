package com.example.cseducation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cseducation.Adapters.ClassroomFeedRecycleViewAdapter;
import com.example.cseducation.Adapters.ClassroomUserRecycleViewAdapter;
import com.example.cseducation.Adapters.OnitemClickListenerClassroomFeed;
import com.example.cseducation.Class.Announcement;
import com.example.cseducation.Class.ClassroomUser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.example.cseducation.Class.StaticGlobal.SERVER;
import static com.example.cseducation.Class.StaticGlobal.Tanvir;

public class ClassroomUsersActivity extends DrawerActivity {

    ImageButton homeImgBtn, forumImgBtn, groupImgBtn;
    TextView courseNameTxt, codeInsTxt;
    static String courseId;
    String response;
    Map course_data;
    List<ClassroomUser> classroomUserList = new ArrayList<>();
    String courseName, courseCode, instructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_classroom_users);
        super.setContent(R.layout.activity_classroom_users,getApplicationContext());
        Intent intent = getIntent();
        courseId = intent.getStringExtra("courseId");
        this.response = intent.getStringExtra("response");



        INIT();
        SHOW();
        SETUP();

        courseNameTxt.setText(courseCode +" : " + courseName);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("USERINFO", MODE_PRIVATE);
        String userName = pref.getString("username", null);

        if(userName.equals(instructor))
        {
            codeInsTxt.setText("Join Code :"+ courseId);
        }
        else
        {
            codeInsTxt.setText("Instructor : "+this.instructor);
        }

    }

    void SETUP()
    {
        ClassroomUserRecycleViewAdapter classroomUserRecycleViewAdapter = new ClassroomUserRecycleViewAdapter(this.classroomUserList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleview_classroom_user_activity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(classroomUserRecycleViewAdapter);
    }

    void SHOW()
    {
        System.out.println("Here at classroom user: " + response);
        Object object = new Object();
        try {
            object = new JSONParser().parse(this.response);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) object;

        this.courseName = (String) jsonObject.get("course_name");
        this.courseCode = (String) jsonObject.get("course_code");
        this.courseId = (String) jsonObject.get("_id");
        this.instructor = (String) jsonObject.get("instructor");

        JSONArray jsonArray = (JSONArray) jsonObject.get("enrolled");
        Iterator<String> iterator = jsonArray.iterator();
        while (iterator.hasNext())
        {
            ClassroomUser classroomUser = new ClassroomUser();
            while (iterator.hasNext()) {
                String userName = (String) iterator.next();
                classroomUser.setUserName(userName);
            }
            classroomUserList.add(classroomUser);
        }
    }

    void INIT()
    {
        homeImgBtn = (ImageButton) findViewById(R.id.home_btn_classroom_user_activity);
        forumImgBtn = (ImageButton) findViewById(R.id.forum_btn_classroom_user_activity);
        groupImgBtn = (ImageButton) findViewById(R.id.people_btn_classroom_user_activity);
        courseNameTxt = (TextView) findViewById(R.id.course_name_classroom_user_activity);
        codeInsTxt = (TextView) findViewById(R.id.class_code_classroom_user_activity);
        homeImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ClassroomActivity.class);
                startActivity(intent1);
            }
        });

        forumImgBtn.setOnClickListener(new View.OnClickListener() {
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

        groupImgBtn.setOnClickListener(new View.OnClickListener() {
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
    }


}
