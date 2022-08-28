package com.slavik.tdam.ui.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slavik.tdam.R;
import com.slavik.tdam.model.Photoset;

import java.util.ArrayList;
import java.util.List;

public class PhotosetsAdapter extends RecyclerView.Adapter<PhotosetsAdapter.PhotosetsVH> {

    private final HomeFragment mFragment;
    private List<Photoset> mPhotosets = new ArrayList<>();

    public PhotosetsAdapter(HomeFragment fragment) {
        mFragment = fragment;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPhotosets(List<Photoset> photosets) {
        mPhotosets = photosets;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotosetsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_photoset, parent, false);

        return new PhotosetsVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosetsVH holder, int position) {
        holder.bind(mPhotosets.get(position));
    }

    @Override
    public int getItemCount() {
        return mPhotosets == null ? 0 : mPhotosets.size();
    }

    class PhotosetsVH extends RecyclerView.ViewHolder {

        private final TextView lblTitle;
        private final TextView lblPhotosCount;
        private final TextView lblDescription;
        private final TextView lblViewsCount;
        private final TextView lblCommentsCount;
        private final ImageView imgPhotoset;
        private Photoset mPhotoset;

        public PhotosetsVH(@NonNull View itemView) {
            super(itemView);

            lblTitle = itemView.findViewById(R.id.lblTitle);
            lblPhotosCount = itemView.findViewById(R.id.lblPhotosCount);
            lblDescription = itemView.findViewById(R.id.lblDescription);
            lblViewsCount = itemView.findViewById(R.id.lblViewsCount);
            lblCommentsCount = itemView.findViewById(R.id.lblCommentsCount);
            imgPhotoset = itemView.findViewById(R.id.imgPhotoset);

            itemView.findViewById(R.id.card_photoset).setOnClickListener(v -> {
                mFragment.onPhotosetClicked(mPhotoset);
            });
        }

        public void bind(Photoset photoset) {
            mPhotoset = photoset;
            lblTitle.setText(photoset.getTitle());
            lblPhotosCount.setText(photoset.getCountPhotos() == 0 ? "Sin" : String.valueOf(photoset.getCountPhotos()));
            lblDescription.setText(photoset.getDescription());
            lblViewsCount.setText(photoset.getCountViews());
            lblCommentsCount.setText(photoset.getCountComments());
            imgPhotoset.setImageBitmap(photoset.getPrimaryPhoto());
        }
    }
}
