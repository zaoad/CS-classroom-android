package com.example.cseducation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BlogActivity extends DrawerActivity {

    Button myBlog,search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContent(R.layout.activity_blog,getApplicationContext());

        myBlog = (Button)findViewById(R.id.blog_my_blog);
        myBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MyBlogActivity.class);
                startActivity(intent);
            }
        });

        search=(Button)findViewById(R.id.blog_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SearchBlogActivity.class);
                startActivity(intent);
            }
        });
    }
}
