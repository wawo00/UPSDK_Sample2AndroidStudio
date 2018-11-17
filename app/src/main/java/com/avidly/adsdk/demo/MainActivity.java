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
import com.up.ads.UPAdsSdk;
import com.up.ads.tool.AccessPrivacyInfoManager;
import java.util.List;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
	public static final int RC_PHONE_STATE=3;
	private static final String TAG = "upsdk_demo";
     TextView tv_version;
	Button btnRwardVideo;
	Button btnBanner;
	Button btnInterstitial;
	Button btnExit,btnGetAbTest,btnShowDebug;
	private static final String[] WRITE_EXTERNALWithREQUEST_INSTALL_PACKAGESWithREAD_PHONE_STATE=
			{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,Manifest.permission.REQUEST_INSTALL_PACKAGES};
	private static final int RC_WRITE_EXTERNAL_STORAGE = 111;
	private static final int RC_READ_PHONE_STATE = 112;
	private static final int RC_REQUEST_INSTALL_PACKAGES = 113;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		UPAdsSdk.setDebuggable(true);
		requestPermissions();
		//设置customid
		UPAdsSdk.setCustomerId(GetAndroid(this));

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

	@AfterPermissionGranted(RC_WRITE_EXTERNAL_STORAGE)
	public void requestPermissions() {
		if (hasAllPermissions()) {
			// Have permission, do the thing!
			Toast.makeText(this, "TODO:获得所有需要的权限了", Toast.LENGTH_LONG).show();
		} else {
			// Ask for one permission
			EasyPermissions.requestPermissions(
					this,
					"需要以下权限",
					RC_WRITE_EXTERNAL_STORAGE,
					RC_PHONE_STATE,
					RC_REQUEST_INSTALL_PACKAGES,
					WRITE_EXTERNALWithREQUEST_INSTALL_PACKAGESWithREAD_PHONE_STATE
					);
		}
	}

	private boolean hasWriteExteralPermission() {
		return EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
	}

	private boolean hasAllPermissions() {
		return EasyPermissions.hasPermissions(this, WRITE_EXTERNALWithREQUEST_INSTALL_PACKAGESWithREAD_PHONE_STATE);
	}

	private boolean hasReadPhonePermission() {
		return EasyPermissions.hasPermissions(this, Manifest.permission.READ_PHONE_STATE);
	}
	@Override
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String[] permissions,
										   @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		// EasyPermissions handles the request result.
		EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
	}

	@Override
	public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
		Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
	}

	@Override
	public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
		Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
		// (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
		// This will display a dialog directing them to enable the permission in app settings.
		if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
			new AppSettingsDialog.Builder(this).build().show();
		}
	}

}
