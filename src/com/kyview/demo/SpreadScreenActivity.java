package com.kyview.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kyview.InitConfiguration;
import com.kyview.InitConfiguration.BannerSwitcher;
import com.kyview.InitConfiguration.RunMode;
import com.kyview.InitConfiguration.UpdateMode;
import com.kyview.interfaces.AdViewSpreadListener;
import com.kyview.manager.AdViewBannerManager;
import com.kyview.manager.AdViewInstlManager;
import com.kyview.manager.AdViewNativeManager;
import com.kyview.manager.AdViewSpreadManager;
import com.kyview.manager.AdViewVideoManager;

/**
 * 
 * 如果想使用开屏，请联系AdView客服
 * 
 */
public class SpreadScreenActivity extends Activity implements
		AdViewSpreadListener {
	public static InitConfiguration initConfiguration;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.spread_layout);
		//获取后台配置
		initConfiguration = new InitConfiguration.Builder(
				this).setUpdateMode(UpdateMode.EVERYTIME)
				.setBannerCloseble(BannerSwitcher.CANCLOSED)
				.setRunMode(RunMode.TEST)
				.build();
		//横幅 配置
		AdViewBannerManager.getInstance(this).init(initConfiguration,
				MainActivity.keySet);
		//插屏 配置
		AdViewInstlManager.getInstance(this).init(initConfiguration,
				MainActivity.keySet);
		//原生 配置
		AdViewNativeManager.getInstance(this).init(initConfiguration,
				MainActivity.keySet);
		//开屏 配置
		AdViewSpreadManager.getInstance(this).init(initConfiguration,
				MainActivity.keySet);
		AdViewVideoManager.getInstance(this).init(initConfiguration,
				MainActivity.keySet);

		// 设置开屏下方LOGO，必须调用该方法
		AdViewSpreadManager.getInstance(this).setSpreadLogo(R.drawable.spread_logo);
		// 设置开屏背景颜色，可不设置
		AdViewSpreadManager.getInstance(this).setSpreadBackgroudColor(
				Color.WHITE);
		// 设置开屏倒计时通知方式
		AdViewSpreadManager.getInstance(this).setSpreadNotifyType(AdViewSpreadManager.NOTIFY_COUNTER_NUM);
		// 请求开屏广告
		AdViewSpreadManager.getInstance(this).request(this, MainActivity.key1,
				(RelativeLayout) findViewById(R.id.spreadlayout), this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			return false;
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// waitingOnRestart 需要自己控制
		waitingOnRestart = true;
		jumpWhenCanClick();
	}

	public boolean waitingOnRestart = false;

	private void jump() {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		// if (null != adSpreadManager)
		// adSpreadManager.setAdSpreadInterface(null);
		this.finish();
	}

	/*
	 * 包含点击的开屏广告时会调用该方法广告
	 */
	private void jumpWhenCanClick() {
		if (this.hasWindowFocus() || waitingOnRestart) {
			this.startActivity(new Intent(this, MainActivity.class));
			// adSpreadManager.setAdSpreadInterface(null);
			this.finish();
		} else {
			waitingOnRestart = true;
		}

	}

	// /**
	// * 用户点击跳过事调用
	// */

	@Override
	public void onAdClose(String arg0) {
		jump();
	}

	@Override
	public void onAdDisplay(String arg0) {

	}

	@Override
	public void onAdFailed(String arg0) {
		jump();
	}

	@Override
	public void onAdRecieved(String arg0) {
		Toast.makeText(this, "onAdRecieved", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 用户必须设置 adSpreadManager.setSpreadNotifyType(this,
	 * AdSpreadManager.NOTIFY_CUSTOM); 方可回调该方法，否则不调用
	 * 
	 * @param view
	 *            返回顶部自定义布局（RelativeLayout）
	 * @param ruleTime
	 *            规定必须展示时间 适用于cpm&cpc 在规定时间内不可关闭，否则不计入数据
	 * @param delayTime
	 *            在延时时间内可以自由处理，延时时间到达后自动调用 onAdSpreadPrepareClosed 接口
	 */
	@Override
	public void onAdSpreadNotifyCallback(String arg0, ViewGroup arg1, int arg2,
			int arg3) {

	}

	@Override
	public void onAdClick(String arg0) {
		
	}
}
