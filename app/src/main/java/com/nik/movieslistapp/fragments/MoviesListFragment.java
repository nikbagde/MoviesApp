package com.nik.movieslistapp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nik.movieslistapp.R;
import com.nik.movieslistapp.adapter.MoviesListAdapter;
import com.nik.movieslistapp.interfaces.RecyclerViewItemClickListener;
import com.nik.movieslistapp.model.MovieListModel;
import com.nik.movieslistapp.model.MovieListResponse;
import com.nik.movieslistapp.rest.ApiClient;
import com.nik.movieslistapp.rest.ApiInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesListFragment extends Fragment {

    private View view;
    private Unbinder unbinder;
    private Context context;
    private ApiInterface apiService;
    private static final String API_KEY= "2998046d";
    private String movieTitle="batman";
    private String title;
    private String director;
    private String poster;
    @BindView(R.id.searchBox) EditText searchBox;
    @BindView(R.id.search) Button search;
    @BindView(R.id.recycler_movies_list)
    RecyclerView recyclerMoviesList;


    public MoviesListFragment() {
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
        view= inflater.inflate(R.layout.fragment_movies_list, container, false);
        context= getActivity();

        // bind view using butter knife
        unbinder = ButterKnife.bind(this, view);

        apiService=
                ApiClient.getClient().create(ApiInterface.class);

        initRecycler();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText= searchBox.getText().toString().trim();
                movieTitle= searchText;
                getMoviesList();
            }
        });

        return view;
    }

    private void initRecycler() {
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(context, LinearLayout.VERTICAL,false);
        recyclerMoviesList.setLayoutManager(layoutManager);
        getMoviesList();
        /*RecyclerItemDecoration recyclerItemDecoration= new RecyclerItemDecoration(context,R.dimen.recycler_padding);
        recycler_history.addItemDecoration(recyclerItemDecoration);*/
    }


    private void getMoviesList(){
        Call<MovieListModel> call= apiService.getMoviesList(API_KEY, movieTitle);
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(context);
        progressDoalog.setMessage("Its loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        call.enqueue(new Callback<MovieListModel>() {
            @Override
            public void onResponse(Call<MovieListModel> call, Response<MovieListModel> response) {
                Log.e("response", "onResponse: "+response.body());
                final List<MovieListResponse> moviesListRes= response.body().getSearch();

                if (response.body().getResponse().equalsIgnoreCase("False")) {
                    Toast.makeText(context, "Movie not found!!", Toast.LENGTH_LONG).show();
                }
                    MoviesListAdapter moviesListAdapter = new MoviesListAdapter(context, moviesListRes);
                    recyclerMoviesList.setAdapter(moviesListAdapter);
                    progressDoalog.dismiss();

                moviesListAdapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String imdbID = moviesListRes.get(position).getImdbID();
                        Fragment fragment = new MovieDescriptionFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("param", imdbID);
                        fragment.setArguments(bundle);

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, fragment);
                        if (!fragmentManager.getFragments().isEmpty()) {
                            fragmentTransaction.addToBackStack(null);
                        }
                        fragmentTransaction.commit();

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
            }

            @Override
            public void onFailure(Call<MovieListModel> call, Throwable t) {
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
