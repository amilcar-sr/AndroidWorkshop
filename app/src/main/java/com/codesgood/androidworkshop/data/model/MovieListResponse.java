package com.codesgood.androidworkshop.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieListResponse implements Parcelable {

    @SerializedName("page")
    private int page;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<Movie> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeInt(this.totalResults);
        dest.writeInt(this.totalPages);
        dest.writeTypedList(this.results);
    }

    public MovieListResponse() {
    }

    protected MovieListResponse(Parcel in) {
        this.page = in.readInt();
        this.totalResults = in.readInt();
        this.totalPages = in.readInt();
        this.results = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Parcelable.Creator<MovieListResponse> CREATOR = new Parcelable.Creator<MovieListResponse>() {
        @Override
        public MovieListResponse createFromParcel(Parcel source) {
            return new MovieListResponse(source);
        }

        @Override
        public MovieListResponse[] newArray(int size) {
            return new MovieListResponse[size];
        }
    };
}
