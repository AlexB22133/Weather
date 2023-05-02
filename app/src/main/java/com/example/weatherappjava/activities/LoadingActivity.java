package com.example.weatherappjava.activities;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.weatherappjava.R;
import com.example.weatherappjava.databinding.ActivityLoading2Binding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class LoadingActivity extends AppCompatActivity {
    ActivityLoading2Binding binding;
    MyCountdownTimer countdownTimer;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int FINE_LOCATION_PERMISSION_CODE = 1000;
    private static final int COARSE_LOCATION_PERMISSION_CODE = 101;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding =ActivityLoading2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Glide.with(this).load(R.drawable.ic_image).into(binding.image);

        countdownTimer = new MyCountdownTimer(5000, 1000 );
        countdownTimer.start();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, FINE_LOCATION_PERMISSION_CODE);
        checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, COARSE_LOCATION_PERMISSION_CODE);

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"Error trying to get last GPS location");
                e.printStackTrace();
            }
        });
    }

    private void checkPermission(String permission, int requestCode) {
        if (ActivityCompat.checkSelfPermission(LoadingActivity.this, permission) ==
                PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(LoadingActivity.this, new String[]{permission},
                    requestCode);
        }else{
            Toast.makeText(LoadingActivity.this,"Permission already",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public class MyCountdownTimer extends CountDownTimer
    {
        int progress = 0;
        public MyCountdownTimer(long millisInFuture, long countDownInterval){
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            progress = progress + 20;
            binding.barpro.setProgress(progress);
        }
        @Override
        public void onFinish()
        {
            Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
            intent.putExtra("LATITUDE", String.valueOf(latitude));
            intent.putExtra("LONGITUDE", String.valueOf(longitude));
            startActivity(intent);

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestcode,@NonNull String[] permissions,
                                           @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestcode,
                permissions,
                grantResults);

        if (requestcode == FINE_LOCATION_PERMISSION_CODE){
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(LoadingActivity.this, "Fine location Permission Granted",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(LoadingActivity.this, "Fine location Permission Denied",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (requestcode == COARSE_LOCATION_PERMISSION_CODE) {
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(LoadingActivity.this, "Fine location Permission Granted",
                        Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(LoadingActivity.this, "Fine location Permission Denied",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}