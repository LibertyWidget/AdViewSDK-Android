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
		requestAd.setOnClickListener(this);
		showAd = (Button) findViewById(R.id.showad);
		showAd.setOnClickListener(this);
		requestShow = (Button) findViewById(R.id.requestshow);
		requestShow.setOnClickListener(this);
		customShow = (Button) findViewById(R.id.customshow);
		customShow.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.requestad) {
			// 只请求广告，适用于预加载
			AdViewInstlManager.getInstance(this).requestAd(this, MainActivity.key2,this);
//			AdViewInstlManager.getInstance(this).requestAd(this, MainActivity.key1,this);
		} else if (v.getId() == R.id.showad) {
//			AdViewInstlManager.getInstance(this)
//					.showAd(this, MainActivity.key1);
		} else if (v.getId() == R.id.requestshow) {
			AdViewInstlManager.getInstance(this)
				.showAd(this, MainActivity.key2);
		}else if(v.getId()==R.id.customshow){
			View view = AdViewInstlManager.getInstance(this).getInstlView(MainActivity.key1);
			
			int width = 897;
            int height = 897;
            if (null == view)
                return;
            final View mPopupView = LayoutInflater.from(this).inflate(R.layout.popup_instl, null);
            LinearLayout mInstlAdLayout = (LinearLayout) mPopupView.findViewById(R.id.ad_layout);
            mInstlAdLayout.addView(view);
            AdViewInstlManager.getInstance(this).reportImpression(MainActivity.key1);
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
            mInstlAdLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                	AdViewInstlManager.getInstance(getApplicationContext()).reportClick(MainActivity.key1);
                	mPopupWindow.dismiss();
                }
            });
            Button close_btn=(Button) mPopupView.findViewById(R.id.close_btn);
            Button click_btn=(Button) mPopupView.findViewById(R.id.click_btn);
            close_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mPopupWindow.dismiss();
					
				}
			});
            click_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AdViewInstlManager.getInstance(getApplicationContext()).reportClick(MainActivity.key1);	
					mPopupWindow.dismiss();
				}
			});
		}
	}

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
