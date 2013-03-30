package com.irun.sm.ui.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class BannerEssentials extends Activity{
  @Override
  public void onCreate(Bundle savedInstanceState) { 
    super.onCreate(savedInstanceState);
    setContentView(R.layout.admo);

    // Lookup R.layout.main
    LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout);
      
    // Create the adView
    // Please replace MY_BANNER_UNIT_ID with your AdMob Publisher ID
    AdView adView = new AdView(this, AdSize.BANNER, "a1500903837807d");
  
    // Add the adView to it
    layout.addView(adView);
     
    // Initiate a generic request to load it with an ad
    AdRequest request = new AdRequest();
    request.setTesting(true);

    adView.loadAd(request);            
  }
}