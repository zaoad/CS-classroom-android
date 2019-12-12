package com.example.cseducation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.cseducation.Class.StaticGlobal.Tanvir;

public class ShowArticleActivity extends DrawerActivity {

    TextView title,body,date,author;
    JSONObject article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContent(R.layout.activity_show_article,getApplicationContext());

        try {
            article=new JSONObject(getIntent().getExtras().getString("article"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        title=(TextView)findViewById(R.id.show_article_title);
        body=(TextView)findViewById(R.id.show_article_body);
        date=(TextView)findViewById(R.id.show_article_date);
        author=(TextView)findViewById(R.id.show_article_author);

        try {
            title.setText(article.getString("title"));
            body.setText(article.getString("body"));
            date.setText(article.getString("date"));
            author.setText(article.getString("author"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
