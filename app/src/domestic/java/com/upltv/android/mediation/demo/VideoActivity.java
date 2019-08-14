package com.upltv.android.mediation.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.up.ads.UPRewardVideoAd;
import com.up.ads.wrapper.video.UPRewardVideoAdListener;
import com.up.ads.wrapper.video.UPRewardVideoLoadCallback;

public class VideoActivity extends Activity {
	private static final String TAG = "upsdk_demo";
	private int coins;

	Button btnPlay;
	Button btnVideo;
	TextView text;
	TextView coin;
	Button mBtnDebugView;

	UPRewardVideoAd mVideoAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);

		btnPlay = (Button) findViewById(R.id.btnPlay);
		btnVideo = (Button) findViewById(R.id.btnVideo);
		text = (TextView) findViewById(R.id.text);
		coin = (TextView) findViewById(R.id.coin);
		mBtnDebugView = (Button) findViewById(R.id.btnDebugView);

		coins = 0;
		mVideoAd = UPRewardVideoAd.getInstance(this);
		mVideoAd.load(new UPRewardVideoLoadCallback() {
			@Override
			public void onLoadFailed() {
				// code
				// 激励视屏加载失败，请等待加载成功
				Log.i(TAG, "video load failed: ");
				Toast.makeText(VideoActivity.this, "广告还没准备好", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onLoadSuccessed() {
				// code
				Log.i(TAG, "video load successed: ");
			}
		});
		mVideoAd.setUpVideoAdListener(new UPRewardVideoAdListener() {
			@Override
			public void onVideoAdClicked() {
				// 此处为广告点击的回调
				Log.i(TAG, "onVideoAdClicked: ");
			}

			@Override
			public void onVideoAdClosed() {
				Log.i(TAG, "onVideoAdClosed: ");
				// 此处为广告关闭的
			}

			@Override
			public void onVideoAdDisplayed() {
				// 此处为广告展示的回调
				Log.i(TAG, "onVideoAdDisplayed: ");
			}

			@Override
			public void onVideoAdReward() {
				// 此处为广告可以发放奖励的回调
				Log.i(TAG, "onVideoAdReward: ");

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						coins = coins + 300;
						coin.setText(coins + " coins");
					}
				});
			}

			@Override
			public void onVideoAdDontReward(String reason) {
				// 此处为广告观看不符合条件，不发放奖励的回调，一般是因为观看视频时间短
				Log.i(TAG, "onVideoAdDontReward: " + reason);
			}
		});

		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				btnPlay.setText("游戏中 ... ...");
				new Handler(getMainLooper()).postDelayed(new Runnable() {
					@Override
					public void run() {
						gameOver();
					}
				}, 10 * 1000);
			}
		});

		btnVideo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mVideoAd.isReady()) {
					mVideoAd.show("rewardvideo");
				} else {
					Toast.makeText(VideoActivity.this, "广告还没准备好", Toast.LENGTH_LONG).show();
				}
			}
		});

		mBtnDebugView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mVideoAd != null) {
					mVideoAd.showVideoDebugActivity(VideoActivity.this);
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (mVideoAd != null)
			mVideoAd.destroy();
	}

	private void gameOver() {
		btnPlay.setText("GAME OVER");
		btnPlay.setEnabled(true);
//		new MyThread().start();
		if (mVideoAd.isReady()) {
			Toast.makeText(VideoActivity.this, "ads ready", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(VideoActivity.this, "ads not ready", Toast.LENGTH_LONG).show();
		}
	}
//	class MyThread extends Thread {
//		public void run() {
//			for (int i = 0; i < 1000; i++) {
//
//				try {
//					Thread.sleep(1000);
//					mVideoAd.isReady();
//					Log.i("<aly","运行了 "+i+" 次");
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
}
