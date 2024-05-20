package com.example.loginpage.fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.loginpage.Adapter;
import com.example.loginpage.InggrisService;
import com.example.loginpage.R;
import com.example.loginpage.Team;
import com.example.loginpage.TeamResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Inggris extends Fragment implements Adapter.ItemClickListener {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private static final String BASE_URL = "https://www.thesportsdb.com/api/v1/json/3/";
    public Inggris() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_inggris, container, false);

        recyclerView = view.findViewById(R.id.rvListFootbal);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InggrisService service = retrofit.create(InggrisService.class);
        Call<TeamResponse> call = service.getTeams();
        call.enqueue(new Callback<TeamResponse>() {
            @Override
            public void onResponse(Call<TeamResponse> call, Response<TeamResponse> response) {
                if (response.isSuccessful()) {
                    TeamResponse teamResponse = response.body();
                    if (teamResponse != null) {
                        List<Team> teams = teamResponse.getTeams();
                        if (!teams.isEmpty()) {
                            adapter = new Adapter(teams, getActivity());
                            adapter.setClickListener(Inggris.this);
                            recyclerView.setAdapter(adapter);
                        } else {
                            Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Response body is null", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TeamResponse> call, Throwable t) {
                Log.e("Inggris", "Error: " + t.getMessage());
                Toast.makeText(getActivity(), "Network error. Please check your connection and try again.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onItemClick(Team team) {

    }
}