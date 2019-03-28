package com.codesgood.androidworkshop.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codesgood.androidworkshop.R;
import com.codesgood.androidworkshop.data.model.Movie;
import com.codesgood.androidworkshop.data.model.MovieListResponse;
import com.codesgood.androidworkshop.data.network.NetworkManager;
import com.codesgood.androidworkshop.ui.adapter.MovieAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//TODO: [39] Implement MovieAdapterListener in order to use this fragment as listener for the MovieAdapter
//TODO: [52] Implement OnRefreshListener to listen for "Swipe to refresh" action
public class MovieListFragment extends Fragment implements MovieAdapter.MovieAdapterListener, SwipeRefreshLayout.OnRefreshListener {

    public final static String TAG = "MovieListFragment";

    private RecyclerView moviesRecycler;
    private MovieAdapter movieAdapter;

    //TODO: [53] Get instance of SwipeRefreshLayout and assign listener
    private SwipeRefreshLayout swipeRefreshLayout;

    //TODO: [55] Add a page counter to control list pagination (it can't be zero or negative)
    private int pageCounter = 1;

    //TODO: [63]
    private MovieListListener listener;

    //TODO: [63] Create interface to communicate with Activity and make sure the activity using the fragment implements it
    public interface MovieListListener {
        void onMovieClicked(Movie movie);

        void onFirstPageLoaded(Movie firstMovie);

        //TODO: [71] Add method that defines if the device is a tablet in landscape mode
        boolean isLandscapeTablet();
    }


    public static MovieListFragment getInstance() {
        return new MovieListFragment();
    }

    //TODO: [63]
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (MovieListListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Your Activity must implement MovieListListener in order to use this fragment.");
        }
    }

    //TODO: [19] Inflate fragment_movie_list and assign recycler instance
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        moviesRecycler = view.findViewById(R.id.movies_recycler);

        //TODO: [72] Add logic for 1 or 2 columns depending on the configuration the app is running
        moviesRecycler.setLayoutManager(new GridLayoutManager(getContext(), listener.isLandscapeTablet() ? 1 : 2));

        //TODO: [20] Create MovieAdapter class and movie_item view

        //TODO: [53]
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        //TODO: [23] Add method to request popular movies
        requestNextMoviesPage(false);

        return view;
    }

    //TODO: [60] Add a boolean parameter into this method in order to know when to refresh the list and when to append the response
    private void requestNextMoviesPage(final Boolean isRefresh) {
        swipeRefreshLayout.setRefreshing(true);
        NetworkManager.getPopularMovies(pageCounter).enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(@NotNull Call<MovieListResponse> call, @NotNull Response<MovieListResponse> response) {

                if (response.body() != null) {
                    //TODO: [24] Assign adapter to RecyclerView or update movie list with response
                    List<Movie> movies = response.body().getResults();
                    if (movieAdapter == null) {
                        movieAdapter = new MovieAdapter(MovieListFragment.this, movies);
                        moviesRecycler.setAdapter(movieAdapter);
                    } else {
                        if (isRefresh) {
                            //TODO: [25] Implement method to update movies in MovieAdapter
                            movieAdapter.updateList(movies);
                        } else {
                            movieAdapter.appendList(movies);
                        }
                    }
                    //TODO: [54] Make sure to hide the loading indicator when finished movie page download
                    swipeRefreshLayout.setRefreshing(false);

                    //TODO: [67] Notify the activity if the first page has been loaded
                    if (pageCounter == 1 && movies.size() > 0) {
                        listener.onFirstPageLoaded(movies.get(0));
                    }

                    //TODO: [56] Increment the counter
                    pageCounter++;
                }
            }

            @Override
            public void onFailure(@NotNull Call<MovieListResponse> call, @NotNull Throwable t) {
                //TODO: [54] Make sure to hide the loading indicator when page download fails
                swipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void onMovieClicked(Movie movie) {
        //TODO: [40] Start MovieDetailActivity
//        startActivity(MovieDetailActivity.getInstance(getContext(), movie));

        //TODO: [41] Create toolbar layout to customize it with movie title

        //TODO: [68] Replace start activity in step [40] with call to listener
        listener.onMovieClicked(movie);
    }

    //TODO: [58] Implement method and call requestNextMoviesPage() so the next page is loaded
    @Override
    public void onBottomReached() {
        requestNextMoviesPage(false);
    }

    @Override
    public void onRefresh() {
        //TODO: [56] Reset counter to 1 when refreshing
        pageCounter = 1;

        requestNextMoviesPage(true);
    }
}
