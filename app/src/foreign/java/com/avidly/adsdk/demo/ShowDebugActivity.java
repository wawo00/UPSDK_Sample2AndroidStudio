package com.avidly.adsdk.demo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
//import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ShowDebugActivity extends AppCompatActivity {
//  Button btnDebugInter,btnDebugVideo;
//    TextInputEditText etPackageName;
//  String packageName="";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_debug);
//        etPackageName=findViewById(R.id.et_packageName);
//        btnDebugInter=findViewById(R.id.btn_debug_inter);
//        btnDebugVideo=findViewById(R.id.btn_debug_video);
//
//        btnDebugVideo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                packageName=etPackageName.getText().toString().trim();
//                startOtherActivity(packageName,"com.avidly.ads.debug.AvidlyRewardVideoDebugActivity");
//            }
//        });
//
//        btnDebugInter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                packageName=etPackageName.getText().toString().trim();
//                startOtherActivity(packageName,"com.avidly.ads.debug.AvidlyInterstitialDebugActivity");
//            }
//        });
//
//    }
//
//    public void startOtherActivity(String packageName,String activityName){
//        if (packageName.equals("")){
//            Toast.makeText(this, "缺少包名", Toast.LENGTH_SHORT).show();
//            return;
//        }else
//        {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_LAUNCHER);
//            ComponentName cn = new ComponentName(packageName,activityName);
//            intent.setComponent(cn);
//            try {
//                ShowDebugActivity.this.startActivity(intent);
//            }catch (Exception e){
//                if (e.getMessage().contains("Unable to find")){
//
//                    Toast.makeText(this, "no this app", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        }
//    }


}
