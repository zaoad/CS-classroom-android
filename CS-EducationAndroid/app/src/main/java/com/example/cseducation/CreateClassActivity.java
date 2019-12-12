package com.example.cseducation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class CreateClassActivity extends DrawerActivity {
    Button create_btn;
    EditText course_id_edtxt, course_name_edtxt;
    TextView msg_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_class);
        super.setContent(R.layout.activity_create_class,getApplicationContext());

        //initialized ..
        create_btn = (Button) findViewById(R.id.create_btn_create_class_acitvity);
        course_id_edtxt = (EditText) findViewById(R.id.course_id_txt_create_class_activity);
        course_name_edtxt = (EditText) findViewById(R.id.course_name_txt_create_class_activity);
        msg_view = (TextView) findViewById(R.id.msg_create_class_activity);

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                StringRequest sr = new StringRequest(Request.Method.POST,SERVER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.toString().equals("OK")){
//                            SharedPreferences pref = getApplicationContext().getSharedPreferences("USERINFO", MODE_PRIVATE);
//                            SharedPreferences.Editor editor = pref.edit();
//                            editor.putString("username", username.getText().toString());
//                            editor.commit();
//                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
                            Intent intent = new Intent(getApplicationContext(), ClassroomActivity.class);
                            startActivity(intent);

                        }
                        else{
                            msg_view.setText(response.toString());
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
                        params.put("request","create_class");
                        params.put("course_code",course_id_edtxt.getText().toString());
                        params.put("course_name",course_name_edtxt.getText().toString());
                        params.put("username", userName.toString());
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
