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
 * �����ʹ�ÿ���������ϵAdView�ͷ�
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
		//��ȡ��̨����
		initConfiguration = new InitConfiguration.Builder(
				this).setUpdateMode(UpdateMode.EVERYTIME)
				.setBannerCloseble(BannerSwitcher.CANCLOSED)
				.setRunMode(RunMode.TEST)
				.build();
		//��� ����
		AdViewBannerManager.getInstance(this).init(initConfiguration,
				MainActivity.keySet);
		//���� ����
		AdViewInstlManager.getInstance(this).init(initConfiguration,
				MainActivity.keySet);
		//ԭ�� ����
		AdViewNativeManager.getInstance(this).init(initConfiguration,
				MainActivity.keySet);
		//���� ����
		AdViewSpreadManager.getInstance(this).init(initConfiguration,
				MainActivity.keySet);
		AdViewVideoManager.getInstance(this).init(initConfiguration,
				MainActivity.keySet);

		// ���ÿ����·�LOGO��������ø÷���
		AdViewSpreadManager.getInstance(this).setSpreadLogo(R.drawable.spread_logo);
		// ���ÿ���������ɫ���ɲ�����
		AdViewSpreadManager.getInstance(this).setSpreadBackgroudColor(
				Color.WHITE);
		// ���ÿ�������ʱ֪ͨ��ʽ
		AdViewSpreadManager.getInstance(this).setSpreadNotifyType(AdViewSpreadManager.NOTIFY_COUNTER_NUM);
		// ���������
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
		// waitingOnRestart ��Ҫ�Լ�����
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
	 * ��������Ŀ������ʱ����ø÷������
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
	// * �û���������µ���
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
