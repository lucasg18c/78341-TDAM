package com.slavik.tdam.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.slavik.tdam.R;
import com.slavik.tdam.data.repository.IRepository;
import com.slavik.tdam.data.repository.MockRepository;
import com.slavik.tdam.data.repository.Repository;
import com.slavik.tdam.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    private IRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        repository = new Repository(queue);
//        repository = new MockRepository();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, HomeFragment.class, null)
                    .commit();
        }
    }

    public IRepository getRepository() {
        return repository;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll("");
        }
    }
}