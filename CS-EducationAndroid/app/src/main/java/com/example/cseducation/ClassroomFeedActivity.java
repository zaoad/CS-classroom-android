package com.example.cseducation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
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
import com.example.cseducation.Adapters.OnitemClickListenerClassroomFeed;
import com.example.cseducation.Class.Announcement;
import com.example.cseducation.Class.EnrollClass;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.example.cseducation.Class.StaticGlobal.SERVER;
import static com.example.cseducation.Class.StaticGlobal.Tanvir;

public class ClassroomFeedActivity extends DrawerActivity {

    ImageButton forum_imbtn, group_imbtn, comment_imbtn, home_imbtn;
    FloatingActionButton announcement_fab;
    TextView post_body_txt_view, username_view;
    static String courseId, userName, userID;
    String response;
    Map course_data;

    List<Announcement> announcementList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContent(R.layout.activity_classroom_feed,getApplicationContext());

        Intent intent = getIntent();
        courseId = intent.getStringExtra("courseId");
        this.response = intent.getStringExtra("response");
        System.out.println("CourseId: "+ courseId + " Printing at feed main activity");

        SharedPreferences pref = getApplicationContext().getSharedPreferences("USERINFO", MODE_PRIVATE);
        this.userName = pref.getString("username", null);

        // initialize
        this.INIT();

        try {
            this.ShowFEED();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.SETUP();

    }

    void SETUP()
    {
        ClassroomFeedRecycleViewAdapter classroomFeedRecycleViewAdapter = new ClassroomFeedRecycleViewAdapter(this.announcementList, new OnitemClickListenerClassroomFeed() {
            @Override
            public void OnItemClick(Announcement announcement) {
                Toast.makeText(getBaseContext(), "Comment Button is clicked", Toast.LENGTH_LONG).show();
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleview_classroom_feed_activity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(classroomFeedRecycleViewAdapter);
    }


    void ShowFEED() throws ParseException {
        System.out.println("Here at feed activity :  "+response);
        Object object = new Object();
        try {
            object = new JSONParser().parse(this.response);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) object;

        this.course_data = (Map) jsonObject.get("course_related_data");

        JSONArray jsonArray = (JSONArray) jsonObject.get("classroom_related_data");
        Iterator iterator = jsonArray.iterator();
        while (iterator.hasNext())
        {
            Iterator<Map.Entry> iterator1 = ((Map) iterator.next()).entrySet().iterator();
            Announcement announcement = new Announcement();
            while (iterator1.hasNext())
            {
                Map.Entry pair = iterator1.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());

                if(pair.getKey().equals("authors"))
                {
                    announcement.setAuthor(pair.getValue().toString());
                }
                else if(pair.getKey().equals("posttext"))
                {
                    announcement.setPostText(pair.getValue().toString());
                }
                else if(pair.getKey().equals("course_id"))
                {
                    announcement.setCourseId(pair.getValue().toString());
                }
                else if(pair.getKey().equals("_id"))
                {
//                    System.out.println("YES. Found courseId,. Printing at enrolledClassActivity");
                        announcement.setPostId(pair.getValue().toString());
                }
                else if(pair.getKey().toString().trim().equals("date"))
                {
                    System.out.println(pair.getValue().toString()+" printing at parsing");
//                    enrollClass.setInstructorName(pair.getValue().toString());
                    announcement.setDate(pair.getValue().toString());
                }
            }
            announcementList.add(announcement);
        }
    }



    void INIT()
    {
        forum_imbtn = (ImageButton) findViewById(R.id.forum_btn_classroom_activity);
        group_imbtn = (ImageButton) findViewById(R.id.people_btn_classroom_activity);
        comment_imbtn = (ImageButton) findViewById(R.id.comment_btn_layout_classroom_feed);
        announcement_fab = (FloatingActionButton) findViewById(R.id.annoucement_fab_classroom_feed_activity);
        post_body_txt_view = (TextView) findViewById(R.id.post_body_layout_classroom_feed);
        username_view = (TextView) findViewById(R.id.username_layout_classroom_feed);
        home_imbtn = (ImageButton) findViewById(R.id.home_btn_classroom_activity);

        forum_imbtn.setOnClickListener(new View.OnClickListener() {
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
        announcement_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AnnouncementActivity.class);
                intent.putExtra("courseId", courseId);
                System.out.println(courseId +" Before clicking fab button");
                startActivity(intent);
            }
        });
        group_imbtn.setOnClickListener(new View.OnClickListener() {
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
        home_imbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClassroomActivity.class);
                startActivity(intent);
            }
        });
    }

}
