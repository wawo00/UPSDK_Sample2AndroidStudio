package com.avidly.adsdk.demo;

import com.avidly.ads.AvidlyRewardVideoAd;
import com.avidly.ads.wrapper.video.AvidlyRewardVideoAdListener;
import com.avidly.adsdk.demo.R;
import com.up.ads.UPRewardVideoAd;
import com.up.ads.wrapper.video.UPRewardVideoAdListener;
import com.up.ads.wrapper.video.UPRewardVideoLoadCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class VideoActivity extends Activity {
	private static final String TAG = "AdsSdk_demo";
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

		btnVideo.setEnabled(false);
		coins = 0;
		mVideoAd = UPRewardVideoAd.getInstance(this);
		mVideoAd.load(new UPRewardVideoLoadCallback() {
			@Override
			public void onLoadFailed() {
				// code
				// 激励视屏加载失败，请等待加载成功
				Log.i(TAG, "video load failed: ");

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
			}

			@Override
			public void onVideoAdClosed() {
				// 此处为广告关闭的回调
				if (mVideoAd.isReady()) {
					new Handler(getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							final AlertDialog.Builder builder = new AlertDialog.Builder(VideoActivity.this);
							builder.setTitle("Game Over");
							builder.setMessage("再看一个, +300 coins");
							builder.setNegativeButton("开始", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									showAd();
								}
							});
							builder.show();
						}
					});
				} else {
					btnVideo.setEnabled(false);
				}
			}

			@Override
			public void onVideoAdDisplayed() {
				// 此处为广告展示的回调
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
				btnPlay.setEnabled(false);
				btnVideo.setEnabled(false);

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
				btnPlay.setText("开始游戏");

				final AlertDialog.Builder builder = new AlertDialog.Builder(VideoActivity.this);
				builder.setTitle("Game Over");
				builder.setMessage("看视频拿奖励, +300 coins");
				builder.setNegativeButton("开始", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						showAd();
					}
				});
				builder.show();
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
		btnPlay.setText("游戏结束");
		btnPlay.setEnabled(true);
		new MyThread().start();
		btnVideo.setEnabled(true);
		if (mVideoAd.isReady()) {
			btnVideo.setEnabled(true);
//		else {
//			Toast.makeText(this, "视频广告没有加载成功", Toast.LENGTH_SHORT).show();
//			btnVideo.setEnabled(false);
//		}
		}
	}

	private void showAd() {
		if (mVideoAd.isReady()) {
			mVideoAd.show("game");
		} else {
			Toast.makeText(VideoActivity.this, "广告还没准备好", Toast.LENGTH_LONG).show();
		}
	}

	class MyThread extends Thread {
		public void run() {
			for (int i = 0; i < 1000; i++) {

				try {
					Thread.sleep(1000);
					mVideoAd.isReady();
					Log.i("<aly","运行了 "+i+" 次");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
