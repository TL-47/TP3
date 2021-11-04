package com.example.tp3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.tp3.model.GitUser;
import com.example.tp3.model.GitUserResponse;
import com.example.tp3.service.GitRepoServiceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public List<String> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy().Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button buttonsearch = findViewById(R.id.searchUser);
        final EditText editText = findViewById(R.id.editTextUser);
        ListView listView = findViewById(R.id.listUser);

        final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        listView.setAdapter(stringArrayAdapter);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        buttonsearch.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String q = editText.getText().toString();

                        GitRepoServiceAPI service = retrofit.create(GitRepoServiceAPI.class);
                        Call<GitUserResponse> gitUserResponseCall = service.searchUsers(q);
                        gitUserResponseCall.enqueue(
                                new Callback<GitUserResponse>() {
                                    @Override
                                    public void onResponse(Call<GitUserResponse> call, Response<GitUserResponse> response) {
                                        if (response.isSuccessful()) {
                                            Log.i("error", String.valueOf(response.code()));
                                            return;
                                        }
                                        GitUserResponse gitUserResponse = response.body();
                                        for(GitUser user : gitUserResponse.users){
                                            data.add(user.username);

                                        }
                                        stringArrayAdapter.notifyDataSetChanged();
                                    }


                                    @Override
                                    public void onFailure(Call<GitUserResponse> call, Throwable t) {
                                        Log.i("error", "Error onFailure");

                                    }
                                }
                        );


                    }
                }
        );


    }



    }
