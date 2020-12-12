package com.example.retrofit_blood_assist.api_interfaces;

import com.example.retrofit_blood_assist.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("posts")

    Call<List<Post>> getPosts();
}
