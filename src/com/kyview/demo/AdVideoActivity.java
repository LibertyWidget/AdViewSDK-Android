package com.kyview.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.kyview.interfaces.AdViewVideoListener;
import com.kyview.manager.AdViewVideoManager;

public class AdVideoActivity extends Activity implements OnClickListener,
		AdViewVideoListener {

	private Button requestAd, showAd;
	PopupWindow mPopupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_advideo);

		requestAd = (Button) findViewById(R.id.requestad);
		requestAd.setOnClickListener(this);
		showAd = (Button) findViewById(R.id.showad);
		showAd.setOnClickListener(this);

		//Basic Initialization
		InitConfiguration initConfiguration = new InitConfiguration.Builder(
				this).setUpdateMode(InitConfiguration.UpdateMode.EVERYTIME)
				.setBannerCloseble(InitConfiguration.BannerSwitcher.CANCLOSED)
				.setInstlControlMode(InitConfiguration.InstlControlMode.USERCONTROL)
				.setSupportHtml(InitConfiguration.Html5Switcher.SUPPORT)
				.setRunMode(InitConfiguration.RunMode.TEST)
				.build();

		//Initialization For Video ads
		AdViewVideoManager.getInstance(this).init(initConfiguration,
				new String[]{MainActivity.SDK_KEY});

	}

	//Click Listener for handling Video Ad

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.requestad) {

			// Only ads are requested for preloading
			AdViewVideoManager.getInstance(this).requestAd(this, SDK_KEY, this);
		} else if (v.getId() == R.id.showad) {

			//Playing the video
			AdViewVideoManager.getInstance(this).playVideo(this, SDK_KEY);
		}
	}


	//CallBack Method for Video Ad

	@Override
	public void onAdFailed(String arg0) {
		Log.i("AdInstlActivity", "onAdFailed");
		Toast.makeText(AdVideoActivity.this, "onReceiveAdFailed",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onAdRecieved(String arg0) {
		Log.i("AdInstlActivity", "onReceivedAd");
		Toast.makeText(AdVideoActivity.this, "onAdRecieved",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onAdClose(String arg0) {
		Log.i("AdInstlActivity", "onAdDismiss");
		
	}

	@Override
	public void onAdPlayEnd(String arg0, Boolean arg1) {
		Log.i("AdInstlActivity", "onDisplayAd");
		
	}

	@Override
	public void onAdPlayStart(String arg0) {
		
	}

}
