package com.example.cseducation;

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
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static com.example.cseducation.Class.StaticGlobal.SERVER;
import static com.example.cseducation.Class.StaticGlobal.SERVER_PRFIX;
import static com.example.cseducation.Class.StaticGlobal.Tanvir;

public class TakeAttendanceActivity extends DrawerActivity {

    TextView currentServer;
    Button toggle;
    Server thread = null;
    String courseID = null;
    boolean start_flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContent(R.layout.activity_take_attendance,getApplicationContext());

        courseID = getIntent().getExtras().getString("id");
        currentServer = (TextView)findViewById(R.id.take_attendance_server_status);
        toggle = (Button)findViewById(R.id.take_attendance_toggle);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(start_flag){
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest sr = new StringRequest(Request.Method.POST,SERVER, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(Tanvir,response.toString());
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
                            params.put("request","set-attendance-server");
                            params.put("ip",getIp());
                            params.put("id",courseID);
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

                    thread = new Server();
                    thread.start();
                    currentServer.setText("Stop");
                    toggle.setBackgroundResource(R.drawable.cross);
                    start_flag = false;
                }

                else {


                    if(thread!=null){
                        try {

                            thread.stop = true;
                            new FakeClient().start();

                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            StringRequest sr = new StringRequest(Request.Method.POST,SERVER, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d(Tanvir,response.toString());
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
                                    params.put("request","delete-attendance-server");
                                    params.put("id",courseID);
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
                        catch (Exception e){
                            Log.d(Tanvir,e.toString());
                        }
                        currentServer.setText("Start");
                        toggle.setBackgroundResource(R.drawable.tick);


                    }
                    start_flag = true;
                }
            }
        });




    }

    String getIp(){
        Vector<String> result = new Vector<>();
        Enumeration<NetworkInterface> n = null;
        try {
            n = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        for (; n.hasMoreElements();)
        {
            NetworkInterface e = n.nextElement();

            Enumeration<InetAddress> a = e.getInetAddresses();
            for (; a.hasMoreElements();)
            {
                InetAddress addr = a.nextElement();
                String ip = addr.getHostAddress();
                if(ip.startsWith(SERVER_PRFIX)){
                    return ip;
                }
            }
        }
        return "";
    }

    class Server extends Thread{
        boolean stop = false;
        public void run() {
            while (true){
                try {
                    ServerSocket s = new ServerSocket(1234);
                    Socket s1 = s.accept();
                    DataInputStream is = new DataInputStream(s1.getInputStream());
                    final DataOutputStream os = new DataOutputStream(s1.getOutputStream());
                    final String user = is.readUTF();
                    Log.d(Tanvir,user);

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest sr = new StringRequest(Request.Method.POST,SERVER, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                            } catch (Exception e) {
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
                            params.put("request","give-attendance");
                            params.put("id",courseID);
                            params.put("user",user);
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



                    is.close();
                    os.close();
                    s1.close();
                    s.close();
                    sleep(100);
                    if(stop)break;

                } catch (IOException e) {
                } catch (Exception e) {
                }
            }
        }
    }



    class FakeClient extends Thread{
        public void run() {
            try {
                Socket s = new Socket("localhost", 1234);
                DataInputStream is = new DataInputStream(s.getInputStream());
                DataOutputStream os=new DataOutputStream(s.getOutputStream());

                os.writeUTF("fake");
                is.close();
                os.close();
                s.close();

            } catch (IOException e) {
            } catch (Exception e) {
            }
        }
    }
}
