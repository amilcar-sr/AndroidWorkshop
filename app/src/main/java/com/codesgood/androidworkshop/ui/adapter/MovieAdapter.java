package com.codesgood.androidworkshop.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codesgood.androidworkshop.R;
import com.codesgood.androidworkshop.data.model.Movie;
import com.codesgood.androidworkshop.data.network.NetworkManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieItemViewHolder> {

    private List<Movie> movies;

    //TODO: [38] Hold a reference to a MovieAdapterListener
    private MovieAdapterListener listener;

    //TODO: [37] Create interface to communicate user interaction with movie items
    public interface MovieAdapterListener {
        void onMovieClicked(Movie movie);

        //TODO: [57] Add method to notify the las item has bottom of the list has been reached
        void onBottomReached();
    }

    public MovieAdapter(MovieAdapterListener listener, List<Movie> movies) {
        this.listener = listener;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);

        return new MovieItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemViewHolder holder, int position) {
        holder.bind(movies.get(position));

        //TODO: [61] Notify the bottom has been reached when appropriate
        if (position == getItemCount() - 1){
            listener.onBottomReached();
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void updateList(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    //TODO: [59] Add method to append list of movies
    public void appendList(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    //TODO: [21] Create MovieViewHolder to bind movie info and recycler's views
    class MovieItemViewHolder extends RecyclerView.ViewHolder {
        TextView movieName;
        ImageView moviePoster;

        MovieItemViewHolder(@NonNull View itemView) {
            super(itemView);

            movieName = itemView.findViewById(R.id.movie_name);
            moviePoster = itemView.findViewById(R.id.movie_poster);
        }

        //TODO: [22] Create method to bind movie info to item view
        void bind(final Movie movie) {
            movieName.setText(movie.getTitle());

            Glide.with(itemView.getContext()).load(NetworkManager.BASE_IMAGE_URL + movie.getPosterPath()).into(moviePoster);

            //TODO: [38] Call onMovieClicked when movie item is clicked
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMovieClicked(movie);
                }
            });
        }
    }
}
