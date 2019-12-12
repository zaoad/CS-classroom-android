package com.example.cseducation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AnnouncementCommentActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_announcement_comment);
        super.setContent(R.layout.activity_announcement_comment,getApplicationContext());


    }
}
