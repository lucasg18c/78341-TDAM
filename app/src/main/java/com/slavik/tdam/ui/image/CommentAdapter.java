package com.slavik.tdam.ui.image;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slavik.tdam.R;
import com.slavik.tdam.model.Comment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Viewholder> {

    private List<Comment> comments = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.bind(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments == null ? 0 : comments.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        private final TextView lblAuthor;
        private final TextView lblCreated;
        private final TextView lblDescription;

        public Viewholder(@NonNull View v) {
            super(v);

            lblAuthor = v.findViewById(R.id.lblAuthor);
            lblCreated = v.findViewById(R.id.lblCreated);
            lblDescription = v.findViewById(R.id.lblDescription);
        }

        public void bind(Comment comment) {
            lblAuthor.setText(comment.getAuthor().getUserName());

            Calendar c = comment.getPosted();
            String date = c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR);

            lblCreated.setText(date);
            lblDescription.setText(comment.getContent());
        }
    }
}
