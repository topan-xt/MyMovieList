package net.topan_xt.movielist.module.home;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import net.topan_xt.movielist.R;
import net.topan_xt.movielist.module.comingsoon.ComingSoonFragment;
import net.topan_xt.movielist.module.nowplaying.NowPlayingFragment;
import net.topan_xt.movielist.module.popular.PopularFragment;
import net.topan_xt.movielist.module.toprated.TopRatedFragment;


import butterknife.BindView;
import butterknife.ButterKnife;

/*************************************************
 * Author     : Topan E.                         *
 * Contact    : topan.xt@gmail.com               *
 * Created on : Jan 25, 2018.                    *
 *************************************************/

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title_bar)
    TextView mTextTitleBar;


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

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mTextTitleBar.setText("Now Playing");

        mFragmentTransaction.replace(R.id.frame_place, new NowPlayingFragment(), "Now Plang Fragment");
        mFragmentTransaction.commit();

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
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
            mTextTitleBar.setText("Now Playing");

            mFragmentTransaction.replace(R.id.frame_place, new NowPlayingFragment(), "Now Plang Fragment");
            mFragmentTransaction.commit();

        } else if (id == R.id.nav_top_rated) {
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
            mTextTitleBar.setText("Top Rated");

            mFragmentTransaction.replace(R.id.frame_place, new TopRatedFragment(), "Top Rated Fragment");
            mFragmentTransaction.commit();

        } else if (id == R.id.nav_popular) {
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
            mTextTitleBar.setText("Popular");

            mFragmentTransaction.replace(R.id.frame_place, new PopularFragment(), "Popular Fragment");
            mFragmentTransaction.commit();

        } else if (id == R.id.nav_up_coming) {
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
            mTextTitleBar.setText("Coming Soon");

            mFragmentTransaction.replace(R.id.frame_place, new ComingSoonFragment(), "Coming Soon Fragment");
            mFragmentTransaction.commit();
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
