package com.example.cseducation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static com.example.cseducation.Class.StaticGlobal.INSTRUCTOR;
import static com.example.cseducation.Class.StaticGlobal.STUDENT;
import static com.example.cseducation.Class.StaticGlobal.Tanvir;

public class AttendanceTrackingActivity extends DrawerActivity {

    Button instructor;
    Button student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContent(R.layout.activity_attendance_tracking,getApplicationContext());

        instructor=(Button)findViewById(R.id.attendance_tracking_instructor);
        instructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AttendanceCourseListActivity.class);
                intent.putExtra("mode",INSTRUCTOR);
                startActivity(intent);
            }
        });

        student = (Button)findViewById(R.id.attendance_tracking_student);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AttendanceCourseListActivity.class);
                intent.putExtra("mode",STUDENT);
                startActivity(intent);
            }
        });

    }
}
