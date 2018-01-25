package net.topan_xt.movielist.module.nowplaying;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import net.topan_xt.movielist.R;
import net.topan_xt.movielist.adapter.MoviesAdapter;
import net.topan_xt.movielist.api.ApiClient;
import net.topan_xt.movielist.model.MoviesResponse;
import net.topan_xt.movielist.model.ResultsItem;

import java.util.List;

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

public class NowPlayingFragment extends Fragment {
    @BindView(R.id.rv_place)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private final static String API_KEY = "671ee92ab3a0f7e022724ce32cf06a93";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ButterKnife.bind(this,view);

        initView();
        return view;
    }

    public void initView(){
        Call<MoviesResponse> call = ApiClient.getService().getNowPlaying(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                mProgressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);

                List<ResultsItem> movies = response.body().getResults();

                mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                mRecyclerView.setAdapter(new MoviesAdapter(movies, R.layout.row_content, getContext()));
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Request data error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
