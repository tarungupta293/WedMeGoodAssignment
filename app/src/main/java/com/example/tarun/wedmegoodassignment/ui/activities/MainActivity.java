package com.example.tarun.wedmegoodassignment.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.tarun.wedmegoodassignment.restapi.ApiClient;
import com.example.tarun.wedmegoodassignment.restapi.ApiInterface;
import com.example.tarun.wedmegoodassignment.ui.adapters.MovieAdapter;
import com.example.tarun.wedmegoodassignment.ui.fragments.MoviesFragment;
import com.example.tarun.wedmegoodassignment.ui.fragments.TVFragment;
import com.example.tarun.wedmegoodassignment.utils.AppUtils;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public List<MovieResponse.Result> moviesList = new ArrayList<>();
    public List<TVResponse.Result> tvList = new ArrayList<>();
    public List<MovieResponse.Result> favouriteMovieList = new ArrayList<>();
    public List<TVResponse.Result> favouriteTvList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private boolean isMovieDataNeedToFetch;
    private Button btnMovies,btnTV,btnFavouriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        UIComponents();
        isMovieDataNeedToFetch = false;
        enableDisableButtons(false);
        getDataFromApi(true);
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
        btnFavouriteList = findViewById(R.id.btnFavouriteList);
        btnMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMovieDataNeedToFetch = false;
                if (moviesList==null || moviesList.isEmpty())
                    getDataFromApi(false);
                else {
                    enableDisableButtons(true);
                    launchMoviesFragment();
                }
            }
        });

        btnTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMovieDataNeedToFetch = true;
                if (tvList==null || tvList.isEmpty())
                    getDataFromApi(false);
                else {
                    enableDisableButtons(true);
                    launchTVFragment();
                }
            }
        });

        btnFavouriteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FavouriteListActivity.class);
                intent.putExtra("favouriteMovieList",(Serializable) favouriteMovieList);
                intent.putExtra("favouriteTVList",(Serializable) favouriteTvList);
                startActivity(intent);

            }
        });
    }

    private void getDataFromApi(final boolean isMovieDataNeedToFetch) {
        enableDisableButtons(false);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call;
        if (isMovieDataNeedToFetch)
                call = apiService.getMoviesResults("fc41313308a6fbece76ebf7fd21306c6","en-US","popularity.desc","false","false",1);
        else
            call = apiService.getTVResults("fc41313308a6fbece76ebf7fd21306c6","en-US","popularity.desc","false","false",1);
        progressDialog.show();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                Gson gson = new Gson();
                try {
                    if (response.body() != null) {
                        if (isMovieDataNeedToFetch) {
                            MovieResponse moviesResponse = gson.fromJson(response.body().string(), MovieResponse.class);
                            if (moviesResponse != null && moviesResponse.getResults() != null && moviesResponse.getResults().size() > 0) {
                                moviesList = moviesResponse.getResults();
                                launchMoviesFragment();
                            } else {
                                Toast.makeText(MainActivity.this, "Something happened in fetching data. Please try again later", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            TVResponse tvResponse = gson.fromJson(response.body().string(), TVResponse.class);
                            if (tvResponse != null && tvResponse.getResults() != null && tvResponse.getResults().size() > 0) {
                                tvList = tvResponse.getResults();
                                launchTVFragment();
                            } else {
                                Toast.makeText(MainActivity.this, "Something happened in fetching data. Please try again later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if (response.errorBody() != null) {
                        Toast.makeText(MainActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //progressDialog.dismiss();
                enableDisableButtons(true);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                enableDisableButtons(true);
                Toast.makeText(MainActivity.this,"Network not connected. Please try again...",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void launchMoviesFragment() {
        AppUtils.setFragment(R.id.parentLayout,getSupportFragmentManager(),new MoviesFragment(),false);
    }

    private void launchTVFragment() {
        AppUtils.setFragment(R.id.parentLayout,getSupportFragmentManager(),new TVFragment(),false);
    }
}
