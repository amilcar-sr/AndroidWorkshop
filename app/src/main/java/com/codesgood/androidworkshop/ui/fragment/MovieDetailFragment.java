package com.codesgood.androidworkshop.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codesgood.androidworkshop.R;
import com.codesgood.androidworkshop.data.model.Movie;
import com.codesgood.androidworkshop.data.network.NetworkManager;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MovieDetailFragment extends Fragment {

    public final static String TAG = "MovieDetailFragment";
    private final static String ARG_MOVIE = "arg.movie";

    //TODO: [32] Create method that will provide an instance of this fragment with a Movie in its arguments
    public static MovieDetailFragment getInstance(Movie movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_MOVIE, movie);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //TODO: [33] Create the layout for the movie detail
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        //TODO: [34] Throw exception if movie is not present in fragment's arguments
        if (getArguments() == null || getArguments().getParcelable(ARG_MOVIE) == null) {
            throw new IllegalArgumentException("MovieDetailFragment requires a movie in its arguments");
        }

        //TODO: [35] Bind movie info with views in layout
        Movie movie = getArguments().getParcelable(ARG_MOVIE);

        //TODO: [46] Get reference to toolbar
        Toolbar toolbar = view.findViewById(R.id.movie_detail_toolbar);

        TextView movieRating = view.findViewById(R.id.movie_detail_rating);
        TextView movieDescription = view.findViewById(R.id.movie_detail_description);
        ImageView moviePoster = view.findViewById(R.id.movie_detail_poster);

        if (movie != null && getContext() != null) {
            //TODO: [47] Set toolbar to activity
            AppCompatActivity activity = (AppCompatActivity) getActivity();

            //TODO: [70] Verify toolbar isn't null for the case of landscape tablets
            if (activity != null && toolbar != null) {
                activity.setSupportActionBar(toolbar);
                activity.setTitle(movie.getTitle());

                //TODO: [48] Enable back navigation
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            movieRating.setText(String.format(Locale.getDefault(), "%.1f", movie.getVoteAverage()));
            movieDescription.setText(movie.getOverview());
            Glide.with(getContext()).load(NetworkManager.BASE_IMAGE_URL + movie.getPosterPath()).into(moviePoster);
        }

        return view;
    }
}
