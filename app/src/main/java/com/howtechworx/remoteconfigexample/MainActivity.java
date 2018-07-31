package com.howtechworx.remoteconfigexample;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private FirebaseRemoteConfig remoteConfigInstance;
    private TextView hello_txt;
    private String name;
    private String color;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hello_txt = findViewById(R.id.hello);
        layout = findViewById(R.id.layout);

        remoteConfigInstance=FirebaseRemoteConfig.getInstance();
        remoteConfigInstance.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
        .setDeveloperModeEnabled(true)
        .build());

        HashMap<String,Object> map = new HashMap<>();
        map.put("my_name","Sakshay");
        map.put("color","#000000" );

        remoteConfigInstance.setDefaults(map);
        final Task<Void> task = remoteConfigInstance.fetch(0);
        task.addOnSuccessListener(this,new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                remoteConfigInstance.activateFetched();
                updateActivity();
            }
        });

    }

    private void updateActivity() {
        name=remoteConfigInstance.getString("my_name");
        color= remoteConfigInstance.getString("color");
        hello_txt.setText(String.format("Hello %s", name));
        layout.setBackgroundColor(Color.parseColor(color));

    }
}
