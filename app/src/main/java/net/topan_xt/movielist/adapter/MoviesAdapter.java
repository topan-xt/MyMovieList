package net.topan_xt.movielist.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.topan_xt.movielist.R;
import net.topan_xt.movielist.model.ResultsItem;
import net.topan_xt.movielist.model.moviedetail.MovieDetailResponse;
import net.topan_xt.movielist.module.detail.DetailMovieActivity;
import net.topan_xt.movielist.util.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/*************************************************
 * Author     : Topan E.                         *
 * Contact    : topan.xt@gmail.com               *
 * Created on : Jan 21, 2018.                    *
 *************************************************/


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<ResultsItem> movies;
    private int rowLayout;
    private Context context;

    public MoviesAdapter(List<ResultsItem> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }


    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.MovieViewHolder holder, final int position) {
        holder.mTextTitle.setText(movies.get(position).getTitle());
        holder.mTextRating.setText(String.valueOf(movies.get(position).getVoteAverage())+" ");
        Picasso.with(context).load(Constant.POSTER_PATH+movies.get(position).getPosterPath()).into(holder.mImagePoster);

        Log.d(TAG, "onBindViewHolder: "+movies.get(position).getId());
        holder.mRowMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, ""+movies.get(position).getId(), Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, DetailMovieActivity.class).putExtra("id", movies.get(position).getId()));
            }
        });
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title) TextView mTextTitle;
        @BindView(R.id.img_poster) ImageView mImagePoster;
        @BindView(R.id.row_movie) CardView mRowMovie;
        @BindView(R.id.txt_rating) TextView mTextRating;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

