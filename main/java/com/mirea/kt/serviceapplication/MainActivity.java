package com.mirea.kt.serviceapplication;

import static com.mirea.kt.serviceapplication.MyCustomApp.LOG_TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnShowNotif = findViewById(R.id.btnShow);
        Button btnCancelNotif = findViewById(R.id.btnCancel);
        btnShowNotif.setOnClickListener(this);
        btnCancelNotif.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnShow){
            if(askNotificationPermission()){
                NotificationHelper notificationHelper = new
                        NotificationHelper(this);
                notificationHelper.showNotification("Внимание",
                        "Это текст тестового уведомления",
                        123);
            }
        }else if(v.getId() == R.id.btnCancel){
            NotificationHelper notificationHelper = new
                    NotificationHelper(this);
            notificationHelper.cancelNotification(123);
        }
    }
    // сюда возвращается результат того, что нажал пользователь в диалоге
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new
                    ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Log.d(LOG_TAG, "Permission granted!");
                } else {
                    Log.d(LOG_TAG, "Permission not granted");
                    Toast.makeText(this, "Приложение может работать некорректно!",Toast.LENGTH_LONG).show();
                }
            });
    private boolean askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            //проверяем есть ли у приложения разрешение на показ уведомлений
            if (ContextCompat.checkSelfPermission(this,
                    "android.permission.POST_NOTIFICATIONS") ==
                    PackageManager.PERMISSION_GRANTED) {
                return true;
                //проверяем отказался ли пользователь от показа уведомлений
            } else if
            (shouldShowRequestPermissionRationale("android.permission.POST_NOTIFICATIONS"
                    )) {
                Toast.makeText(this,"Вы не предоставили приложению разрешение для получения уведомлений!",Toast.LENGTH_LONG).show();
                return false;
            } else {
                //спрашиваем у пользователя разрешение на показ уведомлений
                requestPermissionLauncher.launch("android.permission.POST_NOTIFICATIONS");
                return false;
            }
        }
        return true;
    }
}