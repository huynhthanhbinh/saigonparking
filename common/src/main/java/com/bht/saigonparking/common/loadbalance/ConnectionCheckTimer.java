package com.bht.saigonparking.common.loadbalance;

import java.util.Timer;

/**
 *
 * @author bht
 */
final class ConnectionCheckTimer {

    private final int pauseInSeconds;
    private final Timer timer;
    private final SaigonParkingNameResolver saigonParkingNameResolver;

    private ConnectionCheckTimerTask timerTask;

    public ConnectionCheckTimer(SaigonParkingNameResolver saigonParkingNameResolver, int pauseInSeconds) {
        this.saigonParkingNameResolver = saigonParkingNameResolver;
        this.pauseInSeconds = pauseInSeconds;
        timerTask = new ConnectionCheckTimerTask(this.saigonParkingNameResolver);
        timer = new Timer();
    }

    public void runTimer() {
        timer.scheduleAtFixedRate(timerTask, 1000, pauseInSeconds * 1000L);
    }

    public void reset() {
        timerTask.cancel();
        timer.purge();
        timerTask = new ConnectionCheckTimerTask(saigonParkingNameResolver);
    }
}