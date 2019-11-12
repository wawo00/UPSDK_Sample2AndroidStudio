package com.upltv.android.mediation.demo;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.up.ads.UPBannerAd;
import com.up.ads.UPRectangleAd;
import com.up.ads.unity.BaseProxy;
import com.up.ads.wrapper.banner.UPGameEasyBannerWrapper;

public class BannerActivityForXiaomi extends AppCompatActivity {
	private static final String TAG = "AdsSdk_demo";

	LinearLayout banner_container;
	LinearLayout rectangle_container;

	UPBannerAd mBannerAd;
	UPRectangleAd mRectangleAd;

	private void hideStatusNavigationBar(){
		if(Build.VERSION.SDK_INT<16){
			this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}else{
			int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_FULLSCREEN //hide statusBar
					| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; //hide navigationBar
			getWindow().getDecorView().setSystemUiVisibility(uiFlags);
		}
	}

	private void setSystemUIVisible(boolean show) {
		if (show) {
			int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
			uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			getWindow().getDecorView().setSystemUiVisibility(uiFlags);
		} else {
			int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_FULLSCREEN;
			uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			getWindow().getDecorView().setSystemUiVisibility(uiFlags);
		}
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_banner);
//        hideStatusNavigationBar();
		UPGameEasyBannerWrapper.getInstance().initGameBannerWithActivity(this);

		(new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
			@Override
			public void run() {
				UPGameEasyBannerWrapper.getInstance().showBottomBannerAtADPlaceId("sample_banner");
			}
		}, 0);
//        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        mBannerAd = new UPBannerAd(BannerActivity.this, "banner_aaa");
//        mBannerAd.setUpBannerAdListener(new UPBannerAdListener() {
//            @Override
//            public void onClicked() {
//                // 此处为广告点击的回调
//            }
//
//            @Override
//            public void onDisplayed() {
//                // 此处为广告显示的回调
//            }
//        });
//
//        mRectangleAd = new UPRectangleAd(BannerActivity.this, "banner_bbb");
//        mRectangleAd.setUpBannerAdListener(new UPBannerAdListener() {
//            @Override
//            public void onClicked() {
//
//            }
//
//            @Override
//            public void onDisplayed() {
//
//            }
//        });
//
//        banner_container = (LinearLayout) findViewById(R.id.banner_container);
//        banner_container.addView(mBannerAd.getBannerView());
//
//        rectangle_container = (LinearLayout) findViewById(R.id.rectangle_container);
//        rectangle_container.addView(mRectangleAd.getBannerView());
//
//        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(BannerActivity.this, "click", Toast.LENGTH_LONG).show();
//            }
//        });

	}

	@Override
	protected void onResume() {
		Log.i(TAG, "BannerActivity onResume .................................. ");
		super.onResume();
		setSystemUIVisible(false);
		BaseProxy.onApplicationFocus(true);
		//        UPGameEasyBannerWrapper.getInstance().showBottomBannerAtADPlaceId("banner_aaa");
	}

	@Override
	protected void onPause() {
		Log.i(TAG, "BannerActivity onPause .................................. ");
		super.onPause();
		BaseProxy.onApplicationFocus(false);
		//        UPGameEasyBannerWrapper.getInstance().hideBottomBanner();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mBannerAd != null) {
			mBannerAd.destroy();
		}
		if (mRectangleAd != null) {
			mRectangleAd.destroy();
		}
		UPGameEasyBannerWrapper.getInstance().removeGameBannerAtADPlaceId("summer_day_banner_advertising");
	}

}
