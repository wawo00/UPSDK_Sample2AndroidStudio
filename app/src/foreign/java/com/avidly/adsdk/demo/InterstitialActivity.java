package com.avidly.adsdk.demo;

import com.avidly.ads.AvidlyInterstitialAd;
import com.avidly.ads.wrapper.interstitial.AvidlyInterstitialAdListener;
import com.avidly.adsdk.demo.R;
import com.up.ads.UPAdsSdk;
import com.up.ads.UPInterstitialAd;
import com.up.ads.wrapper.interstitial.UPInterstitialLoadCallback;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class InterstitialActivity extends Activity {
	private static final String TAG = "upsdk_demo";

	UPInterstitialAd mInterstitialAdAAA;

	Button mButtonAAA;
	Button mButtonBBB;
	Button mBtnDebugView;

    private static  String interPlacementId="Interstitial0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interstitial);

		mInterstitialAdAAA = new UPInterstitialAd(InterstitialActivity.this, interPlacementId);
// 设置回调接口
		final UPInterstitialLoadCallback callback = new UPInterstitialLoadCallback() {
			@Override
			public void onLoadSuccessed(String placement) {
				Log.i(TAG,   placement + " onLoadSuccessed:");

			}
			@Override
			public void onLoadFailed(String placement) {
				Log.i(TAG, placement + " onLoadFailed:");
			}
		};

//展示
		mInterstitialAdAAA.load(callback);

		mButtonAAA = (Button) findViewById(R.id.buttonAAA);
		mButtonBBB = (Button) findViewById(R.id.buttonBBB);
		mBtnDebugView = (Button) findViewById(R.id.btnDebugView);

		mButtonAAA.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mInterstitialAdAAA != null && mInterstitialAdAAA.isReady()) {
					mInterstitialAdAAA.show();
				}
			}
		});



		mBtnDebugView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mInterstitialAdAAA != null) {
					mInterstitialAdAAA.showInterstitialDebugActivity(InterstitialActivity.this);
				}
			}
		});



	}

}
