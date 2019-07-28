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
import com.example.tarun.wedmegoodassignment.beans.TVResponse;
import com.example.tarun.wedmegoodassignment.interfaces.OnRecyclerItemClickListener;
import com.example.tarun.wedmegoodassignment.ui.activities.DetailActivity;
import com.example.tarun.wedmegoodassignment.ui.activities.FavouriteListActivity;
import com.example.tarun.wedmegoodassignment.ui.activities.MainActivity;
import com.example.tarun.wedmegoodassignment.ui.adapters.TVAdapter;
import com.example.tarun.wedmegoodassignment.utils.AppConstants;

import java.io.Serializable;
import java.util.List;

public class TVFragment extends Fragment {

    private TVAdapter tvAdapter;
    private boolean isfromFavourite;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_movies, container, false);
        if (getArguments()!=null)
        isfromFavourite = getArguments().getBoolean("isFromFavourite");
        RecyclerView moviesRecyclerView = layoutView.findViewById(R.id.recyclerView);
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<TVResponse.Result> tvList;
        if (isfromFavourite)
            tvList = ((FavouriteListActivity)getActivity()).favouriteTvList;
        else
            tvList = ((MainActivity)getActivity()).tvList;
        tvAdapter =new TVAdapter(getActivity(), tvList,isfromFavourite, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position, int clickType) {
                if (clickType == AppConstants.CLICK_TYPE_FAVOURITE) {
                    if (((MainActivity) getActivity()).tvList.get(position).isFavouriteSelected())
                        ((MainActivity) getActivity()).tvList.get(position).setFavouriteSelected(false);
                    else
                        ((MainActivity) getActivity()).tvList.get(position).setFavouriteSelected(true);

                    if (!((MainActivity) getActivity()).tvList.get(position).isFavouriteSelected()){
                        for (int i=0;i<((MainActivity) getActivity()).favouriteTvList.size();i++){
                            if (((MainActivity) getActivity()).favouriteTvList.get(i).getId()==((MainActivity) getActivity()).tvList.get(position).getId())
                                ((MainActivity) getActivity()).favouriteTvList.remove(i);
                        }
                    }else
                        ((MainActivity) getActivity()).favouriteTvList.add(((MainActivity) getActivity()).tvList.get(position));
                    tvAdapter.notifyDataSetChanged();
                }else if (clickType == AppConstants.CLICK_TYPE_ITEM_CLICK){
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("selectedData", ((MainActivity) getActivity()).tvList.get(position));
                    getActivity().startActivity(intent);
                }
            }
        });
        moviesRecyclerView.setAdapter(tvAdapter);
        return layoutView;
    }
}
