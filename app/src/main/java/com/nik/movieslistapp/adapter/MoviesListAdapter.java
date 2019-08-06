package com.nik.movieslistapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nik.movieslistapp.R;
import com.nik.movieslistapp.interfaces.RecyclerViewItemClickListener;
import com.nik.movieslistapp.model.MovieListResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MyViewHolder> {

    private Context context;
    private List<MovieListResponse> movieListResponses;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    public MoviesListAdapter(Context context, List<MovieListResponse> movieListResponses) {
        this.context= context;
        this.movieListResponses= movieListResponses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_movies_list, viewGroup, false);
        MyViewHolder vh= new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.i= i;

        MovieListResponse movieList= movieListResponses.get(i);
        String title= movieList.getTitle();
        String year= movieList.getYear();
        String poster= movieList.getPoster();

        Picasso.with(context)
                .load(poster)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(myViewHolder.imgPoster);

        myViewHolder.movieTitle.setText(title);
        myViewHolder.movieYear.setText(year);
    }

    @Override
    public int getItemCount() {
        return movieListResponses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgPoster) ImageView imgPoster;
        @BindView(R.id.movieTitle) TextView movieTitle;
        @BindView(R.id.movieYear) TextView movieYear;
        public int i=0;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // binding view
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
                    recyclerViewItemClickListener.onItemClick(itemView,i);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View itemView) {
                    recyclerViewItemClickListener.onItemLongClick(itemView,i);
                    return true;
                }
            });
        }
    }

    //Set method of OnItemClickListener object
    public void setOnItemClickListener(RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.recyclerViewItemClickListener=recyclerViewItemClickListener;
    }
}
