package com.nik.movieslistapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nik.movieslistapp.fragments.MoviesListFragment;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind the view using butterknife
        ButterKnife.bind(this);

        Fragment fragment= new MoviesListFragment();
        loadFragment(fragment);
    }

    //load fragment
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
//            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return true;
        }
        return false;
    }
}
