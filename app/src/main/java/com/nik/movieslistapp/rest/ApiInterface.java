package com.nik.movieslistapp.rest;


import com.nik.movieslistapp.model.MovieDescriptionModel;
import com.nik.movieslistapp.model.MovieListModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET(".")
    Call<MovieListModel> getMoviesList(@Query("apikey") String apiKey, @Query("s") String movieTitle);

    @GET(".")
    Call<MovieDescriptionModel> getMovieDescription(@Query("apikey") String apiKey, @Query("i") String imdbID);
}
