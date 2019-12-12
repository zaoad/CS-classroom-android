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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.cseducation.Class.StaticGlobal.SERVER;
import static com.example.cseducation.Class.StaticGlobal.Tanvir;

public class MyAccountActivity extends DrawerActivity {

    EditText password ;
    EditText confirm_password ;
    EditText email;
    EditText institute;
    Button changeInfo;
    TextView msg;
    String occupation ,birthday,country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContent(R.layout.activity_my_account,getApplicationContext());

        password = (EditText)findViewById(R.id.my_account_password);
        confirm_password = (EditText)findViewById(R.id.my_account_confirm_password);
        email = (EditText)findViewById(R.id.my_account_email);
        institute = (EditText)findViewById(R.id.my_account_institute);
        changeInfo = (Button)findViewById(R.id.my_account_changeinfo);
        msg = (TextView)findViewById(R.id.my_account_msg);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(response.toString());
                    Log.d(Tanvir,response.toString());

                    email.setText(jsonObj.getString("email"));
                    institute.setText(jsonObj.getString("institute"));
                    occupation = jsonObj.getString("occupation");
                    birthday = jsonObj.getString("birthday");
                    country = jsonObj.getString("country");
                } catch (JSONException e) {
                    e.printStackTrace();
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
                params.put("request","change-info");
                SharedPreferences pref = getApplicationContext().getSharedPreferences("USERINFO", MODE_PRIVATE);
                params.put("username",pref.getString("username",""));
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


        changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest sr = new StringRequest(Request.Method.POST,SERVER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            if(response.toString().equals("ok")){
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            }
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
                        params.put("request","change-info-validation");
                        params.put("password",password.getText().toString());
                        params.put("c_password",confirm_password.getText().toString());
                        params.put("email",email.getText().toString());
                        params.put("institute",institute.getText().toString());
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("USERINFO", MODE_PRIVATE);
                        params.put("username",pref.getString("username",""));
                        params.put("occupation",occupation);
                        params.put("birthday",birthday);
                        params.put("country",country);
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
