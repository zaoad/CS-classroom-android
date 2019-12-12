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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import static com.example.cseducation.Class.StaticGlobal.SERVER;
import static com.example.cseducation.Class.StaticGlobal.Tanvir;

public class JoinClassActivity extends DrawerActivity {

    Button submit_btn;
    EditText join_code_edtxt;
    TextView join_msg_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_join_class);
        super.setContent(R.layout.activity_join_class,getApplicationContext());

        //initialized...
        submit_btn = (Button) findViewById(R.id.submit_btn_join_class_acitvity);
        join_code_edtxt = (EditText)findViewById(R.id.join_code_edtxt_join_class_activity);
        join_msg_view = (TextView) findViewById(R.id.join_msg_join_class_activity);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest sr = new StringRequest(Request.Method.POST,SERVER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("here is the repsonse: "+ response.toString());
                        if(response.toString().equals("OK")){
                            Intent intent = new Intent(getApplicationContext(), ClassroomActivity.class);
                            startActivity(intent);
                        }
                        else{
                            join_msg_view.setText(response.toString());
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

                        params.put("request","join_class");
                        params.put("courseId",join_code_edtxt.getText().toString());
                        params.put("username",userName.toString());
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
