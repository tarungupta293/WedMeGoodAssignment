package com.example.tarun.wedmegoodassignment.ui.activities;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tarun.wedmegoodassignment.R;
import com.example.tarun.wedmegoodassignment.beans.MovieResponse;
import com.example.tarun.wedmegoodassignment.beans.TVResponse;
import com.example.tarun.wedmegoodassignment.ui.fragments.MoviesFragment;
import com.example.tarun.wedmegoodassignment.ui.fragments.TVFragment;
import com.example.tarun.wedmegoodassignment.utils.AppUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FavouriteListActivity extends AppCompatActivity {

    public List<MovieResponse.Result> favouriteMovieList = new ArrayList<>();
    public List<TVResponse.Result> favouriteTvList = new ArrayList<>();
    private boolean isMovieDataNeedToFetch;
    private Button btnMovies,btnTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);
        setTitle("Favourites");
        if (getIntent()!=null) {
            favouriteMovieList = (List<MovieResponse.Result>) getIntent().getExtras().get("favouriteMovieList");
            favouriteTvList = (List<TVResponse.Result>) getIntent().getExtras().get("favouriteTVList");
        }
        UIComponents();
        isMovieDataNeedToFetch = false;
        enableDisableButtons(true);
        launchMoviesFragment();

    }

    private void enableDisableButtons(boolean isNeedToEnable) {
        btnTV.setEnabled(false);
        btnMovies.setEnabled(false);
        btnTV.setBackground(ContextCompat.getDrawable(this,R.drawable.tv_unselected_background));
        btnMovies.setBackground(ContextCompat.getDrawable(this,R.drawable.movie_unselected_background));
        btnMovies.setTextColor(ContextCompat.getColor(this,R.color.black));
        btnTV.setTextColor(ContextCompat.getColor(this,R.color.black));
        if (isNeedToEnable) {
            if (isMovieDataNeedToFetch) {
                btnMovies.setEnabled(true);
                btnTV.setBackground(ContextCompat.getDrawable(this,R.drawable.tv_selected_background));
                btnTV.setTextColor(ContextCompat.getColor(this,R.color.white));
            }
            else {
                btnTV.setEnabled(true);
                btnMovies.setBackground(ContextCompat.getDrawable(this,R.drawable.movie_selected_background));
                btnMovies.setTextColor(ContextCompat.getColor(this,R.color.white));
            }
        }
    }

    private void UIComponents() {
        btnMovies = findViewById(R.id.btnMovies);
        btnTV = findViewById(R.id.btnTV);
        btnMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMovieDataNeedToFetch = false;
                enableDisableButtons(true);
                if (favouriteMovieList==null || favouriteMovieList.isEmpty())
                    Toast.makeText(FavouriteListActivity.this,"No favourite movie list available.",Toast.LENGTH_SHORT).show();
                else {
                    launchMoviesFragment();
                }
            }
        });

        btnTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMovieDataNeedToFetch = true;
                enableDisableButtons(true);
                if (favouriteTvList==null || favouriteTvList.isEmpty())
                    Toast.makeText(FavouriteListActivity.this,"No favourite tv series list available.",Toast.LENGTH_SHORT).show();
                else {
                    launchTVFragment();
                }
            }
        });
    }

    private void launchMoviesFragment() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isFromFavourite",true);
        MoviesFragment moviesFragment = new MoviesFragment();
        moviesFragment.setArguments(bundle);
        AppUtils.setFragment(R.id.parentLayout,getSupportFragmentManager(),moviesFragment,false);
    }

    private void launchTVFragment() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isFromFavourite",true);
        TVFragment tvFragment = new TVFragment();
        tvFragment.setArguments(bundle);
        AppUtils.setFragment(R.id.parentLayout,getSupportFragmentManager(),tvFragment,false);
    }
}
