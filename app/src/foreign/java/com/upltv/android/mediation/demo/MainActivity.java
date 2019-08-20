package com.upltv.android.mediation.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.up.ads.UPAdsSdk;
import com.up.ads.UPIconAd;
import com.up.ads.tool.AccessPrivacyInfoManager;
import com.up.ads.wrapper.banner.UPGameEasyBannerWrapper;
import com.up.ads.wrapper.icon.UPIconAdListener;
import com.upltv.android.mediation.demo.BannerCustomActivity;
import com.upltv.android.mediation.demo.BannerQuickActivity;
import com.upltv.android.mediation.demo.util.VersionUtil;

public class MainActivity extends Activity {
	private static final String TAG = "AdsSdk_demo";
     TextView tv_version;
	Button btnRwardVideo;
	Button btnBanner,btnBannerQuick;
	Button btnInterstitial;
	Button btnExit,btnGetAbTest,btnShowDebug,btnShowIcon;
    RelativeLayout icon_container;
    boolean iconIsReady=false;
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
		btnBannerQuick=findViewById(R.id.btnBannerQuick);
		btnShowIcon=findViewById(R.id.btnShowIcon);
        //icon使用的填充布局
		icon_container=findViewById(R.id.icon_container);



		btnBanner.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, BannerCustomActivity.class);
				startActivity(intent);
			}
		});
		btnBannerQuick.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, BannerQuickActivity.class);
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

//		(new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
//			@Override
//			public void run() {
//
//				UPGameEasyBannerWrapper.getInstance().showBottomBannerAtADPlaceId("sample_banner_foreign");
//			}
//		}, 1000);

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
		 UPAdsSdk.setIsChild(true);
		 //开启ironsource的log
//		 IronSource.setAdaptersDebug(true);
		 UPAdsSdk.initAbtConfigJson("wt_8080", true, 100, "avidly", "M", 80, new String[]{"tag1", "tag2"});

	 }

	@Override
	protected void onDestroy() {
	 	super.onDestroy();
		UPGameEasyBannerWrapper.getInstance().removeGameBannerAtADPlaceId("sample_banner");
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
//        UPAdsSdk.onApplicationResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
//		UPAdsSdk.onApplicationPause();

	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	public void showIcon(){
		final UPIconAd iconAd = new UPIconAd(this);
		iconAd.setUpIconAdListener(new UPIconAdListener() {
			@Override
			public void onLoadSuccessed() {
				Log.i(TAG, "icon onLoadSuccessed");
				iconAd.showIconAd(icon_container,0);
			}

			@Override
			public void onLoadFailed() {
				Log.i(TAG, "icon onLoadFailed");
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
	}
}
