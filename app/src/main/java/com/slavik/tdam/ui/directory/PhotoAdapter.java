package com.slavik.tdam.ui.directory;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slavik.tdam.R;
import com.slavik.tdam.model.Photo;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private final DirectoryFragment fragment;
    private List<Photo> photos;

    public PhotoAdapter(DirectoryFragment fragment) {
        this.fragment = fragment;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(photos.get(position));
    }

    @Override
    public int getItemCount() {
        return photos == null ? 0 : photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView lblPhotoTitle;
        private final TextView lblCommentsCount;
        private final ImageView imgPhoto;
        private Photo mPhoto;

        public ViewHolder(@NonNull View v) {
            super(v);

            lblPhotoTitle = v.findViewById(R.id.lblPhotoTitle);
            lblCommentsCount = v.findViewById(R.id.lblCommentsCount);
            imgPhoto = v.findViewById(R.id.imgPhoto);

            v.findViewById(R.id.card_photo)
                    .setOnClickListener(c -> fragment.onClickPhoto(mPhoto));
        }

        public void bind(Photo photo) {
            mPhoto = photo;

            lblPhotoTitle.setText(photo.getTitle());
            lblCommentsCount.setText(String.valueOf(photo.commentsCount()));

            Bitmap bm = photo.getLowQuality();

            if (bm != null) {
                imgPhoto.setImageBitmap(bm);
            }
        }
    }
}
