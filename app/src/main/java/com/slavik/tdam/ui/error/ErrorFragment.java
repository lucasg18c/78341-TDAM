package com.slavik.tdam.ui.error;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.slavik.tdam.R;
import com.slavik.tdam.ui.MainActivity;
import com.slavik.tdam.ui.home.HomeFragment;

public class ErrorFragment extends Fragment {

    private ErrorViewModel mViewModel;

    public static ErrorFragment newInstance() {
        return new ErrorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_error, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ErrorViewModel.class);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) requireActivity())
                .isConnected().observe(getViewLifecycleOwner(), connected -> {
                    if (connected) {
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container_view, HomeFragment.class, null)
                                .commit();
                    }
                });
    }
}