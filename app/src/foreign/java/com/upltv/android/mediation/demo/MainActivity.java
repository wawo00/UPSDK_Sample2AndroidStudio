package com.upltv.android.mediation.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.up.ads.UPAdsSdk;
import com.up.ads.UPBannerAd;
import com.up.ads.UPIconAd;
import com.up.ads.tool.AccessPrivacyInfoManager;
import com.up.ads.tool.Helper;
import com.up.ads.wrapper.banner.UPBannerAdListener;
import com.up.ads.wrapper.banner.UPGameEasyBannerWrapper;
import com.up.ads.wrapper.icon.UPIconAdListener;
import com.upltv.android.mediation.demo.BannerCustomActivity;
import com.upltv.android.mediation.demo.BannerQuickActivity;
import com.upltv.android.mediation.demo.util.VersionUtil;

public class MainActivity extends Activity {
    private static final String TAG = "AdsSdk_demo";
    TextView tv_version;
    Button btnRwardVideo;
    Button btnBanner, btnBannerQuick;
    Button btnInterstitial;
    Button btnExit, btnGetAbTest, btnShowDebug, btnShowIcon;
    RelativeLayout icon_container;
    boolean iconIsReady = false;
    CheckBox ckBoxIsChild;
    EditText et_year, et_month;
    private String androidAppKey = " 30490d1391e3";
    String bannerPlacementId = "sample_banner";
    private LinearLayout ll_banner_container;

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
        AccessPrivacyInfoManager.UPAccessPrivacyInfoStatusEnum result = UPAdsSdk.getAccessPrivacyInfoStatus(MainActivity.this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 3 * 1000);
        initUpAdsSdk(result);


        initView();
        //icon使用的填充布局
        icon_container = findViewById(R.id.icon_container);

//        btnBanner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(MainActivity.this, BannerCustomActivity.class);
////                startActivity(intent);
//                Toast.makeText(MainActivity.this, "1s后在下方展示banner", Toast.LENGTH_SHORT).show();
//                showCustomBanner();
//
//            }
//        });
//        btnBannerQuick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showQuickBanner();
//                Toast.makeText(MainActivity.this, "1s后在下方展示banner", Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(MainActivity.this, BannerQuickActivity.class);
////                startActivity(intent);
//            }
//        });

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


        btnGetAbTest = findViewById(R.id.btnGetAbTest);
        btnGetAbTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String abtestResult0 = UPAdsSdk.getAbtConfigString("ad_hp");
                String abtestResult1 = UPAdsSdk.getAbtConfigString("ad_hp");
                Log.i(TAG, "abtestResult0 : " + abtestResult0 + " ----abtestResult1: " + abtestResult1);
            }
        });
        btnShowDebug = findViewById(R.id.btnShowDebug);
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

                iconIsReady = true;
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
                if (iconIsReady) {
                    iconAd.showIconAd(icon_container, 0);
                } else {
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

    public void showCustomBanner(View view) {
        Toast.makeText(MainActivity.this, "1s后在下方展示banner", Toast.LENGTH_SHORT).show();
        if (UPGameEasyBannerWrapper.getInstance()!=null){
            UPGameEasyBannerWrapper.getInstance().removeGameBannerAtADPlaceId(bannerPlacementId);
        }
        if (ll_banner_container.getVisibility()==View.GONE){
            ll_banner_container.setVisibility(View.VISIBLE);
        }
        final UPBannerAd mBannerAd = new UPBannerAd(this, bannerPlacementId);
        ll_banner_container.addView(mBannerAd.getBannerView());
        mBannerAd.setUpBannerAdListener(new UPBannerAdListener() {
            @Override
            public void onClicked() {
                Log.i(TAG, bannerPlacementId + " onClicked ");
            }

            @Override
            public void onDisplayed() {
                Log.i(TAG, bannerPlacementId + " onDisplayed ");
            }
        });
    }

    public void showQuickBanner(View view) {
        Toast.makeText(MainActivity.this, "1s后在下方展示banner", Toast.LENGTH_SHORT).show();
        if (ll_banner_container.getVisibility()==View.VISIBLE){
            ll_banner_container.setVisibility(View.GONE);
        }
        // 添加回调接口
        UPGameEasyBannerWrapper.getInstance().addBannerCallbackAtADPlaceId(bannerPlacementId, new UPBannerAdListener() {
            @Override
            public void onClicked() {
                Log.i(TAG, bannerPlacementId + " onClicked ");
            }

            @Override
            public void onDisplayed() {
                Log.i(TAG, bannerPlacementId + " onDisplayed ");
            }
        });

        (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
            @Override
            public void run() {
                UPGameEasyBannerWrapper.getInstance().showBottomBannerAtADPlaceId(bannerPlacementId);
            }
        }, 1000);
    }

    public void hideQuickBanner(View view) {
    if ( UPGameEasyBannerWrapper.getInstance()!=null){
        UPGameEasyBannerWrapper.getInstance().hideBottomBanner();
    }
    }

    public void removeQuickBanner(View view) {
        if ( UPGameEasyBannerWrapper.getInstance()!=null){
            UPGameEasyBannerWrapper.getInstance().removeBannerCallbackAtADPlaceId(bannerPlacementId);
        }
    }

    public void hideCustomBanner(View view) {
        if (ll_banner_container.getVisibility()==View.VISIBLE){
            ll_banner_container.setVisibility(View.GONE);
        }
    }

    private void initView() {
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setText(VersionUtil.getVersionName(this));
        btnShowIcon = findViewById(R.id.btnShowIcon);
        ckBoxIsChild = findViewById(R.id.ckBoxSetIsChild);
        et_year = findViewById(R.id.et_year);
        et_month = findViewById(R.id.et_month);
        ckBoxIsChild.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (UPAdsSdk.isInited()) {
                        UPAdsSdk.setIsChild(b);
                        showAsToast("coppa is " + b);
                    } else {
                        showAsToast("请先初始化upsdk");
                    }
                }
            }
        });

        UPGameEasyBannerWrapper.getInstance().initGameBannerWithActivity(this);
        ll_banner_container = findViewById(R.id.banner_container);


    }


    /**
     * 此种初始化方式在您已经询问过用户并获得结果
     */
    private void initUpAdsSdk() {

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

        UPAdsSdk.init(MainActivity.this, androidAppKey);

        UPAdsSdk.initAbtConfigJson("wt_8080", true, 100, "avidly", "M", 80, new String[]{"tag1", "tag2"});

    }

    /**
     * 此种初始化方式会通过sdk自带弹框询问用户
     */
    private void initUpAdsSdk(AccessPrivacyInfoManager.UPAccessPrivacyInfoStatusEnum result) {
        if (result == AccessPrivacyInfoManager.UPAccessPrivacyInfoStatusEnum.UPAccessPrivacyInfoStatusUnkown) {

            //首先判断是否为欧盟地区，满足GDPR政策
            UPAdsSdk.isEuropeanUnionUser(MainActivity.this, new UPAdsSdk.UPEuropeanUnionUserCheckCallBack() {
                @Override
                public void isEuropeanUnionUser(boolean isEurope) {
                    if (isEurope) {
                        /**
                         * 弹出系统弹框询问用户
                         */
                        UPAdsSdk.notifyAccessPrivacyInfoStatus(MainActivity.this, myAccessPrivacyStatusInfoCallBack);
                    } else {
                        initSdkAndGDPR();
                    }
                }
            });
        } else {
            initSdkAndGDPR();
        }
    }

    private UPAdsSdk.UPAccessPrivacyInfoStatusCallBack myAccessPrivacyStatusInfoCallBack = new UPAdsSdk.UPAccessPrivacyInfoStatusCallBack() {
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
    public void initSdkAndGDPR() {


        UPAdsSdk.init(MainActivity.this, androidAppKey);
//        UPAdsSdk.setIsChild(true);
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
        UPAdsSdk.onApplicationResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        UPAdsSdk.onApplicationPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void showIcon() {
        final UPIconAd iconAd = new UPIconAd(this);
        iconAd.setUpIconAdListener(new UPIconAdListener() {
            @Override
            public void onLoadSuccessed() {
                Log.i(TAG, "icon onLoadSuccessed");
                iconAd.showIconAd(icon_container, 0);
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

    public void setBirthday(View view) {
        String yearStr = et_year.getText().toString().trim();
        String monthStr = et_month.getText().toString().trim();
        if (!yearStr.equals("") && !monthStr.equals("")) {
            UPAdsSdk.setBirthday(Integer.valueOf(yearStr), Integer.valueOf(monthStr));
        } else {
            showAsToast("输入格式错误");
        }
    }

    public void showAge(View view) {
        if (UPAdsSdk.isInited()) {
            showAsToast("当前用户年龄是" + UPAdsSdk.getAge());
        }
    }

    public void showAsToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    public void autoOneKeyInspect(View v) {
        UPAdsSdk.autoOneKeyInspect(this);

    }

    @Override
    public void onBackPressed() {
        UPAdsSdk.onAndroidBackKeyDown();
    }


}
