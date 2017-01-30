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
 *Open Screen Advertisement
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

		//Basic Initialization
		InitConfiguration initConfiguration = new InitConfiguration.Builder(
				this).setUpdateMode(InitConfiguration.UpdateMode.EVERYTIME)
				.setBannerCloseble(InitConfiguration.BannerSwitcher.CANCLOSED)
				.setInstlControlMode(InitConfiguration.InstlControlMode.USERCONTROL)
				.setSupportHtml(InitConfiguration.Html5Switcher.SUPPORT)
				.setRunMode(InitConfiguration.RunMode.TEST)
				.build();


		//Intialization for Open Screen ad
		AdViewSpreadManager.getInstance(this).init(initConfiguration, new String[]{SDK_KEY});


		// setting logo for open screen ad
		AdViewSpreadManager.getInstance(this).setSpreadLogo(R.drawable.spread_logo);

		// setting background color for open screen ad
		AdViewSpreadManager.getInstance(this).setSpreadBackgroudColor(
				Color.WHITE);

		//how much time you want show open screen ad
		AdViewSpreadManager.getInstance(this).setSpreadNotifyType(AdViewSpreadManager.NOTIFY_COUNTER_NUM);

		// Requesting open screen ad
		AdViewSpreadManager.getInstance(this).request(this, MainActivity.SDK_KEY,
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
		// waitingOnRestart ��Ҫ�Լ�����
		waitingOnRestart = true;
		jumpWhenCanClick();
	}

	public boolean waitingOnRestart = false;

	//this function used for going to previous activity
	private void jump() {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		// if (null != adSpreadManager)
		// adSpreadManager.setAdSpreadInterface(null);
		this.finish();
	}


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
	// * CallBack Method for SpreadScreen/OpenScreen Ad
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
	 * �û��������� adSpreadManager.setSpreadNotifyType(this,
	 * AdSpreadManager.NOTIFY_CUSTOM); ���ɻص��÷��������򲻵���
	 * 
	 * @param view
	 *            ���ض����Զ��岼�֣�RelativeLayout��
	 * @param ruleTime
	 *            �涨����չʾʱ�� ������cpm&cpc �ڹ涨ʱ���ڲ��ɹرգ����򲻼�������
	 * @param delayTime
	 *            ����ʱʱ���ڿ������ɴ�����ʱʱ�䵽����Զ����� onAdSpreadPrepareClosed �ӿ�
	 */
	@Override
	public void onAdSpreadNotifyCallback(String arg0, ViewGroup arg1, int arg2,
			int arg3) {

	}

	@Override
	public void onAdClick(String arg0) {
		
	}
}
