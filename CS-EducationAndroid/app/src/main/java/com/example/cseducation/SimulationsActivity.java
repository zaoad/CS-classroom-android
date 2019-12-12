package com.example.cseducation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
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

import static com.example.cseducation.Class.StaticGlobal.SERVER;
import static com.example.cseducation.Class.StaticGlobal.Tanvir;

public class SimulationsActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContent(R.layout.activity_simulations,getApplicationContext());

        final Vector<String> m= new Vector<>();
        final Vector<String> simulations= new Vector<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, m);


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                StringTokenizer stringTokenizer = new StringTokenizer(response.toString(),"-");
                while (stringTokenizer.hasMoreElements()){
                    try {
                        String token = stringTokenizer.nextToken();
                        simulations.add(token);
                        JSONObject jsonObject=new JSONObject(token);
                        String type = jsonObject.getString("type");

                        if (type.equals("ml")){
                            type="Machine Learning";
                        }
                        else if (type.equals("la")){
                            type="Linear Algebra";
                        }
                        String algo = jsonObject.getString("algo");
                        if(algo.equals("ev")){
                            algo = "Eigen Value";
                        }
                        else if(algo.equals("gs")){
                            algo = "Gauss Elimination";
                        }
                        else if(algo.equals("gj")){
                            algo = "Gauss Jordan Elimination";
                        }
                        else if(algo.equals("km")){
                            algo = "K Means";
                        }
                        else if(algo.equals("lr")){
                            algo = "Linear Regression";
                        }
                        else if(algo.equals("lgr")){
                            algo = "Logistic Regression";
                        }
                        m.add(type+" : "+algo+"\n"+jsonObject.getString("algo_id"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                ListView listView = (ListView) findViewById(R.id.simulation_list);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            launchSimulation(simulations.get(position));
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                params.put("request","get-simulations");
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

    }

    protected void launchSimulation(String simualtion) throws JSONException {
        Log.d(Tanvir,simualtion);
        JSONObject jsonObject=new JSONObject(simualtion);
        String url = null;
        if(jsonObject.getString("type").equals("ml")){
            if (jsonObject.getString("algo").equals("km")){
                url = SERVER+"simulation/ml/k-means-clustering/"+jsonObject.getString("algo_id");
            }
            if (jsonObject.getString("algo").equals("lr")){
                url = SERVER+"simulation/ml/linear-regression/"+jsonObject.getString("algo_id");
            }
            if (jsonObject.getString("algo").equals("lgr")){
                url = SERVER+"simulation/ml/logistic-regression/"+jsonObject.getString("algo_id");
            }
        }
        if(jsonObject.getString("type").equals("la")) {
            if (jsonObject.getString("algo").equals("ev")){
                url = SERVER+"simulation/la/eigen-value/"+jsonObject.getString("algo_id");
            }
            if (jsonObject.getString("algo").equals("gs")){
                url = SERVER+"simulation/la/gauss-elimination/"+jsonObject.getString("algo_id");
            }
            if (jsonObject.getString("algo").equals("gj")){
                url = SERVER+"simulation/la/gauss-jordan-elimination/"+jsonObject.getString("algo_id");
            }


        }
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));

    }
}
