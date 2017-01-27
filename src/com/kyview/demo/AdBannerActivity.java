package com.kyview.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.amazon.device.ads.AdError;
import com.amazon.device.ads.AdLayout;
import com.amazon.device.ads.AdLayout.AdSize;
import com.amazon.device.ads.AdListener;
import com.amazon.device.ads.AdProperties;
import com.amazon.device.ads.AdRegistration;
import com.amazon.device.ads.AdTargetingOptions;
import com.kyview.InitConfiguration;
import com.kyview.InitConfiguration.BannerSwitcher;
import com.kyview.InitConfiguration.RunMode;
import com.kyview.InitConfiguration.UpdateMode;
import com.kyview.adapters.AdViewAdapter;
import com.kyview.interfaces.AdViewBannerListener;
import com.kyview.manager.AdViewBannerManager;

public class AdBannerActivity extends Activity implements AdViewBannerListener {
	/** Called when the activity is first created. */
	private LinearLayout layout;
	private AdLayout adView = null;
	public static InitConfiguration initConfiguration;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_adbanner);
		initConfiguration = new InitConfiguration.Builder(
				this).setUpdateMode(UpdateMode.EVERYTIME)
				.setBannerCloseble(BannerSwitcher.CANCLOSED)
//				.setSupportHtml(Html5Switcher.SUPPORT)
//				.setInstlControlMode(InstlControlMode.USERCONTROL)
				.setRunMode(RunMode.TEST)
				.build();
		//横幅 配置
		AdViewBannerManager.getInstance(this).init(initConfiguration,
				MainActivity.keySet);
		
		// Code布局Button处理
		findViewById(R.id.btnCode).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						CodeLayout(MainActivity.key1);
					}
				});

		// 纯Code布局Button处理
		findViewById(R.id.btnCode2).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						CodeLayout(MainActivity.key2);
					}
				});

		// 自定义广告平台Button
		findViewById(R.id.btnCustom).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						CodeLayout(MainActivity.key3);
					}
				});

	}

	public void CodeLayout(String key) {
		layout = (LinearLayout) findViewById(R.id.adLayout);
		if (layout == null)
			return;
		View view = AdViewBannerManager.getInstance(this).getAdViewLayout(this,
				key);
		if (null != view) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				parent.removeAllViews();
			}
		}
		AdViewBannerManager.getInstance(this).requestAd(this, key, this);
		view.setTag(key);
		layout.addView(view);
		layout.invalidate();
	}

	public void amazon_proc(final AdViewAdapter adapter, final String key) {
		Log.d("AdViewSample", "Into azmazon");
		try {
			// 测试模式
			AdRegistration.enableLogging(this, true);
			AdRegistration.enableTesting(this, true);
			AdRegistration.setAppKey(this, "sample-app-v1_pub-2");

			// 创建amazon的adview实例
			adView = new AdLayout(this, AdSize.AD_SIZE_320x50);
			// 指定侦听接口
			adView.setListener(new AdListener() {

				@Override
				public void onAdLoaded(AdLayout arg0, AdProperties arg1) {
					Log.d("AdViewSample", arg1.getAdType().toString()
							+ " Ad loaded successfully.");

					// 广告请求成功之后，启动定时器，到时后请求下一个广告
					adapter.reportImpression(AdBannerActivity.this, key);

					adapter.rotateDelayedAd(AdBannerActivity.this, key);

				}

				@Override
				public void onAdExpanded(AdLayout arg0) {
				}

				@Override
				public void onAdCollapsed(AdLayout arg0) {
				}

				@Override
				public void onAdFailedToLoad(AdLayout arg0, AdError arg1) {
					Log.w("AdViewSample",
							"Ad failed to load. Code: "
									+ arg1.getResponseCode() + ", Message: "
									+ arg1.getResponseMessage());

					// 失败之后开始请求下一个广告
//					adapter.rotatePriAd(AdBannerActivity.this, key);
				}

			});
			AdViewBannerManager.getInstance(AdBannerActivity.this).addSubView(
					AdViewBannerManager.getInstance(AdBannerActivity.this)
							.getAdViewLayout(AdBannerActivity.this, key),
					adView, key);
			AdTargetingOptions adOptions = new AdTargetingOptions();
			adView.loadAd(adOptions);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onAdClick(String arg0) {
		Log.i("AdBannerActivity", "onAdClick");
	}

	@Override
	public void onAdClose(String arg0) {
		Log.i("AdBannerActivity", "onAdClose");
		if (null != layout)
			layout.removeView(layout.findViewWithTag(arg0));
	}

	@Override
	public void onAdDisplay(String arg0) {
		Log.i("AdBannerActivity", "onDisplayAd");
	}

	@Override
	public void onAdFailed(String arg0) {
		Log.i("AdBannerActivity", "onAdFailed");
	}

	@Override
	public void onAdReady(String arg0) {
		Log.i("AdBannerActivity", "onAdReady");
	}

	@Override
	protected void onDestroy() {
		AdViewBannerManager.getInstance(this).destroy();
		try {
			super.onDestroy();
			if (null != layout)
				layout.removeAllViews();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}