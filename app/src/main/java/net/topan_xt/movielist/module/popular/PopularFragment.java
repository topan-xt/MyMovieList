package net.topan_xt.movielist.module.popular;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import net.topan_xt.movielist.module.home.HomeActivity;
import net.topan_xt.movielist.util.Constant;

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

public class PopularFragment extends Fragment {
    @BindView(R.id.rv_place)
    RecyclerView mRecyclerView;

    private ProgressDialog dialog;
    private  View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_content, container, false);
        ButterKnife.bind(this,view);

        onLoading();
        initView();
        return view;
    }

    public void onLoading(){
        dialog = ProgressDialog.show(getContext(), "","Please Wait while loading data", true);
    }

    public void initView(){
        Call<MoviesResponse> call = ApiClient.getService().getPopular(Constant.API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                dialog.dismiss();
                mRecyclerView.setVisibility(View.VISIBLE);

                List<ResultsItem> movies = response.body().getResults();

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
}
