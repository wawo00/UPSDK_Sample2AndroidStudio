package com.avidly.adsdk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.up.ads.wrapper.banner.UPBannerAdListener;
import com.up.ads.wrapper.banner.UPGameEasyBannerWrapper;

public class BannerActivity extends Activity {
	private static final String TAG = "upsdk_demo";

	private static final String bannerPlacementId="sample_banner";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_banner);
		UPGameEasyBannerWrapper.getInstance().initGameBannerWithActivity(this);
		// 添加回调接口
		UPGameEasyBannerWrapper.getInstance().addBannerCallbackAtADPlaceId(bannerPlacementId, new UPBannerAdListener() {
			@Override
			public void onClicked() {
				Log.i(TAG, bannerPlacementId+" onClicked ");
			}

			@Override
			public void onDisplayed() {
				Log.i(TAG, bannerPlacementId+" onDisplayed ");
			}
		});

//        UPGameEasyBannerWrapper.getInstance().showTopBannerAtADPlaceId("banner_aaa");

		(new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
			@Override
			public void run() {

				UPGameEasyBannerWrapper.getInstance().showBottomBannerAtADPlaceId(bannerPlacementId);
			}
		}, 1000);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		UPGameEasyBannerWrapper.getInstance().removeGameBannerAtADPlaceId(bannerPlacementId);
	}


}
