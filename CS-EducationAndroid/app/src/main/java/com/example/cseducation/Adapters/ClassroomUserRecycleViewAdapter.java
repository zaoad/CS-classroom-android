package com.example.cseducation.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cseducation.Class.ClassroomUser;
import com.example.cseducation.R;

import java.util.List;

public class ClassroomUserRecycleViewAdapter extends RecyclerView.Adapter<ClassroomUserRecycleViewAdapter.ClassroomUserHolder>{

    List<ClassroomUser> classroomUserList;

    public  ClassroomUserRecycleViewAdapter(List<ClassroomUser> classroomUsers)
    {
        this.classroomUserList = classroomUsers;
    }

    @NonNull
    @Override
    public ClassroomUserHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_classroom_user, viewGroup, false);
        return new ClassroomUserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassroomUserHolder classroomUserHolder, int i) {
        ClassroomUser classroomUser = this.classroomUserList.get(i);
        classroomUserHolder.userNameTxt.setText(classroomUser.getUserName());
    }

    @Override
    public int getItemCount() {
        return this.classroomUserList.size();
    }

    public static class ClassroomUserHolder extends RecyclerView.ViewHolder
    {
        TextView userNameTxt;
        public ClassroomUserHolder(@NonNull View itemView) {
            super(itemView);
            userNameTxt = (TextView) itemView.findViewById(R.id.user_name_classroom_user);
        }
    }

}
