package com.avidly.adsdk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.up.ads.UPInterstitialAd;
import com.up.ads.wrapper.interstitial.UPInterstitialAdListener;
import com.up.ads.wrapper.interstitial.UPInterstitialLoadCallback;

import java.nio.file.Files;
import java.util.ArrayList;

public class MyInterstitialActivity extends Activity {
    private static final String TAG = "AdsSdk_3005";

    public String[] placementIds= {"inter_unity", "inter_applovin", "inter_facebook", " ", "_inter_admob_reward_interstitial", "inter_nend", "inter_ironsource",
            "inter_mobvista",
            "inter_playableads", "inter_vk", "inter_admob", "inter_fbn", "inter_dap", "inter_vungle", "inter_aaa"};


    ArrayList<UPInterstitialAd> UPInterstitialAds=new ArrayList<>();
    UPInterstitialAd mInterstitialAdAAA;
    UPInterstitialAd mInterstitialAdBBB;

    Button mButtonAAA;
//    Button mButtonBBB;
//    Button mBtnDebugView;

    GridView gvRoot;
    BaseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_new);
        gvRoot=(GridView)findViewById(R.id.gv_root);
        adapter=new MyAdapter();
        gvRoot.setAdapter(adapter);
        for (int i = 0; i < placementIds.length; i++) {
            UPInterstitialAd upInterstitialAd=new UPInterstitialAd(this,placementIds[i]);
            UPInterstitialAds.add(upInterstitialAd);
            UPInterstitialLoadCallback callback = new UPInterstitialLoadCallback() {
                @Override
                public void onLoadSuccessed(String placement) {
                    Log.i(TAG, "InterstitialAd " + placement + " onLoadSuccessed:");

                }

                @Override
                public void onLoadFailed(String placement) {
                    Log.i(TAG, "InterstitialAd " + placement + " onLoadFailed:");
                }
            };

            upInterstitialAd.load(callback);
            upInterstitialAd.setUpInterstitialAdListener(new UPInterstitialAdListener() {
                @Override
                public void onClicked() {
                    Log.i(TAG, "InterstitialAd "  + " onClicked:");
                }

                @Override
                public void onClosed() {
                    Log.i(TAG, "InterstitialAd "  + " onClosed:");
                }

                @Override
                public void onDisplayed() {
                    Log.i(TAG, "InterstitialAd "  + " onDisplayed:");
                }
            });


        }
     gvRoot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (UPInterstitialAds.get(i)!=null&&UPInterstitialAds.get(i).isReady()){
                UPInterstitialAds.get(i).show();
            }else{
                Toast.makeText(MyInterstitialActivity.this,"inter广告没有准备好",Toast.LENGTH_SHORT).show();
            }
         }
     });



    }


    class MyAdapter extends BaseAdapter {

        //得到listView中item的总数
        @Override
        public int getCount() {
            return placementIds.length;
        }


        @Override
        public String getItem(int position) {
            return placementIds[position];
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View layout = View.inflate(MyInterstitialActivity.this, R.layout.inter_item, null);
            TextView tvName = (TextView) layout.findViewById(R.id.inter_item_content);
            tvName.setText(placementIds[position]);
            return layout;
        }

    }




}
