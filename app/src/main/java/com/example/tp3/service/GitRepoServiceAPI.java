package com.example.tp3.service;

import com.example.tp3.model.GitUserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitRepoServiceAPI {
    @GET("search/users")
    public Call<GitUserResponse> searchUsers(@Query("q") String query);
}
