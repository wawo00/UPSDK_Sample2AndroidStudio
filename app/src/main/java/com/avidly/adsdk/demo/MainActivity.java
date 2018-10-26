package com.avidly.adsdk.demo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avidly.ads.AvidlyAdsSdk;
import com.avidly.adsdk.demo.util.VersionUtil;
import com.up.ads.UPAdsSdk;
import com.up.ads.tool.AccessPrivacyInfoManager;
import com.up.ads.wrapper.banner.UPBannerAdListener;
import com.up.ads.wrapper.banner.UPGameEasyBannerWrapper;

public class MainActivity extends Activity {
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
//				Intent intent = new Intent(MainActivity.this, InterstitialActivity.class);
				Intent intent = new Intent(MainActivity.this, MyInterstitialActivity.class);

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

		//初始化banner

		UPGameEasyBannerWrapper.getInstance().initGameBannerWithActivity(this);
		// 添加回调接口
		UPGameEasyBannerWrapper.getInstance().addBannerCallbackAtADPlaceId("banner_aaa", new UPBannerAdListener() {
			@Override
			public void onClicked() {
				Log.i(TAG, "banner_aaa onClicked ");
			}

			@Override
			public void onDisplayed() {
				Log.i(TAG, "banner_aaa onDisplayed ");
			}
		});
		UPGameEasyBannerWrapper.getInstance().addBannerCallbackAtADPlaceId("banner_bbb", new UPBannerAdListener() {
			@Override
			public void onClicked() {
				Log.i(TAG, "banner_bbb onClicked ");
			}

			@Override
			public void onDisplayed() {
				Log.i(TAG, "banner_bbb onDisplayed ");
			}
		});
//        UPGameEasyBannerWrapper.getInstance().showTopBannerAtADPlaceId("banner_aaa");

		(new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
			@Override
			public void run() {
//				UPGameEasyBannerWrapper.getInstance().showTopBannerAtADPlaceId("banner_aaa");

			}
		}, 1000);

		(new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
			@Override
			public void run() {

				UPGameEasyBannerWrapper.getInstance().showBottomBannerAtADPlaceId("banner_aaa");
			}
		}, 1000);

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

	/**
	 * 初始化sdk
	 * 初始化abtest
	 * 初始化GDPR
	 */
	 public void initSdkAndGDPR()
	 {
		 UPAdsSdk.init(MainActivity.this, UPAdsSdk.UPAdsGlobalZone.UPAdsGlobalZoneForeign);
		 UPAdsSdk.initAbtConfigJson("wt_8080", true, 100, "avidly", "M", 80, new String[]{"tag1", "tag2"});

	 }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		UPGameEasyBannerWrapper.getInstance().removeGameBannerAtADPlaceId("banner_aaa");
	}

}
