package com.codesgood.androidworkshop.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.codesgood.androidworkshop.R;
import com.codesgood.androidworkshop.data.model.Movie;
import com.codesgood.androidworkshop.ui.fragment.MovieDetailFragment;
import com.codesgood.androidworkshop.ui.fragment.MovieListFragment;

import androidx.fragment.app.Fragment;

//TODO: [64] Implement MovieListListener in order to listen for MovieListFragment events
public class MainActivity extends BaseActivity implements MovieListFragment.MovieListListener {

    //TODO: [1] Pick the folder structure you'll use to work:
    //I'll use this one:
    //ui
    //-> activity
    //-> fragment
    //-> ... (anything related to UI behaviours)
    //data

    //TODO: [2] Create Application class, we'll need it later.


    //TODO: [66] Declare a view, this will define if the device is a tablet in portrait mode
    private View detailContainer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailContainer = findViewById(R.id.fragment_detail_container);

        //TODO: [62] Implement logic to add/replace list fragment when appropriate
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MovieListFragment.TAG);

        if (fragment == null) {
            //TODO: [26] Replace fragment with MovieListFragment
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, MovieListFragment.getInstance(), MovieListFragment.TAG).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, MovieListFragment.TAG).commit();
        }
    }

    @Override
    public void onMovieClicked(Movie movie) {
        //TODO: [65] Start MovieDetailActivity or replace detail fragment if appropriate
        if (detailContainer == null) {
            startActivity(MovieDetailActivity.getInstance(this, movie));
        } else {
            updateDetailFragment(movie);
        }
    }

    @Override
    public void onFirstPageLoaded(Movie firstMovie) {
        if (detailContainer != null) {
            updateDetailFragment(firstMovie);
        }
    }

    @Override
    public boolean isLandscapeTablet() {
        return detailContainer != null;
    }

    private void updateDetailFragment(Movie movie) {
        setTitle(movie.getTitle());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail_container, MovieDetailFragment.getInstance(movie), MovieDetailFragment.TAG).commit();

        //TODO: [69] Create a version of fragment_movie_detail with no toolbar for landscape tablets
    }
}
