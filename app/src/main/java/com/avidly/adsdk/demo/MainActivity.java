package com.avidly.adsdk.demo;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avidly.ads.AvidlyAdsSdk;
import com.avidly.adsdk.demo.util.VersionUtil;
import com.hola.sdk.HolaAnalysis;
import com.up.ads.UPAdsSdk;
import com.up.ads.tool.AccessPrivacyInfoManager;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks   {
	public static final int RC_PHONE_STATE=3;
	private static final String TAG = "upsdk_demo";
     TextView tv_version;
	Button btnRwardVideo;
	Button btnBanner;
	Button btnInterstitial;
	Button btnExit,btnGetAbTest,btnShowDebug;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		UPAdsSdk.setDebuggable(true);
		devicesState();
		initOuterAnalysis();
		initUpAdsSdk();
		tv_version= (TextView)findViewById(R.id.tv_version);
		tv_version.setText(VersionUtil.getVersionName(this));
		btnBanner = (Button) findViewById(R.id.btnBanner);
		btnBanner.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, BannerActivity.class);
				startActivity(intent);
			}
		});

		btnInterstitial = (Button) findViewById(R.id.btnInterstitial);
		btnInterstitial.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, InterstitialActivity.class);
				startActivity(intent);

			}
		});

		btnRwardVideo = (Button) findViewById(R.id.btnRwardVideo);
		btnRwardVideo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, VideoActivity.class);
				startActivity(intent);
			}
		});

		btnExit = (Button) findViewById(R.id.btnExit);
		btnExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ExitActivity.class);
				startActivity(intent);
			}
		});

		btnGetAbTest=findViewById(R.id.btnGetAbTest);
		btnGetAbTest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String abtestResult0=	 UPAdsSdk.getAbtConfigString("ad_hp");
				String abtestResult1=	 UPAdsSdk.getAbtConfigString("ad_hp");
				Log.i(TAG, "abtestResult0 : "+abtestResult0+" ----abtestResult1: "+abtestResult1);
			}
		});
		btnShowDebug=findViewById(R.id.btnShowDebug);
		btnShowDebug.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this,ShowDebugActivity.class));
			}
		});
	}


	/**
	 *  初始化sdk
	 */
	private void initUpAdsSdk()
	{
		UPAdsSdk.init(MainActivity.this, UPAdsSdk.UPAdsGlobalZone.UPAdsGlobalZoneDomestic);

	}



	/**
	 * 初始化sdk
	 * 初始化abtest
	 */
	 public void initSdkAndAbtest()
	 {
		 UPAdsSdk.init(MainActivity.this, UPAdsSdk.UPAdsGlobalZone.UPAdsGlobalZoneDomestic);
		 UPAdsSdk.initAbtConfigJson("wt_8080", true, 100, "avidly", "M", 80, new String[]{"tag1", "tag2"});

	 }

	/**
	 * 获得androidid
	 * @param context
	 * @return
	 */
	public static String GetAndroid(Context context){
		final String androidId;
		androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		return androidId;
	}


	@AfterPermissionGranted(RC_PHONE_STATE)
	public void devicesState() {
		if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_PHONE_STATE)) {
			Toast.makeText(this, "TODO: ", Toast.LENGTH_LONG).show();
		} else {
			EasyPermissions.requestPermissions(this, "需要获取设备信息！", RC_PHONE_STATE, Manifest.permission.READ_PHONE_STATE);
		}
	}


	/**
	 * 用于测试外部统计包
	 */
	private void initOuterAnalysis() {
		Log.i(TAG, "initOuterAnalysis: enter");
		//设置customid
		HolaAnalysis.initWithZone(getApplicationContext(), "8888882", "000002",1);
		HolaAnalysis.setCustomerId(GetAndroid(this)); //android studio user
		HolaAnalysis.log("initOuterAnalysis");

		for (int i = 0; i < 10; i++) {
			HolaAnalysis.log("Call OuterAnalysis2domestic times "+i);
			Log.i(TAG, "Call OuterAnalysis2domestic times "+i);
			try {
				Thread.sleep(
						1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
	}

	@Override
	public void onPermissionsGranted(int requestCode, List<String> perms) {
		Toast.makeText(this, "onPermissionsGranted", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onPermissionsDenied(int requestCode, List<String> perms) {
		Toast.makeText(this, "onPermissionsDenied", Toast.LENGTH_SHORT).show();

		new AppSettingsDialog.Builder(this)
				.setTitle("请求权限")
				.setRationale("需要开启才能进行下去！")
				.build()
				.show();
	}


}
