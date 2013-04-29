package cdlsy.bu.octorock;

import android.os.SystemClock;

//Returns the time in milliseconds since the object was created or reset

public class ElapsedTime {
	private long startTime;

	public ElapsedTime() {
		startTime = SystemClock.elapsedRealtime();
	};

	public long get() {
		return SystemClock.elapsedRealtime() - startTime;
	}

	public void reset() {
		startTime = SystemClock.elapsedRealtime();
	}

}
