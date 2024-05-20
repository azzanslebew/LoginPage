package com.example.loginpage;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SpanyolService {
    @GET("search_all_teams.php?s=Soccer&c=Spain")
    Call<TeamResponse> getTeams();
}
