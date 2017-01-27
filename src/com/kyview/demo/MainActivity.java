package com.kyview.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity {


	/**
	 * If you want more than one  banner placements
	 * you have to create same project project and get the key use for different placement
	 */
	public static final String key1 = "SDK2016093109051932rem7dipq42aj3";//SDK20151523030620lkfznoxeda4a7i3��SDK2016132801103979g4m0a2znmrt2a
	public static final String key2 = "SDK2016093109051932rem7dipq42aj3";//SDK20161629040641z7snyxkrbndasty
	public static final String key3 = "SDK2016093109051932rem7dipq42aj3";
	public static final String keySet[] = new String[] { key1, key2, key3 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);

		// ��ת������
		findViewById(R.id.banner).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								AdBannerActivity.class);
						startActivity(intent);
					}
				});
		// ��תȫ/�������
		findViewById(R.id.instl).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						AdInstlActivity.class);
				startActivity(intent);
			}
		});

		// ��תԭ�����
		findViewById(R.id.natives).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								AdNativeActivity.class);
						startActivity(intent);
					}
				});
		// ��תԭ�����
		findViewById(R.id.video).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								AdVideoActivity.class);
						startActivity(intent);
					}
				});

		// �汾����
		findViewById(R.id.release).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						LayoutInflater inflater = LayoutInflater
								.from(MainActivity.this);
						View view = inflater.inflate(R.layout.version_layout,
								null);
						final AlertDialog dialog = new AlertDialog.Builder(
								MainActivity.this).create();
						dialog.setView(view);
						dialog.show();
						Button ok = (Button) view.findViewById(R.id.close);
						ok.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								if (null != dialog)
									dialog.dismiss();
							}
						});

					}
				});
	}
}