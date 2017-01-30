package com.kyview.demo;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kyview.interfaces.AdViewInstlListener;
import com.kyview.manager.AdViewInstlManager;

public class AdInstlActivity extends Activity implements OnClickListener,
		AdViewInstlListener {

	private Button requestAd, showAd, requestShow, customShow;
	PopupWindow mPopupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_adinstl);

		requestAd = (Button) findViewById(R.id.requestad);
		requestAd = (Button) findViewById(R.id.requestad);
		requestAd.setOnClickListener(this);
		requestShow = (Button) findViewById(R.id.requestshow);
		requestShow.setOnClickListener(this);
		customShow = (Button) findViewById(R.id.customshow);
		customShow.setOnClickListener(this);


		//Basic Initialization
		InitConfiguration initConfiguration = new InitConfiguration.Builder(
				this).setUpdateMode(InitConfiguration.UpdateMode.EVERYTIME)
				.setBannerCloseble(InitConfiguration.BannerSwitcher.CANCLOSED)
				.setInstlControlMode(InitConfiguration.InstlControlMode.USERCONTROL)
				.setSupportHtml(InitConfiguration.Html5Switcher.SUPPORT)
				.setRunMode(InitConfiguration.RunMode.TEST)
				.build();


		//Initialization for interstitual advertisement
		AdViewInstlManager.getInstance(this).init(initConfiguration,
				new String[]{MainActivity.SDK_KEY});



	}


	//Click Listener for handling Interstial Ad
	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.requestad) {

			//Requesting for Interstitual Advertisement
			AdViewInstlManager.getInstance(this).requestAd(this, MainActivity.SDK_KEY,this);

		} else if (v.getId() == R.id.requestshow) {

			//Showing Interstitual Advertisement
			AdViewInstlManager.getInstance(this)
					.showAd(this, MainActivity.SDK_KEY);

		}else if(v.getId()==R.id.customshow){

			//Showing popup Interstitual Advertisement
			View view = AdViewInstlManager.getInstance(this).getInstlView(MainActivity.SDK_KEY);

			int width = 897;
			int height = 897;
			if (null == view)
				return;
			final View mPopupView = LayoutInflater.from(this).inflate(R.layout.popup_instl, null);
			LinearLayout mInstlAdLayout = (LinearLayout) mPopupView.findViewById(R.id.ad_layout);
			mInstlAdLayout.addView(view);
			AdViewInstlManager.getInstance(this).reportImpression(MainActivity.SDK_KEY);
			mPopupWindow = new PopupWindow(mPopupView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
			mPopupWindow.setWidth(width);
			mPopupWindow.setHeight(height);
//            LinearLayout.LayoutParams c_params = new LinearLayout.LayoutParams(width, height + DensityUtil.dip2px(this, 50));
//            c_params.addRule(RelativeLayout.CENTER_IN_PARENT);
//            RelativeLayout rl_contentLayout = (RelativeLayout) mPopupView.findViewById(R.id.rl_content);
//            rl_contentLayout.setLayoutParams(c_params);
			mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
			mPopupWindow.setAnimationStyle(android.R.anim.fade_in);
			mPopupWindow.update();
			mPopupWindow.setTouchable(true);
			mPopupWindow.setOutsideTouchable(true);
			mPopupWindow.setFocusable(true);
			mPopupWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
			mInstlAdLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					AdViewInstlManager.getInstance(getApplicationContext()).reportClick(MainActivity.SDK_KEY);
					mPopupWindow.dismiss();
				}
			});
			Button close_btn=(Button) mPopupView.findViewById(R.id.close_btn);
			Button click_btn=(Button) mPopupView.findViewById(R.id.click_btn);
			close_btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					mPopupWindow.dismiss();

				}
			});
			click_btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					AdViewInstlManager.getInstance(getApplicationContext()).reportClick(MainActivity.SDK_KEY);
					mPopupWindow.dismiss();
				}
			});
		}
	}



	//CallBack Methods for Interstial

	@Override
	public void onAdClick(String arg0) {
		Log.i("AdInstlActivity", "onAdClick");
	}

	@Override
	public void onAdDismiss(String arg0) {
		Log.i("AdInstlActivity", "onAdDismiss");
	}

	@Override
	public void onAdDisplay(String arg0) {
		Log.i("AdInstlActivity", "onDisplayAd");
	}

	@Override
	public void onAdFailed(String arg0) {
		Log.i("AdInstlActivity", "onAdFailed");
		Toast.makeText(AdInstlActivity.this, "onReceiveAdFailed",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onAdRecieved(String arg0) {
		Log.i("AdInstlActivity", "onReceivedAd");
		Toast.makeText(AdInstlActivity.this, "onAdRecieved",
				Toast.LENGTH_SHORT).show();
	}

}
