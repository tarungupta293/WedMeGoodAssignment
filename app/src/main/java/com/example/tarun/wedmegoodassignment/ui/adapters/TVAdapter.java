package com.example.tarun.wedmegoodassignment.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tarun.wedmegoodassignment.R;
import com.example.tarun.wedmegoodassignment.beans.TVResponse;
import com.example.tarun.wedmegoodassignment.interfaces.OnRecyclerItemClickListener;
import com.example.tarun.wedmegoodassignment.utils.AppConstants;

import java.util.List;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.MyViewHolder> {

    private Context context;
    private List<TVResponse.Result> tvList;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;
    private boolean isFromFavourite;

    public TVAdapter(Context context, List<TVResponse.Result> tvList,boolean isFromFavourite, OnRecyclerItemClickListener onRecyclerItemClickListener){
        this.context = context;
        this.tvList = tvList;
        this.isFromFavourite = isFromFavourite;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.txtOverview.setText(tvList.get(position).getOverview());
        holder.txtTitle.setText(tvList.get(position).getOriginalName());
        if (!isFromFavourite) {
            holder.imgFavourite.setVisibility(View.VISIBLE);
            if (tvList.get(position).isFavouriteSelected())
                holder.imgFavourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_black_24dp));
            else
                holder.imgFavourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_border_black_24dp));
        }else{
            holder.imgFavourite.setVisibility(View.GONE);
        }
        if (!isFromFavourite) {
            holder.imgFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecyclerItemClickListener.onItemClick(position, AppConstants.CLICK_TYPE_FAVOURITE);
                }
            });
        }
        if (!isFromFavourite) {
            holder.layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecyclerItemClickListener.onItemClick(position, AppConstants.CLICK_TYPE_ITEM_CLICK);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{

        private TextView txtTitle,txtOverview;
        private ImageView imgFavourite;
        private RelativeLayout layoutItem;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView)itemView.findViewById(R.id.txtTitle);
            txtOverview = (TextView)itemView.findViewById(R.id.txtOverview);
            imgFavourite = itemView.findViewById(R.id.imgFavourite);
            layoutItem = itemView.findViewById(R.id.layoutItem);
        }
    }
}
