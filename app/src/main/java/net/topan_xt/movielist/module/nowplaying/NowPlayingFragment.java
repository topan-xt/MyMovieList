package net.topan_xt.movielist.module.nowplaying;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.topan_xt.movielist.R;
import net.topan_xt.movielist.adapter.MoviesAdapter;
import net.topan_xt.movielist.api.ApiClient;
import net.topan_xt.movielist.model.MoviesResponse;
import net.topan_xt.movielist.model.ResultsItem;
import net.topan_xt.movielist.module.detail.DetailMovieActivity;
import net.topan_xt.movielist.module.home.HomeActivity;
import net.topan_xt.movielist.util.Constant;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/*************************************************
 * Author     : Topan E.                         *
 * Contact    : topan.xt@gmail.com               *
 * Created on : Jan 25, 2018.                    *
 *************************************************/
/*This fragmeent is also called as home*/

public class NowPlayingFragment extends Fragment {
    @BindView(R.id.rv_place) RecyclerView mRecyclerView;
    @BindView(R.id.tv_header_title) TextView mTexHeaderTitle;
    @BindView(R.id.txt_overview) TextView mTextHeaderOverview;
    @BindView(R.id.img_backdrop) ImageView mImageBackdrop;

    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);

        onLoading();
        initView();
        return view;
    }

    public void onLoading(){
        dialog = ProgressDialog.show(getContext(), "","Please Wait while loading data", true);
    }

    public void initView(){
        Call<MoviesResponse> call = ApiClient.getService().getNowPlaying(Constant.API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                dialog.dismiss();
                mRecyclerView.setVisibility(View.VISIBLE);

                List<ResultsItem> movies = response.body().getResults();

                sliderImages(movies);
                mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                mRecyclerView.setAdapter(new MoviesAdapter(movies, R.layout.row_content, getContext()));
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(), "Request data error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sliderImages(final List<ResultsItem> movies) {
        final Random random = new Random();
        final ResultsItem movie = movies.get(random.nextInt(movies.size()));
        Picasso.with(getContext()).load(Constant.BACKDROP_PATH+movie.getBackdropPath()).into(mImageBackdrop);
        mTexHeaderTitle.setText(movie.getTitle());
        mTextHeaderOverview.setText(movie.getOverview());

        mImageBackdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DetailMovieActivity.class).putExtra("id", movie.getId()));
            }
        });
        new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                final ResultsItem movie = movies.get(random.nextInt(movies.size()));
                Picasso.with(getContext()).load(Constant.BACKDROP_PATH+movie.getBackdropPath()).into(mImageBackdrop);
                mTexHeaderTitle.setText(movie.getTitle());
                mTextHeaderOverview.setText(movie.getOverview());
                start();

                mImageBackdrop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getContext(), ""+movie.getId(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(), DetailMovieActivity.class).putExtra("id", movie.getId()));
                    }
                });

            }
        }.start();
    }
}
