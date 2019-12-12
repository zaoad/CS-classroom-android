package com.example.cseducation.Class;

public class EnrollClass {
    public String courseCode, courseName, instructorName, courseObjectId;
    public int totalUsers;

    public EnrollClass()
    {

    }
    public EnrollClass(String courseObjectId, String courseCode, String courseName, String instructorName, int total_users)
    {
        this.courseObjectId = courseObjectId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.instructorName= instructorName;
        this.totalUsers = total_users;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getCourseObjectId() {
        return courseObjectId;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public void setCourseObjectId(String courseObjectId) {
        this.courseObjectId = courseObjectId;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }
}
