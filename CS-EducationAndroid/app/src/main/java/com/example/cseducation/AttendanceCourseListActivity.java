package com.example.cseducation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

import static com.example.cseducation.Class.StaticGlobal.INSTRUCTOR;
import static com.example.cseducation.Class.StaticGlobal.SERVER;
import static com.example.cseducation.Class.StaticGlobal.Tanvir;

public class AttendanceCourseListActivity extends DrawerActivity {

    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContent(R.layout.activity_attendance_course_list,getApplicationContext());

        mode = getIntent().getExtras().getInt("mode");

        final Vector<String> courses= new Vector<>();
        final Vector<String> m= new Vector<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, m);




        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                StringTokenizer stringTokenizer = new StringTokenizer(response.toString(), "#");

                while (stringTokenizer.hasMoreElements()){
                    try {
                        String token = stringTokenizer.nextToken();
                        JSONObject jsonObject = new JSONObject(token);
                        m.add(jsonObject.getString("course_name")+"\n"+jsonObject.getString("course_code"));
                        courses.add(jsonObject.getString("_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ListView listView = (ListView) findViewById(R.id.attendance_course_list);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(mode == INSTRUCTOR){
                            Intent intent = new Intent(getApplicationContext(),TakeAttendanceActivity.class);
                            intent.putExtra("id",courses.get(position));
                            startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(getApplicationContext(),GiveAttendanceActivity.class);
                            intent.putExtra("id",courses.get(position));
                            startActivity(intent);
                        }
                    }
                });
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
                params.put("request","course-list");
                SharedPreferences pref = getApplicationContext().getSharedPreferences("USERINFO", MODE_PRIVATE);
                params.put("username",pref.getString("username",""));
                params.put("mode",Integer.toString(mode));
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
}
