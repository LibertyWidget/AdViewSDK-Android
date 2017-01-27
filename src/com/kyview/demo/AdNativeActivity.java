package com.kyview.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kyview.interfaces.AdViewNativeListener;
import com.kyview.manager.AdViewNativeManager;
import com.kyview.natives.NativeAdInfo;

public class AdNativeActivity extends Activity implements AdViewNativeListener {
	private ListView listView;
	public static String HTML = "<meta charset='utf-8'><style type='text/css'>* { padding: 0px; margin: 0px;}a:link { text-decoration: none;}</style><div  style='width: 100%; height: 100%;'><img src=\"image_path\" width=\"100%\" height=\"100%\" ></div>";
	private NativeAdAdapter adAdapter;
	private ArrayList<Data> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adnative);
		listView = (ListView) findViewById(R.id.list);
		list = new ArrayList<AdNativeActivity.Data>();
		for (int i = 0; i < 10; i++) {
			Data data = new Data();
			data.icon = "http://www.adview.cn/static/images/logo_1.png";
			data.title = "AdView,移动广告交易平台";
			data.descript = "AdView是国内首个“独立第三方”移动广告交易平台（AdExchange）";
			list.add(data);
		}

		adAdapter = new NativeAdAdapter(AdNativeActivity.this, list);

		listView.setAdapter(adAdapter);
		// listView.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// Data data = (Data) parent.getItemAtPosition(position);
		// if (data.isAd) {
		// data.adInfo.onClick(view);
		// }
		// }
		// });
		AdViewNativeManager.getInstance(this).requestAd(this,
				MainActivity.key3, 2, this);
	}

	@Override
	public void onAdFailed(String arg0) {

	}

	@Override
	public void onAdRecieved(String arg1, ArrayList arg0) {
		for (int i = 0; i < arg0.size(); i++) {
			Data data = new Data();
			NativeAdInfo nativeAdInfo = (NativeAdInfo) arg0.get(i);
			data.descript = nativeAdInfo.getDescription();
			data.icon = nativeAdInfo.getIconUrl();
			data.title = ((NativeAdInfo) arg0.get(i)).getTitle();
			data.adInfo = (NativeAdInfo) arg0.get(i);
			((NativeAdInfo) arg0.get(i)).getIconHeight();
			data.isAd = true;
			Log.i("原生信息：", "data.descript: " + data.descript + "\ndata.icon: "
					+ data.icon + "\ndata.title:" + data.title);
			list.add(3, data);
			((NativeAdInfo) arg0.get(i)).onDisplay(new View(
					AdNativeActivity.this));
		}
		adAdapter.notifyDataSetChanged();

		// ((NativeAdInfo) arg0.get(0)).onClick(new View(
		// AdNativeActivity.this));
	}

	@Override
	public void onAdStatusChanged(String arg0, int arg1) {

	}

	class NativeAdAdapter extends BaseAdapter {
		private List<Data> list;
		private Context context;

		public NativeAdAdapter(Context context, List<Data> list) {
			this.list = list;
			this.context = context;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (null != convertView) {
				holder = (ViewHolder) convertView.getTag();
			} else {
				holder = new ViewHolder();
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(R.layout.native_ad_item, parent,
						false);
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.webView = (WebView) convertView.findViewById(R.id.icon);
				holder.desc = (TextView) convertView.findViewById(R.id.desc);
				holder.logo = (TextView) convertView.findViewById(R.id.logo);
				convertView.setTag(holder);
			}
			holder.webView.setVerticalScrollBarEnabled(false);
			holder.webView.setHorizontalScrollBarEnabled(false);
			holder.webView.loadData((new String(HTML)).replace("image_path",
					list.get(position).icon), "text/html; charset=UTF-8", null);
			holder.title.setText(list.get(position).title);
			holder.desc.setText(list.get(position).descript);
			if (list.get(position).isAd)
				holder.logo.setVisibility(View.VISIBLE);
			else
				holder.logo.setVisibility(View.GONE);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Data data = list.get(position);
					if (data.isAd) {
						data.adInfo.onClick(v);
					}
				}
			});
			return convertView;
		}

		class ViewHolder {
			private WebView webView;
			private TextView title;
			private TextView desc;
			private TextView logo;
		}
	}

	class Data {
		private String icon;
		private String title;
		private String descript;
		public boolean isAd;
		public NativeAdInfo adInfo;
	}
}
