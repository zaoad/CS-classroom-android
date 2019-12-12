package com.example.cseducation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.cseducation.Class.StaticGlobal.Tanvir;

public class HomeActivity extends DrawerActivity {

    Button simulations,onlineJudge,attendanceTracking,classroom,blog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContent(R.layout.activity_home, getApplicationContext());

        simulations = (Button)findViewById(R.id.home_activity_simulation);
        simulations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SimulationsActivity.class);
                startActivity(intent);
            }
        });

        onlineJudge = (Button)findViewById(R.id.home_activity_online_judge);
        onlineJudge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OnlineJudgeActivity.class);
                startActivity(intent);
            }
        });

        attendanceTracking = (Button)findViewById(R.id.home_activity_attendance_tracking);
        attendanceTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AttendanceTrackingActivity.class);
                startActivity(intent);
            }
        });

        classroom = (Button)findViewById(R.id.home_activity_classroom);
        classroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ClassroomActivity.class);
                startActivity(intent);
            }
        });

        blog=(Button)findViewById(R.id.home_activity_blog);
        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),BlogActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
