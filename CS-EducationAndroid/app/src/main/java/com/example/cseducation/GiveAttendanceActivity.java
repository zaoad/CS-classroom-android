package com.example.cseducation;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static com.example.cseducation.Class.StaticGlobal.SERVER;
import static com.example.cseducation.Class.StaticGlobal.Tanvir;

public class GiveAttendanceActivity extends DrawerActivity {

    String courseID,IP,mac;
    Button give;
    TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContent(R.layout.activity_give_attendance,getApplicationContext());

        WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        mac = info.getMacAddress();


        courseID = getIntent().getExtras().getString("id");

        msg = (TextView)findViewById(R.id.give_attendance_msg);
        give = (Button)findViewById(R.id.give_attendance_button);
        give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest sr = new StringRequest(Request.Method.POST,SERVER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.toString().equals("notok")){
                            msg.setText("Class is off now");
                            return;
                        }
                        if(response.toString().equals("nomac")){
                            msg.setText("Same device cannot be used twice");
                            return;
                        }
                        IP = response.toString();
                        giveAttendance();
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
                        params.put("request","get-attendance-server");
                        params.put("id",courseID);
                        params.put("mac",mac);
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

    protected void giveAttendance(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("USERINFO", MODE_PRIVATE);
        new Client(IP,pref.getString("username","")).start();
        msg.setText("Attendance Given");

    }


    class Client extends Thread{
        String IP,username;
        Client(String IP,String username){
            if(IP.startsWith("["))
                IP = IP.substring(2,IP.length()-2);
            this.IP=IP;
            this.username=username;
        }
        public void run() {
                try {

                    Socket s = new Socket(IP, 1234);
                    DataInputStream is = new DataInputStream(s.getInputStream());
                    DataOutputStream os = new DataOutputStream(s.getOutputStream());

                    os.writeUTF(username + "##" + mac);

                    is.close();
                    os.close();
                    s.close();
                }catch (Exception e) {
                    msg.setText("Could not reach Server");
                }
        }
    }

}
