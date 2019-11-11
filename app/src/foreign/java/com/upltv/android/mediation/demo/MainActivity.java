package com.upltv.android.mediation.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aly.sdk.ALYAnalysis;
import com.up.ads.UPAdsSdk;
import com.up.ads.UPIconAd;
import com.up.ads.tool.AccessPrivacyInfoManager;
import com.up.ads.tool.Helper;
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
    // 用于统计包的测试，对应的是正式服务器上包名com.game.greedycandy.free
    private String productid="1002457";
    private String channelId="32400";


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

    private void initView() {
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setText(VersionUtil.getVersionName(this));
        btnBanner = (Button) findViewById(R.id.btnBanner);
        btnBannerQuick = findViewById(R.id.btnBannerQuick);
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

        UPAdsSdk.init(MainActivity.this);

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
        // 初始化外部统计包
//        testExtAly();
        UPAdsSdk.init(MainActivity.this, UPAdsSdk.UPAdsGlobalZone.UPAdsGlobalZoneForeign);
//        UPAdsSdk.setIsChild(true);
        //开启ironsource的log
//		 IronSource.setAdaptersDebug(true);
        UPAdsSdk.initAbtConfigJson("wt_8080", true, 100, "avidly", "M", 80, new String[]{"tag1", "tag2"});

    }

    private void testExtAly() {
        ALYAnalysis.init(this,productid,channelId);
        ALYAnalysis.setCustomerId("roy-testuser-00001");
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "testExtAly: getOpenId : "+ALYAnalysis.getOpenId(MainActivity.this));
                Log.i(TAG, "testExtAly: getUserId : "+ALYAnalysis.getUserId());

            }
        },2*1000);
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

    public void showAutoTest(View view) {
        UPAdsSdk.autoOneKeyInspect(this);
    }


    @Override
    public void onBackPressed() {
        UPAdsSdk.onAndroidBackKeyDown();
    }
}
