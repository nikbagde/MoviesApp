package com.nik.movieslistapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieListModel {
    @SerializedName("Search")
    @Expose
    private List<MovieListResponse> search = null;
    @SerializedName("totalResults")
    @Expose
    private String totalResults;
    @SerializedName("Response")
    @Expose
    private String response;

    /**
     * No args constructor for use in serialization
     *
     */
    public MovieListModel() {
    }

    /**
     *
     * @param response
     * @param totalResults
     * @param search
     */
    public MovieListModel(List<MovieListResponse> search, String totalResults, String response) {
        super();
        this.search = search;
        this.totalResults = totalResults;
        this.response = response;
    }

    public List<MovieListResponse> getSearch() {
        return search;
    }

    public void setSearch(List<MovieListResponse> search) {
        this.search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
