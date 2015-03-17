//package com.owo.app.test;
//
//import android.app.PendingIntent;
//import android.appwidget.AppWidgetManager;
//import android.appwidget.AppWidgetProvider;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.media.AudioManager;
//import android.os.Debug;
//import android.util.Log;
//import android.widget.RemoteViews;
//
//import com.owo.app.R;
//
//public class MyWidgetProvider extends AppWidgetProvider {
//	public static int Tag;
//	public int max;
//	public int current;
//
//	@Override
//	public void onEnabled(Context context) {
//		// TODO Auto-generated method stub
//
//		super.onEnabled(context);
//	}
//
//	@Override
//	public void onReceive(Context context, Intent intent) {
//		// TODO Auto-generated method stub
//		super.onReceive(context, intent);
//		Debug.waitForDebugger();
//
//		ComponentName thisWidget = new ComponentName(context, MyWidgetProvider.class);
//		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget_ui);
//		AppWidgetManager appmanager = AppWidgetManager.getInstance(context);
//		Log.d("UPUP", intent.getAction());
//		auc(context, 0);
//		views.setProgressBar(R.id.widget_ProgressBar, max, current, false);
//		appmanager.updateAppWidget(thisWidget, views);
//		Tag = current;
//		if (intent.getAction().equals("zyf.test.widget.UP")) {
//
//			Tag += 1;
//			Log.d("tagdd", Integer.toString(Tag));
//
//			if (Tag > max) {
//				Tag = max;
//			}
//			auc(context, 1);
//			views.setProgressBar(R.id.widget_ProgressBar, max, Tag, false);
//			appmanager.updateAppWidget(thisWidget, views);
//		}
//		if (intent.getAction().equals("zyf.test.widget.DOWN")) {
//			Tag -= 1;
//			if (Tag < 0) {
//				Tag = 0;
//			}
//			auc(context, -1);
//			views.setProgressBar(R.id.widget_ProgressBar, max, Tag, false);
//			appmanager.updateAppWidget(thisWidget, views);
//		}
//	}
//
//	@Override
//	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//		// TODO Auto-generated method stub
//		final int N = appWidgetIds.length;
//		auc(context, 0);
//		Log.d("UPUP", "2222222");
//		// Perform this loop procedure for each App Widget that belongs to this
//		// provider
//		for (int i = 0; i < N; i++) {
//			int appWidgetId = appWidgetIds[i];
//			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget_ui);
//			Intent UPintent = new Intent("zyf.test.widget.UP");
//			Intent DOWNintent = new Intent("zyf.test.widget.DOWN");
//			// 实例化两个带有Action 的Intent
//			PendingIntent pendingIntentUp = PendingIntent.getBroadcast(context, 0, UPintent, 0);
//			PendingIntent pendingIntentDown = PendingIntent.getBroadcast(context, 0, DOWNintent, 0);
//			// 实例化两个以Intent 来构造的PendingIntent
//			views.setOnClickPendingIntent(R.id.widget_BT_Up, pendingIntentUp);
//			views.setOnClickPendingIntent(R.id.widget_BT_Down, pendingIntentDown);
//			// 给View 上的两个按钮绑定事件，这里是广播消息的发送
//			appWidgetManager.updateAppWidget(appWidgetId, views);
//		}
//	}
//
//	public void auc(Context context, int i) {
//		// TODO Auto-generated method stub
//		// ---开始音量处理,i=0 仅查询,i=其他的进行操作
//		AudioManager am = null;
//		am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//		max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//		current = am.getStreamVolume(AudioManager.STREAM_MUSIC);
//		Log.d("MUSIC", "max : " + max + "current : " + current);
//		current = current + i;
//		am.setStreamVolume(AudioManager.STREAM_MUSIC, current, 0);
//	}
//}