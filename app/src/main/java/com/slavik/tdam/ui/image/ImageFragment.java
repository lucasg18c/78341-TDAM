package com.slavik.tdam.ui.image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.slavik.tdam.R;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.ui.MainActivity;

import java.util.Calendar;

public class ImageFragment extends Fragment {

    private ImageViewModel mViewModel;
    private TextView lblCreated;
    private TextView lblDescription;
    private TextView lblCommentsCount;
    private ImageView img;
    private CommentAdapter adapter;
    private View divider;
    private Photo currentPhoto;
    private SwipeRefreshLayout strPhoto;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image, container, false);

        Photo photo = ((MainActivity) requireActivity()).getCurrentPhoto();
        currentPhoto = photo;

        ((TextView) v.findViewById(R.id.lblTitle)).setText(photo.getTitle());

        lblCreated = v.findViewById(R.id.lblCreated);
        lblDescription = v.findViewById(R.id.lblDescription);
        lblCommentsCount = v.findViewById(R.id.lblCommentsCount);
        divider = v.findViewById(R.id.dividerComments);
        strPhoto = v.findViewById(R.id.strPhoto);

        img = v.findViewById(R.id.imgPhoto);
        Bitmap bm = photo.getLowQuality();
        if (bm != null) {
            img.setImageBitmap(bm);
        }

        RecyclerView rv = v.findViewById(R.id.listComments);
        LinearLayoutManager lm = new LinearLayoutManager(requireContext());

        adapter = new CommentAdapter();
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

        v.findViewById(R.id.btnShare).setOnClickListener(b -> {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_title));
            email.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text));
            String path = MediaStore.Images.Media.insertImage(requireActivity().getContentResolver(), currentPhoto.getHighQuality(), currentPhoto.getTitle(), currentPhoto.getDescription() == null ? "" : currentPhoto.getDescription());
            Uri uri = Uri.parse(path);

            email.putExtra(Intent.EXTRA_STREAM, uri);
            email.setType("image/jpg");

            startActivity(Intent.createChooser(email, getString(R.string.send_by_email)));
        });

        v.findViewById(R.id.btnBack).setOnClickListener(
                b -> requireActivity().getSupportFragmentManager().popBackStack());

        img.setOnClickListener(b -> {
            String path = MediaStore.Images.Media.insertImage(requireActivity().getContentResolver(), currentPhoto.getHighQuality(), currentPhoto.getTitle(), currentPhoto.getDescription() == null ? "" : currentPhoto.getDescription());
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ImageViewModel.class);
        mViewModel.init(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel.photo().observe(getViewLifecycleOwner(), photo -> {
            strPhoto.setRefreshing(false);
            currentPhoto = photo;

            Calendar c = photo.getPosted();
            String date = c.get(Calendar.DAY_OF_MONTH)
                    + "/" + c.get(Calendar.MONTH)
                    + "/" + c.get(Calendar.YEAR);

            String uploaded = getString(R.string.uploaded) + " " + date;
            lblCreated.setText(uploaded);

            lblDescription.setText(photo.getDescription());
            if (photo.getDescription().length() == 0) {
                lblDescription.setVisibility(View.GONE);
            }

            divider.setVisibility(photo.getCommentsCount() == 0 ? View.GONE : View.VISIBLE);
            if (photo.getCommentsCount() == 0) {
                lblCommentsCount.setText(R.string.no_comments);
            } else {
                String commentCount = photo.getCommentsCount() + " " + getString(R.string.comments);
                lblCommentsCount.setText(commentCount);
            }

            Bitmap bm = photo.getHighQuality();
            if (bm != null) {
                img.setImageBitmap(bm);
            }
        });

        mViewModel.comments().observe(getViewLifecycleOwner(),
                comments -> {
                    strPhoto.setRefreshing(false);
                    adapter.setComments(comments);
                    divider.setVisibility(comments.size() == 0 ? View.GONE : View.VISIBLE);
                    if (comments.size() == 0) {
                        lblCommentsCount.setText(R.string.no_comments);
                    } else {
                        String commentCount = comments.size() + " " + getString(R.string.comments);
                        lblCommentsCount.setText(commentCount);
                    }
                });

        strPhoto.setOnRefreshListener(() -> mViewModel.fetchInfo());
    }
}