package com.nik.movieslistapp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nik.movieslistapp.R;
import com.nik.movieslistapp.model.MovieDescriptionModel;
import com.nik.movieslistapp.rest.ApiClient;
import com.nik.movieslistapp.rest.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDescriptionFragment extends Fragment {

    private View view;
    private Context context;
    private Unbinder unbinder;
    private ApiInterface apiService;
    private static final String API_KEY= "2998046d";
    String imdbID="";
    @BindView(R.id.moviePoster) ImageView moviePoster;
    @BindView(R.id.movieTitleName) TextView movieTitle;
    @BindView(R.id.movieDescription) TextView movieDescription;

    public MovieDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_movie_description, container, false);
        context= getActivity();

        imdbID= getArguments().getString("param");

        // bind view using butter knife
        unbinder = ButterKnife.bind(this, view);

        apiService=
                ApiClient.getClient().create(ApiInterface.class);

        loadDescription();

        return view;
    }

    private void loadDescription() {
        Call<MovieDescriptionModel> call= apiService.getMovieDescription(API_KEY, imdbID);
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(context);
        progressDoalog.setMessage("Its loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        call.enqueue(new Callback<MovieDescriptionModel>() {
            @Override
            public void onResponse(Call<MovieDescriptionModel> call, Response<MovieDescriptionModel> response) {
                if (response.isSuccessful()){
                    String title= response.body().getTitle();
                    String year= response.body().getYear();
                    String rated= response.body().getRated();
                    String released= response.body().getReleased();
                    String runtime= response.body().getRuntime();
                    String genre= response.body().getGenre();
                    String writer= response.body().getWriter();
                    String director= response.body().getDirector();
                    String actors= response.body().getActors();
                    String plot= response.body().getPlot();
                    String language= response.body().getLanguage();
                    String country= response.body().getCountry();
                    String awards= response.body().getAwards();
                    String poster= response.body().getPoster();
                    String metascore= response.body().getMetascore();
                    String imdbRating= response.body().getImdbRating();
                    String imdbVotes= response.body().getImdbVotes();
                    String imdbID= response.body().getImdbID();
                    String Type= response.body().getType();
                    String DVD= response.body().getDVD();
                    String BoxOffice= response.body().getBoxOffice();
                    String Production= response.body().getProduction();
                    String Website= response.body().getWebsite();
                    String Response= response.body().getResponse();

                    Picasso.with(context)
                            .load(poster)
                            .placeholder(android.R.drawable.sym_def_app_icon)
                            .error(android.R.drawable.sym_def_app_icon)
                            .into(moviePoster);

                    movieTitle.setText(title);

                    movieDescription.setText("Year:\n"+year+"\n\nRated:\n"+rated+"\n\nReleased:\n"+
                    released+"\n\nRuntime:\n"+runtime+"\n\nGenre:\n"+genre+"\n\nDirector:\n"+director+
                    "\n\nWriter:\n"+writer+"\n\nActors:\n"+actors+"\n\nPlot:\n"+plot+"\n\nLanguage:\n"+
                            language+"\n\nCountry\n"+ country+"\n\nAwards:\n"+awards+"\n\nMetascore:\n"+
                    metascore+"\n\nIMDB Rating:\n"+imdbRating+"\n\nIMDB Votes:\n"+imdbVotes+"\n\nIMDB ID:\n"+
                    imdbID+"\n\nType:\n"+Type+"\n\nDVD:\n"+DVD+"\n\nBox Office:\n"+BoxOffice+"\n\nProduction:\n"+
                    Production+"\n\nWebsite:\n"+Website);

                    progressDoalog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<MovieDescriptionModel> call, Throwable t) {
                progressDoalog.dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // unbind the view to free some memory
        unbinder.unbind();
    }
}
