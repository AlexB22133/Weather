package com.example.weatherappjava.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.weatherappjava.R;
import com.example.weatherappjava.databinding.ActivityMainBinding;
import com.example.weatherappjava.network.Network;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    String currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        currentLocation = intent.getStringExtra("LATTITUDE") + ';'
                + intent.getStringExtra("LONGITUDE");
        lOG.v(Tag,currentLocation);

        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(Network.openWeahterAPI + "current.jsonkey=" + Network.getOpenWeahterAPIKEY +"AQUI" + currentLocation)
                .build();
    @SuppressLint("StaticFieldLeak")
    AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
        @Override
        protected String doInBackground(Void... params) {
            try {
                Response response = client.newCall(request).execute();
                if(response.isSuccessful()) {
                    return null;
                }
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
    }
}