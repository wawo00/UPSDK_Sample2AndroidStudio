package com.upltv.android.mediation.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.up.ads.UPBannerAd;
import com.up.ads.wrapper.banner.UPBannerAdListener;

public class BannerCustomActivity extends Activity {
	private static final String TAG = "upsdk_demo";

	private static final String bannerPlacementId="sample_banner";

	private LinearLayout ll_banner_container;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_banner);
		ll_banner_container=findViewById(R.id.banner_container);

		final UPBannerAd mBannerAd = new UPBannerAd(this, bannerPlacementId);
		ll_banner_container.addView(mBannerAd.getBannerView());

		mBannerAd.setUpBannerAdListener(new UPBannerAdListener() {
			@Override
			public void onClicked() {
				Log.i(TAG, bannerPlacementId+" onClicked ");
			}

			@Override
			public void onDisplayed() {
				Log.i(TAG, bannerPlacementId+" onDisplayed ");
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
