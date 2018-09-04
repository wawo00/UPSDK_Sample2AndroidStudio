package com.upltv.adsdk.demo;

import com.avidly.ads.AvidlyBannerAd;
import com.avidly.ads.AvidlyRectangleAd;
import com.avidly.ads.wrapper.banner.AvidlyBannerAdListener;
import com.up.ads.wrapper.banner.UPBannerAdListener;
import com.up.ads.wrapper.banner.UPGameEasyBannerWrapper;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
public class BannerActivity extends Activity {
	private static final String TAG = "AdsSdk_demo";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_banner);
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		UPGameEasyBannerWrapper.getInstance().removeGameBannerAtADPlaceId("banner_aaa");
	}
}
