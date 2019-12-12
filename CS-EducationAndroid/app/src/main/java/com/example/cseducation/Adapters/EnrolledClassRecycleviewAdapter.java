package com.example.cseducation.Adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cseducation.Class.EnrollClass;
import com.example.cseducation.R;

import java.util.List;

public class EnrolledClassRecycleviewAdapter extends RecyclerView.Adapter<EnrolledClassRecycleviewAdapter.EnrolledClassHolder> {

    List<EnrollClass> enrollClassList;
    OnItemClickListenerEnrollClass onItemClickListenerEnrollClass;

    public EnrolledClassRecycleviewAdapter(List<EnrollClass> enrollClassList, OnItemClickListenerEnrollClass onItemClickListenerEnrollClass)
    {
        this.onItemClickListenerEnrollClass = onItemClickListenerEnrollClass;
        this.enrollClassList = enrollClassList;
    }
    @Override
    public EnrolledClassHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.enrolled_class_cardview, viewGroup, false);
        return new EnrolledClassHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EnrolledClassHolder viewHolder, int i) {
        EnrollClass enrollClass = enrollClassList.get(i);
        viewHolder.course_id.setText(enrollClass.courseCode);
        viewHolder.course_name.setText(enrollClass.courseName);
        viewHolder.total_count.setText(Integer.toString(enrollClass.totalUsers));
        System.out.println(enrollClass.instructorName+" printing at bindholder");
        viewHolder.instructor_name.setText(enrollClass.instructorName);
        viewHolder.bind(enrollClass, this.onItemClickListenerEnrollClass);
    }

    @Override
    public int getItemCount() {
        System.out.println(enrollClassList.size()+" printing at counting method of list length");
        return enrollClassList.size();
    }

    public static class EnrolledClassHolder extends RecyclerView.ViewHolder
    {
        protected TextView course_name, course_id, instructor_name, total_count;
        protected Button joinBtn;
        public EnrolledClassHolder(View v)
        {
            super(v);
            course_id = (TextView) v.findViewById(R.id.course_id_layout_enrolled_class);
            course_name = (TextView) v.findViewById(R.id.course_name_layout_enrolled_class);
            instructor_name = (TextView) v.findViewById(R.id.creator_name_layout_enrolled_class);
            total_count = (TextView) v.findViewById(R.id.total_erolled_user_layout_enrolled_class);
            joinBtn = (Button) v.findViewById(R.id.join_btn_layout_enrolled_class);
        }
        public void bind(final EnrollClass enrollClass, final OnItemClickListenerEnrollClass itemListener)
        {
            joinBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemListener.onItemClick(enrollClass);
                }
            });
        }

    }
}

