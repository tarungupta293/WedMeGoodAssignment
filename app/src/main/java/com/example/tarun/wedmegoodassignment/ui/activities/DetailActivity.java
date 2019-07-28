package com.example.tarun.wedmegoodassignment.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tarun.wedmegoodassignment.R;
import com.example.tarun.wedmegoodassignment.beans.MovieResponse;
import com.example.tarun.wedmegoodassignment.beans.TVResponse;
import com.example.tarun.wedmegoodassignment.utils.AppConstants;
import com.example.tarun.wedmegoodassignment.utils.AppUtils;

public class DetailActivity extends AppCompatActivity {

    private MovieResponse.Result movieData;
    private TVResponse.Result tvData;
    private TextView txtTitle,txtReleaseYear,txtRuntime,txtRatings,txtCategory,txtDirector,txtCast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (getIntent()!=null) {
            try {
                movieData = (MovieResponse.Result) getIntent().getExtras().getSerializable("selectedData");
            } catch (ClassCastException ex) {
                tvData = (TVResponse.Result) getIntent().getExtras().getSerializable("selectedData");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        initialiseViews();
        setDataInUI();
    }

    private void setDataInUI() {
        if (movieData!=null){
            setTitle(movieData.getOriginalTitle());
            txtTitle.setText(movieData.getOriginalTitle());
            txtReleaseYear.setText(AppUtils.changeDateFormate(movieData.getReleaseDate(), AppConstants.FROM_CHANGE_DATE_FORMAT,AppConstants.TO_CHANGE_DATE_FORMAT));
            //txtRuntime.setText(movieData.getReleaseDate());
            txtRatings.setText(String.valueOf(movieData.getVoteAverage()));
            //txtReleaseYear.setText(movieData.getReleaseDate());
//            txtReleaseYear.setText(movieData.getReleaseDate());
//            txtReleaseYear.setText(movieData.getReleaseDate());
        }else if (tvData!=null){
            setTitle(tvData.getOriginalName());
            txtTitle.setText(tvData.getOriginalName());
            txtReleaseYear.setText(AppUtils.changeDateFormate(tvData.getFirstAirDate(), AppConstants.FROM_CHANGE_DATE_FORMAT,AppConstants.TO_CHANGE_DATE_FORMAT));
            //txtRuntime.setText(tvData.getReleaseDate());
            txtRatings.setText(String.valueOf(tvData.getVoteAverage()));
            //txtReleaseYear.setText(tvData.getReleaseDate());
//            txtReleaseYear.setText(tvData.getReleaseDate());
//            txtReleaseYear.setText(tvData.getReleaseDate());
        }
    }

    private void initialiseViews() {
        txtTitle = findViewById(R.id.txtTitle);
        txtReleaseYear = findViewById(R.id.txtReleaseYear);
        txtRuntime = findViewById(R.id.txtRuntime);
        txtRatings = findViewById(R.id.txtRatings);
        txtCategory = findViewById(R.id.txtCategory);
        txtDirector = findViewById(R.id.txtDirector);
        txtCast = findViewById(R.id.txtCast);
    }
}
