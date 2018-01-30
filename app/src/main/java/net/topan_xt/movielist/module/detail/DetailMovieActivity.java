package net.topan_xt.movielist.module.detail;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.topan_xt.movielist.R;
import net.topan_xt.movielist.adapter.CastAdapter;
import net.topan_xt.movielist.adapter.ReviewAdapter;
import net.topan_xt.movielist.api.ApiClient;
import net.topan_xt.movielist.model.cast.CastItem;
import net.topan_xt.movielist.model.cast.CastResponse;
import net.topan_xt.movielist.model.cast.CrewItem;
import net.topan_xt.movielist.model.general.MoviesResponse;
import net.topan_xt.movielist.model.moviedetail.GenresItem;
import net.topan_xt.movielist.model.moviedetail.MovieDetailResponse;
import net.topan_xt.movielist.model.review.ResultsItem;
import net.topan_xt.movielist.model.review.ReviewsResponse;
import net.topan_xt.movielist.util.Constant;

import java.util.ConcurrentModificationException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovieActivity extends AppCompatActivity {
    private static final String TAG = "";
    @BindView(R.id.img_backdrop)
    ImageView mImageBackdrop;
    @BindView(R.id.img_poster_detail)
    ImageView mImagePoster;
    @BindView(R.id.text_description)
    TextView mTextDescription;
    @BindView(R.id.text_detail_movie_genre)
    TextView mTextGenre;
    @BindView(R.id.text_detail_movie_language)
    TextView mTextSubtitle;
    @BindView(R.id.text_detail_rating)
    TextView mTextRating;
    @BindView(R.id.text_detail_movie_releasedate)
    TextView mTextReleaseDate;
    @BindView(R.id.text_detail_movie_runtime)
    TextView mTextRunTime;
    @BindView(R.id.toolbar_detail_activity)
    Toolbar mToolbar;
    @BindView(R.id.ll_detail_error)
    LinearLayout mLayoutError;
    @BindView(R.id.rv_cast)
    RecyclerView mRecyclerViewCast;
    @BindView(R.id.rv_reviews)
    RecyclerView getmRecyclerViewReview;
    ProgressDialog dialog;
    private int mID;
    private String shareContent;

    private CastAdapter castAdapter;
    private ReviewAdapter reviewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);

        initToolbar();
        mID = getIntent().getIntExtra("id", 1);
        getDetailMovie(mID);
        loadCastAndReview(mID);
    }

    private void initToolbar() {
        mToolbar.setTitle("Please wait...");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            shareMovie();
            return true;
        } else if (item.getItemId() == R.id.action_refresh) {
            getDetailMovie(mID);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void shareMovie() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void onLoading() {
        dialog = ProgressDialog.show(this, "", "Please Wait while loading data", true);
    }

    public void getDetailMovie(int id) {
        mLayoutError.setVisibility(View.GONE);
        onLoading();
        Call<MovieDetailResponse> call = ApiClient.getService().getMovieDetails(id, Constant.API_KEY);
        call.enqueue(new Callback<MovieDetailResponse>() {
            @Override
            public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
                dialog.dismiss();
                shareContent = "Check this movie out\n" + Constant.WEB_URL + mID;

                MovieDetailResponse movie = response.body();
                mToolbar.setTitle(movie.getTitle());
                Picasso.with(getApplicationContext()).load(Constant.BACKDROP_PATH + movie.getBackdropPath()).into(mImageBackdrop);
                Picasso.with(getApplicationContext()).load(Constant.POSTER_PATH + movie.getPosterPath()).into(mImagePoster);
                mTextDescription.setText(movie.getOverview());
                String genres = "";
                for (GenresItem genre : movie.getGenres()) {
                    if ("".equals(genres)) genres = genre.getName();
                    else genres += ", " + genre.getName();
                }
                mTextGenre.setText(genres);
                mTextSubtitle.setText(movie.getSpokenLanguages().get(0).getIso6391() + " _ " + movie.getSpokenLanguages().get(0).getName());
                mTextRating.setText(movie.getVoteAverage() + " / 10");
                mTextReleaseDate.setText(movie.getReleaseDate());
                int hour = 0;
                int minute = 0;
                String durationString = "";
                if (movie.getRuntime() > 60) {
                    hour = movie.getRuntime() / 60;
                    minute = movie.getRuntime() - (60 * hour);
                    durationString = hour + "h " + minute + " m";
                } else {
                    durationString = minute + "m";
                }
                mTextRunTime.setText(durationString);
            }

            @Override
            public void onFailure(Call<MovieDetailResponse> call, Throwable t) {
                mLayoutError.setVisibility(View.VISIBLE);
                mToolbar.setTitle("Error occured");
                dialog.dismiss();
            }
        });
    }

    public void loadCastAndReview(final int id) {
        Call<MovieDetailResponse> call = ApiClient.getService().getMovieDetails(id, Constant.API_KEY);
        call.enqueue(new Callback<MovieDetailResponse>() {
            @Override
            public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
                getCasts(response.body());
            }

            @Override
            public void onFailure(Call<MovieDetailResponse> call, Throwable t) {

            }
        });

    }

    private void getCasts(final MovieDetailResponse detail) {
        Call<CastResponse> call = ApiClient.getService().getCasts(detail.getId(), Constant.API_KEY);
        call.enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                if (response.code() == 200) {
                    getReviews(detail, response.body().getCast());
                }
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {

            }
        });
    }

    private void getReviews(final MovieDetailResponse detail, final List<CastItem> cast) {
        Call<ReviewsResponse> call = ApiClient.getService().getReviews(detail.getId(), Constant.API_KEY);
        call.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                getCastAndReview(cast, response.body().getResults());
            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {

            }
        });
    }

    public void getCastAndReview(final List<CastItem> casts, final List<ResultsItem> review) {


        if (castAdapter == null) castAdapter = new CastAdapter(casts, this);
        mRecyclerViewCast.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false));
        mRecyclerViewCast.setHasFixedSize(true);
        mRecyclerViewCast.setAdapter(castAdapter);

        if (reviewAdapter == null) reviewAdapter = new ReviewAdapter(review, this);
        getmRecyclerViewReview.setLayoutManager(new LinearLayoutManager(this));
        getmRecyclerViewReview.setHasFixedSize(true);
        getmRecyclerViewReview.setAdapter(reviewAdapter);
    }
}
