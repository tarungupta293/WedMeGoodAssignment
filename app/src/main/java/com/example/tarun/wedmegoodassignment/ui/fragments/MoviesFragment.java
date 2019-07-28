package com.example.tarun.wedmegoodassignment.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tarun.wedmegoodassignment.R;
import com.example.tarun.wedmegoodassignment.beans.MovieResponse;
import com.example.tarun.wedmegoodassignment.interfaces.OnRecyclerItemClickListener;
import com.example.tarun.wedmegoodassignment.ui.activities.DetailActivity;
import com.example.tarun.wedmegoodassignment.ui.activities.FavouriteListActivity;
import com.example.tarun.wedmegoodassignment.ui.activities.MainActivity;
import com.example.tarun.wedmegoodassignment.ui.adapters.MovieAdapter;
import com.example.tarun.wedmegoodassignment.ui.adapters.TVAdapter;
import com.example.tarun.wedmegoodassignment.utils.AppConstants;

import java.io.Serializable;
import java.util.List;

public class MoviesFragment extends Fragment {

    private MovieAdapter moviesAdapter;
    private boolean isfromFavourite;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_movies, container, false);
        if (getArguments()!=null)
        isfromFavourite = getArguments().getBoolean("isFromFavourite");
        RecyclerView moviesRecyclerView = layoutView.findViewById(R.id.recyclerView);
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<MovieResponse.Result> movieList;
        if (isfromFavourite)
            movieList = ((FavouriteListActivity)getActivity()).favouriteMovieList;
        else
            movieList = ((MainActivity)getActivity()).moviesList;
        moviesAdapter =new MovieAdapter(getActivity(), movieList,isfromFavourite, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position, int clickType) {
                if (clickType == AppConstants.CLICK_TYPE_FAVOURITE) {
                    if (((MainActivity) getActivity()).moviesList.get(position).isFavouriteSelected())
                        ((MainActivity) getActivity()).moviesList.get(position).setFavouriteSelected(false);
                    else
                        ((MainActivity) getActivity()).moviesList.get(position).setFavouriteSelected(true);

                    if (!((MainActivity) getActivity()).moviesList.get(position).isFavouriteSelected()){
                        for (int i=0;i<((MainActivity) getActivity()).favouriteMovieList.size();i++){
                            if (((MainActivity) getActivity()).favouriteMovieList.get(i).getId()==((MainActivity) getActivity()).moviesList.get(position).getId())
                                ((MainActivity) getActivity()).favouriteMovieList.remove(i);
                        }
                    }else
                        ((MainActivity) getActivity()).favouriteMovieList.add(((MainActivity) getActivity()).moviesList.get(position));
                    moviesAdapter.notifyDataSetChanged();
                }else if (clickType == AppConstants.CLICK_TYPE_ITEM_CLICK){
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("selectedData", ((MainActivity) getActivity()).moviesList.get(position));
                    getActivity().startActivity(intent);
                }
            }
        });
        moviesRecyclerView.setAdapter(moviesAdapter);
        return layoutView;
    }
}
