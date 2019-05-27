package com.avidly.adsdk.demo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avidly.adsdk.demo.util.VersionUtil;
import com.up.ads.UPAdsSdk;
import com.up.ads.UPIconAd;
import com.up.ads.wrapper.icon.UPIconAdListener;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.REQUEST_INSTALL_PACKAGES;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class MainActivity extends AppCompatActivity  {
	//implements EasyPermissions.PermissionCallbacks
	public static final int RC_PHONE_STATE=3;
	private static final String TAG = "upsdk_demo";
	TextView tv_version;
	Button btnRwardVideo;
	Button btnBanner,btnBannerQuick;
	Button btnInterstitial;
	Button btnExit,btnGetAbTest,btnShowDebug,btnShowIcon;
	private static final String[] WRITE_EXTERNALWithREQUEST_INSTALL_PACKAGESWithREAD_PHONE_STATE=
			{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,Manifest.permission.REQUEST_INSTALL_PACKAGES};
	private static final int RC_WRITE_EXTERNAL_STORAGE = 111;
	private static final int RC_READ_PHONE_STATE = 112;
	private static final int RC_REQUEST_INSTALL_PACKAGES = 113;
	RelativeLayout icon_container;
	boolean iconIsReady=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		UPAdsSdk.setDebuggable(true);
		requestPermissions();
		//设置customid
		UPAdsSdk.setCustomerId(GetAndroid(this));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (ContextCompat.checkSelfPermission(MainActivity.this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
					|| ContextCompat.checkSelfPermission(MainActivity.this, REQUEST_INSTALL_PACKAGES) != PackageManager.PERMISSION_GRANTED
					|| ContextCompat.checkSelfPermission(MainActivity.this, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(MainActivity.this, new String[]{WRITE_EXTERNAL_STORAGE, REQUEST_INSTALL_PACKAGES, READ_PHONE_STATE}, 001);
			}
		}
		initUpAdsSdk();
		tv_version= (TextView)findViewById(R.id.tv_version);
		tv_version.setText(VersionUtil.getVersionName(this));
		btnBanner = (Button) findViewById(R.id.btnBanner);
		btnBannerQuick=findViewById(R.id.btnBannerQuick);
				btnShowIcon=findViewById(R.id.btnShowIcon);
		//icon使用的填充布局
		icon_container=findViewById(R.id.icon_container);
		btnBannerQuick.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, BannerCustomActivity.class);
				startActivity(intent);

			}
		});
		btnBanner.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, BannerQuickActivity.class);
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


		final UPIconAd iconAd = new UPIconAd(this);
		iconAd.setUpIconAdListener(new UPIconAdListener() {
			@Override
			public void onLoadSuccessed() {
				Log.i(TAG, "icon onLoadSuccessed");

				iconIsReady=true;
			}

			@Override
			public void onLoadFailed() {
				Log.i(TAG, "icon onLoadFailed");
				Toast.makeText(MainActivity.this, "icon load failed", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onClicked() {
				Log.i(TAG, "icon onClicked");
			}

			@Override
			public void onDisplayed() {
				Log.i(TAG, "icon onDisplayed");
			}
		});
		iconAd.loadIconAd();
		btnShowIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (iconIsReady){
					iconAd.showIconAd(icon_container,0);
				}else{
					Toast.makeText(MainActivity.this, "Icon还没有准备好", Toast.LENGTH_SHORT).show();
				}
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
//	@Override
//	public void onRequestPermissionsResult(int requestCode,
//										   @NonNull String[] permissions,
//										   @NonNull int[] grantResults) {
//		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//		// EasyPermissions handles the request result.
//		EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//	}

//	@Override
//	public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
//		Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
//	}
//
//	@Override
//	public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
//		Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
//		// (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
//		// This will display a dialog directing them to enable the permission in app settings.
//		if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//			new AppSettingsDialog.Builder(this).build().show();
//		}
//	}
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
	super.onRequestPermissionsResult(requestCode, permissions, grantResults);
}

}