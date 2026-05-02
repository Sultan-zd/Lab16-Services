package com.example.servicechronometrejava;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.servicechronometrejava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ChronometreService chronometreService;
    private boolean isBound = false;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            if (isBound && chronometreService != null) {
                binding.tvTemps.setText(chronometreService.getTempsFormate());
            }
            handler.postDelayed(this, 1000);
        }
    };

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ChronometreService.LocalBinder binder = (ChronometreService.LocalBinder) service;
            chronometreService = binder.getService();
            isBound = true;
            handler.post(updateTimeRunnable);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupListeners();
        checkPermissions();
    }

    private void setupListeners() {
        binding.btnStart.setOnClickListener(v -> startChronometre());
        binding.btnStop.setOnClickListener(v -> stopChronometre());
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) 
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, 
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
    }

    private void startChronometre() {
        Intent intent = new Intent(this, ChronometreService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private void stopChronometre() {
        Intent intent = new Intent(this, ChronometreService.class);
        // Correction : Utilisation de la constante définie dans le Service pour que l'arrêt soit effectif
        intent.setAction(ChronometreService.ACTION_STOP);
        startService(intent);

        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
        handler.removeCallbacks(updateTimeRunnable);
        binding.tvTemps.setText(R.string.default_time);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, ChronometreService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
        handler.removeCallbacks(updateTimeRunnable);
    }
}