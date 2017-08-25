package edu.g26.util;

/**
 * This call is used to calculate the time interval between start and stop call
 * @author Sharath
 */
public class Timer {
    private long startTime;
    private long endTime;

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public long getTime() {
        if (startTime == 0) {
            return 0;
        }
        if (endTime == 0) {
            stop();
        }
        return (endTime - startTime) / 1000000;
    }

    public void clear() {
        startTime = endTime = 0;
    }
}
