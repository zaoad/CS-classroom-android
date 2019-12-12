package com.example.cseducation.Adapters;

import android.support.annotation.AnimatorRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cseducation.Class.Announcement;
import com.example.cseducation.R;

import java.util.List;

public class ClassroomFeedRecycleViewAdapter  extends RecyclerView.Adapter<ClassroomFeedRecycleViewAdapter.ClassroomFeedHolder> {

    List<Announcement> announcementList;
    OnitemClickListenerClassroomFeed onitemClickListenerClassroomFeed;
    public ClassroomFeedRecycleViewAdapter(List<Announcement> announcements, OnitemClickListenerClassroomFeed onitemClickListenerClassroomFeed)
    {
        this.announcementList = announcements;
        this.onitemClickListenerClassroomFeed = onitemClickListenerClassroomFeed;
    }

    @NonNull
    @Override
    public ClassroomFeedHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_classroom_feed, viewGroup, false);
        return new ClassroomFeedHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassroomFeedHolder classroomFeedHolder, int i) {
        Announcement announcement = announcementList.get(i);
        classroomFeedHolder.authorTxtVw.setText(announcement.getAuthor());
        classroomFeedHolder.bodyTxtVw.setText(announcement.getPostText());
        classroomFeedHolder.bind(announcement, this.onitemClickListenerClassroomFeed);
    }

    @Override
    public int getItemCount() {
        return announcementList.size();
    }

    public static class ClassroomFeedHolder extends RecyclerView.ViewHolder
    {
        protected TextView authorTxtVw, bodyTxtVw;
        private ImageButton commentImgBtn;

        public ClassroomFeedHolder(@NonNull View itemView) {
            super(itemView);

            authorTxtVw = (TextView) itemView.findViewById(R.id.username_layout_classroom_feed);
            bodyTxtVw = (TextView) itemView.findViewById(R.id.post_body_layout_classroom_feed);
            commentImgBtn = (ImageButton) itemView.findViewById(R.id.comment_btn_layout_classroom_feed);
        }
        public void bind(final Announcement announcement, final OnitemClickListenerClassroomFeed onitemClickListenerClassroomFeed)
        {
            commentImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onitemClickListenerClassroomFeed.OnItemClick(announcement);
                }
            });
        }
    }

}
