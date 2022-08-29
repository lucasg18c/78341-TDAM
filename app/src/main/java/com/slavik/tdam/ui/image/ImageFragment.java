package com.slavik.tdam.ui.image;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.slavik.tdam.R;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.ui.MainActivity;

import java.util.Calendar;

public class ImageFragment extends Fragment {

    private ImageViewModel mViewModel;
    private TextView lblAuthor;
    private TextView lblCreated;
    private TextView lblDescription;
    private TextView lblCommentsCount;
    private ImageView img;

    public static ImageFragment newInstance() {
        return new ImageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image, container, false);

        Photo photo = ((MainActivity) requireActivity()).getCurrentPhoto();

        ((TextView) v.findViewById(R.id.lblTitle)).setText(photo.getTitle());

        lblAuthor = v.findViewById(R.id.lblAuthor);
        lblCreated = v.findViewById(R.id.lblCreated);
        lblDescription = v.findViewById(R.id.lblDescription);
        lblCommentsCount = v.findViewById(R.id.lblCommentsCount);

        img = v.findViewById(R.id.imgPhoto);
        img.setImageBitmap(photo.getImage());

        v.findViewById(R.id.btnShare).setOnClickListener(b -> {
        });

        v.findViewById(R.id.btnBack).setOnClickListener(
                b -> requireActivity().getSupportFragmentManager().popBackStack());

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
            lblAuthor.setText("Por " + photo.getAuthor());

            Calendar c = photo.getDateUploaded();
            String date = c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR);
            lblCreated.setText("Subida " + date);

            lblDescription.setText(photo.getDescription());
            if (photo.getDescription().length() == 0) {
                lblDescription.setVisibility(View.GONE);
            }

            lblCommentsCount.setText(photo.getCommentCount() + " comentarios");

            if (photo.getImage() != null) {
                img.setImageBitmap(photo.getImage());
            }

        });
    }
}