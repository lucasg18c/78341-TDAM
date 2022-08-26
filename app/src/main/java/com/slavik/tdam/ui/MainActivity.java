package com.slavik.tdam.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.slavik.tdam.R;
import com.slavik.tdam.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, HomeFragment.class, null)
                    .commit();
        }
    }
}