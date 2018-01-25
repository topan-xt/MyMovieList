package net.topan_xt.movielist;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout) DrawerLayout mDrawer;
    @BindView(R.id.nav_view) NavigationView mNavigationView;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.rv_place) RecyclerView mRecyclerView;
    @BindView(R.id.progres_bar) ProgressBar mProgressBar;

    private final static String API_KEY = "671ee92ab3a0f7e022724ce32cf06a93";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(mToolbar);
        ButterKnife.bind(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        initView();
    }

    void initView(){
        Call<MoviesResponse> call = ApiClient.getService().getTopRatedMovies(API_KEY);

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                mProgressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(mNavigationView.VISIBLE);

                List<ResultsItem> movies = response.body().getResults();

                mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                mRecyclerView.setAdapter(new MoviesAdapter(movies, R.layout.row_content, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Request data error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_now_playing) {
            Toast.makeText(this, "Now Playing clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_top_rated) {
            Toast.makeText(this, "Top Rated Clicked", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_up_coming) {
            Toast.makeText(this, "Coming Soon clicked", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "Share clicked", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "Send Clicked", Toast.LENGTH_SHORT).show();
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
