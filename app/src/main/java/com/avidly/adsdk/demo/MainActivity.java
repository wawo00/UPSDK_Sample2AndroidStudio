package com.avidly.adsdk.demo;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
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


public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
	public static final int RC_PHONE_STATE=3;
	private static final String TAG = "AdsSdk_demo";
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
//        UPAdsSdk.init();
		//方法一 用户自己检查GDPR
//		initUpAdsSdk();
		photoState();
		initOuterAnalysis();

		//设置customid
		UPAdsSdk.setCustomerId("123321");


		//设置customid
//		UPAdsSdk.setCustomerId("66666666666666666666666666");

		//方法二
		AccessPrivacyInfoManager.UPAccessPrivacyInfoStatusEnum result=UPAdsSdk.getAccessPrivacyInfoStatus(MainActivity.this);

		initUpAdsSdk(result);
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
//				Intent intent = new Intent(MainActivity.this, MyInterstitialActivity.class);

				startActivity(intent);

			}
		});

//		IronSource.init(this,"2121");


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
	 * 此种初始化方式在您已经询问过用户并获得结果
	 */
	private void initUpAdsSdk()
	{

		/**
		 * 同意将设设备信息传递给sdk
		 */
		UPAdsSdk.updateAccessPrivacyInfoStatus(this, AccessPrivacyInfoManager.UPAccessPrivacyInfoStatusEnum.UPAccessPrivacyInfoStatusAccepted);

		/**
		 * 不同意将设设备信息传递给sdk
		 */
		UPAdsSdk.updateAccessPrivacyInfoStatus(this, AccessPrivacyInfoManager.UPAccessPrivacyInfoStatusEnum.UPAccessPrivacyInfoStatusDefined);

		/**
		 * 初始化sdk
		 */

		UPAdsSdk.init(MainActivity.this);

        UPAdsSdk.initAbtConfigJson("wt_8080", true, 100, "avidly", "M", 80, new String[]{"tag1", "tag2"});

	}

	/**
	 * 此种初始化方式会通过sdk自带弹框询问用户
	 */
	private void initUpAdsSdk(AccessPrivacyInfoManager.UPAccessPrivacyInfoStatusEnum result) {
    if (result== AccessPrivacyInfoManager.UPAccessPrivacyInfoStatusEnum.UPAccessPrivacyInfoStatusUnkown){

	  //首先判断是否为欧盟地区，满足GDPR政策
	  UPAdsSdk.isEuropeanUnionUser(MainActivity.this, new UPAdsSdk.UPEuropeanUnionUserCheckCallBack() {
		  @Override
		  public void isEuropeanUnionUser(boolean isEurope) {
			  if (isEurope){
				  /**
				   * 弹出系统弹框询问用户
				   */
				  UPAdsSdk.notifyAccessPrivacyInfoStatus(MainActivity.this,myAccessPrivacyStatusInfoCallBack);
			  }else{
				 initSdkAndGDPR();
			  }
		  }
	  });
    }else{
		initSdkAndGDPR();
	}
	}

   private UPAdsSdk.UPAccessPrivacyInfoStatusCallBack myAccessPrivacyStatusInfoCallBack =new UPAdsSdk.UPAccessPrivacyInfoStatusCallBack() {
	   @Override
	   public void onAccessPrivacyInfoAccepted() {
		   Log.i(TAG, "onAccessPrivacyInfoAccepted: ..............................");
		   /**
			* 同意将设设备信息传递给sdk
			*/
		   //您自己的操作

		   //进行sdk的初始化
		   initSdkAndGDPR();
	   }

	   @Override
	   public void onAccessPrivacyInfoDefined() {
		   Log.i(TAG, "onAccessPrivacyInfoDefined: ..............................");
		   /**
			* 不同意将设设备信息传递给sdk
			*/
		   //您自己的操作
		   //进行sdk的初始化
		   initSdkAndGDPR();
	   }
   };





	@AfterPermissionGranted(RC_PHONE_STATE)
	public void photoState() {
		if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_PHONE_STATE)) {
			Toast.makeText(this, "TODO: Photo things", Toast.LENGTH_LONG).show();
		} else {
			EasyPermissions.requestPermissions(this, "需要获取设备信息！", RC_PHONE_STATE, Manifest.permission.READ_PHONE_STATE);
		}
	}







	/**
	 * 初始化sdk
	 * 初始化abtest
	 * 初始化GDPR
	 */
	 public void initSdkAndGDPR()
	 {
		 UPAdsSdk.init(MainActivity.this, UPAdsSdk.UPAdsGlobalZone.UPAdsGlobalZoneDomestic);
		 UPAdsSdk.initAbtConfigJson("wt_8080", true, 100, "avidly", "M", 80, new String[]{"tag1", "tag2"});

	 }



	/**
	 * 用于测试外部统计包
	 */
	private void initOuterAnalysis() {
		Log.i(TAG, "initOuterAnalysis: enter");
		HolaAnalysis.initWithZone(getApplicationContext(), "999999", "666666",0);
		HolaAnalysis.setCustomerId("99999");
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
