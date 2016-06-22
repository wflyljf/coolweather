package service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

public class AutoUpdateService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO 自动生成的方法存根
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	new Thread(new Runnable() {
	@Override
	public void run() {
	updateWeather();
	}
	}).start();
	AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
	int anHour = 8 * 60 * 60 * 1000; // 这是8小时的毫秒数
	long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
	Intent i = new Intent(this, AutoUpdateReceiver.class);
	PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
	manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
	return super.onStartCommand(intent, flags, startId);
	}

}
