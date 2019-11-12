package com.upltv.android.mediation.demo;

import android.content.ComponentName;
import android.content.Intent;

import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class ShowDebugActivity extends AppCompatActivity {
    Button btnDebugInter, btnDebugVideo;
    TextInputEditText etPackageName;
    String packageName = "";
    String avidlyRewardVideoName="com.avidly.ads.debug.AvidlyRewardVideoDebugActivity";
    String upRewardVideoName="com.up.ads.debug.UPRewardVideoDebugActivity";
    String avidlyInterName="com.avidly.ads.debug.AvidlyInterstitialDebugActivity";
    String upInterName="com.up.ads.debug.UPInterstitialDebugActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_debug);
        etPackageName = findViewById(R.id.et_packageName);
        btnDebugInter = findViewById(R.id.btn_debug_inter);
        btnDebugVideo = findViewById(R.id.btn_debug_video);

        btnDebugVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                packageName = etPackageName.getText().toString().trim();
                showRewardVideo(packageName);
            }
        });

        btnDebugInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                packageName = etPackageName.getText().toString().trim();
                showInter(packageName);
            }
        });

    }

    public boolean startOtherActivity(String packageName, String activityName) {
        if (packageName.equals("")) {
            Toast.makeText(this, "缺少包名", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName(packageName, activityName);
            intent.setComponent(cn);
            try {
                ShowDebugActivity.this.startActivity(intent);
                return true;
            } catch (Exception e) {
                if (e.getMessage().contains("Unable to find")) {
                    Toast.makeText(this, "no this app---"+activityName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;


            }
        }
    }

    public void showRewardVideo(String packageName){
        if (!startOtherActivity(packageName,avidlyRewardVideoName)){
            startOtherActivity(packageName,upRewardVideoName);
        }
    }

    public void showInter(String packageName){
        if (!startOtherActivity(packageName,avidlyInterName)){
            startOtherActivity(packageName,upInterName);
        }
    }



}
