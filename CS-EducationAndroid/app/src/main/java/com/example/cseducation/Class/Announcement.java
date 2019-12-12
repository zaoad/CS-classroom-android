package com.example.cseducation.Class;

import com.example.cseducation.DrawerActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Announcement {
    String postText, author, courseId, postId;
    Date date;
    public Announcement()
    {

    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(String date) throws ParseException {
        this.date = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss").parse(date);
    }
}
