package com.example.retrofit_blood_assist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.retrofit_blood_assist.api_interfaces.JsonPlaceHolderApi;
import com.example.retrofit_blood_assist.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResults = findViewById(R.id.text_view_result);

        //getPosts();
        createPost();

    }

    void createPost(){
        Post post=new Post(89,"MockTitle","Mock Body Data");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Post> call = jsonPlaceHolderApi.createPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textViewResults.setText("Code: " + response.code());
                    return;
                }
                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code()+"\n";
                content += "ID: " + postResponse.getId()+"\n";
                content += "User ID: " + postResponse.getUserId()+"\n";
                content += "Title: " + postResponse.getTitle()+"\n";
                content += "Text: " + postResponse.getText()+"\n\n";

                textViewResults.setText(content);


            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                    textViewResults.setText(t.getMessage());
            }
        });
    }

    public void getPosts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call =jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    textViewResults.setText("Code: "+ response.code());
                    return;
                }

                List<Post> posts = response.body();
                for(Post post:posts){
                    String content = "";
                    content += "ID: " + post.getId()+"\n";
                    content += "User ID: " + post.getUserId()+"\n";
                    content += "Title: " + post.getTitle()+"\n";
                    content += "Text: " + post.getText()+"\n\n";

                    textViewResults.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResults.setText(t.getMessage());

            }
        });
    }
}