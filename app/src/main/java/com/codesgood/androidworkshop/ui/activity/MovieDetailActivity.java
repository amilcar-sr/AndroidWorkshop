package com.codesgood.androidworkshop.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.codesgood.androidworkshop.R;
import com.codesgood.androidworkshop.data.model.Movie;
import com.codesgood.androidworkshop.ui.fragment.MovieDetailFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {

    public final static String TAG = "MovieDetailActivity";
    private final static String EXT_MOVIE = "ext.movie";

    //TODO: [30] Create method to retrieve intent with movie in its extras
    public static Intent getInstance(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXT_MOVIE, movie);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: [31] Throw exception if movie is not present in intent
        Movie movie = getIntent().getParcelableExtra(EXT_MOVIE);
        if (movie == null) {
            throw new IllegalArgumentException("MovieDetailActivity requires a movie in its extras");
        }

        setContentView(R.layout.activity_movie_detail);

        //TODO: [32] Create MovieDetailFragment
        //TODO: [36] Replace fragment container with MovieDetailFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MovieDetailFragment.getInstance(movie), MovieDetailFragment.TAG).commit();
    }

    //TODO: [50] Listen to clicks on menu options (we're interested in the back/home button)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
